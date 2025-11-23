import path from "path";
import fs from "fs";
import ffmpeg from "fluent-ffmpeg";
import ffmpegPath from "ffmpeg-static";

export function getThumbnail(VIDEOS_DIR, RESULTS_DIR) {
  return (req, res) => {
    const { videoName } = req.params;
    const videoPath = path.join(VIDEOS_DIR, videoName);
    const framePath = path.join(RESULTS_DIR, `${videoName}-frame.jpg`);

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
      console.error("FFmpeg error:", err);
      res.status(500).send("Error generating thumbnail");
    });
  };
}
