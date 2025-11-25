package io.github.abdirashidexe.centroidfinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Utility class for writing video analysis results to a CSV file.
 */
public class CsvWriter {

    /**
     * Writes a list of FrameData to a CSV file.
     *
     * Each row contains time, x, and y coordinates separated by commas.
     *
     * @param outputPath the file path to write the CSV
     * @param data the list of FrameData to write
     * @throws IOException if the file cannot be written
     */
    public static void writeResults(String outputPath, List<FrameData> data) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (FrameData f : data) {
                writer.write(String.format("%.2f,%d,%d%n", f.time(), f.x(), f.y()));
            }
        }
    }
}
