import express from "express";
import { spawn } from "child_process";
import path from "path";
import fs from "fs";
import { createJob, completeJob, failJob } from "../jobs.js";

const router = express.Router();

export default function processRouter(VIDEOS_DIR, RESULTS_DIR, JAR_PATH) {

router.post("/:filename", (req, res) => {
    const filename = req.params.filename;
    const targetColor = req.query.targetColor;
    const threshold = req.query.threshold;

    // Validate query params
    if (!targetColor || !threshold) {
      return res.status(400).json({
        error: "Missing targetColor or threshold query parameter.",
      });
    }

    const videoPath = path.join(VIDEOS_DIR, filename);

    // Validate video file exists
    if (!fs.existsSync(videoPath)) {
      return res.status(400).json({ error: "Video file not found." });
    }

    // Create a job object
    const job = createJob();  // returns { jobId, status: "processing", result: null }
    const outputPath = path.join(RESULTS_DIR, `${job.jobId}.csv`);//***

    try {
      // Spawn the Java process asynchronously
      const child = spawn("java", [
        "-jar",
        JAR_PATH,
        videoPath,
        outputPath,
        targetColor,
        threshold,
      ], {
        detached: true,
        stdio: "ignore"
      });

      child.unref(); // allow Node to exit without waiting for the child

      // When Java finishes, mark job done or failed
      child.on("exit", (code) => {
        if (code === 0) {
          completeJob(job.jobId, outputPath);  // CSV is ready
        } else {
          failJob(job.jobId, `Java process exited with code ${code}`);
        }
      });

      // Respond immediately with jobId
      res.status(202).json({ jobId: job.jobId });

    } catch (error) {
      console.error("Error starting job:", error);
      failJob(job.jobId, error.message);
      res.status(500).json({ error: "Error starting job." });
    }
  });

  return router;
}
