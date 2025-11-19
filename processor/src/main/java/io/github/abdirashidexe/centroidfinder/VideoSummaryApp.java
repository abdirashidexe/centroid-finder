package io.github.abdirashidexe.centroidfinder;

import org.jcodec.api.JCodecException;
import java.io.IOException;
import java.util.List;

public class VideoSummaryApp {
    public static void main(String[] args) throws IOException, JCodecException {
        if (args.length < 4) {
            System.out.println("Usage: java -jar videoprocessor.jar inputPath outputCsv targetColor threshold");
            return;
        }

        String inputPath = args[0];
        String outputCsv = args[1];
        int targetColor = Integer.parseInt(args[2], 16);
        int threshold = Integer.parseInt(args[3]);

        VideoProcessor processor = new VideoProcessor(inputPath);
        FrameAnalyzer analyzer = new FrameAnalyzer(targetColor, threshold);
        CentroidTracker tracker = new CentroidTracker(processor, analyzer);

        List<FrameData> results;
        try {
            results = tracker.processVideo();
        } catch (Exception e) {
            System.err.println("Error processing video: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        CsvWriter.writeResults(outputCsv, results);
        System.out.println("Processing complete! Results saved to " + outputCsv);
    }
}

