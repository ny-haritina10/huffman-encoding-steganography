package mg.itu.test;

import mg.itu.steganography.audio.AudioProcessor;
import mg.itu.steganography.audio.AudioSteganography;
import mg.itu.steganography.audio.AudioSteganographyEncoder;
import mg.itu.steganography.sequence.RecurrenceSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestAudioSteganography {
    public static boolean testAudioSteganography(String inputAudioPath, String outputAudioPath, String messageToEncode) {
        try {
            // setup recurrence sequence: Un+1 = (Un + 1) mod 8, U0 = 0
            RecurrenceSequence sequence = new RecurrenceSequence(1, 1, 8, 0); // produces [0, 1, 2, 3]
            int[] positions = sequence.generateSequence(messageToEncode.length());

            // check for duplicate positions
            Set<Integer> uniquePositions = new HashSet<>();
            for (int pos : positions) {
                uniquePositions.add(pos % 441000); // use approximate max position
            }
            if (uniquePositions.size() < positions.length) {
                throw new IllegalStateException("recurrence sequence contains duplicate positions: " + Arrays.toString(positions));
            }

            AudioProcessor processor = new AudioProcessor();
            processor.loadAudio(inputAudioPath);

            // encode message
            AudioSteganographyEncoder encoder = new AudioSteganographyEncoder(processor, sequence);
            encoder.encodeMessage(messageToEncode, outputAudioPath);

            // decode message
            AudioProcessor decodeProcessor = new AudioProcessor();
            decodeProcessor.loadAudio(outputAudioPath);
            AudioSteganography decoder = new AudioSteganography(decodeProcessor, sequence);
            String decodedMessage = decoder.extractMessage(messageToEncode.length());

            // debug: print sequence and messages
            System.out.println("Recurrence sequence positions: " + Arrays.toString(positions));
            System.out.println("Encoded message: " + messageToEncode);
            System.out.println("Decoded message: " + decodedMessage);

            // verify
            return decodedMessage.length() == messageToEncode.length() &&
                   decodedMessage.equals(messageToEncode);
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // test with user audio
        System.out.println("=== Testing with User Audio ===");
        String userAudio = "/home/ny-haritina/Documents/Studies/ITU/S6/INF-310_Codage/_tp-final-codage/assets/wav/clean_melody.wav";
        String userOutputAudio = "/home/ny-haritina/Documents/Studies/ITU/S6/INF-310_Codage/_tp-final-codage/outputencoded-sample.wav";
        boolean userAudioResult = testAudioSteganography(userAudio, userOutputAudio, "1010");
        System.out.println("User Audio Test: " + (userAudioResult ? "PASSED" : "FAILED"));
    }
}