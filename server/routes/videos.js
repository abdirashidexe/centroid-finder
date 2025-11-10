import express from "express";
import fs from "fs";

const router = express.Router();

export default (VIDEOS_DIR) => {
  router.get("/", (req, res) => {
    try {
      const files = fs.readdirSync(VIDEOS_DIR);         //reads all files syncronously
      const videoFiles = files.filter(file => file.endsWith(".mp4"));   //filters only mp4 files
      res.json({ videos: videoFiles });               //sends back json response with video files

    } 
    //catch error if reading fails
    catch (err) {
      console.error(err);
      res.status(500).json({ error: "Could not read videos folder" });
    }
  });

  return router;
};
