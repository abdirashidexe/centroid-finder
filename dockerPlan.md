Plan for making our docker image. (can optionally include a picture or diagram)
-------------------------------------------------------------------------------

Things to consider (can fill out):

1. What base docker image will you use? (Do research online and with AIs!)

Our base docker image is: node:20-bullseye

✅ Pros:
- Stable, well-supported Linux base.
- Installing Java is simple: apt-get install openjdk-17-jre.
- Most Java programs will work 100% reliably.
- Easier to debug — includes more Linux tools by default.

⚠️ Cons:
- Bigger (~300–400 MB).
- Slightly slower to build and push.

🐸 Relevance to Salamander:
- Best balance between simplicity and reliability.
- You get Node preinstalled and can easily add Java.
- Very low chance of binary or runtime issues.

2. How will you make sure that both node and Java can run in your image?
-   Its node based so that wont be an issue but we will need to install java manually which shouldn't be a big issue
-   "RUN apt-get update && apt-get install -y openjdk-17-jre-headless"


3. How will you test your Dockerfile and image?
docker images: To check if the image is installed properly
docker run -it to check if Node and Java are really working properly
docker run -p 3000:3000 salamander:latest: to check if the server will respond correctly. 

docker ps -a
docker logs <container_id>: These will be used to check logs and see which errors pop up.

docker run \
  -p 3000:3000 \
  -v "$(pwd)/videos:/videos" \
  -v "$(pwd)/results:/results" \
  salamander:latest: This will check if videos are coming up properly and if the csv files are accurrate and getting created properly 


4. How will you make sure the endpoints are available outside the image?

We cna make sure the endpoints are available by:

- Adding EXPOSE 3000 to the Dockerfile (since the Express app runs on port 3000).
- Running the container with docker run -p 3000:3000 to map (or connect) the container’s port 3000 to our local machine’s port 3000.
- Testing access by visiting http://localhost:3000/api/videos in the browser or Postman.

Reminder in the command "docker run -p 3000:3000", it means:
EXTERNAL : INTERNAL
  3000   :   3000

5. How will your code know where to access the video/results directory? Hint: environment variables and volumes. (We'll talk about volumes on Thursday)

6. How can you make you docker image small, cacheable, and quick to make changes to?
*node:20-bullseye-slim*
-   We plan on using the full version of bullseye and we plan on changing it to slim once we see everything is working 
-   Once we find out the image is reliable we will change it to slim and add any dependecies that we will need later

