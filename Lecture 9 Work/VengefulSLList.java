public class VengefulSLList<Item> extends SLList<Item> {
    SLList<Item> deletedItems;

    public static void main(String[] args) {
        VengefulSLList<Integer> list = new VengefulSLList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.addLast(6);
        System.out.println(list);

        System.out.println("Removing last two items...");
        list.removeLast();
        list.removeLast();
        System.out.println(list);

        System.out.println("Removed items: ");
        list.printLostItems();
    }

    public VengefulSLList() {
        deletedItems = new SLList<Item>();
    }

    @Override
    public Item removeLast() {
        Item x = super.removeLast();
        deletedItems.addLast(x);
        return x;
    }

    public void printLostItems() {
        System.out.println(deletedItems);
    }
}
