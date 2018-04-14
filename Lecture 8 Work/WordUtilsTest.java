import org.junit.Test;
import static org.junit.Assert.*;

public class WordUtilsTest {
    
    @Test
    public void testLongestString() {
        SLList<String> listA = new SLList<>();
        listA.addFirst("a");
        listA.addFirst("ab");
        listA.addFirst("abc");
        listA.addFirst("ab");
        listA.addFirst("a");

        SLList<String> listB = new SLList<>();
        listB.addFirst("neil");

        SLList<String> listC = new SLList<>();

        String expA = "abc";
        String expB = "neil";
        String expC = "";

        assertEquals(expA, WordUtils.longestString(listA));
        assertEquals(expB, WordUtils.longestString(listB));
        assertEquals(expC, WordUtils.longestString(listC));
    }
}
