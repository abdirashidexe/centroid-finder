# Refactoring code
What improvements can you make to the design/architecture of your code?
- Having the csv files be outputted in one folder rather than having them be created and dumped in the target folder

How can you split up large methods or classes into smaller components?
- As for the processor, I think we did really well on splitting up our methods and classes into smaller components. That was a goal of ours we tried to hit from the get-go.

Are there unused files/methods that can be removed?
- Deleting and finding out why it keeps duplicating package.json, as well as the input and output samples

Where would additional Java interfaces be appropriate?
- Not any that we can think of at the moment.

How can you make things simpler, more-usable, and easier to maintain?
- Creating a controller in our server to handle some of the logic in the server splitting up some things in the route 

Other refactoring improvements?
- Similar to the first improvement with the CSVs, just an overall cleare file system and structure (having a notes folder, adding a controllers folder, etc.)

# adding tests
What portions of your code are untested / only lightly tested?
- The frontend is lightly tested compared to the backend.

Where would be the highest priority places to add new tests?
- The highest priority places would be:
1. front end (all the routes using extensive postman testing)
2. backend: specifically VideoTestFinder.java

Other testing improvements?
- None besdies those core ones.

# improving error handling
What parts of your code are brittle? 
- I would assume parts of the video processing back-end because for bigger videos the run time takes a long time 

Where could you better be using exceptions?
- Some error handling in the api isn't very specifc on what's wrong 

Where can you better add input validation to check invalid input?
- In the backend and adding error handling more user friendly

How can you better be resolving/logging/surfacing errors? Hint: almost any place you're using "throws Exception" or "catch(Exception e)"should likely be improved to specify the specific types of exceptions that might be thrown or caught.
- Everything in the api would need to be improved and more user friendly

Other error handling improvements?
- 


# writing documentation
What portions of your code are missing Javadoc/JSdoc for the methods/classes? 
- More javadoc for the back end in the video portions

What documentation could be made clearer or improved? 
- In some of the classes that we didn't implement ourselves, there's not much documentation there (in the backend the files we didn't have to work on)

Are there sections of dead code that are commented out?
- test cases that were never used for the video

Where would be the most important places to add documentation to make your code easier to read? 
- The video processor and summary for the back end. For the front end the index.js and the api files

Other documentation improvements?
- Having a document with the location and structure of the folders and files would be good. 

# improving performance (optional)
What parts of your code / tests / Docker image run particularly slowly?
- The video processor in particular is really slow for bigger videos

What speed improvements would most make running / maintaining your code better?
- Either using a different video processor or using a lighter version of our docker image if there's one avilable

Other performance improvements?
- Looking closely at out video processor file 

# hardening security (optional)
What packages / images are out of date / have security issues?
- 

Where could you have better input validation in your code to prevent malicious use?
- 

Other security improvements?
- 

# bug fixes (optional)
What bugs do you know exist?
- 

What parts of the code do you think might be causing them?
- 

Other bug fix improvements?
- 

# other
Any other improvements in general you could make?
- 

