package ubb.flcd;

import java.util.*;

public class Parser {
    private final Grammar grammar;
    private HashMap<String, Set<String>> firstSet;
    private HashMap<String, Set<String>> followSet;
    private HashMap<Pair, String> parseTable;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.firstSet = new HashMap<>();
        this.followSet = new HashMap<>();
        this.parseTable = new HashMap<>();

        generateFirst();
        generateFollow();
        generateParseTable();
    }

    public void generateFirst() {
        //initialization
        for (String nonterminal : grammar.getN()) {
            firstSet.put(nonterminal, new HashSet<>());
            Set<List<String>> productionForNonterminal = grammar.getProductionForNonterminal(nonterminal);
            for (List<String> production : productionForNonterminal) {
                if (grammar.getE().contains(production.get(0)) || production.get(0).equals("epsilon"))
                    firstSet.get(nonterminal).add(production.get(0));
            }
        }

        //rest of iterations
        var isChanged = true;
        while (isChanged) {
            isChanged = false;
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonterminal : grammar.getN()) {
                Set<List<String>> productionForNonterminal = grammar.getProductionForNonterminal(nonterminal);
                Set<String> toAdd = new HashSet<>(firstSet.get(nonterminal));
                for (List<String> production : productionForNonterminal) {
                    List<String> rhsNonTerminals = new ArrayList<>();
                    String rhsTerminal = null;
                    for (String symbol : production)
                        if (this.grammar.getN().contains(symbol))
                            rhsNonTerminals.add(symbol);
                        else {
                            rhsTerminal = symbol;
                            break;
                        }
                    toAdd.addAll(multipleConcatenation(firstSet, rhsNonTerminals, rhsTerminal));
                }
                if (!toAdd.equals(firstSet.get(nonterminal))) {
                    isChanged = true;
                }
                newColumn.put(nonterminal, toAdd);
            }
            firstSet = newColumn;
        }
        //System.out.println(firstSet);
    }

    private Set<String> multipleConcatenation(HashMap<String, Set<String>> firstSet, List<String> nonTerminals, String terminal) {
        if (nonTerminals.size() == 0)
            return new HashSet<>();
        if (nonTerminals.size() == 1) {
            return firstSet.get(nonTerminals.iterator().next());
        }

        Set<String> concatenation = new HashSet<>();
        var step = 0;
        var allEpsilon = true;

        for (String nonTerminal : nonTerminals) {
            if (!firstSet.get(nonTerminal).contains("epsilon")) {
                allEpsilon = false;
            }
        }
        if (allEpsilon) {
            concatenation.add(Objects.requireNonNullElse(terminal, "epsilon"));
        }

        while (step < nonTerminals.size()) {
            boolean thereIsOneEpsilon = false;
            for (String s : firstSet.get(nonTerminals.get(step)))
                if (s.equals("epsilon"))
                    thereIsOneEpsilon = true;
                else
                    concatenation.add(s);

            if (thereIsOneEpsilon)
                step++;
            else
                break;
        }
        return concatenation;
    }

    public void generateFollow() {
        //initialization
        for (String nonterminal : grammar.getN()) {
            followSet.put(nonterminal, new HashSet<>());
        }
        followSet.get(grammar.getS()).add("epsilon");

        //rest of iterations
        var isChanged = true;
        while (isChanged) {
            isChanged = false;
            HashMap<String, Set<String>> newColumn = new HashMap<>();

            for (String nonterminal : grammar.getN()) {
                newColumn.put(nonterminal, new HashSet<>());
                var productionsWithNonterminalInRhs = new HashMap<String, Set<List<String>>>();
                var allProductions = grammar.getP();
                allProductions.forEach((k, v) -> {
                    for (var eachProduction : v) {
                        if (eachProduction.contains(nonterminal)) {
                            var key = k.iterator().next();
                            if (!productionsWithNonterminalInRhs.containsKey(key))
                                productionsWithNonterminalInRhs.put(key, new HashSet<>());
                            productionsWithNonterminalInRhs.get(key).add(eachProduction);
                        }
                    }
                });
                var toAdd = new HashSet<>(followSet.get(nonterminal));
                productionsWithNonterminalInRhs.forEach((k, v) -> {
                    for (var production : v) {
                        var productionList = (ArrayList<String>) production;
                        var indexOfNonterminal = productionList.indexOf(nonterminal);
                        if (indexOfNonterminal + 1 == productionList.size()) {
                            toAdd.addAll(followSet.get(k));
                        } else {
                            var followSymbol = productionList.get(indexOfNonterminal + 1);
                            if (grammar.getE().contains(followSymbol))
                                toAdd.add(followSymbol);
                            else {
                                toAdd.addAll(firstSet.get(followSymbol));
                                toAdd.addAll(followSet.get(k));
                            }
                        }
                    }
                });
                if (!toAdd.equals(followSet.get(nonterminal))) {
                    isChanged = true;
                }
                newColumn.put(nonterminal, toAdd);
            }

            followSet = newColumn;

        }
    }

    public void generateParseTable() {
        List<String> rows = new ArrayList<>();
        rows.addAll(grammar.getN());
        rows.addAll(grammar.getE());
        rows.add("$");

        List<String> columns = new ArrayList<>();
        columns.addAll(grammar.getE());
        columns.add("$");

        for (var row : rows)
            for (var col : columns)
                parseTable.put(new Pair(row, col), "err");

        for (var col : columns)
            parseTable.put(new Pair(col, col), "pop");

        parseTable.put(new Pair("$", "$"), "acc");

        var productions = grammar.getP();
        productions.forEach((k,v) -> {
            var key = k.iterator().next();

            for(var production : v){
                var firstSymbol = production.get(0);
                if(grammar.getE().contains(firstSymbol))
                    parseTable.put(new Pair(key, firstSymbol), String.join(" ",production));
                else if(grammar.getN().contains(firstSymbol)){
                    if(production.size() == 1)
                        for (var symbol : firstSet.get(firstSymbol))
                            parseTable.put(new Pair(key, symbol), String.join(" ",production));
                    else {
                        var i = 1;
                        var nextSymbol = production.get(1);
                        var firstSetForProduction = firstSet.get(firstSymbol);

                        while (i < production.size() && grammar.getN().contains(nextSymbol)){
                            var firstForNext = firstSet.get(nextSymbol);
                            if(firstSetForProduction.contains("epsilon")){
                                firstSetForProduction.remove("epsilon");
                                firstSetForProduction.addAll(firstForNext);
                            }

                            i++;
                            if(i<production.size())
                                nextSymbol = production.get(i);
                        }

                        for(var symbol : firstSetForProduction)
                            parseTable.put(new Pair(key, symbol), String.join(" ",production));
                    }
                }
                else {
                    var follow = followSet.get(key);
                    for(var symbol : follow){
                        if(symbol.equals("epsilon"))
                            parseTable.put(new Pair(key, "$"), "epsilon");
                        else
                            parseTable.put(new Pair(key, symbol), "epsilon");
                    }
                }
            }
        });
    }

    public String printFirst() {
        StringBuilder builder = new StringBuilder();
        firstSet.forEach((k, v) -> {
            builder.append(k).append(": ").append(v).append("\n");
        });
        return builder.toString();
    }

    public String printFollow() {
        StringBuilder builder = new StringBuilder();
        followSet.forEach((k, v) -> {
            builder.append(k).append(": ").append(v).append("\n");
        });
        return builder.toString();
    }

    public String printParseTable(){
        StringBuilder builder = new StringBuilder();
        parseTable.forEach((k,v) -> {
            builder.append(k).append(" -> ").append(v).append("\n");
        });
        return builder.toString();
    }
}
