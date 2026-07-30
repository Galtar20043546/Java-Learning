package q8;

public class Check<K, V> {
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

    private final Node<K, V>[] buckets;

    public Check() {
        buckets = new Node[1];
        buckets[0] = null;
    }

    public void put(K key, V value) {
        Node<K, V> first = buckets[0];

        while (first != null) {
            if (first.key.equals(key)) {
                first.value = value;
                return;
            }
            first = first.next;
        }

        Node<K, V> prev = buckets[0];
        int hash = (key == null) ? 0 : key.hashCode();

        buckets[0] = new Node<>(hash, key, value, prev);
    }

    public V get(K key) {
        Node<K, V> first = buckets[0];

        while (first != null) {
            if (first.key.equals(key)) {
                return first.value;
            }
            first = first.next;
        }

        return null;
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