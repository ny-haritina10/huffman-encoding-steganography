package mg.itu.steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {
    private BufferedImage image;
    private int width;
    private int height;

    public void loadImage(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("image file does not exist");
        }
        image = ImageIO.read(file);
        if (image == null) {
            throw new IOException("failed to load image");
        }
        width = image.getWidth();
        height = image.getHeight();
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
        int rgb = image.getRGB(x, y);
        
        // extract red channel for consistency across image types
        return (rgb >> 16) & 0xFF; // return red channel value (0-255)
    }

    public int getMaxPosition() {
        return width * height;
    }
}