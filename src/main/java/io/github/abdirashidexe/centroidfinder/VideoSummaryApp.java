package io.github.abdirashidexe.centroidfinder;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;


public class VideoSummaryApp {
    public static void main(String[] args) throws IOException {

        //Getting the user input for the video path, target color, and threshold
        if (args.length < 3) {
            System.out.println("Usage: java VideoSummaryApp <input_video> <hex_target_color> <threshold>");
            return;
        }
        String inputVideoPath = args[0];
        String hexTargetColor = args[1];
        int threshold = 0;
        File videoFile = new File(inputVideoPath);
        int frameNumber = 0;

        //converting the threshold string into an integer
        try {
            threshold = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Threshold must be an integer.");
            return;
        }

        // Parse the target color from a hex string (format RRGGBB) into a 24-bit integer (0xRRGGBB)
        int targetColor = 0;
        try {
            targetColor = Integer.parseInt(hexTargetColor, 16);
        } catch (NumberFormatException e) {
            System.err.println("Invalid hex target color. Please provide a color in RRGGBB format.");
            return;
        }
        
        //Grabbing the frames of the video that we need and handling potential errors
        FrameGrab grab = null;
        try {
            grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
        } catch (JCodecException e) {
            e.printStackTrace(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //If the frame we grabbed isn't null then keep finding other frames
        if(grab != null)
        {
            Picture picture = null;
            while ((picture = grab.getNativeFrame()) != null) {
                BufferedImage frameImage = AWTUtil.toBufferedImage(picture);
        
                String fileName = String.format("frame_%05d.png", frameNumber);
                ImageIO.write(frameImage, "png", new File(fileName));

                // Create the DistanceImageBinarizer with a EuclideanColorDistance instance.
                ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
                ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
                
                // Binarize the input image.
                int[][] binaryArray = binarizer.toBinaryArray(frameImage);
                BufferedImage binaryImage = binarizer.toBufferedImage(binaryArray);
    
                String binName = String.format("binarized_%05d.png", frameNumber);
                ImageIO.write(binaryImage, "png", new File(binName));
        

                // Create an ImageGroupFinder using a BinarizingImageGroupFinder with a DFS-based BinaryGroupFinder.
                ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
                
                //Groups will need to be changed to return [time, x, y] (Where there is the BIGGEST connected groups)
                List<Group> groups = groupFinder.findConnectedGroups(frameImage);
                
                String csvFileName = String.format("groups_%05d.csv", frameNumber);
                try (PrintWriter writer = new PrintWriter(csvFileName)) {
                    for (Group group : groups) writer.println(group.toCsvRow());
                }

                // Now read the first line of that just-created CSV
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
                    String firstLine = reader.readLine();
                    if (firstLine != null) {
                        System.out.println(csvFileName + " → " + firstLine);
                    } else {
                        System.out.println(csvFileName + " → [empty file]");
                    }
                } catch (IOException e) {
                    System.err.println("Error reading " + csvFileName + ": " + e.getMessage());
                }
                System.out.println("Processed frame " + frameNumber++);
            }
        }
    }
}
