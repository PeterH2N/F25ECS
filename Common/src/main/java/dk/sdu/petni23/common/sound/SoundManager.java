package dk.sdu.petni23.common.sound;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Vector;

class SoundManager {

    //private static javax.sound.sampled.Line.Info lineInfo;

    public static Vector<AudioFormat> afs = new Vector<>();
    public static Vector<Integer> sizes = new Vector<>();
    public static Vector<DataLine.Info> infos = new Vector<>();
    public static Vector<byte[]> audios = new Vector<>();
    public static int numSounds = 0;

    public static int addClip(String s)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        URL url = Objects.requireNonNull(SoundManager.class.getResource("/soundeffects/" + s));

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(loadStream(url.openStream()));
        AudioFormat af = audioInputStream.getFormat();
        int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
        byte[] audio = new byte[size];
        DataLine.Info info = new DataLine.Info(Clip.class, af, size);
        audioInputStream.read(audio, 0, size);

        afs.add(af);
        sizes.add(size);
        infos.add(info);
        audios.add(audio);

        numSounds++;
        return numSounds - 1;
    }

    private static ByteArrayInputStream loadStream(InputStream inputstream)
            throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte data[] = new byte[1024];
        for(int i = inputstream.read(data); i != -1; i = inputstream.read(data))
            bytearrayoutputstream.write(data, 0, i);

        inputstream.close();
        bytearrayoutputstream.close();
        data = bytearrayoutputstream.toByteArray();
        return new ByteArrayInputStream(data);
    }

    static Clip getClip(int x)
            throws LineUnavailableException
    {
        if(x > numSounds)
        {
            System.out.println("playSound: sample nr["+x+"] is not available");
            return null;
        }
        else
        {
            Clip clip = (Clip) AudioSystem.getLine(infos.elementAt(x));
            clip.addLineListener(lineEvent -> {
                if (lineEvent.getType() == LineEvent.Type.STOP)
                    clip.close();
            });
            clip.open(afs.elementAt(x), audios.elementAt(x), 0, sizes.elementAt(x));
            return clip;
        }
    }
}
