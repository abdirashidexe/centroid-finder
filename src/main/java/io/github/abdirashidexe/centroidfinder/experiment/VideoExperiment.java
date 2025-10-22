package main.java.io.github.abdirashidexe.centroidfinder.experiment;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class VideoExperiment {
    File videoFile = new File("src/main/resources/videos/butterfly.mp4");

    FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));    
    
}
