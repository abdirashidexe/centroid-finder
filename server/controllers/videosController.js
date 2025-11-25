import fs from "fs";

export function getVideos(VIDEOS_DIR) {
  return (req, res) => {
    try {
      const files = fs.readdirSync(VIDEOS_DIR);
      const videoFiles = files.filter(f => f.endsWith(".mp4"));
      res.json({ videos: videoFiles });

    } catch (err) {
      console.error(err);

      if (err.code === "ENOENT") {
        return res.status(500).json({ error: "Video directory not found." });
      }

      if (err.code === "EACCES") {
        return res.status(500).json({ error: "No permission to read video directory." });
      }

      return res.status(500).json({ error: "Unexpected error reading video directory." });
    }
  };
}
