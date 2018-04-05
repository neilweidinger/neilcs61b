public class AList<T> {
    private T[] items;
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
        this.items = (T []) new Object[10]; //for this class capacity is always ten
        this.size = 0;
    }

    //for when you want to instantiate AList with custom capacity
    public AList(int n) {
        this.items = (T []) new Object[n]; //for this class capacity is always ten
        this.size = 0;
    }

    public void addLast(T x) {
        if (this.size == this.items.length) {
            this.resize();
        }
        
        this.items[size] = x;
        size++;
    }

    //resizes array by 1.5
    private void resize() {
        T[] resized = (T []) new Object[(int)(this.items.length * 1.5)];
        System.arraycopy(items, 0, resized, 0, size);
        this.items = resized;
    }

    public T getLast() {
        return this.items[size - 1];
    }

    public T get(int i) {
        return this.items[i];
    }

    public int size() {
        return this.size;
    }

    public T removeLast() {
        T returnItem = getLast();
        this.items[size - 1] = null;
        size--;
        return returnItem;
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
