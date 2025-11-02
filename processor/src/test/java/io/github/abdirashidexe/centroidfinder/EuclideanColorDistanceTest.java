package io.github.abdirashidexe.centroidfinder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EuclideanColorDistanceTest {

    @Test
    void testSameColor() {
        int color = 0x123456;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(0.0, distanceCalculator.distance(color, color), 0.0001);
    }

    @Test
    void testBlackAndWhite() {
        int black = 0x000000;
        int white = 0xFFFFFF;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(Math.sqrt(255*255*3), distanceCalculator.distance(black, white), 0.0001);
    }

    @Test
    void testRedAndGreen() {
        int red = 0xFF0000;
        int green = 0x00FF00;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(Math.sqrt(255*255 + 255*255), distanceCalculator.distance(red, green), 0.0001);
    }

    @Test
    void testBlueAndYellow() {
        int blue = 0x0000FF;
        int yellow = 0xFFFF00;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(Math.sqrt(255*255 + 255*255 + 255*255), distanceCalculator.distance(blue, yellow), 0.0001);
    }

    @Test
    void testGrayShades() {
        int gray1 = 0x888888;
        int gray2 = 0x444444;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(68 * Math.sqrt(3), distanceCalculator.distance(gray1, gray2), 0.0001);

    }

    @Test
    void testRedAndDarkRed() {
        int red = 0xFF0000;
        int darkRed = 0x800000;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(Math.sqrt((255-128)*(255-128)), distanceCalculator.distance(red, darkRed), 0.0001);
    }

    @Test
    void testGreenAndDarkGreen() {
        int green = 0x00FF00;
        int darkGreen = 0x008000;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(Math.sqrt((255-128)*(255-128)), distanceCalculator.distance(green, darkGreen), 0.0001);
    }

    @Test
    void testBlueAndDarkBlue() {
        int blue = 0x0000FF;
        int darkBlue = 0x000080;
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(Math.sqrt((255-128)*(255-128)), distanceCalculator.distance(blue, darkBlue), 0.0001);
    }

    @Test
    void testRandomColors1() {
        int colorA = 0x123456;
        int colorB = 0x654321;
        double expected = Math.sqrt(Math.pow(0x12-0x65, 2) + Math.pow(0x34-0x43, 2) + Math.pow(0x56-0x21, 2));
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(expected, distanceCalculator.distance(colorA, colorB), 0.0001);
    }

    @Test
    void testRandomColors2() {
        int colorA = 0xABCDEF;
        int colorB = 0x123456;
        double expected = Math.sqrt(Math.pow(0xAB-0x12, 2) + Math.pow(0xCD-0x34, 2) + Math.pow(0xEF-0x56, 2));
        EuclideanColorDistance distanceCalculator = new EuclideanColorDistance();

        assertEquals(expected, distanceCalculator.distance(colorA, colorB), 0.0001);
    }
}
