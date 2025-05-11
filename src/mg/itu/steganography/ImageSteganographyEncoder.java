package mg.itu.steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageSteganographyEncoder {

    private final ImageProcessor processor;
    private final RecurrenceSequence sequence;

    public ImageSteganographyEncoder(ImageProcessor processor, RecurrenceSequence sequence) {
        if (processor == null || sequence == null) {
            throw new IllegalArgumentException("processor and sequence cannot be null");
        }
        this.processor = processor;
        this.sequence = sequence;
    }

    public void encodeMessage(String message, String outputFilePath) 
        throws IOException 
    {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("message cannot be null or empty");
        }
        if (!message.matches("[01]+")) {
            throw new IllegalArgumentException("message must be a binary string");
        }
        if (outputFilePath == null || outputFilePath.isEmpty()) {
            throw new IllegalArgumentException("output file path cannot be null or empty");
        }

        // generate positions for message length
        int[] positions = sequence.generateSequence(message.length());
        
        // create a copy of the image to modify
        BufferedImage image = new BufferedImage(
            processor.getMaxPosition() % processor.getMaxPosition() == 0 ? 
                processor.getMaxPosition() / processor.getMaxPosition() : 
                processor.getMaxPosition(),
            processor.getMaxPosition() / (processor.getMaxPosition() % processor.getMaxPosition() == 0 ? 
                processor.getMaxPosition() / processor.getMaxPosition() : 
                processor.getMaxPosition()),
            BufferedImage.TYPE_INT_RGB
        );
        
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pos = y * image.getWidth() + x;
                image.setRGB(x, y, processor.getPixelValue(pos) << 16);
            }
        }

        // encode message by setting LSBs of red channel
        for (int i = 0; i < message.length(); i++) {
            int pos = positions[i];
            int boundedPos = pos % processor.getMaxPosition();
            int x = boundedPos % image.getWidth();
            int y = boundedPos / image.getWidth();
            
            // get current pixel RGB
            int rgb = image.getRGB(x, y);
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;

            // set LSB of red channel to message bit
            int bit = message.charAt(i) == '1' ? 1 : 0;
            red = (red & 0xFE) | bit; // clear LSB and set to bit

            // update pixel
            int newRGB = (red << 16) | (green << 8) | blue;
            image.setRGB(x, y, newRGB);
        }

        // save modified image
        File outputFile = new File(outputFilePath);
        ImageIO.write(image, "png", outputFile);
    }
}