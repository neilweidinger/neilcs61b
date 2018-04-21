public class Combine {
    public static void main(String[] args) {
        ComFunc cf = new Add();
        int[] nums = {1, 2, 3, 4};
        System.out.println(combine(cf, nums));
    }

    public static int combine(ComFunc f, int[] x) {
        if (x.length == 0) {
            return 0;
        }
        if (x.length == 1) {
            return x[0];
        }

        return f.apply(x[0], h(f, x, 1));
    }

    private static int h(ComFunc f, int[] x, int index) {
        if (index == x.length - 1) {
            return x[index];
        }

        return f.apply(x[index], h(f, x, index + 1));
    }
}
