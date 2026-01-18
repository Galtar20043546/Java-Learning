package org.Nurel.Q3;


public class ArrayList {
    public static void main(String[] args) {
        java.util.ArrayList<String> el = new java.util.ArrayList<String>();  //Создание ArrayList/LinkedList
        el.add("1");
        el.add("2");
        el.add("5");
        el.add("9");  // добавление элемента
        el.get(0);
        el.get(2);
        el.remove(3);
        el.remove(2);  //удаление элемента по заданному индексу
        el.size();   //получение кол-ва элементов (size)
        el.ensureCapacity(1);             // Я не осбо понял но в документациях с элементами void
    }
}