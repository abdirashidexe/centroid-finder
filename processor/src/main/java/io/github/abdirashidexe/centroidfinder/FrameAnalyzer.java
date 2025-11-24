package io.github.abdirashidexe.centroidfinder;

import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

/**
 * Analyzes a single frame to find connected white pixel groups and returns
 * the centroid of the largest group. Used to track target objects in video frames.
 */
public class FrameAnalyzer {
    private final ImageGroupFinder groupFinder;

    /**
     * Constructs a FrameAnalyzer with a target color and threshold for binarization.
     *
     * @param targetColor the color to track (as 0xRRGGBB)
     * @param threshold allowable distance from target color to consider a pixel "white"
     */
    public FrameAnalyzer(int targetColor, int threshold) {
        // Make a color distance calculator (Euclidean formula)
        ColorDistanceFinder distanceFinder = new EuclideanColorDistance();

        // Make a binarizer (turns the image into black/white)
        ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);

        // Make a group finder (finds connected white pixels)
        this.groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
    }

    /**
     * Analyzes a BufferedImage and returns the centroid of the largest detected group.
     *
     * @param frame the image frame to analyze
     * @return Coordinate of the largest group's centroid, or (-1, -1) if none found
     */
    public Coordinate analyzeFrame(BufferedImage frame) {
        // Find all groups in this frame
        List<Group> groups = groupFinder.findConnectedGroups(frame);

        // If no white pixels found
        if (groups.isEmpty()) {
            return new Coordinate(-1, -1);
        }

        // Find the biggest group (most white pixels)
        Group largest = null;  // Start with no largest group yet

        for (Group g : groups) {       // Go through each group in the list
            if (largest == null || g.size() > largest.size()) {
                largest = g;           // Update largest if this group is bigger
            }
        }

        if (largest == null) {
            throw new RuntimeException("No groups found!"); // Handle empty list
        }


        // Return the centroid (the (x, y) average of all white pixels)
        return largest.centroid();
    }
}

