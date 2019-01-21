package task3;

public class Pair<K, V> {

    private final K key;
    private final V value;

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public Pair(K k, V v) {
        this.key = k;
        this.value = v;
    }
}
