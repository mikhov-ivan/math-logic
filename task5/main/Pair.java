package task5;

public class Pair<K, V> {

    private final K key;
    private final V value;

    public static <K, V> Pair<K, V> createPair(K k, V v) {
        return new Pair<>(k, v);
    }

    public Pair(K k, V e) {
        this.key = k;
        this.value = e;
    }

    public K getFirst() {
        return key;
    }

    public V getSecond() {
        return value;
    }
}
