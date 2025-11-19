package io.github.abdirashidexe.centroidfinder.experiment;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//pushing to test merge conflict
public class VideoExperiment {
    public static void main(String[] args) throws IOException, JCodecException {
        File videoFile = new File("src/main/resources/videos/butterfly.mp4");

        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
        Picture picture = grab.getNativeFrame();
        int frameNumber = 0;

        while ((picture = grab.getNativeFrame()) != null) {
        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

        String fileName = String.format("frame_%05d.png", frameNumber++);
        ImageIO.write(bufferedImage, "png", new File(fileName));

        System.out.println("Saved " + fileName);
    }
        System.out.println("All frames extracted successfully!");
}
}