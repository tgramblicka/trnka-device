package com.trnka.trnkadevice.service.sync;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.trnka.restapi.dto.SequenceType;
import com.trnka.restapi.dto.statistics.DeviceStatisticsSyncDto;
import com.trnka.restapi.dto.statistics.ExaminationStatisticDto;
import com.trnka.restapi.dto.statistics.ExaminationStepStatisticDto;
import com.trnka.restapi.dto.statistics.StudentDeviceStatisticsDto;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.domain.visitor.SequenceVisitor;
import com.trnka.trnkadevice.ui.messages.Messages;



public class StatsMapper {

    public DeviceStatisticsSyncDto mapToStatisticsSyncDto(final List<SequenceStatistic> sequencStats, final String deviceId){
        Map<String, StudentDeviceStatisticsDto> map = new HashMap<>(); // key = studentCode
        for (SequenceStatistic stat : sequencStats) {
            User user = stat.getUser();
            if (map.get(user.getCode()) == null ) {
                StudentDeviceStatisticsDto studentDto = new StudentDeviceStatisticsDto();
                studentDto.setLoginCount(user.getLoginCount());
                studentDto.setStudentCode(user.getCode());
                studentDto.getStatistics().add(mapToExaminationStatistic(stat));

                map.put(user.getCode(), studentDto);
            } else {
                StudentDeviceStatisticsDto studentDto = map.get(user.getCode());
                studentDto.getStatistics().add(mapToExaminationStatistic(stat));
            }
        }
        DeviceStatisticsSyncDto syncDto = new DeviceStatisticsSyncDto();
        syncDto.setStatistics(map.values().stream().collect(Collectors.toList()));
        syncDto.setDeviceId(deviceId);
        return syncDto;
    }

    private ExaminationStatisticDto mapToExaminationStatistic(SequenceStatistic stat){
        Sequence sequence = stat.getSequence();
        ExaminationStatisticDto examinationStatDto = new ExaminationStatisticDto();
        examinationStatDto.setLetterSequence(sequence.getAllStepsAsMessagesList().stream().map(Messages ::getText).collect(Collectors.joining(",")));
        examinationStatDto.setExaminationId(sequence.getExternalId());
        examinationStatDto.setFinishedOn(stat.getCreatedOn());
        examinationStatDto.setPassed(stat.getPassed());
        examinationStatDto.setSequenceType(sequence.accept(new SequenceVisitorImpl()));
        examinationStatDto.setTotalTimeInMs(stat.getTook());

        stat.getStepStats().forEach(stepStat -> {
            ExaminationStepStatisticDto stepStatDto = new ExaminationStepStatisticDto();
            stepStatDto.setExaminationStepId(stepStat.getStep().getExternalId());
            stepStatDto.setCorrect(stepStat.isCorrect());
            stepStatDto.setDurationInMs(stepStat.getTook());
            stepStatDto.setLetter(stepStat.getStep().getBrailCharacter().getLetter());
            stepStatDto.setRetries(stepStat.getRetries());
            examinationStatDto.getStepStatistics().add(stepStatDto);
        });
        return  examinationStatDto;
    }

    private final static class SequenceVisitorImpl implements SequenceVisitor<SequenceType> {

        @Override public SequenceType visit(final MethodicalLearningSequence seq) {
            return SequenceType.METHODICAL;
        }

        @Override public SequenceType visit(final TestingSequence seq) {
            return SequenceType.TESTING;
        }

        @Override public SequenceType visit(final LearningSequence seq) {
            return SequenceType.LEARNING;
        }
    }
}
