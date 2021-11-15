package ubb.flcd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Grammar {
    private Set<String> N = new HashSet<>();
    private Set<String> E = new HashSet<>();
    private HashMap<Set<String>, Set<List<String>>> P = new HashMap<>();
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
                    String[] lhsTokens = tokens[0].split(",");
                    String[] rhsTokens = tokens[1].split("\\|");

                    Set<String> lhs = new HashSet<>();
                    for(String l : lhsTokens)
                        lhs.add(l.strip());
                    if(!P.containsKey(lhs))
                        P.put(lhs,new HashSet<>());

                    for(String rhsT : rhsTokens) {
                        ArrayList<String> productionElements = new ArrayList<>();
                        String[] rhsTokenElement = rhsT.strip().split(" ");
                        for(String r : rhsTokenElement)
                            productionElements.add(r.strip());
                        P.get(lhs).add(productionElements);
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
            sb.append("\t");
            int count = 0;
            for(String lh : lhs) {
                sb.append(lh);
                count++;
                if(count<lhs.size())
                    sb.append(", ");
            }
            sb.append(" -> ");
            count = 0;
            for(List<String> rh : rhs){
                for(String r : rh) {
                    sb.append(r);
                }
                count++;
                if (count < rhs.size())
                    sb.append(" | ");

            }
            sb.append("\n");
        });
        sb.append("}");
        return sb.toString();
    }

    public String printProductionsForNonTerminal(String nonTerminal){
        StringBuilder sb = new StringBuilder(nonTerminal).append(" -> ");

        for(Set<String> lhs : P.keySet()) {
            if(lhs.contains(nonTerminal)) {
                Set<List<String>> rhs = P.get(lhs);
                int count = 0;
                for (List<String> rh : rhs) {
                    for(String r : rh) {
                        sb.append(r);
                    }
                    count++;
                    if (count < rhs.size())
                        sb.append(" | ");
                }
            }
        }

        return sb.toString();
    }

    public boolean checkIfCFG(){
        var checkStartingSymbol = false;
        for(Set<String> lhs : P.keySet())
            if (lhs.contains(S)) {
                checkStartingSymbol = true;
                break;
            }
        if(!checkStartingSymbol)
            return false;

        for(Set<String> lhs : P.keySet()){
            if(lhs.size()>1)
                return false;
            else if(!N.contains(lhs.iterator().next()))
                return false;

            Set<List<String>> rhs = P.get(lhs);

            for(List<String> rh : rhs) {
                for (String r : rh) {
                    if(!(N.contains(r) || E.contains(r) || r.equals("epsilon")))
                        return false;
                }
            }
        }
        return true;
    }
}
