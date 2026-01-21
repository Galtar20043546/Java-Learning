package org.Nurel.Q3.Arraylist;

public class Main {
    public static void main(String[] args) {
        ArrayListDouble arrayListQ = new ArrayListDouble(6);

        arrayListQ.add(2.2);
        arrayListQ.add(4.5);
        arrayListQ.add(5.76);
        arrayListQ.add(3.3);
        arrayListQ.add(7.256);
        arrayListQ.add(10.01);

        // arrayListQ.add("Apple");
        // arrayListQ.add("Banana");
        // arrayListQ.add("Berry");
        // arrayListQ.add("Cherry");
        // arrayListQ.add("Watermelon");
        // arrayListQ.add("Orange");

        // arrayListQ.add(2);
        // arrayListQ.add(3);
        // arrayListQ.add(4);
        // arrayListQ.add(5);
        // arrayListQ.add(7);
        // arrayListQ.add(10);

        System.out.println("получение кол-ва элементов (size): " + arrayListQ.size());

        System.out.println("получение элемента по заданному индексу: " + arrayListQ.get(0));
        System.out.println("получение элемента по заданному индексу: " + arrayListQ.get(1));


        System.out.println("удаление элемента по заданному индексу: " + arrayListQ.remove(2));
        System.out.println("удаление элемента по заданному индексу: " + arrayListQ.remove(3));

        System.out.println("получение кол-ва элементов (size): " + arrayListQ.size());

        System.out.println("(Для ArrayList) получение емкости (capacity): " + arrayListQ.getCapacity());
    }
}
