package ubb.flcd;

import java.util.Objects;

public class Pair {
    public String first;
    public String second;

    public Pair(String f, String s){
        this.first = f;
        this.second = s;
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
}
