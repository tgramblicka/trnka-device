package com.trnka.trnkadevice.sync;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trnka.restapi.dto.BrailCharacterDto;
import com.trnka.restapi.dto.ExaminationDto;
import com.trnka.restapi.dto.ExaminationStepDto;
import com.trnka.restapi.dto.SequenceType;
import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncDto;

public class SyncDataFactory {

    public static SyncDto getSyncData() throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(ResourceUtils.getFile("classpath:sync.json"), SyncDto.class);
    }

    // todo : delete
    public static SyncDto createSyncData() {
        SyncDto dto = new SyncDto();
        dto.setStudents(Stream.of(createStudent(1L)).collect(Collectors.toList()));
        dto.setExaminations(Stream.of(createExamination(10L)).collect(Collectors.toList()));

        return dto;
    }

    private static StudentDTO createStudent(Long id){
        StudentDTO dto = new StudentDTO();
        dto.setUserName("username");
        dto.setId(id);
        dto.setCode("0000");
        dto.setExaminationIds(Stream.of(10L,11L,12L).collect(Collectors.toSet()));
        return dto;
    }

    private static ExaminationDto createExamination(Long id){
        ExaminationDto dto = new ExaminationDto();
        dto.setId(id);
        dto.setTimeout(1000L);
        dto.setName("???");
        dto.setSteps(Stream.of(createStep(1L,"a")).collect(Collectors.toList()));
        dto.setAllowedRetries(2);
        dto.setType(SequenceType.TESTING);
        return dto;
    }

    private static ExaminationStepDto createStep(Long id, String brail){
        ExaminationStepDto dto = new ExaminationStepDto();
        dto.setPreserveOrder(true);
        dto.setBrailCharacter(new BrailCharacterDto(brail));
        return dto;
    }
}


