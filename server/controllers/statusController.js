import express from "express";
import { getJob } from "../jobs.js";

const router = express.Router();

export default () => {
  router.get("/:jobId/status", (req, res) => {
    try {
    const { jobId } = req.params;
    const job = getJob(jobId);      // Get JobID from URL

      if (!job) {
        return res.status(404).json({ 
          status: "error", 
          error: `Job ID ${jobId} not found` 
        });      
      }

      // Return job info
      if (job.status === "done") {
          return res.json({ 
            status: "done", 
            result: job.result  // return csv path
          });
      } 
      else if (job.status === "error") {
          return res.json({ 
            status: "error", 
            error: job.error  // return error message
          });      
      } 
      else {
        return res.json({ status: "processing" });
      }
    // Error if thumbnail generation fails
    } catch (err) {
      console.error("Error fetching job status:", err);
      return res.status(500).json({ 
        status: "error", 
        error: "Unexpected server error"  // Error message for client
      });
    }
  });
return router;
};
