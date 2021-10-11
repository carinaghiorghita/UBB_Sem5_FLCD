package ubb.flcd.Domain;

public class Pair<K,V> {
    private final K first;
    private final V second;

    public K getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
