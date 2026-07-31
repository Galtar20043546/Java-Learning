package q8;

public class ArrayListGenerics<T> {
    private final Object[] array;
    private int size;

    public ArrayListGenerics(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    public void add(T element){
        array[size] = element;
        size++;
    }
    public T get(int index){
        return (T) array[index];
    }

    public T remove(int index){
        T removedElement = (T) array[index];

        for (int i = index; i < size -1; i++) {
            array[i] = array[i+1];
        }
        array[size - 1] = null;
        size--;

        return removedElement;
    }

    public int size(){
        return size;
    }

    public int getCapacity(){
        return array.length;
    }
}
