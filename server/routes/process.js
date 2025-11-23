import express from "express";
import { processVideo } from "../controllers/processController.js";

const router = express.Router();

export default function processRouter(VIDEOS_DIR, RESULTS_DIR, JAR_PATH) {
  router.post("/:filename", processVideo(VIDEOS_DIR, RESULTS_DIR, JAR_PATH));
  return router;
}
