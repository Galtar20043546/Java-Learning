package org.Nurel.Q3;

import java.util.ArrayList;

public class ArrayListQ {
    public static void main(String[] args) {
        ArrayList<String> el = new ArrayList<String>(); //Создание ArrayList/LinkedList

        el.add("1");  // добавление элемента
        el.add("2");
        el.add("5");
        el.add("9");
        System.out.println(el.get(0));  //получение элемента по заданному индексу
        System.out.println(el.get(2));


        el.remove(3);  // удаление элемента по заданному индексу
        el.remove(2);

        System.out.println(el);

        System.out.println(el.size());  // получение кол-ва элементов (size)

        el.ensureCapacity(20);  // (Для ArrayList) получение емкости (capacity)
    }
}