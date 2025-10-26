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
        
        //Creating a csv file outside the loop to have only one 
        try (PrintWriter summaryWriter = new PrintWriter("video_summary.csv")) 
        {
            //If the frame we grabbed isn't null then keep finding other frames
            if(grab != null)
            {

                Picture picture = null;
                while ((picture = grab.getNativeFrame()) != null) {
                    int time = (int)(frameNumber / 30); 
                    BufferedImage frameImage = AWTUtil.toBufferedImage(picture);
            
                    String fileName = String.format("frame_%05d.png", frameNumber);
                    ImageIO.write(frameImage, "png", new File(fileName));       //delete after testing
                    
                    // Create the DistanceImageBinarizer with a EuclideanColorDistance instance.
                    ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
                    ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
                    
                    // Binarize the input image.
                    int[][] binaryArray = binarizer.toBinaryArray(frameImage);
                    BufferedImage binaryImage = binarizer.toBufferedImage(binaryArray);
        
                    String binName = String.format("binarized_%05d.png", frameNumber);
                    ImageIO.write(binaryImage, "png", new File(binName));       //delete after testing
            

                    // Create an ImageGroupFinder using a BinarizingImageGroupFinder with a DFS-based BinaryGroupFinder.
                    ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
                    
                    //Groups will need to be changed to return [time, x, y] (Where there is the BIGGEST connected groups)
                    List<Group> groups = groupFinder.findConnectedGroups(frameImage);
                    
                    if (!groups.isEmpty()) {
                        Group largestGroup = groups.get(0);
                        int x = largestGroup.centroid().x();
                        int y = largestGroup.centroid().y();
                        // Write a row: time,x,y
                        summaryWriter.println(time + "," + x + "," + y); 
                    }
                    else {
                        System.out.println("Frame " + frameNumber + " → no groups found");
                    }
                    System.out.println("Processed frame " + frameNumber++);
                }
            }
        }
    }
}
