@SuppressWarnings("unchecked")
public class SLList<T> {
    public class IntNode {
        public T item;
        public IntNode next;

        public IntNode(T i, IntNode n) {
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
        this.sentinel = new IntNode(null, null);
        this.size = 0;
    }

    public SLList(T n) {
        this();
        this.sentinel.next = new IntNode(n, null);
        this.size++;
    }

    public void addFirst(T n) {
        this.sentinel.next = new IntNode(n, this.sentinel.next);
        this.size++;
    }

    public T getFirst() {
        if (this.sentinel.next == null) {
            return null;
        }
        return this.sentinel.next.item;
    }

    public T get(int index) {
        if (this.sentinel.next == null) {
            return null;
        }

        return get(index, this.sentinel.next);
    }

    public T get(int index, IntNode p) {
        if (p == null) {
            return null;
        }
        else if (index == 0) {
            return p.item;
        }
        else {
            return get(index - 1, p.next);
        }
    }

    public void addLast(T n) {
        IntNode ptr = this.sentinel;

        while (ptr.next != null) {
            ptr = ptr.next;
        }

        ptr.next = new IntNode(n, null);
        this.size++;
    }

    public void recursiveAddLast(T n) {
        recursiveAddLast(this.sentinel, n);
        this.size++;
    }

    private void recursiveAddLast(IntNode p, T n) {
        if (p.next == null) {
            p.next = new IntNode(n, null);
        }
        else {
            recursiveAddLast(p.next, n);
        }
    }

    public T removeLast() {
        if (this.sentinel.next == null) {
            return null;
        }

        IntNode curr = this.sentinel.next;
        IntNode prev = null;

        while (curr.next != null) {
            prev = curr;
            curr = curr.next;
        }

        prev.next = null;
        this.size--;
        return curr.item;
    }

    public int size() {
        //don't need to use this method anymore, since size is now cached
        //return SLList.size(this.sentinel.next);

        //instead return cached variable
        return this.size;
    }

    private int size(IntNode p) {
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

    private String intNodePrint(IntNode p) {
        if (p == null) {
            return "null";
        }
        else {
            return "" + p.item + " -> " + intNodePrint(p.next);
        }
    }
}
