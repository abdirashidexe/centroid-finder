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
        return 0;
    }

    // helper method for converting a hex int into R, G, and B components
    public int convertHexToRGB(int hexValue) {

        // 1: convert 24-bit to 3 split up 8 bits
        String hexString = String.valueOf(hexValue);

        String sR1 = hexString.substring(0,4);
        String sR2 = hexString.substring(4,8);
        String sG1 = hexString.substring(8,12);
        String sG2 = hexString.substring(12,16);
        String sB1 = hexString.substring(16, 20);
        String sB2 = hexString.substring(20,24);

        // 2: convert back to int
        int R1 = Integer.parseInt(sR1); // 4 binary bits
        int R2 = Integer.parseInt(sR2); // 4 binary bits
        int G1 = Integer.parseInt(sG1); // 4 binary bits
        int G2 = Integer.parseInt(sG2); // 4 binary bits
        int B1 = Integer.parseInt(sB1); // 4 binary bits
        int B2 = Integer.parseInt(sB2); // 4 binary bits
        
        return 0;
    }
}
