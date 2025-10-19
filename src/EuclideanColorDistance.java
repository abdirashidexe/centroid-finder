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

        String stringRR = hexString.substring(0,8);
        String stringGG = hexString.substring(8,16);
        String stringBB = hexString.substring(16, 24);

        // 2: convert back to int
        int RR = Integer.parseInt(stringRR); // 8 binary bits
        int GG = Integer.parseInt(stringGG); // 8 binary bits
        int BB = Integer.parseInt(stringBB); // 8 binary bits
        
        return 0;
    }
}
