package org.Nurel.Q3;

public class MainArrayList {
    public static void main(String[] args) {
        ArrayListQ arrayListQ = new ArrayListQ(6);
        arrayListQ.add(2);
        arrayListQ.add(3);
        arrayListQ.add(4);
        arrayListQ.add(5);
        arrayListQ.add(7);
        arrayListQ.add(10);

        System.out.println("получение кол-ва элементов (size): " + arrayListQ.size());

        System.out.println("получение элемента по заданному индексу: " + arrayListQ.get(1));
        System.out.println("получение элемента по заданному индексу: " + arrayListQ.get(3));


        System.out.println("удаление элемента по заданному индексу: " + arrayListQ.remove(1));
        System.out.println("удаление элемента по заданному индексу: " + arrayListQ.remove(3));

        System.out.println("получение кол-ва элементов (size): " + arrayListQ.size());

        System.out.println("(Для ArrayList) получение емкости (capacity): " + arrayListQ.getCapacity());
    }
}
