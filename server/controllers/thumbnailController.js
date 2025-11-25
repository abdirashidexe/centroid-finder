import path from "path";
import fs from "fs";
import ffmpeg from "fluent-ffmpeg";
import ffmpegPath from "ffmpeg-static";

export function getThumbnail(VIDEOS_DIR, RESULTS_DIR) {
  return (req, res) => {
    const { videoName } = req.params;
    const videoPath = path.join(VIDEOS_DIR, videoName);
    const framePath = path.join(RESULTS_DIR, `${videoName}-frame.jpg`);

    if (!fs.existsSync(videoPath)) {
      return res.status(404).json({ error: "Video not found." });
    }

    if (!fs.existsSync(RESULTS_DIR)) {
      fs.mkdirSync(RESULTS_DIR, { recursive: true });
    }

    const command = ffmpeg(videoPath)
      .setFfmpegPath(ffmpegPath)
      .screenshots({
        count: 1,
        folder: RESULTS_DIR,
        filename: `${videoName}-frame.jpg`,
        timemarks: ["00:00:00"],
      });

    command.on("end", () => res.sendFile(framePath));

    command.on("error", (err) => {
      console.error("FFmpeg error:", err.message);

      if (err.message.includes("No such file"))
        return res.status(404).send("Input video not found.");

      if (err.message.includes("not found") || err.message.includes("ffmpeg"))
        return res.status(500).send("FFmpeg binary not available.");

      res.status(500).send("Failed to generate thumbnail.");
    });
  };
}
