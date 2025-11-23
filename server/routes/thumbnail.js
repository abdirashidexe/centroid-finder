import express from "express";
import { getThumbnail } from "../controllers/thumbnailController.js";

const router = express.Router();

export default (VIDEOS_DIR, RESULTS_DIR) => {
  router.get("/:videoName", getThumbnail(VIDEOS_DIR, RESULTS_DIR));
  return router;
};
