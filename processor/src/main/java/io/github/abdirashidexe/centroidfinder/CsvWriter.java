package io.github.abdirashidexe.centroidfinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    public static void writeResults(String outputPath, List<FrameData> data) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (FrameData f : data) {
                writer.write(String.format("%d,%d,%d%n", f.time(), f.x(), f.y()));
            }
        }
    }
}
