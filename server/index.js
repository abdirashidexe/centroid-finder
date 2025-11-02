import express from "express";
import dotenv from "dotenv";
import path from "path";

dotenv.config(); // loads variables from .env
const app = express();

const PORT = process.env.PORT || 3000;
const VIDEOS_DIR = process.env.VIDEOS_DIR;
const RESULTS_DIR = process.env.RESULTS_DIR;
const JAR_PATH = process.env.JAR_PATH;

//testing
console.log("Videos directory:", VIDEOS_DIR);
console.log("Results directory:", RESULTS_DIR);
console.log("Java JAR path:", JAR_PATH);

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});