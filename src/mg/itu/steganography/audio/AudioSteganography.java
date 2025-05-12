package mg.itu.steganography.audio;

import mg.itu.steganography.sequence.RecurrenceSequence;

public class AudioSteganography {
    
    private final AudioProcessor processor;
    private final RecurrenceSequence sequence;

    public AudioSteganography(AudioProcessor processor, RecurrenceSequence sequence) {
        if (processor == null || sequence == null) {
            throw new IllegalArgumentException("processor and sequence cannot be null");
        }
        this.processor = processor;
        this.sequence = sequence;
    }

    public String extractMessage(int messageLength) {
        if (messageLength < 0) {
            throw new IllegalArgumentException("message length must be non-negative");
        }
        
        int[] positions = sequence.generateSequence(messageLength);
        StringBuilder message = new StringBuilder();
        System.out.println("Decoding: Max position = " + processor.getMaxPosition());

        for (int pos : positions) {
            int boundedPos = pos % processor.getMaxPosition();
            int byteValue = processor.getByteValue(boundedPos);
            int lsb = byteValue & 1;
            System.out.println("Decoding position " + boundedPos + ": Byte=" + byteValue + ", LSB=" + lsb);
            message.append(lsb == 1 ? '1' : '0');
        }

        return message.toString();
    }
}