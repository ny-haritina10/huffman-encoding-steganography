// File: mg/itu/steganography/ImageProcessor.java
package mg.itu.steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {

    private BufferedImage image;
    private int width;
    private int height;

    public void loadImage(String filePath) 
        throws IOException 
    {
        File file = new File(filePath);
        if (!file.exists()) 
        { throw new IOException("image file does not exist"); }

        image = ImageIO.read(file);
        if (image == null) 
        { throw new IOException("failed to load image"); }

        width = image.getWidth();
        height = image.getHeight();

        // check if image is effectively grayscale or convert it
        if (!isGrayscale(image)) 
        { image = convertToGrayscale(image); }
    }

    private boolean isGrayscale(BufferedImage img) {
        if (img.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            return true;
        }

        // check pixels for grayscale property (R=G=B)
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                
                if (r != g || g != b) {
                    return false;
                }
            }
        }
        return true;
    }

    private BufferedImage convertToGrayscale(BufferedImage src) {
        BufferedImage gray = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int rgb = src.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int grayValue = (r + g + b) / 3; // average for grayscale
                gray.setRGB(x, y, (grayValue << 16) | (grayValue << 8) | grayValue);
            }
        }
        return gray;
    }

    public int getPixelValue(int position) {
        if (image == null) {
            throw new IllegalStateException("image not loaded");
        }
        if (position < 0 || position >= width * height) {
            throw new IllegalArgumentException("invalid pixel position");
        }
        int x = position % width;
        int y = position / width;
        return image.getRGB(x, y) & 0xFF; // extract grayscale value
    }

    public int getMaxPosition() {
        return width * height;
    }
}