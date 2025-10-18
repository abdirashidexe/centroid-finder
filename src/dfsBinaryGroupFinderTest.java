import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class dfsBinaryGroupFinderTest {

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
public void testFindConnectedGroups_MultipleGroupsInImage() {
    int[][] image = {
        {0,1,0,0},
        {1,1,0,0},
        {0,0,1,0},
        {0,0,1,1}
    };

    DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
    List<Group> groups = finder.findConnectedGroups(image);

    assertNotNull(groups);
    assertEquals(2, groups.size()); // ✅ two connected components

    // Optional: verify group sizes
    assertEquals(3, groups.get(0).size()); // top-left cluster
    assertEquals(3, groups.get(1).size()); // bottom-right cluster
}

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

    @Test
    public void testNullArrayThrowsException() {
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        assertThrows(NullPointerException.class, () -> finder.findConnectedGroups(null));
    }

    @Test
    public void testNullSubArrayThrowsException() {
        int[][] image = { null, {1, 0} };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        assertThrows(NullPointerException.class, () -> finder.findConnectedGroups(image));
    }

    @Test
    public void testEmptyArrayThrowsException() {
        int[][] image = new int[0][0];
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        assertThrows(IllegalArgumentException.class, () -> finder.findConnectedGroups(image));
    }

    @Test
    public void testSinglePixelOne() {
        int[][] image = {
            {1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);
        assertEquals(1, groups.size());
        assertEquals(1, groups.get(0).size());
        assertEquals(0, groups.get(0).centroid().x());
        assertEquals(0, groups.get(0).centroid().y());
    }

    @Test
    public void testSinglePixelZero() {
        int[][] image = {
            {0}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);
        assertTrue(groups.isEmpty());
    }

    @Test
    public void testHorizontalConnection() {
        int[][] image = {
            {1, 1, 1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);
        assertEquals(1, groups.size());
        Group g = groups.get(0);
        assertEquals(3, g.size());
        assertEquals(1, g.centroid().x()); // avg of (0+1+2)/3 = 1
        assertEquals(0, g.centroid().y());
    }

    @Test
    public void testVerticalConnection() {
        int[][] image = {
            {1},
            {1},
            {1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);
        assertEquals(1, groups.size());
        Group g = groups.get(0);
        assertEquals(3, g.size());
        assertEquals(0, g.centroid().x());
        assertEquals(1, g.centroid().y()); // avg of (0+1+2)/3 = 1
    }

    @Test
    public void testTwoSeparateGroups() {
        int[][] image = {
            {1, 0, 1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);
        assertEquals(2, groups.size());

        // Each pixel is its own group
        Group g1 = groups.get(0);
        Group g2 = groups.get(1);

        assertEquals(1, g1.size());
        assertEquals(1, g2.size());
    }

    @Test
    public void testComplexShapeMultipleGroups() {
        int[][] image = {
            {1, 1, 0, 0},
            {1, 0, 0, 1},
            {0, 0, 1, 1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(3, groups.size());

        // Expected group sizes: 3, 2, 2
        assertEquals(3, groups.get(0).size());
        assertEquals(2, groups.get(1).size());
        assertEquals(2, groups.get(2).size());
    }

    @Test
    public void testCentroidIntegerDivision() {
        int[][] image = {
            {1, 1, 0},
            {0, 1, 0}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        Group g = groups.get(0);

        // pixels at (0,0), (1,0), (1,1)
        // centroidX = (0+1+1)/3 = 0 (int division)
        // centroidY = (0+0+1)/3 = 0
        assertEquals(0, g.centroid().x());
        assertEquals(0, g.centroid().y());
    }

    @Test
    public void testNoDiagonalConnection() {
        int[][] image = {
            {1, 0},
            {0, 1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);
        assertEquals(2, groups.size());
        assertEquals(1, groups.get(0).size());
        assertEquals(1, groups.get(1).size());
    }

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
}