public class LList {
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            this.item = i;
            this.next = n;
        }
    }

    private IntNode first;

    public static void main(String[] args) {
        LList list = new LList();
        for (int i = 0; i < 10; i++) {
            list.addFirst(i);
        }
        list.insert(-1, 0);
        System.out.println(list);

        list.reverse();
        System.out.println(list);

        list.reverseRecursively();
        System.out.println(list);
    }

    public LList() {
        first = null; 
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public void insert(int item, int position) {
        if (position < 0) {
            System.out.println("index out of bounds");
        }

        if (position == 0 || first == null) {
            first = new IntNode(item, first);
            return;
        }

        IntNode ptr = this.first;
        int count = 1;

        while (count < position && ptr.next != null) {
            ptr = ptr.next;
            count++;
        }

        ptr.next = new IntNode(item, ptr.next);
    }

    public void reverse() {
        if (first == null) {
            return;
        }

        IntNode prevPtr = null;
        IntNode currPtr = first;
        IntNode nextPtr = first.next;

        while (true) {
            currPtr.next = prevPtr;
            prevPtr = currPtr;
            currPtr = nextPtr;
            if (nextPtr == null) {
                break;
            }
            nextPtr = nextPtr.next;
        }

        first = prevPtr;
    }

    public void reverseRecursively() {
        first = reverseHelper(first, null);
    }

    private IntNode reverseHelper(IntNode head, IntNode next) {
        if (head == null) {
            return next;
        }

        IntNode newHead = head.next;
        head.next = next;

        return reverseHelper(newHead, head);
    }

    @Override
    public String toString() {
        return printList(this.first);
    }

    private String printList(IntNode p) {
        if (p == null) {
            return "null";
        }
        else {
            return "" + p.item + " -> " + printList(p.next);
        }
    }
}
