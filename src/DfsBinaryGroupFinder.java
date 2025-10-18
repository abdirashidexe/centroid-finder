import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DfsBinaryGroupFinder implements BinaryGroupFinder {
   /**
    * Finds connected pixel groups of 1s in an integer array representing a binary image.
    * 
    * The input is a non-empty rectangular 2D array containing only 1s and 0s.
    * If the array or any of its subarrays are null, a NullPointerException
    * is thrown. If the array is otherwise invalid, an IllegalArgumentException
    * is thrown.
    *
    * Pixels are considered connected vertically and horizontally, NOT diagonally.
    * The top-left cell of the array (row:0, column:0) is considered to be coordinate
    * (x:0, y:0). Y increases downward and X increases to the right. For example,
    * (row:4, column:7) corresponds to (x:7, y:4).
    *
    * The method returns a list of sorted groups. The group's size is the number 
    * of pixels in the group. The centroid of the group
    * is computed as the average of each of the pixel locations across each dimension.
    * For example, the x coordinate of the centroid is the sum of all the x
    * coordinates of the pixels in the group divided by the number of pixels in that group.
    * Similarly, the y coordinate of the centroid is the sum of all the y
    * coordinates of the pixels in the group divided by the number of pixels in that group.
    * The division should be done as INTEGER DIVISION.
    * The groups are sorted in DESCENDING order according to Group's compareTo method.

    [0,1,0,0],
    [1,1,0,0],
    [0,0,1,0],
    [0,0,1,1]

    for the top left group: 

    row:0,col:1
    row:1,col:0
    row:1,col:1

    so x in this groups coordinate(x,y) is: 0 + 1 + 1 / 3 (sum of all x coor. / # of pixels in group)

    * 
    * @param image a rectangular 2D array containing only 1s and 0s
    * @return the found groups of connected pixels in descending order
    */
    @Override
    public List<Group> findConnectedGroups(int[][] image) {    
        if (image == null || image.length == 0) {
            throw new NullPointerException("the array or any of its subarrays are null");
        }
        if (image.length < 0 || image[0].length < 0) {
            throw new IllegalArgumentException("The array is invalid");
        }

        int groupIndex = 0;
        int rows = image.length;
        int cols = image[0].length;
        boolean[][] visited = new boolean[rows][cols];
        
        //map to hold the coordinates of each group | key: group index, value: list of coordinates for that group in array form
        HashMap<Integer, List<int[]>> coordinateMap = new HashMap<>();      

        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if (image[r][c] == 1 && !visited[r][c])
                {
                    List<int[]> connectedPixels = new ArrayList<>();

                    coordinateMap.put(groupIndex,returnGroupList(image, visited, r, c, connectedPixels));
                    groupIndex++;
                }
            }
        }

        // {0, [[1,0], 1,1], [0,1]}

        List<Group> officalListOfGroups = new ArrayList<Group>();
        for (int group : coordinateMap.keySet()) {

            int groupSize = coordinateMap.get(group).size();

            int[] centroidCoordinates = calculateCentroid(coordinateMap.get(group));
            Coordinate centroid = new Coordinate(centroidCoordinates[0], centroidCoordinates[1]);

            Group newGroup = new Group(groupSize, centroid);
            officalListOfGroups.add(newGroup);
        }

        return officalListOfGroups;
    }

    /* [0,1,0,0],
       [1,1,0,0],
       [0,0,1,0],
       [0,0,1,1] */

    /**
     * Group method is supposed to get all the coordinates of the group that we want and return them after getting the 
     * centroid and size. 
     * 
     * -------------------------------------------GOALS------------------------------------------------
     * Find a way to obtain the size and centroid 
     * 
     * Find a way to traverse through the pixels and get only the connected connected pixels 
     * ------------------------------------------------------------------------------------------------
     * (Maybe using another helper method that will take in the coordinates given and convert the centroid)
     * 
     * (Study different dfs code to find how to traverse )
     */
    public static List<int[]> returnGroupList(int[][] image, boolean[][] visited, int r, int c, List<int[]> newConnectedPixels)
    {
        if(r < 0 || c < 0 ||r >= image.length || c >= image[0].length || image[r][c] == 0 || visited[r][c] == true) return newConnectedPixels;

        visited[r][c] = true;

        newConnectedPixels.add(new int[]{r,c});
        
        returnGroupList(image, visited, r, c + 1, newConnectedPixels);    // right
        returnGroupList(image, visited, r + 1, c, newConnectedPixels);    // down
        returnGroupList(image, visited, r - 1, c, newConnectedPixels);    // up
        returnGroupList(image, visited, r, c - 1, newConnectedPixels);    // left
        
        return newConnectedPixels;
    }

    public static int[] calculateCentroid(List<int[]> groupList) {

        int xTotals = 0;
        int yTotals = 0;
        int numOfPixelsInGroup = groupList.size(); // error here: testing returning 0

        // add x coordinates
        for (int[] coordinates : groupList) {
            xTotals += coordinates[0];
        }

        // add y coordinates
        for (int[] coordinates : groupList) {
            yTotals += coordinates[1];
        }

        int centroidX = xTotals/numOfPixelsInGroup;
        int centroidY = yTotals/numOfPixelsInGroup;

        int[] centroid = {centroidX,centroidY};

        return centroid;
    }
}
