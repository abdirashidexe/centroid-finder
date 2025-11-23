import express from "express";
import { getJobStatus } from "../controllers/statusController.js";

const router = express.Router();

export default () => {
  router.get("/:jobId/status", getJobStatus);
  return router;
};
