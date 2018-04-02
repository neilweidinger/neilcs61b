public class SLList {
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            this.item = i;
            this.next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    public static void main(String[] args) {
        SLList neil = new SLList(1);

        for (int i = 2; i <= 3; i++) {
            neil.recursiveAddLast(i);
        }
        
        System.out.println(neil);
        System.out.println("Size: " + neil.size());
        System.out.println("");

        SLList neilTwo = new SLList();
        System.out.println(neilTwo);
        System.out.println("Size: " + neilTwo.size());
        System.out.println("First: " + neilTwo.getFirst());
        System.out.println("");

        for (int i = 1; i <= 2; i++) {
            neilTwo.addLast(i);
        }

        System.out.println(neilTwo);
        System.out.println("Size: " + neilTwo.size());
        System.out.println("First: " + neilTwo.getFirst());
    }

    public SLList() {
        this.sentinel = new IntNode(Integer.MIN_VALUE, null);
        this.size = 0;
    }

    public SLList(int n) {
        this();
        this.sentinel.next = new IntNode(n, null);
        this.size++;
    }

    public void addFirst(int n) {
        this.sentinel.next = new IntNode(n, this.sentinel.next);
        this.size++;
    }

    public int getFirst() {
        if (this.sentinel.next == null) {
            return Integer.MIN_VALUE;
        }
        return this.sentinel.next.item;
    }

    public void addLast(int n) {
        IntNode ptr = this.sentinel;

        while (ptr.next != null) {
            ptr = ptr.next;
        }

        ptr.next = new IntNode(n, null);
        this.size++;
    }

    public void recursiveAddLast(int n) {
        SLList.recursiveAddLast(this.sentinel, n);
        this.size++;
    }

    private static void recursiveAddLast(IntNode p, int n) {
        if (p.next == null) {
            p.next = new IntNode(n, null);
        }
        else {
            recursiveAddLast(p.next, n);
        }
    }

    public int size() {
        //don't need to use this method anymore, since size is now cached
        //return SLList.size(this.sentinel.next);

        //instead return cached variable
        return this.size;
    }

    private static int size(IntNode p) {
        if (p == null) {
            return 0;
        }
        else {
            return 1 + size(p.next);
        }
    }

    @Override
    public String toString() {
        return intNodePrint(this.sentinel.next);
    }

    private static String intNodePrint(IntNode p) {
        if (p == null) {
            return "null";
        }
        else {
            return "" + p.item + " -> " + intNodePrint(p.next);
        }
    }
}