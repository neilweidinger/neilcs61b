import org.junit.Test;
import static org.junit.Assert.*;

public class RotatingSLListTest {

    @Test
    public void testRemoveLast() {
        SLList<Integer> listA = new SLList<>();
        listA.addLast(1);
        listA.addLast(2);
        listA.addLast(3);
        listA.removeLast();

        SLList<Integer> listB = new SLList<>();
        listB.removeLast();

        SLList<Integer> expA = new SLList<>();
        expA.addLast(1);
        expA.addLast(2);

        SLList<Integer> expB = new SLList<>();

        assertEquals(expA, listA);
        assertEquals(expB, listB);
    }

    @Test
    public void testRotateRight() {
        RotatingSLList<Integer> listA = new RotatingSLList<>();
        listA.addLast(1);
        listA.addLast(2);
        listA.addLast(3);
        listA.rotateRight();

        RotatingSLList<Integer> listB = new RotatingSLList<>();
        listB.rotateRight();

        RotatingSLList<Integer> expA = new RotatingSLList<>();
        expA.addLast(3);
        expA.addLast(1);
        expA.addLast(2);

        RotatingSLList<Integer> expB = new RotatingSLList<>();

        assertEquals(expA, listA);
        assertEquals(expB, listB);
    }
}
