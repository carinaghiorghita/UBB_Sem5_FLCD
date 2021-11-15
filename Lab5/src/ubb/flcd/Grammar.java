package ubb.flcd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Grammar {
    private Set<String> N = new HashSet<>();
    private Set<String> E = new HashSet<>();
    private HashMap<String, Set<String>> P = new HashMap<>();
    private String S = "";

    public Grammar() {
        readFromFile("src/ubb/flcd/Resources/g1.txt");
    }

    private void readFromFile(String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            this.N = new HashSet<>(Arrays.asList(reader.readLine().split("=")[1].replace("{","").replace("}","").strip().split(" ")));
            this.E = new HashSet<>(Arrays.asList(reader.readLine().split("=")[1].replace("{","").replace("}","").strip().split(" ")));
            this.S = reader.readLine().split("=")[1].strip();

            reader.readLine(); // first and last lines for productions will not contain any relevant information, we only need to check starting from the second until the second-last
            String line = reader.readLine();
            while(line != null){
                if(!line.equals("}")) {
                    String[] tokens = line.split("->");
                    String lhs = tokens[0].strip();
                    String[] rhs = tokens[1].split("\\|");

                    if(!P.containsKey(lhs))
                        P.put(lhs,new HashSet<>());

                    for (String rh : rhs) {
                        P.get(lhs).add(rh.strip());
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String printNonTerminals() {
        StringBuilder sb = new StringBuilder("N = { ");
        for(String n : N)
            sb.append(n).append(" ");
        sb.append("}");
        return sb.toString();
    }

    public String printTerminals() {
        StringBuilder sb = new StringBuilder("E = { ");
        for(String e : E)
            sb.append(e).append(" ");
        sb.append("}");
        return sb.toString();
    }

    public String printProductions() {
        StringBuilder sb = new StringBuilder("P = { \n");
        P.forEach((lhs, rhs) -> {
            sb.append(lhs).append(" -> ");
            int count = 0;
            for(String rh : rhs){
                sb.append(rh);
                count++;
                if(count<rhs.size())
                    sb.append(" | ");
            }
            sb.append("\n");
        });
        sb.append("}");
        return sb.toString();
    }

    public String printProductionsForNonTerminal(String nonTerminal){
        StringBuilder sb = new StringBuilder(nonTerminal).append(" -> ");

        Set<String> rhs = P.get(nonTerminal);
        int count = 0;
        for(String rh : rhs){
            sb.append(rh);
            count++;
            if(count<rhs.size())
                sb.append(" | ");
        }

        return sb.toString();
    }

    public boolean checkIfCFG(){
        for(String lhs : P.keySet()){
            if(!N.contains(lhs))
                return false;

            Set<String> rhs = P.get(lhs);
            for(String rh : rhs){
                if(!rh.equals("epsilon")){
                    if(rh.contains(" ")){
                        //String[] rhTokens = rh.split(" ");

                    }
                    else {
                        for(int i=0;i<rh.length();++i){
                            if(!(N.contains(String.valueOf(rh.charAt(i))) || E.contains(String.valueOf(rh.charAt(i)))))
                                return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
