// server/jobs.js
import { v4 as uuidv4 } from "uuid";

// In-memory job store
const jobs = {};

// Create a new job
export function createJob() {
  const jobId = uuidv4();
  jobs[jobId] = {
    jobId,
    status: "processing",
    result: null,
    error: null
  };
  return jobs[jobId];
}

// Update a job when done
export function completeJob(jobId, resultPath) {
  if (jobs[jobId]) {
    jobs[jobId].status = "done";
    jobs[jobId].result = resultPath;
  }
}

// Update a job if error
export function failJob(jobId, errorMsg) {
  if (jobs[jobId]) {
    jobs[jobId].status = "error";
    jobs[jobId].error = errorMsg;
  }
}

// Get job by ID
export function getJob(jobId) {
  return jobs[jobId] || null;
}
