import express from "express";
import dotenv from "dotenv";
import path from "path";
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

app.get('/api/videos', (req, res) => {
    try {
        // read the directory, filter out anything that isn’t a video file (optional), & send JSON response
        const files = fs.readdirSync(VIDEOS_DIR);
        const videoFiles = files.filter(file => file.endsWith('.mp4'));
        res.json({ videos: videoFiles });
    } catch (err) {
        // if folder doesn't exist or some error happens
        console.error(err);
        res.status(500).json({ error: 'Could not read videos folder' });
    }
});

app.listen(PORT, () => {
  console.log(`Server running on port http://localhost:${PORT}`);
});