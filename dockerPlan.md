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

4. How will you make sure the endpoints are available outside the image?

5. How will your code know where to access the video/results directory? Hint: environment variables and volumes. (We'll talk about volumes on Thursday)

6. How can you make you docker image small, cacheable, and quick to make changes to?

