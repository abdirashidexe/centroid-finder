# centroid-finder

# Description:

This project allows the user to insert a video and a target color (for the moving object). The processor extracts the centroids locations from video frames. This writes -per-frame- CSV summaries for the user that shows the time and coordinates of the targeted moving object.


# Overview:
Processor: (java) Reads video frames, computes centroid, and writes CSV
Server: - Server (Node): accepts jobs / uploads, spawns the Java processor per-job, stores results and job state, returns job/result URLs to frontend.


## File Structure ##
C:\centroid-finder\
├─ .gitignore
├─ README.md
├─ PLAN.md
├─ robotImprovements.md
├─ priorityImprovements.md
├─ .env
├─ .github\
│  └─ workflows\
│     └─ run-tests.yml
├─ server\
│  ├─ .env
│  ├─ index.js
│  ├─ package.json
│  ├─ Dockerfile
│  ├─ jobs.js
│  ├─ routes\
│  │  └─ process.js
│  ├─ lib\
│  │  └─ (helpers like runner/logger)
│  └─ uploads\ or results\
├─ processor\
│  ├─ pom.xml
│  ├─ target\
│  │  ├─ centroidfinder-1.0-SNAPSHOT-jar-with-dependencies.jar
│  │  └─ results\   (RESULTS_DIR)
│  └─ src\
│     ├─ main\
│     │  ├─ java\
│     │  │  └─ io\github\abdirashidexe\centroidfinder\
│     │  │     ├─ VideoProcessor.java
│     │  │     ├─ CsvWriter.java
│     │  │     ├─ FrameAnalyzer.java
│     │  │     ├─ FrameData.java
│     │  │     ├─ DfsBinaryGroupFinder.java
│     │  │     ├─ EuclideanColorDistance.java
│     │  │     ├─ CentroidTracker.java
│     │  │     └─ experiment\
│     │  │        └─ VideoExperiment.java
│     │  └─ resources\
│     │     └─ videos\
│     │        └─ ball.mp4
│     └─ test\   (JUnit tests)
├─ sampleInput\     (optional fixtures)
└─ scripts\ or tools\  (build/run helpers)



# ***** Commands ***** #


# Docker

# 1. Build Docker image
docker build -t centroid-finder .

# 2. Run container
docker run -p 3000:3000 \
  -v "<path>/videos:/videos" \
  -v "<path>/results:/results" \
  -e VIDEOS_ACCESS=/videos \
  -e RESULTS_ACCESS=/results \
  centroid-finder



# Maven 
mvn test

mvn clean compile

mvn package




# API

## List Available Videos

**GET** `/api/videos`

**Description:**  
Return a list of all video files in the mounted directory, available publicly at /videos/VIDEO_NAME.

**Responses:**

- **200 OK**

  ```json
  ["intro.mp4", "demo.mov"]
  ```

- **500 Internal Server Error**

  ```json
  {
    "error": "Error reading video directory"
  }
  ```

---

## Generate Thumbnail

**GET** `/thumbnail/{filename}`

**Path Parameters:**

- `filename` (string, required) — Name of the video file (e.g. `demo.mov`)

**Description:**  
Extract and return the first frame from the video as a JPEG.

**Responses:**

- **200 OK**  
  JPEG binary data  
  _Content-Type: image/jpeg_


- **500 Internal Server Error**

  ```json
  {
    "error": "Error generating thumbnail"
  }
  ```

---

## Start Video Processing Job

**POST** `/process/{filename}`  
 `?targetColor=<hex>&threshold=<int>`

**Path Parameters:**

- `filename` (string, required) — Name of the video file to process (e.g. `intro.mp4`)

**Query Parameters:**

- `targetColor` (string, required) — Hex color code to match (e.g. `ff0000`)
- `threshold`   (number, required) — Match threshold (e.g. `120`)

**Description:**  
Kick off an asynchronous job to analyze the video. Returns a `jobId` you can poll.

**Responses:**

- **202 Accepted**

  ```json
  {
    "jobId": "123e4567-e89b-12d3-a456-426614174000"
  }
  ```

- **400 Bad Request**

  ```json
  {
    "error": "Missing targetColor or threshold query parameter."
  }
  ```


- **500 Internal Server Error**

  ```json
  {
    "error": "Error starting job"
  }
  ```

---

## Get Processing Job Status

**GET** `/process/{jobId}/status`

**Path Parameters:**

- `jobId` (string, required) — ID returned by the **POST** `/process` call

**Description:**  
Check whether the job is still running, has completed, or failed.

**Responses:**

- **200 OK** (processing)

  ```json
  {
    "status": "processing"
  }
  ```

- **200 OK** (done)

  ```json
  {
    "status": "done",
    "result": "/results/intro.mp4.csv"
  }
  ```

- **200 OK** (error)

  ```json
  {
    "status": "error",
    "error": "Error processing video: Unexpected ffmpeg error"
  }
  ```

- **404 Not Found**

  ```json
  {
    "error": "Job ID not found"
  }
  ```

- **500 Internal Server Error**

  ```json
  {
    "error": "Error fetching job status"
  }
  ```


# Requirements:
- Java 17+

- Maven 3.9+

- Node.js 18+

- Docker 

# Authors:

- Shawn 
- Abdi 

