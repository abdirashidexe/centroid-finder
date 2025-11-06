import express from "express";
import { getJob } from "../jobs.js";

const router = express.Router();

export default () => {
  router.get("/:jobId", (req, res) => {
    try {
    const { jobId } = req.params;
    const job = getJob(jobId);      // Get JobID from URL

      if (!job) {
        return res.status(404).json({ error: "Job ID not found" });
      }

      // Return job info
      if (job.status === "done") {
        return res.json({ status: "done", result: job.result });
      } else if (job.status === "error") {
        return res.json({ status: "error", error: job.error });
      } else {
        return res.json({ status: "processing" });
      }
    } catch (err) {
      console.error(err);  // optional, logs error for debugging
      return res.status(500).json({ error: "Error fetching job status" });
    }

  });

return router;
};
