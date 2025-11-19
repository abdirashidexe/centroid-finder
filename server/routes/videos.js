import express from "express";
import fs from "fs";

const router = express.Router();

export default (VIDEOS_DIR) => {
  router.get("/", (req, res) => {
    try {
      const files = fs.readdirSync(VIDEOS_DIR);                         // Reads all files syncronously
      const videoFiles = files.filter(file => file.endsWith(".mp4"));   // Filters only mp4 files
      res.json({ videos: videoFiles });                                 // Sends back json response with video files

    } 
    // Error if reading fails
    catch (err) {
      console.error(err);
      res.status(500).json({ error: "Error reading video directory" });
    }
  });

  return router;
};
