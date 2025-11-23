import { spawn } from "child_process";
import path from "path";
import fs from "fs";
import { createJob, completeJob, failJob } from "../jobs.js";

export function processVideo(VIDEOS_DIR, RESULTS_DIR, JAR_PATH) {
  return (req, res) => {
    const { filename } = req.params;
    const { targetColor, threshold } = req.query;

    if (!targetColor || !threshold) {
      return res.status(400).json({
        error: "Missing targetColor or threshold query parameter.",
      });
    }

    const videoPath = path.join(VIDEOS_DIR, filename);

    if (!fs.existsSync(videoPath)) {
      return res.status(400).json({ error: "Video file not found." });
    }

    const job = createJob();
    const outputPath = path.join(RESULTS_DIR, `${job.jobId}.csv`);

    try {
      const child = spawn(
        "java",
        ["-Xmx4g", "-jar", JAR_PATH, videoPath, outputPath, targetColor, threshold],
        { detached: true, stdio: "ignore" }
      );

      child.unref();

      child.on("exit", (code) => {
        if (code === 0) {
          completeJob(job.jobId, outputPath);
        } else {
          failJob(job.jobId, `Java process exited with code ${code}`);
        }
      });

      res.status(202).json({ jobId: job.jobId });
    } catch (error) {
      console.error("Error starting job:", error);
      failJob(job.jobId, error.message);
      res.status(500).json({ error: "Error starting job." });
    }
  };
}
