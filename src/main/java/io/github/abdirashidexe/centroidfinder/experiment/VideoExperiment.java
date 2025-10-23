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

public class VideoExperiment {
    public static void main(String[] args) throws IOException, JCodecException {
        File videoFile = new File("src/main/resources/videos/butterfly.mp4");

        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
        Picture picture = grab.getNativeFrame();

        BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
        ImageIO.write(bufferedImage, "png", new File("frame1.png"));

        System.out.println("Frame extracted successfully!");
    }
}
