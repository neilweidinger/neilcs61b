public class Sort {
    public static void main(String[] args) {
        String [] arr = {"cows", "dwell", "above", "clouds"};
        System.out.print("Og:  ");
        printArray(arr);

        Sort.sort(arr);
        System.out.print("New: ");
        printArray(arr);
    }

    public static void sort(String[] x) {
        for (int j = 0; j < x.length; j++) {
            int index = j;
            String small = x[j];
            for (int i = j + 1; i < x.length; i++) {
                if (x[i].compareTo(small) < 0) {
                    small = x[i];
                    index = i;
                }
            }
            String temp = x[j];
            x[j] = small;
            x[index] = temp;
        }
    }

    public static void printArray(String[] x) {
        for (String S : x) {
            System.out.print(S + " ");
        }
        System.out.println("");
    }
}
