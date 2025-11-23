import fs from "fs";

export function getVideos(VIDEOS_DIR) {
  return (req, res) => {
    try {
      const files = fs.readdirSync(VIDEOS_DIR);
      const videoFiles = files.filter(file => file.endsWith(".mp4"));
      res.json({ videos: videoFiles });
    } catch (err) {
      console.error(err);
      res.status(500).json({ error: "Error reading video directory" });
    }
  };
}
