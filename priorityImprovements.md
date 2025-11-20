# place the top two improvements you think would be most important in each of the below categories:
# (can copy-paste directly from the human / robot improvement files)

refactoring code (required)
- priority improvement #1: We could change the video processing we're using from Jcodec into ffmpeg. Because docker already has it installed it would allow cpu and processing to run faster than before if we make this big change. 
[
    Pros:

    - native ffmpeg is highly optimized (multithreaded, SIMD, hardware accel) → much faster frame extraction/decoding than pure-Java JCodec.

    - battle-tested CLI with predictable behavior and mature error reporting on stderr

    - many wrappers (fluent-ffmpeg, ffmpeg-static) and wide community examples for transformations and filters

    Cons:

    - switching from in-process Java library to external process changes error handling, testing, and data flow; requires robust child-process management (timeouts, streaming, parsing stderr).


    - External dependency: requires shipping/hosting an ffmpeg binary (versioning, compatibility). Increases deployment complexity.


    - Loss of pure-Java portability: previously “pure Java” was self-contained; now you must ensure compatible binary on all environments.
]


- priority improvement #2: Make server job/process handling robust
- Files: server/routes/process.js, server/jobs.js, index.js
- What to do: stop detaching/ignoring child process output; capture stdout/stderr and stream into job logs; persist job state instead of only in-memory (simple file or sqlite/Redis). Make route handlers thin and move heavy work into a service module.
- Why: enables reliable retries, debugging, and recovery on restart.
- Category: refactoring code


adding tests (required)
- priority improvement #1: Unit tests for core algorithm classes. Adding edge cases and more thourough testing to ensure the logic is solid. 

[location: EuclideanColorDistance, DfsBinaryGroupFinder, DistanceImageBinarizer, CentroidTracker, CsvWriter]



- priority improvement #2: (front end focus)
- Files: processor/src/main/java/... (EuclideanColorDistance.java, DfsBinaryGroupFinder.java, DistanceImageBinarizer.java, CsvWriter.java) and server routes (server/routes/*.js)
- What to do: write unit tests for deterministic pure logic (color distance, group finder). Add server route tests mocking child process spawn to validate job lifecycle.
- Why: prevents regressions and satisfies CI.
- Category: adding tests


improving error handling (required)
- Location [Process.js, jobs.js, index.js]
- priority improvement #1: - Replcaing generic catch-all exceptions with specific exception types and clear messages;

- Location: processor classes (FrameAnalyzer.java, - VideoProcessor.java, VideoSummaryApp.java, CsvWriter.java)
- priority improvement #2: 
- What to do: define specific, small custom exception types for expected failure modes (e.g., InvalidVideoException, FrameReadException, OutputWriteException, ResourceLimitException). Throw those where failures occur, and centralize handling in the app entrypoint (VideoSummaryApp) so every error:
-- is logged with context (job id / filename / frame index),
-- triggers safe cleanup of resources (close FrameGrab, streams),
-- results in a clear non-zero exit code and a machine-readable error payload on stderr (simple JSON with code/message/details) so the server can parse and surface the cause to users.
- Why: makes failures discoverable and actionable for callers (the server) and for CI/automation; avoids ambiguous stack traces and ensures resources are always closed. Add unit tests that assert the processor emits the correct exit code and error payload for common failure cases (corrupt video, unwritable output path, out-of-memory simulation).


writing documentation (required)
- priority improvement #1: Updating the README.md and deleting the waves and instead  outputting the file and folder structure of our project. Showing us the front end portions and the backend portions as well. Also giving a description of our project and each file purpose.

- priority improvement #2: More javadoc for the back end in the video portions
- Specifically:
1. CentroidTracker.java
2. CsvWriter.java
3. 2 methods in DfsBinaryGroupFinder.java
4. EuclideanColorDistance.java
5. FrameAnalyzer.java
6. FrameData.java
7. VideoProcessor.java
8. VideoSummaryApp.java


improving performance (optional)

hardening security (optional)

bug fixes (optional)

other (optional)