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
        // Initialize components for analyzing frames: color distance, binarization, and group finding
        ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
        ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
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
        Group largest = null;

        for (Group g : groups) {
            if (largest == null || g.size() > largest.size()) {
                largest = g;           // Keep largest group so far
            }
        }

        if (largest == null) {
            throw new RuntimeException("No groups found!"); // Should not happen if list is non-empty
        }

        // Return the centroid (the (x, y) average of all white pixels)
        return largest.centroid();
    }
}

