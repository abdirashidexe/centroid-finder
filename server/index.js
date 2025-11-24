import express from "express";
import dotenv from "dotenv";
import path from "path";
import { fileURLToPath } from "url";
import fs from "fs";
import videoRoutes from "./routes/videos.js";
import thumbnailRouter from "./routes/thumbnail.js";
import processRouter from "./routes/process.js";
import statusRouter from "./routes/status.js";

// Needed for __dirname in ES modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Load environment variables from project root
dotenv.config({ path: path.join(__dirname, ".env") });

const app = express();
const PORT = process.env.PORT || 3000;

// Resolve paths from .env
const VIDEOS_DIR = path.join(__dirname, process.env.VIDEOS_DIR);
const RESULTS_DIR = path.join(__dirname, process.env.RESULTS_DIR);
const JAR_PATH = path.join(__dirname, process.env.JAR_PATH);

// Validating environment variables stopping server if paths are missing
if (!VIDEOS_DIR || !RESULTS_DIR || !JAR_PATH) {
  console.error("ERROR: Missing required environment variables.");
  console.error({ VIDEOS_DIR, RESULTS_DIR, JAR_PATH });
  process.exit(1);
}

//Making sure directorys exist
try {
  fs.mkdirSync(RESULTS_DIR, { recursive: true });
} catch (err) {
  console.error("ERROR: Could not create results directory:", RESULTS_DIR, err);
  process.exit(1);
}

// Adding routes in a try catch
try {
  app.use("/api/videos", videoRoutes(VIDEOS_DIR));          
  app.use("/api/thumbnail", thumbnailRouter(VIDEOS_DIR, RESULTS_DIR));
  app.use("/api/process", processRouter(VIDEOS_DIR, RESULTS_DIR, JAR_PATH)); 
  app.use("/api/process", statusRouter());                 
} catch (err) {
  console.error("ERROR mounting routers:", err);
  process.exit(1);
}

// putting server in a try catch 
try {
  app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
  });
} catch (err) {
  console.error("Failed to start server:", err);
  process.exit(1);
}
