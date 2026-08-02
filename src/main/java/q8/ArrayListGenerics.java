package q8;

public class ArrayListGenerics<T> {
    private Object[] array;
    private int size;
    private int capacity;

    public ArrayListGenerics(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void add(T element) {
        if (size == capacity) {
            capacity = capacity * 2;
            Object[] newArray = new Object[capacity];

            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            this.array = newArray;
        }

        array[size] = element;
        size++;
    }

    public T get(int index) {
        return (T) array[index];
    }

    public T remove(int index) {
        T removedElement = (T) array[index];

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;

        if (capacity / 4 > size) {
            int newCapacity = capacity / 2;
            Object[] newArray = new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = array[i];
            }
            this.array = newArray;
            this.capacity = newCapacity;
        }
        return removedElement;
    }

    public void trimToSize(int size) {
        // уменьшить емкость до количества элементов в массиве
        Object[] newArray = new Object[size];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
        this.capacity = size;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void list() {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}