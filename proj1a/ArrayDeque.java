@SuppressWarnings("unchecked")
public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public static void main(String[] args) {
        ArrayDeque list = new ArrayDeque<Character>();
        for (int i = 97; i < 115; i++) {
            list.addLast((char) i);
        }
        list.printDeque();

        for (int i = 0; i < 11; i++) {
            list.removeFirst();
        }

        list.printDeque();

        for (int i = 0; i < 4; i++) {
            list.removeLast();
        }
        
        list.printDeque();
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        this.nextLast = 4;
    }

    private int minusOne(int index) {
        return (index - 1 + this.items.length) % this.items.length;
    }

    private int plusOne(int index) {
        return (index + 1) % this.items.length;
    }

    private int getFirstIndex() {
        return plusOne(this.nextFirst);
    }

    private int getLastIndex() {
        return minusOne(this.nextLast);
    }

    public void addFirst(T item) {
        if (size == items.length) {
            this.resize(1.5);
        }

        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            this.resize(1.5);
        }

        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
    }

    private void resize(double factor) {
        T[] resized = (T[]) new Object[(int) (this.items.length * factor)];
        
        for (int i = 0; i < this.size; i++) {
            resized[i] = this.items[(getFirstIndex() + i) % this.items.length];
        }

        this.items = resized;
        this.nextFirst = items.length - 1;
        this.nextLast = this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (T item : items) {
            if (item == null) {
                System.out.print("!" + " ");
            }
            else {
                System.out.print(item + " ");
            }
        }
        System.out.println("");
        for (int i = 0; i < items.length; i++) {
            System.out.print(i + " ");
        }
        
        System.out.println("");

        int index = getFirstIndex();

        for (int i = 0; i < this.size; i++) {
            System.out.print(this.items[index] + " ");
            index = plusOne(index);
        }

        System.out.println("\n");
    }

    private boolean tooEmpty() {
        if (((double) this.size / this.items.length) < 0.25) {
            return true;
        }
        return false;
    }

    public T removeFirst() {
        T returnItem = items[getFirstIndex()];
        items[getFirstIndex()] = null;
        nextFirst = plusOne(nextFirst);
        size--;

        if (tooEmpty()) {
            resize(0.5);
        }

        return returnItem;
    }

    public T removeLast() {
        T returnItem = items[getLastIndex()];
        items[getLastIndex()] = null;
        nextLast = minusOne(nextLast);
        size--;
        
        if (tooEmpty()) {
            resize(0.5);
        }

        return returnItem;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(getFirstIndex() + index) % this.items.length];
    }
}
