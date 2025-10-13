import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    *
    * The groups are sorted in DESCENDING order according to Group's compareTo method
    * (size first, then x, then y). That is, the largest group will be first, the 
    * smallest group will be last, and ties will be broken first by descending 
    * y value, then descending x value.

    [0,1,0,0],
    [1,1,0,0],
    [0,0,1,0],
    [0,0,0,1]

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

        int rows = image.length;
        int cols = image[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> connectedPixels = new ArrayList<>();

        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if (image[r][c] == 1 && !visited[r][c])
                {
                    // 1 is found, check neighbors for other 1's
                    //coordinates.put(group++, returnGroups(image, visited, rows, cols, connectedPixels));
                    // Group myGroup = returnGroups(image, visited, rows, cols);
                    connectedPixels.add(returnGroups(image, visited, r, c, connectedPixels));
                }
            }
        }

        List<Group> test = new ArrayList<>();
        test.add(new Group(8,new Coordinate(4,5)));
        return test;
    }
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
    public static int[] returnGroups(int[][] image, boolean[][] visited, int r, int c, List<int[]>connectedPixels)
    {
        int[] coordinates = {r,c};
        if(r < 0 || c < 0 ||r > image.length || c > image[0].length) return null;

        visited[r][c] = true;
        connectedPixels.add(new int[]{r,c});
        
        returnGroups(image, visited, r, c + 1, connectedPixels);    // right
        returnGroups(image, visited, r, c - 1, connectedPixels);    // left
        returnGroups(image, visited, r - 1, c, connectedPixels);    // up
        returnGroups(image, visited, r + 1, c, connectedPixels);    // down

        
        return coordinates;
    }
    
}
