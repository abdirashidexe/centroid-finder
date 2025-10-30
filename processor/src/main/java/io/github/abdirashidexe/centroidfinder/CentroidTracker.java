package io.github.abdirashidexe.centroidfinder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CentroidTracker {
    private final VideoProcessor videoProcessor;
    private final FrameAnalyzer analyzer;

    public CentroidTracker(VideoProcessor videoProcessor, FrameAnalyzer analyzer) {
        this.videoProcessor = videoProcessor;
        this.analyzer = analyzer;
    }

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
