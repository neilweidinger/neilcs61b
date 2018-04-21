public class Add implements ComFunc {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println(sumAll(nums));
    }

    public int apply(int a, int b) {
        return a + b;
    }

    public static int sumAll(int [] x) {
        return Combine.combine(new Add(), x);
    }
}