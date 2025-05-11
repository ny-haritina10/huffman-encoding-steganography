package mg.itu.steganography.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import mg.itu.steganography.sequence.RecurrenceSequence;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class AudioSteganographyEncoder {
    
    private final AudioProcessor processor;
    private final RecurrenceSequence sequence;

    public AudioSteganographyEncoder(AudioProcessor processor, RecurrenceSequence sequence) {
        if (processor == null || sequence == null) {
            throw new IllegalArgumentException("processor and sequence cannot be null");
        }
        this.processor = processor;
        this.sequence = sequence;
    }

    public void encodeMessage(String message, String outputFilePath) throws IOException {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("message cannot be null or empty");
        }
        if (!message.matches("[01]+")) {
            throw new IllegalArgumentException("message must be a binary string");
        }
        if (outputFilePath == null || outputFilePath.isEmpty()) {
            throw new IllegalArgumentException("output file path cannot be null or empty");
        }

        int[] positions = sequence.generateSequence(message.length());
        System.out.println("Encoding: Max position = " + processor.getMaxPosition());

        // encode message by setting LSBs
        for (int i = 0; i < message.length(); i++) {
            int pos = positions[i];
            int boundedPos = pos % processor.getMaxPosition();
            int byteValue = processor.getByteValue(boundedPos);
            int bit = message.charAt(i) == '1' ? 1 : 0;
            System.out.println("Encoding position " + boundedPos + ": Original byte=" + byteValue + ", Bit to set=" + bit);
            byteValue = (byteValue & 0xFE) | bit; // set LSB
            processor.setByteValue(boundedPos, byteValue);
            System.out.println("After encoding: Byte=" + processor.getByteValue(boundedPos) + ", LSB=" + (processor.getByteValue(boundedPos) & 1));
        }

        // save modified audio
        byte[] audioData = processor.getAudioData();
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false); // default format
        try (AudioInputStream ais = new AudioInputStream(
                new ByteArrayInputStream(audioData), format, audioData.length / format.getFrameSize())) {
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(outputFilePath));
        }
    }
}