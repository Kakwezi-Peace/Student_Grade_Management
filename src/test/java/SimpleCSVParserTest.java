

import org.junit.jupiter.api.Test;
import service.SimpleCSVParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCSVParserTest {

    private final SimpleCSVParser parser = new SimpleCSVParser();

    @Test
    void testParseLinesReturnsEmptyListForNullInput() {
        List<String[]> result = parser.parseLines(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseLinesSkipsEmptyAndWhitespaceLines() {
        String csv = "\n   \n , , , \n";

        List<String[]> result = parser.parseLines(csv);

        // Should ignore blank lines & lines that don't have exactly 4 values
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseLinesParsesValidFourColumnLines() {
        String csv =
                "A,B,C,D\n" +
                        " 1 , 2 , 3 , 4 \n" +
                        "x,y,z,w";

        List<String[]> result = parser.parseLines(csv);

        assertEquals(3, result.size());

        assertArrayEquals(new String[]{"A", "B", "C", "D"}, result.get(0));
        assertArrayEquals(new String[]{"1", "2", "3", "4"}, result.get(1));
        assertArrayEquals(new String[]{"x", "y", "z", "w"}, result.get(2));
    }

    @Test
    void testParseLinesSkipsLinesWithWrongColumnCount() {
        String csv =
                "A,B,C\n" +        // 3 columns → skip
                        "1,2,3,4,5\n" +    // 5 columns → skip
                        "x,y,z,w";         // 4 columns → accepted

        List<String[]> result = parser.parseLines(csv);

        assertEquals(1, result.size());
        assertArrayEquals(new String[]{"x", "y", "z", "w"}, result.get(0));
    }

    @Test
    void testParseLinesTrimsSpacesProperly() {
        String csv = "  A ,  B , C ,  D   ";

        List<String[]> result = parser.parseLines(csv);

        assertEquals(1, result.size());
        assertArrayEquals(new String[]{"A", "B", "C", "D"}, result.get(0));
    }
}
