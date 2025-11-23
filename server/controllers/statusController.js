import { getJob } from "../jobs.js";

export function getJobStatus(req, res) {
  try {
    const { jobId } = req.params;
    const job = getJob(jobId);

    if (!job) {
      return res.status(404).json({ error: "Job ID not found" });
    }

    if (job.status === "done") {
      return res.json({ status: "done", result: job.result });
    }

    if (job.status === "error") {
      return res.json({ status: "error", error: job.error });
    }

    return res.json({ status: "processing" });
  } catch (err) {
    console.error(err);
    return res.status(500).json({ error: "Error fetching job status" });
  }
}
