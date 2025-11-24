package io.github.abdirashidexe.centroidfinder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;

public class DistanceImageBinarizerTest {

    // Helper stub (can be replaced later with a real ColorDistanceFinder)
    private final ColorDistanceFinder mockDistanceFinder = (c1, c2) -> {
        // You can tweak this during testing
        return Math.abs(c1 - c2);
    };

    // -----------------------------
    // TESTS FOR toBinaryArray()
    // -----------------------------

    @Test
    public void testToBinaryArray_singlePixelMatchTarget() {
        // Arrange
        int targetColor = 0xFFFFFF;
        int threshold = 10;
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, targetColor, threshold);

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, targetColor); // pixel matches target color

        // Act
        int[][] result = binarizer.toBinaryArray(image);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1, result[0].length);
        assertEquals(1, result[0][0]); // expect white pixel
    }

    @Test
    public void testToBinaryArray_pixelFarFromTarget() {
        int targetColor = 0xFFFFFF;
        int threshold = 5;
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, targetColor, threshold);

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0x000000); // black pixel, far from target

        int[][] result = binarizer.toBinaryArray(image);

        assertNotNull(result);
        assertEquals(0, result[0][0]); // expect black pixel
    }

    @Test
    public void testToBinaryArray_multiplePixelsMixedDistances() {
        int targetColor = 0x000000;
        int threshold = 100;
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, targetColor, threshold);

        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0x000000); // close to target (black)
        image.setRGB(1, 0, 0xFFFFFF); // far from target (white)

        int[][] result = binarizer.toBinaryArray(image);

        assertEquals(2, result.length);
        assertEquals(1, result[0].length);
        assertEquals(1, result[0][0]); // supposed to be 1 but is 0
        assertEquals(0, result[1][0]); 
    }

    // -----------------------------
    // TESTS FOR toBufferedImage()
    // -----------------------------

    @Test
    public void testToBufferedImage_preservesDimensions() {
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, 0, 0);

        int[][] binaryArray = new int[5][3]; // width = 5, height = 3
        BufferedImage image = binarizer.toBufferedImage(binaryArray);

        assertEquals(5, image.getWidth());
        assertEquals(3, image.getHeight());
    }

    @Test
    public void testToBufferedImage_throwsExceptionForNullArray() {
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, 0, 0);

        assertThrows(NullPointerException.class, () -> {
            binarizer.toBufferedImage(null);
        });
    }

    @Test
    public void testToBinaryArray_allPixelsMatchTarget() {
        int targetColor = 0x123456;
        int threshold = 1000;
    
        // Fake distance finder always returns 0 (all pixels "match" target)
        ColorDistanceFinder fakeDistanceFinder = (c1, c2) -> 0;
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(fakeDistanceFinder, targetColor, threshold);
    
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, targetColor);
        image.setRGB(0, 1, targetColor);
        image.setRGB(1, 0, targetColor);
        image.setRGB(1, 1, targetColor);
    
        int[][] result = binarizer.toBinaryArray(image);
    
        // All pixels should be white
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                assertEquals(1, result[x][y]);
            }
        }
    }
    @Test
    public void testToBinaryArray_noPixelsMatchTarget() {
        int targetColor = 0xABCDEF;
        int threshold = 0; // Only exact match allowed

        // Fake distance finder always returns large value (pixels "far" from target)
        ColorDistanceFinder fakeDistanceFinder = (c1, c2) -> 1000;
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(fakeDistanceFinder, targetColor, threshold);

        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0x000000);
        image.setRGB(0, 1, 0x111111);
        image.setRGB(1, 0, 0x222222);
        image.setRGB(1, 1, 0x333333);

        int[][] result = binarizer.toBinaryArray(image);

        // All pixels should be black
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                assertEquals(0, result[x][y]);
            }
        }
    }
    @Test
    public void testToBinaryArray_mixedPixelsWithFakeDistance() {
        int targetColor = 0xFFFFFF;
        int threshold = 50;
    
        // Fake distance finder: simulate first pixel close, others far
        ColorDistanceFinder fakeDistanceFinder = (c1, c2) -> (c1 == 0xFFFFFF ? 10 : 100);
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(fakeDistanceFinder, targetColor, threshold);
    
        BufferedImage image = new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0xFFFFFF); // close to target
        image.setRGB(1, 0, 0x000000); // far from target
    
        int[][] result = binarizer.toBinaryArray(image);
    
        assertEquals(1, result[0][0]); // first pixel white
        assertEquals(0, result[1][0]); // second pixel black
    }
    @Test
    public void testToBinaryArray_singleColumnImage() {
        int targetColor = 0x0F0F0F;
        int threshold = 5;
    
        // Fake distance: pixels alternate matching / not matching
        ColorDistanceFinder fakeDistanceFinder = (c1, c2) -> (c1 == 0x0F0F0F ? 0 : 10);
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(fakeDistanceFinder, targetColor, threshold);
    
        BufferedImage image = new BufferedImage(1, 3, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0x0F0F0F); // match
        image.setRGB(0, 1, 0xFFFFFF); // no match
        image.setRGB(0, 2, 0x0F0F0F); // match
    
        int[][] result = binarizer.toBinaryArray(image);
    
        assertEquals(1, result[0][0]);
        assertEquals(0, result[0][1]);
        assertEquals(1, result[0][2]);
    }
    @Test
    public void testToBinaryArray_handlesTinyImageGracefully() {
        int targetColor = 0x000000;
        int threshold = 10;
    
        ColorDistanceFinder fakeDistanceFinder = (c1, c2) -> 0;
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(fakeDistanceFinder, targetColor, threshold);
    
        // Using 1x1 image instead of 0x0, since BufferedImage(0,0) is invalid
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    
        int[][] result = binarizer.toBinaryArray(image);
    
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(1, result[0].length);
    }
    
    @Test
    public void testToBinaryArray_coordinateOrientation() {
        // Make a 2x2 image with distinct pixels:
        // (0,0)=white, (1,0)=black
        // (0,1)=black, (1,1)=white
        int targetColor = 0xFFFFFF;
        int threshold = 1;

        ColorDistanceFinder fake = (c1, c2) -> (c1 == targetColor ? 0 : 100);

        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(fake, targetColor, threshold);

        BufferedImage img = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, 0xFFFFFF);
        img.setRGB(1, 0, 0x000000);
        img.setRGB(0, 1, 0x000000);
        img.setRGB(1, 1, 0xFFFFFF);

        int[][] out = binarizer.toBinaryArray(img);

        assertEquals(1, out[0][0]);
        assertEquals(0, out[1][0]);
        assertEquals(0, out[0][1]);
        assertEquals(1, out[1][1]);
    }
    @Test
    public void testToBufferedImage_pixelValuesAreCorrect() {
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, 0, 0);

        int[][] arr = {
            {1, 0},
            {0, 1}
        };

        BufferedImage img = binarizer.toBufferedImage(arr);

        // top-left  (1)
        assertEquals(0xFFFFFF, img.getRGB(0, 0) & 0xFFFFFF);

        // top-right (0)
        assertEquals(0x000000, img.getRGB(1, 0) & 0xFFFFFF);

        // bottom-left (0)
        assertEquals(0x000000, img.getRGB(0, 1) & 0xFFFFFF);

        // bottom-right (1)
        assertEquals(0xFFFFFF, img.getRGB(1, 1) & 0xFFFFFF);
    }
    @Test
    public void testToBufferedImage_throwsOnJaggedArray() {
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, 0, 0);

        int[][] jagged = {
            {1, 0},
            {1}     // shorter row → illegal
        };

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            binarizer.toBufferedImage(jagged);
        });
    }
    @Test
    public void testDistanceFinderCalledExpectedNumberOfTimes() {
        final int[] calls = {0};

        ColorDistanceFinder countingFinder = (a, b) -> {
            calls[0]++;
            return 0;
        };

        BufferedImage img = new BufferedImage(4, 3, BufferedImage.TYPE_INT_RGB);

        DistanceImageBinarizer binarizer =
            new DistanceImageBinarizer(countingFinder, 0x000000, 10);

        binarizer.toBinaryArray(img);

        assertEquals(12, calls[0]); // 4 × 3
    }
}
