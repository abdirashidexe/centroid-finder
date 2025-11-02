package io.github.abdirashidexe.centroidfinder;

import java.util.ArrayList;
import java.util.List;

public class EuclideanColorDistance implements ColorDistanceFinder {
    /**
     * Returns the euclidean color distance between two hex RGB colors.
     * 
     * Each color is represented as a 24-bit integer in the form 0xRRGGBB, where
     * RR is the red component, GG is the green component, and BB is the blue component,
     * each ranging from 0 to 255.
     * 
     * The Euclidean color distance is calculated by treating each color as a point
     * in 3D space (red, green, blue) and applying the Euclidean distance formula:
     * 
     * sqrt((r1 - r2)^2 + (g1 - g2)^2 + (b1 - b2)^2)
     * 
     * This gives a measure of how visually different the two colors are.
     * 
     * @param colorA the first color as a 24-bit hex RGB integer
     * @param colorB the second color as a 24-bit hex RGB integer
     * @return the Euclidean distance between the two colors
     */
    @Override
    public double distance(int colorA, int colorB) {

        // ex: 0xa24d9f --> 154, 77, 162
        List<Integer> colorA_List = convertHexToRGB(colorA);
        int r1 = colorA_List.get(0); 
        int g1 = colorA_List.get(1);
        int b1 = colorA_List.get(2);

        List<Integer> colorB_List = convertHexToRGB(colorB);
        int r2 = colorB_List.get(0);
        int g2 = colorB_List.get(1);
        int b2 = colorB_List.get(2);

        return Math.sqrt(Math.pow((r1 - r2),2) + Math.pow((g1 - g2),2) + Math.pow((b1 - b2),2));
    }

    // helper method for converting a hex int into R, G, and B components (so: 0x00FF00 --> 0, 255, 0)
    public List<Integer> convertHexToRGB(int hexValue) {

        int color = hexValue;                 // ex: 0xa24d9f
        int red = (color & 0xFF0000) >> 16;   // ex: 159
        int green = (color & 0x00FF00) >> 8;  // ex: 77
        int blue = color & 0x0000FF;          // 162
        
        List<Integer> rgbList = new ArrayList<>();

        rgbList.add(red);
        rgbList.add(green);
        rgbList.add(blue);

        // [159, 77, 162]
        return rgbList;
    }
}
