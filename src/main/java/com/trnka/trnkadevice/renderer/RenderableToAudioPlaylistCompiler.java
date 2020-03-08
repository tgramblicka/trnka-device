package com.trnka.trnkadevice.renderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RenderableToAudioPlaylistCompiler {

    public List<String> compileToPlaylist(Renderable renderable) {
        if (renderable.getMessage().getParameterCount() != renderable.getParams().size()) {
            return compileWithDifferentParamCount(renderable);
        } else {
            return compileWithEqualParamCount(renderable);
        }
    }

    private List<String> compileWithDifferentParamCount(final Renderable renderable) {
        long messageParamCount = renderable.getMessage().getParameterCount();
        int inputParamCount = renderable.getParams().size();
        if (messageParamCount > inputParamCount) {
            String msg = "Count of provided params is LESS than params in message template!";
            log.error(msg);
            throw new MessageTemplateToPlaylistCompilerException(msg);
        }
        if (renderable.getMessage().getParameterCount() > 1) {
            String msg = "When multiple params provided, message template MUST not have more than 1 parameter!";
            log.error(msg);
            throw new MessageTemplateToPlaylistCompilerException(msg);
        }

        List<String> paramAudioFiles = renderable.getParams().stream().map(Messages::getAudioFile).collect(Collectors.toList());

        List<String> audioFiles = new ArrayList<>();
        String[] split = renderable.getMessage().split();

        int n = 1;
        for (String s : split) {
            if (StringUtils.isEmpty(s)) {
                audioFiles.addAll(paramAudioFiles);
                paramAudioFiles.clear();
            } else {
                if (renderable.getMessage().isMessageSplitToMultipleParts()) {
                    audioFiles.add(renderable.getMessage().getNthPartOfAudioFile(n));
                    n++;
                } else {
                    audioFiles.add(renderable.getMessage().getAudioFile());
                }
                audioFiles.addAll(paramAudioFiles);
                paramAudioFiles.clear();
            }
        }
        return audioFiles;
    }

    private List<String> compileWithEqualParamCount(final Renderable renderable) {
        LinkedList<Messages> queue = new LinkedList<>();
        queue.addAll(renderable.getParams());

        List<String> audioFiles = new ArrayList<>();
        String[] split = renderable.getMessage().split();

        int n = 1;
        for (String s : split) {
            if (StringUtils.isEmpty(s)) {
                audioFiles.add(queue.poll().getAudioFile());
            } else {
                if (renderable.getMessage().isMessageSplitToMultipleParts()) {
                    audioFiles.add(renderable.getMessage().getNthPartOfAudioFile(n));
                    n++;
                    if (!queue.isEmpty()) {
                        audioFiles.add(queue.poll().getAudioFile());
                    }
                } else {
                    audioFiles.add(renderable.getMessage().getAudioFile());
                }
            }
        }
        queue.forEach(param -> audioFiles.add(param.getAudioFile()));
        return audioFiles;
    }
}
