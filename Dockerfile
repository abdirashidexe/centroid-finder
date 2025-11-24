FROM timbru31/java-node:21-jdk-20

RUN apt-get update && apt-get install -y ffmpeg \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Work inside /server
WORKDIR /server

# Copy ONLY server package files first
COPY server/package*.json ./

RUN npm install

# Copy the actual server folder
COPY server/. .

# Copy processor directory so it appears as /server/processor
COPY processor ./processor

EXPOSE 3000

CMD ["npm", "start"]
