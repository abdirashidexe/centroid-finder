import express from "express";
import dotenv from "dotenv";
import path from "path";
import videoRoutes from "./routes/videos.js";
import thumbnailRouter from "./routes/thumbnail.js"
import fs from 'fs'; // file stystem module

import { fileURLToPath } from "url";

// Needed for __dirname in ES modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Load .env from the project root (one level above /server)
dotenv.config({ path: path.join(__dirname, "../.env") });

const app = express();
const PORT = process.env.PORT || 3000;

// gets correct path everytime
const VIDEOS_DIR = path.resolve(__dirname, "..", process.env.VIDEOS_DIR);
const RESULTS_DIR = path.resolve(__dirname, "..", process.env.RESULTS_DIR);
const JAR_PATH = path.resolve(__dirname, "..", process.env.JAR_PATH);

// testing
console.log("Videos directory:", VIDEOS_DIR);
console.log("Results directory:", RESULTS_DIR);
console.log("Java JAR path:", JAR_PATH);

app.use("/api", videoRoutes(VIDEOS_DIR));
app.use(thumbnailRouter(VIDEOS_DIR, RESULTS_DIR)); 
//app.use("/api", videoRoutes(VIDEOS_DIR));

app.listen(PORT, () => {
  console.log(`Server running on port http://localhost:${PORT}`);
});