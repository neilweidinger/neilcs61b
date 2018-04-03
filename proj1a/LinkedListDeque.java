public class LinkedListDeque<T> {
    public class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node(T i, Node n, Node p) {
            this.item = i;
            this.next = n;
            this.prev = p;
        }
    }

    private Node sentinel;
    private int size;

    public static void main(String[] args) {
        LinkedListDeque<Integer> list = new LinkedListDeque<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addLast(3);
        list.printDeque();
        int r = list.removeFirst();
        list.printDeque();
        System.out.println("Removed: " + r);
        System.out.println(list.getRecursive(1));
    }

    public LinkedListDeque() {
        this.sentinel = new Node(null, null, null);
        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }

    public void addFirst(T item) {
        //updates prev pointer of old first node to this newly created node
        //newly created node next pointer points to old first node (now second node)
        //newly created node prev pointer points to sentinel
        this.sentinel.next.prev = new Node(item, this.sentinel.next, this.sentinel);

        //updates next pointer of sentinel node to prev pointer of old first node (now second node)
        this.sentinel.next = this.sentinel.next.prev;

        this.size++;
    }

    public void addLast(T item) {
        //updates next pointer of old last node to this newly created node
        //newly created node next pointer points to sentinel
        //newly created node prev pointer points to old last node (now second to last node)
        this.sentinel.prev.next = new Node(item, this.sentinel, this.sentinel.prev);

        //updates prev pointer of sentinel node to next pointer of old last node (now second
        //to last node)
        this.sentinel.prev = this.sentinel.prev.next;

        this.size++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        System.out.println(printNode(this.sentinel.next, this.sentinel));
    }

    //private helper method
    private String printNode(Node p, Node sentinel) {
        //basecase: if we have reached sentinel this means we have reached the end of the list
        if (p == sentinel) {
            return "";
        }
        else {
            return "" + p.item + " " + printNode(p.next, sentinel);
        }
    }

    public T removeFirst() {
        //if list is emtpy
        if (this.sentinel.next == this.sentinel) {
            return null;
        }

        Node ptr = this.sentinel.next;
        this.sentinel.next = ptr.next;
        ptr.next.prev = sentinel;
        this.size--;
        return ptr.item;
    }

    public T removeLast() {
        //if list is empty
        if (this.sentinel.prev == this.sentinel) {
            return null;
        }

        Node ptr = this.sentinel.prev;
        this.sentinel.prev = ptr.prev;
        ptr.prev.next = sentinel;
        this.size--;
        return ptr.item;
    }

    public T get(int index) {
        return getNode(this.sentinel.next, this.sentinel, index); 
    }

    private T getNode(Node p, Node sentinel, int index) {
        //this check is important because if we were to remove it, and the user wants get(0) on an empty list, the method
        //return sentinel.item which is not what is supposed to occur
        if (p == sentinel) {
            return null;
        }

        int count = 0;

        while (count < index) {
            if (p.next == sentinel) {
                return null;
            }
            p = p.next;
            count++;
        }

        return p.item;
    }

    public T getRecursive(int index) {
        return getNodeRecursive(this.sentinel.next, this.sentinel, index);
    }

    private T getNodeRecursive(Node p, Node sentinel, int index) {
        //this means we have reached the end of the list before the correct index, meaning index is out of bounds and we
        //return null
        if (p == sentinel) {
            return null;
        }
        //finally at corret index, return item
        else if (index == 0) {
            return p.item;
        }
        else {
            return getNodeRecursive(p.next, sentinel, index - 1);
        }
    }
}
