package com.trnka.trnkadevice.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlaySound {
    private static boolean tryToInterruptSound = false;
    private static long mainTimeOut = 1000;

    private static long startTime = System.currentTimeMillis();

    public void playSoundInNewThread(final String filename) {
        Thread soundThread = playSoundInNewThread(new File(filename));
    }

    public void playSoundBlocking(final String filename) {
        Thread soundThread = playSoundInNewThread(new File(filename));


        try {
            Thread.sleep(mainTimeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (tryToInterruptSound) {
            try {
                soundThread.interrupt();
                Thread.sleep(1);
                // Sleep in order to let the interruption handling end before
                // exiting the program (else the interruption could be handled
                // after the main thread ends!).
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("" + (System.currentTimeMillis() - startTime) + ": End of main thread; exiting program "
                + (soundThread.isAlive() ? "killing the sound deamon thread"
                                         : ""));
    }


    public synchronized void playInMainThreadBlocking(final String filename) {
        File file = new File(filename);
        try {
                Clip clip;
                AudioInputStream inputStream;
                clip = AudioSystem.getClip();
                inputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = inputStream.getFormat();
                long audioFileLength = file.length();
                int frameSize = format.getFrameSize();
                float frameRate = format.getFrameRate();
                long durationInMilliSeconds = (long) (((float) audioFileLength / (frameSize * frameRate)) * 1000);

                clip.open(inputStream);
                clip.start();
                log.info("" + (System.currentTimeMillis() - startTime) + ": sound started playing!");
                Thread.sleep(durationInMilliSeconds);
                while (true) {
                    if (!clip.isActive()) {
                        log.info("" + (System.currentTimeMillis() - startTime) + ": sound got to it's end!");
                        break;
                    }
                    long fPos = (long) (clip.getMicrosecondPosition() / 1000);
                    long left = durationInMilliSeconds - fPos;
                    log.info("" + (System.currentTimeMillis() - startTime) + ": time left: " + left);
                    if (left > 0)
                        Thread.sleep(left);
                }
                clip.stop();
                log.info("" + (System.currentTimeMillis() - startTime) + ": sound stopped");
                clip.close();
                inputStream.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                log.error("" + (System.currentTimeMillis() - startTime) + ": sound interrupted while playing.");
            }

    }

    private synchronized Thread playSoundInNewThread(final File file) {

        Thread soundThread = new Thread(() -> {
            try {
                Clip clip;
                AudioInputStream inputStream;
                clip = AudioSystem.getClip();
                inputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = inputStream.getFormat();
                long audioFileLength = file.length();
                int frameSize = format.getFrameSize();
                float frameRate = format.getFrameRate();
                long durationInMilliSeconds = (long) (((float) audioFileLength / (frameSize * frameRate)) * 1000);

                clip.open(inputStream);
                clip.start();
                log.info("" + (System.currentTimeMillis() - startTime) + ": sound started playing!");
                Thread.sleep(durationInMilliSeconds);
                while (true) {
                    if (!clip.isActive()) {
                        log.info("" + (System.currentTimeMillis() - startTime) + ": sound got to it's end!");
                        break;
                    }
                    long fPos = (long) (clip.getMicrosecondPosition() / 1000);
                    long left = durationInMilliSeconds - fPos;
                    log.info("" + (System.currentTimeMillis() - startTime) + ": time left: " + left);
                    if (left > 0)
                        Thread.sleep(left);
                }
                clip.stop();
                log.info("" + (System.currentTimeMillis() - startTime) + ": sound stopped");
                clip.close();
                inputStream.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                log.error("" + (System.currentTimeMillis() - startTime) + ": sound interrupted while playing.");
            }
        });
        soundThread.setDaemon(true);
        soundThread.start();
        return soundThread;
    }
}
