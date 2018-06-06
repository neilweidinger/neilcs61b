import java.util.*;

public class ListExercise {
    public static List<String> getWords(String inputFileName) {
        In in = new In(inputFileName);
        List<String> list = new ArrayList<>();

        while (!in.isEmpty()) {
            list.add(in.readString());
        }
        
        return list;
    }

    public static int countUniqueWords(List<String> words) {
        Set<String> ss = new HashSet<>();

        for (String word : words) {
            ss.add(word);
        }

        return ss.size();
    }

    public static Map<String, Integer> collectWordCount(List<String> targets, List<String> words) {
        Map<String, Integer> counts = new HashMap<String, Integer>();

        for (String t : targets) {
            counts.put(t, 0);
        }

        for (String s : words) {
            if (counts.containsKey(s)) {
                counts.put(s, counts.get(s) + 1);
            }
        }

        return counts;
    }
}
