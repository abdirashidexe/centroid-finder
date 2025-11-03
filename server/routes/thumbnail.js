import express from "express";
import path from "path";
import fs from "fs";
import ffmpeg from "fluent-ffmpeg";
import ffmpegPath from "ffmpeg-static";

const router = express.Router();

export default (VIDEOS_DIR, RESULTS_DIR) => {
  // Route to capture the first frame of a video
  router.get("/thumbnail/:videoName", (req, res) => {
    const videoName = req.params.videoName; // Get video name from URL
    const videoPath = path.join(VIDEOS_DIR, videoName); // Full path to video
    const framePath = path.join(RESULTS_DIR, `${videoName}-frame.jpg`); // Output frame path

    // Make sure the results folder exists
    if (!fs.existsSync(RESULTS_DIR)) {
      fs.mkdirSync(RESULTS_DIR, { recursive: true });
    }

    // Capture first frame using fluent-ffmpeg
    const command = ffmpeg(videoPath)
      .setFfmpegPath(ffmpegPath) // Use bundled ffmpeg
      .screenshots({
        count: 1,                // Only one frame
        folder: RESULTS_DIR,     // Save in results folder
        filename: `${videoName}-frame.jpg`, 
        timemarks: ['00:00:00']  // Capture at 1 second
      });

    // When done, send the image back to the client
    command.on('end', () => {
      res.sendFile(framePath);
    });

    // If an error occurs, log it and send 500 response
    command.on('error', (err) => {
      console.error("FFmpeg error:", err);
      res.status(500).send("Error capturing frame");
    });
  });

  return router;
};
