# Top Two Improvements per Category.

-----------------------------
## Refactoring code (required)
-----------------------------
### Priority improvement #1: *IMPROVEMENT #1*
Make a csvFile container for the csv outputs that the program generates and incorporating better naming conventions for them as well. We can name them based on the video it is.

### Priority improvement #2: *IMPROVEMENT #2*
Make server job/process handling robust
- Files: server/routes/process.js, server/jobs.js, index.js
- What to do: stop detaching/ignoring child process output; capture stdout/stderr and stream into job logs; persist job state instead of only in-memory (simple file or sqlite/Redis). Make route handlers thin and move heavy work into a service module.
- Why: enables reliable retries, debugging, and recovery on restart.
- Category: refactoring code


-----------------------------
## Adding tests (required)
-----------------------------
### Priority improvement #1: *IMPROVEMENT #3*
Unit tests for core algorithm classes. Adding edge cases and more thourough testing to ensure the logic is solid. 
- Location: EuclideanColorDistance, DfsBinaryGroupFinder, DistanceImageBinarizer, CentroidTracker, CsvWriter

### Priority improvement #2: 
- Files: processor/src/main/java/... (EuclideanColorDistance.java, DfsBinaryGroupFinder.java, DistanceImageBinarizer.java, CsvWriter.java) and server routes (server/routes/*.js)
- What to do: write unit tests for deterministic pure logic (color distance, group finder). Add server route tests mocking child process spawn to validate job lifecycle.
- Why: prevents regressions and satisfies CI.
- Category: adding tests

-----------------------------
## Improving error handling (required)
-----------------------------
### Priority improvement #1: *IMPROVEMENT #4*
- Location [Process.js, jobs.js, index.js]
- Replacing generic catch-all exceptions with specific exception types and clear messages;

### Priority improvement #2: 
- Location: processor classes (FrameAnalyzer.java, - VideoProcessor.java, VideoSummaryApp.java, CsvWriter.java)
- What to do: define specific, small custom exception types for expected failure modes (e.g., InvalidVideoException, FrameReadException, OutputWriteException, ResourceLimitException). Throw those where failures occur, and centralize handling in the app entrypoint (VideoSummaryApp) so every error:
--> is logged with context (job id / filename / frame index),
--> triggers safe cleanup of resources (close FrameGrab, streams),
--> results in a clear non-zero exit code and a machine-readable error payload on stderr (simple JSON with code/message/details) so the server can parse and surface the cause to users.
- Why: makes failures discoverable and actionable for callers (the server) and for CI/automation; avoids ambiguous stack traces and ensures resources are always closed. Add unit tests that assert the processor emits the correct exit code and error payload for common failure cases (corrupt video, unwritable output path, out-of-memory simulation).

-----------------------------
## Writing documentation (required)
-----------------------------
### Priority improvement #1: *IMPROVEMENT #5*
Updating the README.md and deleting the waves and instead  outputting the file and folder structure of our project. Showing us the front end portions and the backend portions as well. Also giving a description of our project and each file purpose.

### Priority improvement #2: *IMPROVEMENT #6*
More javadoc for the back end in the video portions
- Specifically:
1. CentroidTracker.java
2. CsvWriter.java
3. 2 methods in DfsBinaryGroupFinder.java
4. EuclideanColorDistance.java
5. FrameAnalyzer.java
6. FrameData.java
7. VideoProcessor.java
8. VideoSummaryApp.java


## Improving performance (optional)

## Hardening security (optional)

## Bug fixes (optional)
Potential improvement: dupliate package json files.

## Other (optional)