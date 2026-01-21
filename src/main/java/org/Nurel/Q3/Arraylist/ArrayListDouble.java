package org.Nurel.Q3.Arraylist;

public class ArrayListDouble {
    private Double[] array;
    private int size;


    public ArrayListDouble(int capacity) {
        this.array = new Double[capacity];
        this.size = 0;
    }

    public void add(Double element) {
        array[size] = element;
        size++;
    }

    public Double get(int index) {
        return array[index];
    }

    public Double remove(int index) {
        Double removedElement = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i+1];
        }
        array[size -1] = null;
        size--;

        return removedElement;
    }

    public int size() {
        return size;
    }

    public int getCapacity() {
        return array.length;
    }
}
