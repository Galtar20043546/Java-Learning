package q8;

public class LinkedListGenerics<T> {
    public static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        Node(T element){
            this.element = element;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedListGenerics() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public void add(T element) {
        Node<T> node = new Node<T>(element);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    public T get(int index) {
        Node<T> current = head;  // Временный указатель
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    public T remove(int index) {
        Node<T> toDelete = head;
        for (int i = 0; i < index; i++) {
            toDelete = toDelete.next;
        }

        T element = toDelete.element;

        if (toDelete.prev != null) {
            toDelete.prev.next = toDelete.next;
        } else {
            head = toDelete.next;
        }

        if (toDelete.next != null) {
            toDelete.next.prev = toDelete.prev;
        } else {
            tail = toDelete.prev;
        }

        size--;
        return element;
    }

    public int size(){
        return size;
    }

    public int getCapacity(){
        return Integer.MAX_VALUE;
    }
}
