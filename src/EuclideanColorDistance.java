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

        int colorA_RGB = convertHexToRGB(colorA); // should return: RGB --> will be r1, g1, b1
        int colorB_RGB = convertHexToRGB(colorB); // should return: RGB --> will be r2, g2, b2



        // do the math
        //sqrt((r1 - r2)^2 + (g1 - g2)^2 + (b1 - b2)^2)
        return 0;
    }

    // helper method for converting a hex int into R, G, and B components
    public int convertHexToRGB(int hexValue) {

        // 1: convert 24-bit to 3 split up 8 bits
        // ex: 00000000 11111111 00000000
        String hexString = String.valueOf(hexValue);

        // ex: 0000 0000 1111 1111 0000 0000
        String sR1 = hexString.substring(0,4);
        String sR2 = hexString.substring(4,8);
        String sG1 = hexString.substring(8,12);
        String sG2 = hexString.substring(12,16);
        String sB1 = hexString.substring(16, 20);
        String sB2 = hexString.substring(20,24);

        // 2: convert back to decimal int
        // ex: 0 0 15 15 0 0
        int dR1 = Integer.parseInt(sR1); // 4 binary bits
        int dR2 = Integer.parseInt(sR2); // 4 binary bits
        int dG1 = Integer.parseInt(sG1); // 4 binary bits
        int dG2 = Integer.parseInt(sG2); // 4 binary bits
        int dB1 = Integer.parseInt(sB1); // 4 binary bits
        int dB2 = Integer.parseInt(sB2); // 4 binary bits

        // 3: concat into their components
        // ex: 00 FF 00
        String r = Integer.toHexString(dR1).concat(Integer.toHexString(dR2));
        String g = Integer.toHexString(dG1).concat(Integer.toHexString(dG2));
        String b = Integer.toHexString(dB1).concat(Integer.toHexString(dB2));
        
        return 0;
    }
}
