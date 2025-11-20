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


- priority improvement #2: 


adding tests (required)
- priority improvement #1: Unit tests for core algorithm classes. Adding edge cases and more thourough testing to ensure the logic is solid. 

[location: EuclideanColorDistance, DfsBinaryGroupFinder, DistanceImageBinarizer, CentroidTracker, CsvWriter]



- priority improvement #2: 


improving error handling (required)
- Location [Process.js, jobs.js, index.js]
- priority improvement #1: - Replcaing generic catch-all exceptions with specific exception types and clear messages;

- priority improvement #2: 


writing documentation (required)
- priority improvement #1: Updating the README.md and deleting the waves and instead  outputting the file and folder structure of our project. Showing us the front end portions and the backend portions as well. Also giving a description of our project and each file purpose.

- priority improvement #2: 


improving performance (optional)

hardening security (optional)

bug fixes (optional)

other (optional)