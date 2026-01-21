package org.Nurel.Q3.Arraylist;

public class ArrayListInteger {
    private Integer[] array;
    private int size;
    private int capacity;

    public ArrayListInteger(int capacity) {
        this.array = new Integer[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void add(int element){
        array[size] = element;
        size++;
    }
    public Integer get(int index){
        return array[index];
    }

    public Integer remove(int index){
        Integer removedEl = array[index];

        for (int i = index; i < size -1; i++) {
            array[i] = array[i+1];
        }
        array[size - 1] = null;
        size--;

        return removedEl;
    }

    public int size(){
        return size;
    }

    public int getCapacity(){
        return capacity;
    }

}