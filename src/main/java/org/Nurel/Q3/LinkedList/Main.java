package org.Nurel.Q3.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedListDouble list = new LinkedListDouble(4);

        // list.add(2);
        // list.add(4);
        // list.add(5);
        // list.add(7);

        // list.add("Nurel");
        // list.add("Volve");
        // list.add("Mancini");
        // list.add("Pep");

        list.add(2.4);
        list.add(14.6);
        list.add(135.7);
        list.add(0.5252);

        System.out.println("Size: " + list.size());

        System.out.println("Get: " + list.get(1));

        // "удаление элемента по заданному индексу" - Не смог решить.
    }
}
