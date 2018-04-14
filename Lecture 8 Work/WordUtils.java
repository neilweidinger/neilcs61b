public class WordUtils {
    public static void main(String[] args) {
        SLList<String> list = new SLList<>();
        list.addFirst("a");
        list.addFirst("ab");
        list.addFirst("abc");
        list.addFirst("ab");
        list.addFirst("a");

        System.out.println(WordUtils.longestString(list));
    }

    public static String longestString(SLList<String> list) {
        String max = "";

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).length() > max.length()) {
                max = list.get(i);
            }
        }

        return max;
    }
}