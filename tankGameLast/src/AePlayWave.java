
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AePlayWave extends Thread {
    private String filename;

    public AePlayWave(String wavfile) {
        this.filename = wavfile;
    }

    public void run() {
        File soundFile = new File(filename);
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip audioClip;
        try {
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioInputStream);
            //audioClip.loop(Clip.LOOP_CONTINUOUSLY); 
            // 保持线程运行，直到播放结束
            //Thread.sleep(audioClip.getMicrosecondLength() / 1000);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
            return;
        }

        audioClip.start();

        try {
            Thread.sleep(audioClip.getMicrosecondLength() / 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        audioClip.close();
    }

}