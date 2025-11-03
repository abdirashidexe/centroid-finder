import express from "express";
import { spawn } from "child_process";  // lets us run a Java program
import { v4 as uuidv4 } from "uuid";    // creates unique IDs
import fs from "fs";
import path from "path";

const router = express.Router();

export default function processRouter(VIDEOS_DIR, RESULTS_DIR, JAR_PATH) {

  // POST /process/:filename?targetColor=ff0000&threshold=50
  router.post("/process/:filename", (req, res) => {
    // Get the video name from the URL (e.g. "ball.mp4")
    const filename = req.params.filename;

    // Get color and threshold from the query (after the ?)
    const targetColor = req.query.targetColor;
    const threshold = req.query.threshold;

    // Make sure the user gave both parameters
    if (!targetColor || !threshold) {
      return res.status(400).json({
        error: "Missing targetColor or threshold query parameter.",
      });
    }

    // Full path to the video file
    const videoPath = path.join(VIDEOS_DIR, filename);

    // Check if that video file exists
    if (!fs.existsSync(videoPath)) {
      return res.status(400).json({ error: "Video file not found." });
    }

    // Create a random job ID so we can track this task
    const jobId = uuidv4();

    // Where to save the results
    const outputPath = path.join(RESULTS_DIR, `${jobId}.csv`);

    // Try to start the Java program
    try {
      // Run your JAR file:
      // java -jar yourProgram.jar videoPath outputPath targetColor threshold
      const child = spawn("java", [
        "-jar",
        JAR_PATH,
        videoPath,
        outputPath,
        targetColor,
        threshold,
      ]);

      // Optional: print something when it finishes
      child.on("close", (code) => {
        console.log(`Java process finished with code ${code}`);
      });

      // Send a response right away (we don’t wait for Java to finish)
      res.status(202).json({
        jobId: jobId
      });
    } catch (error) {
      console.error("Error starting job:", error);
      res.status(500).json({ error: "Error starting job." });
    }
  });

  return router;
}
