import { spawn } from "child_process";
import path from "path";
import fs from "fs";
import { createJob, completeJob, failJob } from "../jobs.js";


export function processVideo(VIDEOS_DIR, RESULTS_DIR, JAR_PATH) {
    return (req, res) => {
      const { filename } = req.params;
      const { targetColor, threshold } = req.query;

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
    const job = createJob();                                       // returns { jobId, status: "processing", result: null }
    const base = path.parse(filename).name;     //getting a readable filename for the output csv file
    const outputPath = path.join(RESULTS_DIR, `${base}-${job.jobId}.csv`);   //assigning job id and readable name  
    try {
      // Spawn the Java process asynchronously
      const child = spawn("java", [
        "-Xmx4g",       // <-- increase heap to 4GB
        "-jar",
        JAR_PATH,
        videoPath,
        outputPath,
        targetColor,
        threshold,
      ], {
        detached: true,   // Having the child run even if node isn't done processing
        stdio: ["ignore", "pipe", "pipe"]  // <-- Using stdout and stderr to log output and errors to files
      });

      child.unref();      // Allow Node to exit without waiting for the child

      child.stdout.on("data", data => fs.appendFileSync(path.join(RESULTS_DIR, `${job.jobId}-out.log`), data));   //Logging the output
      child.stderr.on("data", data => fs.appendFileSync(path.join(RESULTS_DIR, `${job.jobId}-err.log`), data));   //Logging the errors

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

    } catch (err) {  // catch for sync errors
      console.error("Error processing video:", err); // logging the error for clarity
      return res.status(500).json({ error: err.message || "Unexpected server error" }); // server error message
    }
  };
}
