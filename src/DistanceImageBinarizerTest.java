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
        assertEquals(1, result[0][0]); // first pixel might pass threshold
        assertEquals(0, result[1][0]); // second pixel should fail threshold
    }

    // -----------------------------
    // TESTS FOR toBufferedImage()
    // -----------------------------

    @Test
    public void testToBufferedImage_convertsBinaryArrayToWhiteAndBlackPixels() {
        DistanceImageBinarizer binarizer = new DistanceImageBinarizer(mockDistanceFinder, 0, 0);

        int[][] binaryArray = {
            {1, 0},
            {0, 1}
        };

        BufferedImage image = binarizer.toBufferedImage(binaryArray);

        assertEquals(0xFFFFFF, image.getRGB(0, 0)); // white
        assertEquals(0x000000, image.getRGB(1, 0)); // black
        assertEquals(0x000000, image.getRGB(0, 1)); // black
        assertEquals(0xFFFFFF, image.getRGB(1, 1)); // white
    }

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
    
}
