package org.Nurel.Q3.LinkedList;

public class LinkedListInteger {
    private static class Node {
        Integer value;
        Node next;

        Node(Integer value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private int capacity;

    public LinkedListInteger(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void add(Integer element) {
        Node node = new Node(element);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public Integer get(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    public int size(){
        return size;
    }
}
