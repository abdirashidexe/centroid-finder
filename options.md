Option 1: JCodec
Pros:
1. Pure Java, isn't an external program. We simply add via Maven and start working immediately.
2. Easily get individual frames from a video as BufferedImage objects (other libraries are hheavier and setup-intensive)

Cons:
1. There's no built=in audio processing, complex filters, or effects
2. Metadata has basic info only - this means it doesn't give detailed codec settings


Option 2: Javafx
Pros:
1. Visually its really modern and it will look better than most other programs 
2. built in methods for play(), pause(), and stop()

Cons:
1. There will be some bugs and we would need to manually add javaFX modules 
2. Not optimized for real time video processing and you can't record, edit, or convert videos 


Option 3: vlcj
Pros:
1. 
2. 

Cons:
1. 
2. 