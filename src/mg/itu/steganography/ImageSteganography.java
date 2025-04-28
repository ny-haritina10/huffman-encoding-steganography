package mg.itu.steganography;

public class ImageSteganography {

    private final ImageProcessor processor;
    private final RecurrenceSequence sequence;

    public ImageSteganography(ImageProcessor processor, RecurrenceSequence sequence) {
        if (processor == null || sequence == null) 
        { throw new IllegalArgumentException("processor and sequence cannot be null"); }

        this.processor = processor;
        this.sequence = sequence;
    }

    public String extractMessage(int messageLength) {
        if (messageLength < 0) 
        { throw new IllegalArgumentException("message length must be non-negative"); }

        int[] positions = sequence.generateSequence(messageLength);
        StringBuilder message = new StringBuilder();

        for (int pos : positions) {
            // ensure position is within image bounds
            int boundedPos = pos % processor.getMaxPosition();
            int pixelValue = processor.getPixelValue(boundedPos);

            // extract lsb as message bit
            message.append((pixelValue & 1) == 1 ? '1' : '0');
        }

        return message.toString();
    }
}