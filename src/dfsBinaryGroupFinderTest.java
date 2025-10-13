import static org.junit.jupiter.api.Assertions.*;
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
        int result = finder.findConnectedGroups(image).size()-1;
        assertEquals(result, finder.findConnectedGroups(image));
    }
}