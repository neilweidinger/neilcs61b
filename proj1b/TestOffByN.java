import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testOffByNIsPalindrome() {
        Palindrome palindrome = new Palindrome();
        CharacterComparator ccByTwo = new OffByN(2);

        assertFalse(palindrome.isPalindrome("cat", ccByTwo));
        assertFalse(palindrome.isPalindrome("racecar", ccByTwo));
        assertFalse(palindrome.isPalindrome("Racecar", ccByTwo));
        assertTrue(palindrome.isPalindrome("a", ccByTwo));
        assertTrue(palindrome.isPalindrome("", ccByTwo));
        assertTrue(palindrome.isPalindrome("adzfc", ccByTwo));
        assertTrue(palindrome.isPalindrome("xhmjz", ccByTwo));
    }
}
