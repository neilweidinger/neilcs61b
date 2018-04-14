public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> chars = new LinkedListDeque<>();

        for (char c : word.toCharArray()) {
            chars.addLast(c);
        }

        return chars;
    }

    public boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    private boolean isPalindrome(Deque<Character> word) {
        if (word.size() == 1 || word.size() == 0) {
            return true;
        }
        if (word.removeFirst() == word.removeLast()) {
            return isPalindrome(word);
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    private boolean isPalindrome(Deque<Character> word, CharacterComparator cc) {
        if (word.size() == 1 || word.size() == 0) {
            return true;
        }
        if (cc.equalChars(word.removeFirst(), word.removeLast())) {
            return isPalindrome(word, cc);
        }
        return false;
    }
}
