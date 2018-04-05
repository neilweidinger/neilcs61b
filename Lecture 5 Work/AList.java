public class AList {
    private int[] items;
    private int size;

    public static void main(String[] args) {
        AList list = new AList(5);
        list.addLast(5);
        list.addLast(4);
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);
        System.out.println(list);

        list.addLast(0);
        list.addLast(-1);
        System.out.println(list);
    }
    
    //default capacity is 10
    public AList() {
        this.items = new int[10]; //for this class capacity is always ten
        this.size = 0;
    }

    //for when you want to instantiate AList with custom capacity
    public AList(int n) {
        this.items = new int[n]; //for this class capacity is always ten
        this.size = 0;
    }

    public void addLast(int x) {
        if (this.size == this.items.length) {
            this.resize();
        }
        
        this.items[size] = x;
        size++;
    }

    //resizes array by 1.5
    private void resize() {
        int[] resized = new int[(int)(this.items.length * 1.5)];
        System.arraycopy(items, 0, resized, 0, size);
        this.items = resized;
    }

    public int getLast() {
        return this.items[size - 1];
    }

    public int get(int i) {
        return this.items[i];
    }

    public int size() {
        return this.size;
    }

    public int removeLast() {
        size--;
        return this.getLast(); //don't necessarily have to "delete" element from array, since size keeps track of last element
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.size; i++) {
            res += this.items[i] + " ";
        }
        return res + "\nSize: " + this.size + "\nCapacity: " + this.items.length;
    }
}
