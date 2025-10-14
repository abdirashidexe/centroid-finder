import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.junit.jupiter.api.Test;

public class dfsBinaryGroupFinderTest {

    @Test
    void testSingleGroup() {
        int[][] image = {
            {1, 1, 0},
            {1, 0, 0},
            {0, 0, 0}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        int result = 3;
        assertEquals(result, finder.findConnectedGroups(image));
    }

        @Test
    void testDFScoordinates() {

            // Input image
        int[][] image = {
            {1, 1, 0},
            {1, 0, 0},
            {0, 0, 0}
        };

        boolean[][] visited = new boolean[image.length][image[0].length];

        //DfsBinaryGroupFinder dfs = new DfsBinaryGroupFinder();

        // Run DFS starting at top-left (0, 0)
        List<int[]> result = DfsBinaryGroupFinder.returnGroups(image, visited, 0, 0);

        // Expected coordinates of connected 1’s
        List<int[]> expectedCoords = List.of(
            new int[]{0, 0},
            new int[]{0, 1},
            new int[]{1, 0}
        );

        assertEquals(expectedCoords, result);
    }
}