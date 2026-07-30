package q8;

public class ArrayListGenerics<T> {
    private final Integer[] array;
    private int size;

    public ArrayListGenerics(int capacity) {
        this.array = new Integer[capacity];
        this.size = 0;
    }

    public void add(int element){
        array[size] = element;
        size++;
    }
    public Integer get(int index){
        return array[index];
    }

    public Integer remove(int index){
        Integer removedElement = array[index];

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
