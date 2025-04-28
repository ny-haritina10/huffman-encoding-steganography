package mg.itu.test;

import mg.itu.steganography.ImageProcessor;
import mg.itu.steganography.ImageSteganography;
import mg.itu.steganography.RecurrenceSequence;

public class TestSteganography {

    public static boolean testSteganography(String image) {
        try {
            // setup recurrence sequence: Un+1 = (2Un + 1) % 4, U0 = 0
            RecurrenceSequence sequence = new RecurrenceSequence(2, 1, 4, 0);
            ImageProcessor processor = new ImageProcessor();
            processor.loadImage(image);

            // extract message of length 4
            ImageSteganography stego = new ImageSteganography(processor, sequence);
            String message = stego.extractMessage(4);

            // expected message: "1010" (LSBs of pixels at positions 0,1,2,3)
            return message.equals("1010");
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String image = "D:\\Studies\\ITU\\S6\\INF-310_Codage\\_tp-final-codage\\assets\\img\\black-white.jpg";

        boolean steganography = testSteganography(image);
        if (steganography) 
        { System.out.println("Test Steganography: PASSED"); }

        else 
        { System.out.println("Test Steganography: FAILED"); }
    }
}