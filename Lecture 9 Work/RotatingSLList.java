public class RotatingSLList<Item> extends SLList<Item> {
    public static void main(String[] args) {
        RotatingSLList<Integer> list = new RotatingSLList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        System.out.println(list);

        list.rotateRight();
        System.out.println(list);
    }

    public void rotateRight() {
        Item x = removeLast();
        if (x == null) return;

        addFirst(x);
    }
}
