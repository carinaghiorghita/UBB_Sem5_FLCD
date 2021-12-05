package ubb.flcd;

import java.util.Objects;

public class Pair<K,V> {
    private K first;
    private V second;

    public Pair(K first, V second){
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first.equals(pair.first) &&
                second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "(" +
                first +
                "," + second +
                ')';
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
