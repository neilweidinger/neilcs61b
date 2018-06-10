package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testDequeue() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        arb.enqueue("first");
        arb.enqueue("second");

        String returnVal = arb.dequeue();

        assertEquals("first", returnVal);
        assertEquals(1, arb.fillCount());
        assertEquals(5, arb.capacity());
    }

    @Test
    public void testEnqueue() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        arb.enqueue("first");
        arb.enqueue("second");

        assertEquals("first", arb.peek());
        assertEquals(2, arb.fillCount());
        assertEquals(5, arb.capacity());
    }

    @Test
    public void testFull() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        arb.enqueue("first");
        arb.enqueue("second");
        arb.enqueue("third");
        arb.enqueue("fourth");
        arb.enqueue("fifth");

        assertTrue(arb.isFull());
        assertFalse(arb.isEmpty());
    }

    @Test
    public void testEmpty() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
    }

    @Test
    public void testIteration() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<>(5);
        String [] words = {
                "first",
                "second",
                "third",
                "fourth",
                "fifth"
        };

        for (String s : words) {
            arb.enqueue(s);
        }

        int index = 0;
        for (String s : arb) {
            assertEquals(words[index], s);
            index++;
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
