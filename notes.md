ImageSummarry purpose: 

Intakes the input image that we are working with

Works with "target color" and utilizes Euclidean distance to convert to binary array

Utilizes the matrix at some point to traverse through the pixels

Euclidean distance: this means the distance between two colors using hex numbers. 

The 5th step that clarifies pixels are connected vertically and horizontally, not diagonally, my partner and I connected that to the dfs matrix concept. We will likely be using that during that step.


ImageGroupFinder purpose: 
This returns a list of a "group of pixels" which is divided by coordinates of arrays 

ImageBinarizer purpose:
This takes in a buffered image and we're meant to return coordinates that are either 0 or 1. The purpose for this entire class is to convert the image to represent white (xFFFFFF) as the notes and black (x000000) as the background.

Group.java purpose: 
Gives us the coordinates of how the image sample input is structured.  