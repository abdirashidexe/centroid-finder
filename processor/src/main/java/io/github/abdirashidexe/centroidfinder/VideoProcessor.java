package io.github.abdirashidexe.centroidfinder;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoProcessor {
    private final String videoPath;

    public VideoProcessor(String videoPath) {
        this.videoPath = videoPath;
    }

    public List<BufferedImage> extractFrames() throws IOException, JCodecException {
        List<BufferedImage> frames = new ArrayList<>();
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(new File(videoPath)));

        Picture picture;
        while ((picture = grab.getNativeFrame()) != null) {
            frames.add(AWTUtil.toBufferedImage(picture));
        }
        return frames;
    }
}

