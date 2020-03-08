package com.trnka.trnkadevice;

import com.trnka.trnkadevice.renderer.RenderableToAudioPlaylistCompiler;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RenderableToAudioPlaylistCompilerTest {
    private static final RenderableToAudioPlaylistCompiler PLAYLIST_CREATOR = new RenderableToAudioPlaylistCompiler();

    @Test
    public void testNoParam() {
        AudioMessage msg = AudioMessage.of(Messages.TEST0);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertEquals(1, playlist.size());
        Assert.assertEquals(Messages.TEST0.getAudioFile(), playlist.get(0));
    }

    @Test
    public void testStartsWithParam() {
        AudioMessage msg = AudioMessage.of(Messages.TEST1, Messages.ONE);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertEquals(2, playlist.size());
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.TEST1.getAudioFile(), playlist.get(1));
    }

    @Test
    public void testEndsWithParam() {
        AudioMessage msg = AudioMessage.of(Messages.TEST2, Messages.ONE);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertEquals(2, playlist.size());
        Assert.assertEquals(Messages.TEST2.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(1));
    }

    @Test
    public void testStartsWith2ParamsAndEndsWith2Params() {
        AudioMessage msg = AudioMessage.of(Messages.TEST3, Messages.ONE, Messages.TWO, Messages.THREE, Messages.FOUR);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertFalse(Messages.TEST3.isMessageSplitToMultipleParts());
        Assert.assertEquals(5, playlist.size());
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.TWO.getAudioFile(), playlist.get(1));
        Assert.assertEquals(Messages.TEST3.getAudioFile(), playlist.get(2));
        Assert.assertEquals(Messages.THREE.getAudioFile(), playlist.get(3));
        Assert.assertEquals(Messages.FOUR.getAudioFile(), playlist.get(4));
    }

    @Test
    public void testStartsWith2ParamsAndEndsWith2ParamsAndHasParamInMiddle() {
        AudioMessage msg = AudioMessage.of(Messages.TEST4, Messages.ONE, Messages.TWO, Messages.THREE, Messages.FOUR, Messages.FIVE);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertTrue(Messages.TEST4.isMessageSplitToMultipleParts());
        Assert.assertEquals(7, playlist.size());
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.TWO.getAudioFile(), playlist.get(1));
        Assert.assertEquals(Messages.TEST4.getNthPartOfAudioFile(1), playlist.get(2));
        Assert.assertEquals(Messages.THREE.getAudioFile(), playlist.get(3));
        Assert.assertEquals(Messages.TEST4.getNthPartOfAudioFile(2), playlist.get(4));
        Assert.assertEquals(Messages.FOUR.getAudioFile(), playlist.get(5));
        Assert.assertEquals(Messages.FIVE.getAudioFile(), playlist.get(6));
    }

    @Test
    public void testStartsWith2ParamsAndEndsWith2ParamsAndHasDoubleParamInMiddle() {
        AudioMessage msg = AudioMessage.of(Messages.TEST5, Messages.ONE, Messages.TWO, Messages.THREE, Messages.FOUR, Messages.FIVE, Messages.SIX);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertTrue(Messages.TEST5.isMessageSplitToMultipleParts());
        Assert.assertEquals(8, playlist.size());
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.TWO.getAudioFile(), playlist.get(1));
        Assert.assertEquals(Messages.TEST5.getNthPartOfAudioFile(1), playlist.get(2));
        Assert.assertEquals(Messages.THREE.getAudioFile(), playlist.get(3));
        Assert.assertEquals(Messages.FOUR.getAudioFile(), playlist.get(4));
        Assert.assertEquals(Messages.TEST5.getNthPartOfAudioFile(2), playlist.get(5));
        Assert.assertEquals(Messages.FIVE.getAudioFile(), playlist.get(6));
        Assert.assertEquals(Messages.SIX.getAudioFile(), playlist.get(7));
    }

    @Test
    public void testMoreParamsProvidedAndEndsWithOneParamInMessage() {
        AudioMessage msg = AudioMessage.of(Messages.TEST6, Messages.ONE, Messages.TWO);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertEquals(3, playlist.size());
        Assert.assertEquals(Messages.TEST6.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(1));
        Assert.assertEquals(Messages.TWO.getAudioFile(), playlist.get(2));
    }

    @Test
    public void testMoreParamsProvidedAndStartsWithOneParamInMessage() {
        AudioMessage msg = AudioMessage.of(Messages.TEST7, Messages.ONE, Messages.TWO);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertEquals(3, playlist.size());
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(0));
        Assert.assertEquals(Messages.TWO.getAudioFile(), playlist.get(1));
        Assert.assertEquals(Messages.TEST7.getAudioFile(), playlist.get(2));
    }

    @Test
    public void testMoreParamsProvidedAndMessageHasOneParamInMiddle() {
        AudioMessage msg = AudioMessage.of(Messages.TEST8, Messages.ONE, Messages.TWO);
        List<String> playlist = PLAYLIST_CREATOR.compileToPlaylist(msg);

        Assert.assertEquals(4, playlist.size());
        Assert.assertEquals(Messages.TEST8.getNthPartOfAudioFile(1), playlist.get(0));
        Assert.assertEquals(Messages.ONE.getAudioFile(), playlist.get(1));
        Assert.assertEquals(Messages.TWO.getAudioFile(), playlist.get(2));
        Assert.assertEquals(Messages.TEST8.getNthPartOfAudioFile(2), playlist.get(3));
    }

    @Test(expected = RuntimeException.class)
    public void testParamCountsNotEqualAndMessageTemplateHasMoreThanOneParam() {
        AudioMessage msg = AudioMessage.of(Messages.TEST9, Messages.ONE, Messages.TWO, Messages.THREE);
        PLAYLIST_CREATOR.compileToPlaylist(msg);
    }

    @Test(expected = RuntimeException.class)
    public void testMoreMessageTemplateParamsThanInputParams() {
        AudioMessage msg = AudioMessage.of(Messages.TEST9, Messages.ONE);
        PLAYLIST_CREATOR.compileToPlaylist(msg);
    }

}
