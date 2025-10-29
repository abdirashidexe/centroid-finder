Goal:
Loop through a video
Obtain each frame in the video 
return a CSV file of [time, x, y] (Where there is the BIGGEST connected groups)


Steps:
Create a videoSummarryApp 
1 Step implement looping logic
2 Use DfsBinary, ImageGroupFinder, and ImageSummary to have the logic of to get the groups needed
3 Insert criteria to return the biggest x and y
4 Have the videoSummaryApp return the biggest x and y from the groups CSV
5 

----

Plan for next meeting:

1. Ask AI how to go about this: Usage: java ImageSummaryApp <input_image> <hex_target_color> <threshold>
2. Combining imageSummary to fit video frames
3. Get a new simple video - square moving from AI

----

To validate that it's actually tracking the salamander, we used a few different videos. The bouncing ball video showed that it accurately outputs (-1,-1) when it's off camera and the bug walking video accurately shows the coordinates increasing in the right direction until they eventually become (-1,-1) because the bug goes off camera.

For the salamander: We cropped the salamander video because it would take about an hour to run the entire 8 minute video. It was cropped to 14 seconds.

Cropped Salamander Video Test command: mvn clean compile exec:java -Dexec.args="src/main/resources/videos/Salamander.mp4 output.csv 64000F 50"

To test the full 8 minute one, we would channge Salamander.mp4 to ensantina.mp4 (WARNING: may take 1hr to run)