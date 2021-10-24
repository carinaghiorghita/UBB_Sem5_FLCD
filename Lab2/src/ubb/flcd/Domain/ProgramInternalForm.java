package ubb.flcd.Domain;

import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<Integer, Pair<Integer, Integer>>> pif = new ArrayList<>();

    public void add(Integer code, Pair<Integer, Integer> value) {
        Pair<Integer, Pair<Integer, Integer>> pair = new Pair<>(code, value);
        pif.add(pair);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Pair<Integer, Pair<Integer, Integer>> pair : pif) {
            result.append(pair.getKey()).append(" -> (").append(pair.getValue().getKey()).append(", ").append(pair.getValue().getValue()).append(")\n");
            result.append("___________________________________\n\n");
        }
        return result.toString();
    }
}
