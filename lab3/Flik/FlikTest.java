import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testEquality() {
        assertEquals(true, Flik.isSameNumber(1, 1));
        assertEquals(true, Flik.isSameNumber(127, 127));
        assertEquals(true, Flik.isSameNumber(128, 128));
    }
}
