package org.Nurel.Q3.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedListInteger listInteger = new LinkedListInteger(4);

        listInteger.add(2);
        listInteger.add(4);
        listInteger.add(5);
        listInteger.add(7);
        System.out.println("Size: " + listInteger.size());

        System.out.println("Get: " + listInteger.get(1));

        // "удаление элемента по заданному индексу" - Не смог решить.
    }
}
