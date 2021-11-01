package ubb.flcd;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FiniteAutomation {
    public List<String> alphabet, states, finalStates;
    public String initialState;
    public Map<Pair<String, String>, String> transitions;

    public FiniteAutomation(String filename) {
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.transitions = new HashMap<>();

        readFiniteAutomaton(filename);
    }

    private void readFiniteAutomaton(String filename){
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            String statesLine = reader.nextLine();
            states = Arrays.asList(statesLine.split(" "));

            String alphabetLine = reader.nextLine();
            alphabet = Arrays.asList(alphabetLine.split(" "));

            initialState = reader.nextLine();

            String finalStatesLine = reader.nextLine();
            finalStates = Arrays.asList(finalStatesLine.split(" "));

            while(reader.hasNextLine()){
                String transitionLine = reader.nextLine();
                String[] transitionElements = transitionLine.split(" ");

                Pair<String, String> transitionStates = new Pair<>(transitionElements[0], transitionElements[1]);

                transitions.put(transitionStates, transitionElements[2]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String writeAlphabet(){
        StringBuilder builder = new StringBuilder();
        builder.append("Alphabet: ");
        for (String a : alphabet){
            builder.append(a).append(" ");
        }

        return builder.toString();
    }

    public String writeStates(){
        StringBuilder builder = new StringBuilder();
        builder.append("States: ");
        for (String s : states){
            builder.append(s).append(" ");
        }

        return builder.toString();
    }

    public String writeFinalStates(){
        StringBuilder builder = new StringBuilder();
        builder.append("Final states: ");
        for (String fs : finalStates){
            builder.append(fs).append(" ");
        }

        return builder.toString();
    }

    public String writeTransitions(){
        StringBuilder builder = new StringBuilder();
        builder.append("Transitions: \n");
        transitions.forEach((K, V) -> {
            builder.append("<").append(K.key).append(",").append(K.value).append("> -> ").append(V).append("\n");
        });

        return builder.toString();
    }

    public boolean checkSequence(String sequence){
        if(sequence.length() == 0) {
            if (finalStates.contains(initialState))
                return true;
            else return false;
        }
        boolean isValid = true;

    }

    @Override
    public String toString() {
        return "FiniteAutomation{" +
                "alphabet=" + alphabet +
                ", states=" + states +
                ", finalStates=" + finalStates +
                ", initialState='" + initialState + '\'' +
                ", transitions=" + transitions +
                '}';
    }
}
