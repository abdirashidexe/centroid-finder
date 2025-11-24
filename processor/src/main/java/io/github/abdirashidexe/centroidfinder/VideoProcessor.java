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


/**
 * Extracts all frames from a video file as BufferedImages.
 */
public class VideoProcessor {
    private final String videoPath;

    /**
     * Constructs a VideoProcessor for the given video file path.
     *
     * @param videoPath path to the video file
     */
    public VideoProcessor(String videoPath) {
        this.videoPath = videoPath;
    }

    /**
     * Reads a video file and returns a list of BufferedImages representing each frame.
     *
     * @return list of frames from the video
     * @throws IOException if the file cannot be read
     * @throws JCodecException if decoding fails
     */
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

