public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    public int size() {
        if (this.rest == null) {
            return 1;
        }
        else {
            return 1 + this.rest.size();
        }
    }

    public IntList addToFront(int n) {
        IntList front = new IntList(n, this);
        return front;
    }

    public void addToBack(int n) {
        if (this.rest == null) {
            this.rest = new IntList(n, null);
        }
        else {
            this.rest.addToBack(n);
        }
    }

    public int iterativeSize() {
        IntList ptr = this;
        int count = 0;
        while (ptr != null) {
            count++;
            ptr = ptr.rest;
        }
        return count;
    }

    public int get(int i) {
        if (i == 0){
            return this.first;
        }
        return this.rest.get(i - 1);
    }

    public int iterativeGet(int i) {
        IntList ptr = this;
        int index = 0;
        while (i != index) {
            index++;
            ptr = ptr.rest;
        }
        return ptr.first;
    }

    //non-destructuve
    public static IntList incrList(IntList L, int x) {
        IntList newList = new IntList(L.first + x, null);
        L = L.rest;

        while (L != null) {
            newList.addToBack(L.first + x);
            L = L.rest;
        }

        return newList;
    }

    //destructive
    public static IntList dincrList(IntList L, int x) {
        if (L == null) {
            return null;
        }
        else {
            L.first -= x;
            L.rest = IntList.dincrList(L.rest, x);
            return L;
        }
    }

    //destructive
    public static IntList iterativeDincrList(IntList L, int x) {
        IntList ptr = L;
        while (ptr != null) {
            ptr.first += x;
            ptr = ptr.rest;
        }
        return L;

        /*
        OR YOU CAN DO THIS, SAME BASICALLY
        for (IntList ptr = L; ptr != null; ptr = ptr.rest) {
            ptr.first += x;
        }
        return L;
        */
    }

    public static IntList shorten(IntList L, int n) {
        if (n == 0) {
            return L;
        }
        L = L.rest;
        return IntList.shorten(L, n - 1);
    }

    @Override
    public String toString() {
        if (this.rest == null) {
            return "" + this.first + " -> null";
        }
        else {
            return "" + this.first + " -> " + this.rest.toString();
        }
    }

    public static void main(String[] args) {
        IntList neil = new IntList(1, null);
        for (int i = 2; i <= 10; i++) {
            neil.addToBack(i);
        }

        IntList neilTwo = new IntList(1, null);
        for (int i = 2; i <= 10; i++) {
            neilTwo = neilTwo.addToFront(i);
        }

        System.out.println("neil: "+ neil);
        System.out.println("Recursive Size: " + neil.size());
        System.out.println("Iterative Size: " + neil.iterativeSize());
        System.out.println("Element at index 3 recursively: " + neil.get(3));
        System.out.println("Element at index 3 iteratively: " + neil.iterativeGet(3));
        System.out.println("neilIncr: "+ IntList.incrList(neil, 3));
        System.out.println("neil: "+ neil);
        System.out.println("neil shortened: "+ IntList.shorten(neil, 5));
        System.out.println("neil: "+ neil);
        System.out.println("neilDincr: "+ IntList.dincrList(neil, 3));
        System.out.println("neil: "+ neil);
        System.out.println("neilDincr: "+ IntList.iterativeDincrList(neil, 3));
        System.out.println("neil: "+ neil + "\n");

        System.out.println("neilTwo " + neilTwo);
        System.out.println("Recursive Size: " + neilTwo.size());
        System.out.println("Iterative Size: " + neilTwo.iterativeSize());
        System.out.println("Element at index 3 recursively: " + neilTwo.get(3));
        System.out.println("Element at index 3 iteratively: " + neilTwo.iterativeGet(3));
    }
}