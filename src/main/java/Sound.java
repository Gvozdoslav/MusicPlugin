import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

public class Sound {
    private Clip clip = null;
    private byte[] bytes;
    private int loopsNumber = 0;

    public Sound(byte[] byteArray) {
        this.bytes = byteArray;
    }

    public Sound(byte[] byteArray, int loopsNumber) {
        this.bytes = byteArray;
        this.loopsNumber = loopsNumber;
    }

    public void play() {
        playSoundFromStream(new ByteArrayInputStream(bytes), loopsNumber);
    }

    private void playSoundFromStream(final InputStream inputStream, int loopsNumber) {
        try {
            Clip clip = AudioSystem.getClip();
            InputStream stream = inputStream;
            if (!stream.markSupported()) stream = new BufferedInputStream(stream);
            clip.open(AudioSystem.getAudioInputStream(stream));
            AtomicReference<LineListener> lineListener = new AtomicReference<>();
            LineListener listener = event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    clip.removeLineListener(lineListener.get());
                }
            };
            lineListener.set(listener);
            clip.addLineListener(lineListener.get());
            this.clip = clip;

            new Thread(() ->
                    clip.loop(loopsNumber))
                    .start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}