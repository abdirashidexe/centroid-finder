import express from "express";
import { getVideos } from "../controllers/videosController.js";

const router = express.Router();

export default (VIDEOS_DIR) => {
  router.get("/", getVideos(VIDEOS_DIR));
  return router;
};
