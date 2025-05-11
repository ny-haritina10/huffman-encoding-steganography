package mg.itu.steganography.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AudioProcessor {

    private byte[] audioData;
    private int dataLength;

    private static final int HEADER_SIZE = 44;

    public void loadAudio(String filePath) 
        throws IOException 
    {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("audio file does not exist");
        }

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(file)) {
            AudioFormat format = ais.getFormat();
            // validate wav format
            if (!format.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
                throw new IOException("audio must be PCM format");
            }

            // read audio data
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = ais.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            audioData = baos.toByteArray();
            dataLength = audioData.length;
        } catch (Exception e) {
            throw new IOException("failed to load audio: " + e.getMessage());
        }
    }

    public int getByteValue(int position) {
        position += HEADER_SIZE;
        if (position < HEADER_SIZE || position >= dataLength) {
            throw new IllegalArgumentException("invalid byte position: " + position);
        }
        return audioData[position] & 0xFF;
    }

    public void setByteValue(int position, int value) {
        position += HEADER_SIZE;
        if (position < HEADER_SIZE || position >= dataLength) {
            throw new IllegalArgumentException("invalid byte position: " + position);
        }
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("byte value must be 0-255");
        }
        audioData[position] = (byte) value;
    }

    public byte[] getAudioData() {
        return audioData;
    }
    
    public int getMaxPosition() {
        return dataLength - HEADER_SIZE;
    }
}