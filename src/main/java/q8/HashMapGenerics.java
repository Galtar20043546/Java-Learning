package q8;

import java.util.Objects;

public class HashMapGenerics<K, V> {
    private static class Node<K, V> {
        Node<K, V> next;
        K key;
        V value;
        int hash;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    private Node<K,V>[] buckets;

    public HashMapGenerics() {
        buckets = new Node[16];
    }

    public void put(K key, V value) {

        if (key == null){
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        if (size > 0.75 * buckets.length) {
            Node<K, V>[] newBuckets = new Node[buckets.length * 2];

            for (int i = 0; i < buckets.length; i++) {
                Node<K, V> current = buckets[i];
                while (current != null) {
                    Node<K, V> nextNode = current.next;

                    int bucketIndex = Math.abs(current.hash % newBuckets.length);

                    current.next = newBuckets[bucketIndex];
                    newBuckets[bucketIndex] = current;

                    current = nextNode;
                }
            }
            buckets = newBuckets;
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % buckets.length);

        Node<K, V> current = buckets[bucketIndex];

        if (current == null) {
            buckets[bucketIndex] = new Node<>(hash,key,value,null);
            size++;
            return;
        }

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            if (current.next == null) {
                current.next = new Node<>(hash,key,value,null);
                size++;
                return;
            }

            current = current.next;
        }
    }

    public V get(K key)  {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % buckets.length);
        Node<K, V> current = buckets[bucketIndex];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть пустым");
        }

        int hash = key.hashCode();
        int bucketIndex = Math.abs(hash % buckets.length);

        Node<K, V> current = buckets[bucketIndex];
        Node<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                V removedVal = current.value;

                if (prev == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return removedVal;
            }

            prev = current;
            current = current.next;
        }
        return null;
    }

    public int size(){
        return size;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;

        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> current = buckets[i];

            while (current != null) {
                if (!isFirst) {
                    result.append(", ");
                }

                result.append(current.key).append("=").append(current.value);

                isFirst = false;
                current = current.next;
            }
        }
        return result.toString();
    }
}