package io.github.abdirashidexe.centroidfinder;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

public class VideoSummaryApp {

        File videoFile = new File("src/main/resources/videos/butterfly.mp4");

        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
        Picture picture = grab.getNativeFrame();
        int frameNumber = 0;
        while((picture = grab.getNativeFrame()) != null) {
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

            String fileName = String.format("frame_%05d.png", frameNumber++);
            ImageIO.write(bufferedImage, "png", new File(fileName));

            System.out.println("Saved " + fileName);
        }
        System.out.println("All frames extracted successfully!");

            // Create an ImageGroupFinder using a BinarizingImageGroupFinder with a DFS-based BinaryGroupFinder.
        ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
        
        // Find connected groups in the input image.
        // The BinarizingImageGroupFinder is expected to internally binarize the image,
        // then locate connected groups of white pixels.
        List<Group> groups = groupFinder.findConnectedGroups(inputImage);
        
        // Write the groups information to a CSV file "groups.csv".
        try (PrintWriter writer = new PrintWriter("groups.csv")) {
            for (Group group : groups) {
                writer.println(group.toCsvRow());
            }
            System.out.println("Groups summary saved as groups.csv");
        } catch (Exception e) {
            System.err.println("Error writing groups.csv");
            e.printStackTrace();
        }
}
