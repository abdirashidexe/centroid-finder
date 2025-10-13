import java.util.ArrayList;
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

        Set<Integer> myVisited = new HashSet<>();
        int rows = image.length;
        int cols = image[0].length;

        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if (image[r][c] == 1 && !myVisited.contains(image[r][c]))
                {
                    findConnectedGroupsHelper(image, myVisited, rows, cols);
                }
            }
        }

        return null;
    }

    public List<Group> findConnectedGroupsHelper(int[][] image, Set<Integer> visited, int rows, int cols)
    {
        List<Group> connectedPixels = new ArrayList<>();
        
        // implement code here

        return connectedPixels;
    }
    
}
