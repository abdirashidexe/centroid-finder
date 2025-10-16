import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class dfsBinaryGroupFinderTest {

    // @Test
    // void testSingleGroup() {
    //     int[][] image = {
    //         {1, 1, 0},
    //         {1, 0, 0},
    //         {0, 0, 0}
    //     };
    //     DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
    //     int result = 3;
    //     assertEquals(result, finder.findConnectedGroups(image));
    // }

        // @Test
    // void testDFScoordinates() {

    //         // Input image
    //     int[][] image = {
    //         {1, 1, 0},
    //         {1, 0, 0},
    //         {0, 0, 0}
    //     };

    //     boolean[][] visited = new boolean[image.length][image[0].length];

    //     //DfsBinaryGroupFinder dfs = new DfsBinaryGroupFinder();

    //     // Run DFS starting at top-left (0, 0)
    //     List<int[]> result = DfsBinaryGroupFinder.returnGroupList(image, visited, 0, 0);

    //     // Expected coordinates of connected 1’s
    //     List<int[]> expectedCoords = List.of(
    //         new int[]{0, 0},
    //         new int[]{0, 1},
    //         new int[]{1, 0}
    //     );

    //     assertEquals(expectedCoords, result);
    // }

    @Test
    public void testReturnGroupList_singleConnectedPixel() {
        int[][] image = {
            {1, 0, 0},
            {0, 0, 0}
        };
        boolean[][] visited = new boolean[2][2];
        List<int[]> newConnectedPixels = new  ArrayList<>();

        List<int[]> result = DfsBinaryGroupFinder.returnGroupList(image, visited, 0, 0, newConnectedPixels);

        // ✅ Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertArrayEquals(new int[]{0, 0}, result.get(0));
    }

    @Test
    public void testReturnGroupList_multipleConnectedPixel() {
        int[][] image = {
            {1, 1, 0},
            {0, 0, 0}
        };
        boolean[][] visited = new boolean[2][2];
        List<int[]> newConnectedPixels = new  ArrayList<>();

        List<int[]> result = DfsBinaryGroupFinder.returnGroupList(image, visited, 0, 0, newConnectedPixels);

        // ✅ Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertArrayEquals(new int[]{0, 0}, result.get(0));
        assertArrayEquals(new int[]{0, 1}, result.get(1));
    }
    
    @Test
    public void testReturnGroupList_verticallyAndHorizontalMultipleConnectedPixel() {
        int[][] image = {
            {1, 1, 0},
            {1, 0, 0}
        };
        boolean[][] visited = new boolean[2][2];
        List<int[]> newConnectedPixels = new  ArrayList<>();

        List<int[]> result = DfsBinaryGroupFinder.returnGroupList(image, visited, 0, 0, newConnectedPixels);

        // ✅ Assertions
        assertNotNull(result);
        assertEquals(3, result.size());
        assertArrayEquals(new int[]{0, 0}, result.get(0));
        assertArrayEquals(new int[]{0, 1}, result.get(1));
        assertArrayEquals(new int[]{1, 0}, result.get(2));
    }

    @Test
    public void testReturnGroupList_MultipleConnectedPixelEverywhere() {
        int[][] image = {
            {0,1,0,0},
            {1,1,0,0},
            {0,0,1,0},
            {0,0,1,1}
        };
        boolean[][] visited = new boolean[100][100];
        List<int[]> newConnectedPixels = new  ArrayList<>();

        List<int[]> result = DfsBinaryGroupFinder.returnGroupList(image, visited, 0, 0, newConnectedPixels);

        // ✅ Assertions
        assertNotNull(result);
        assertEquals(3, result.size());
        //assertArrayEquals(new int[]{0, 1}, result.get(0));
    }
    // ADDING FROM HERE
    @Test
    public void testCalculateCentroid_SmallImage() {
        int[][] image = {
            {0,1,0,0}, // [0,1] [1,0] [1,1] --> X totals: 2, Y totals: 2, num of pixels:3 --> centroid[2/3, 2/3]
            {1,1,0,0},
            {0,0,1,0},
            {0,0,1,1}
        };

        boolean[][] visited = new boolean[image.length][image[0].length];
        List<int[]> newConnectedPixels = new  ArrayList<>();

        List<int[]> list = DfsBinaryGroupFinder.returnGroupList(image, visited, 0, 0, newConnectedPixels);

        int[] resultCentroid = DfsBinaryGroupFinder.calculateCentroid(list);

        assertEquals(new int[] {0,0}, resultCentroid);
    }
}
