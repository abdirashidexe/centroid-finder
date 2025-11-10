import express from "express";
import dotenv from "dotenv";
import path from "path";
import fs from "fs";
import { fileURLToPath } from "url";

// Import routers
import videoRoutes from "./routes/videos.js";
import thumbnailRouter from "./routes/thumbnail.js";
import processRouter from "./routes/process.js";
import statusRouter from "./routes/status.js";

// Needed for __dirname in ES modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Load environment variables from project root
dotenv.config({ path: path.join(__dirname, "../.env") });

const app = express();
const PORT = process.env.PORT || 3000;

// Resolve paths from .env
const VIDEOS_DIR = path.resolve(__dirname, "..", process.env.VIDEOS_DIR);
const RESULTS_DIR = path.resolve(__dirname, "..", process.env.RESULTS_DIR);
const JAR_PATH = path.resolve(__dirname, "..", process.env.JAR_PATH);

// Debug logs
console.log("Videos directory:", VIDEOS_DIR);
console.log("Results directory:", RESULTS_DIR);
console.log("Java JAR path:", JAR_PATH);

// Mount routers with proper API prefixes
app.use("/api/videos", videoRoutes(VIDEOS_DIR));          // GET /api/videos
app.use("/api/thumbnail", thumbnailRouter(VIDEOS_DIR, RESULTS_DIR));
app.use("/api/process", processRouter(VIDEOS_DIR, RESULTS_DIR, JAR_PATH)); // POST /api/process/:filename
app.use("/api/process", statusRouter());                 // GET /api/process/:jobId

// Start server
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
