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

This class also has a method that returns the centroid coordinates of location of the image

EuclideanColorDistance purpose: 
Intakes two colors in hex and calculates the distance between two hex RGB colors using a formula:
sqrt((r1 - r2)^2 + (g1 - g2)^2 + (b1 - b2)^2)

DistanceImage purpose:
This explains what we use the distance for between the images for. It obtains the distance between the target and the pixels using the euclidean 
distance formula. Target (the notepads/salamander) pixels(total image)

this class also returns an array that will determine whether it will be converted to black or white. 

DfsBinaryGroupFinder purpose:
This class has a method findConnectedGroups() that gets a binary image array and runs a dfs through it to find the connected groups of 1s. It will be using the Group record.

location purpose:
location of the image represented as an x y array andy y increases downward( for some reason) and x increases to the right

Color distance finder purpose: 
This is an interface for euclidean color distance and the purpose is to find and compute the distance between two colors 

BinaryGroupFinder purpose:
This is an interface for dfsBinaryGroupFinder and the purpose is for it to be used and implemented by other classes to return a list of groups of connected pixels, specifically in descending order (i think the groups with the least conneceted pixels would be at the bottom of the list).

BinarizingImageGroupFinder purpose:
This class uses a binarized image to get the connected groups of white pixels (which would be represented as 1) it has a method that is meant to convert the input image as a binarized image then sorts it as a list in groups in descending order 