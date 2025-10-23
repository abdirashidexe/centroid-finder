package io.github.abdirashidexe.centroidfinder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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

        int frameNumber = 0;
        File videoFile = new File("src/main/resources/videos/butterfly.mp4");

        FrameGrab grab = null;
        try {
            grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JCodecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Picture picture;
        while((picture = grab.getNativeFrame()) != null) {
            
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

            if (args.length < 3) {
                System.out.println("Usage: java ImageSummaryApp <input_image> <hex_target_color> <threshold>");
                return;
            }
        
            String inputImagePath = args[0];
            String hexTargetColor = args[1];
            int threshold = 0;
            try {
                threshold = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Threshold must be an integer.");
                return;
            }

            try {
                bufferedImage = ImageIO.read(new File(inputImagePath));
            } catch (Exception e) {
                System.err.println("Error loading image: " + inputImagePath);
                e.printStackTrace();
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
            // Create the DistanceImageBinarizer with a EuclideanColorDistance instance.
            ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
            ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
            
            // Binarize the input image.
            int[][] binaryArray = binarizer.toBinaryArray(bufferedImage);
            BufferedImage binaryImage = binarizer.toBufferedImage(binaryArray);
            // Create an ImageGroupFinder using a BinarizingImageGroupFinder with a DFS-based BinaryGroupFinder.
            ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
            // Write the binarized image to disk as "binarized.png".
            try {
                ImageIO.write(binaryImage, "png", new File("binarized.png"));
                System.out.println("Binarized image saved as binarized.png");
            } catch (Exception e) {
                System.err.println("Error saving binarized image.");
                e.printStackTrace();
            }
        
            String fileName = String.format("frame_%05d.png", frameNumber++);
            ImageIO.write(bufferedImage, "png", new File(fileName));

            System.out.println("Saved " + fileName);

            // Find connected groups in the input image.
            // The BinarizingImageGroupFinder is expected to internally binarize the image,
            // then locate connected groups of white pixels.
            List<Group> butterflyGroups = groupFinder.findConnectedGroups(bufferedImage);
        
            System.out.println("All frames extracted successfully!");
            
            // Write the groups information to a CSV file "groups.csv".
            try (PrintWriter writer = new PrintWriter("groups.csv")) {
                for (Group group : butterflyGroups) {
                    writer.println(group.toCsvRow());
                }
                System.out.println("Groups summary saved as groups.csv");
            } catch (Exception e) {
                System.err.println("Error writing groups.csv");
                e.printStackTrace();
            }
        }
    }
}
