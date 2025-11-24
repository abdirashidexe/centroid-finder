package io.github.abdirashidexe.centroidfinder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the centroid of a target object across frames in a video.
 * 
 * Uses a VideoProcessor to extract frames and a FrameAnalyzer to find
 * the centroid of the target in each frame. Returns a list of FrameData
 * containing time and centroid coordinates for each frame.
 */
public class CentroidTracker {
    private final VideoProcessor videoProcessor;
    private final FrameAnalyzer analyzer;

    /**
     * Constructs a CentroidTracker with a video processor and frame analyzer.
     *
     * @param videoProcessor the VideoProcessor to extract frames from a video
     * @param analyzer the FrameAnalyzer to compute centroids for each frame
     */
    public CentroidTracker(VideoProcessor videoProcessor, FrameAnalyzer analyzer) {
        this.videoProcessor = videoProcessor;
        this.analyzer = analyzer;
    }

    /**
     * Processes the video and tracks the centroid in each frame.
     *
     * Extracts frames from the video, analyzes each frame to find the target's centroid,
     * and returns a list of FrameData with timestamps and coordinates.
     *
     * @return a list of FrameData containing the centroid and time for each frame
     * @throws Exception if video extraction or frame analysis fails
     */
    public List<FrameData> processVideo() throws Exception {
        List<FrameData> data = new ArrayList<>();
        List<BufferedImage> frames = videoProcessor.extractFrames();
        double fps = 30.0; // assume 30 frames per second
        int frameNumber = 0;

        for (BufferedImage frame : frames) {
            Coordinate c = analyzer.analyzeFrame(frame);
            double time = frameNumber / fps;
            data.add(new FrameData(time, c.x(), c.y()));
            frameNumber++;
        }
        return data;
    }
}
