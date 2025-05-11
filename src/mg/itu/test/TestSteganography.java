package mg.itu.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import mg.itu.steganography.ImageProcessor;
import mg.itu.steganography.ImageSteganography;
import mg.itu.steganography.ImageSteganographyEncoder;
import mg.itu.steganography.RecurrenceSequence;

public class TestSteganography {
    
    public static boolean testSteganography(String inputImagePath, String outputImagePath, String messageToEncode) {
        try {
            // create a blank test image if none provided
            if (inputImagePath == null || inputImagePath.isEmpty()) {
                inputImagePath = "blank_test_image.png";
                createBlankTestImage(inputImagePath, 2, 2);
            }
            if (outputImagePath == null || outputImagePath.isEmpty()) {
                outputImagePath = "encoded_test_image.png";
            }
            if (messageToEncode == null || messageToEncode.isEmpty()) {
                messageToEncode = "1010"; // default test message
            }
            if (!messageToEncode.matches("[01]+")) {
                throw new IllegalArgumentException("message must be a binary string");
            }

            // setup recurrence sequence: Un+1 = (2Un + 1) % 4, U0 = 0
            RecurrenceSequence sequence = new RecurrenceSequence(2, 1, 4, 0);
            ImageProcessor processor = new ImageProcessor();
            processor.loadImage(inputImagePath);

            // encode message
            ImageSteganographyEncoder encoder = new ImageSteganographyEncoder(processor, sequence);
            encoder.encodeMessage(messageToEncode, outputImagePath);

            // decode message from encoded image
            ImageProcessor decodeProcessor = new ImageProcessor();
            decodeProcessor.loadImage(outputImagePath);
            ImageSteganography decoder = new ImageSteganography(decodeProcessor, sequence);
            String decodedMessage = decoder.extractMessage(messageToEncode.length());

            // debug: print sequence and messages
            int[] positions = sequence.generateSequence(messageToEncode.length());
            System.out.println("Recurrence sequence positions: " + Arrays.toString(positions));
            System.out.println("Encoded message: " + messageToEncode);
            System.out.println("Decoded message: " + decodedMessage);

            // verify message length and content
            return decodedMessage.length() == messageToEncode.length() &&
                   decodedMessage.equals(messageToEncode);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static void createBlankTestImage(String filePath, int width, int height) throws Exception {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, 0xFFFFFF); // white pixels (red=255)
            }
        }
        ImageIO.write(image, "png", new File(filePath));
    }

    public static void main(String[] args) {
        // test with user image
        System.out.println("\n=== Testing with User Image ===");
        String userImage = "/home/ny-haritina/Documents/Studies/ITU/S6/INF-310_Codage/_tp-final-codage/assets/img/black-white.jpg";
        String userOutputImage = "/home/ny-haritina/Documents/Studies/ITU/S6/INF-310_Codage/_tp-final-codage/output/output.png";
        boolean userImageResult = testSteganography(userImage, userOutputImage, "0011"); // match previous output
        System.out.println("User Image Test: " + (userImageResult ? "PASSED" : "FAILED"));
    }
}