package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.ui.messages.Messages;
import org.springframework.beans.factory.annotation.Value;

import com.trnka.trnkadevice.sound.PlaySound;
import com.trnka.trnkadevice.ui.Renderable;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AudioRenderer implements IRenderer {

    @Value("${path.sounds}")
    private String soundsLocation;

    // https://stackoverflow.com/questions/27283729/how-to-play-sound-in-java
    @Override
    public void renderMessage(final Renderable renderable) {
        String messageText = renderable.getMessage().getText();
        log.info(">>> " + messageText + " <<<", renderable.getParams());
        RenderableToAudioPlaylistCompiler compiler = new RenderableToAudioPlaylistCompiler();
        List<String> playlist = compiler.compileToPlaylist(renderable);

        List<String> fullFilepathPlaylist = playlist.stream().map(this :: getFullFilepath).collect(Collectors.toList());
        PlaySound playSound = new PlaySound();

        fullFilepathPlaylist.forEach(audioFilepath -> playSound.playInMainThreadBlocking(audioFilepath));
    }



    @Override public void renderMessages(final List<Messages> messagesList) {
        playAllParams(messagesList, new PlaySound());
    }

    private void playAllParams(List<Messages> params, PlaySound playSound){
        params.stream().forEach(p -> {
            String fullFilePath = getFullFilepath(p.getAudioFile());
            playSound.playInMainThreadBlocking(fullFilePath);
        });
    }

    private String getFullFilepath(String filename) {
        return String.format("%s\\%s", soundsLocation, filename);
    }

    public static void main(String[] args) {
        String msg = "{} adalsd{}{} asdasd";
        String[] split = msg.split("\\{\\}");
        System.out.println(split.length);
        for (String s : split){
            System.out.println("param: " + s);
        }
    }

}
