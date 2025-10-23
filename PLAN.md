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