# Server-PLAN
Creating an express webserver that can handle requests to have videos processed and make the results available
We will need to implement Salamander API (No idea how to do this yet)

## First Step:
Get the api and have the app listen for the server to start 

## Second Step:
(We might have this in a route path) implementing the path to the server. Have the app use the videos we have in the java file and connect the routes 


## Third Step:
implement the route to check if the video exists and return a json list of filenames 

## Fourth Step:
implement the route to that checks returns a captured frame from the user. 

## Fifth Step:
Run Java Jar in the background using child process. This lets java process seprately from express 

## Sixth Step:
After processing the status should output done or complete and have post the results