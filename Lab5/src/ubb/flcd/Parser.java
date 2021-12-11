package ubb.flcd;

import java.util.*;

public class Parser {
    private final Grammar grammar;
    private HashMap<String, Set<String>> firstSet;
    private HashMap<String, Set<String>> followSet;
    private HashMap<Pair, Pair> parseTable;
    private List<List<String>> productionsRhs;

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
                    toAdd.addAll(concatenationOfSizeOne(rhsNonTerminals, rhsTerminal));
                }
                if (!toAdd.equals(firstSet.get(nonterminal))) {
                    isChanged = true;
                }
                newColumn.put(nonterminal, toAdd);
            }
            firstSet = newColumn;
        }
    }

    private Set<String> concatenationOfSizeOne(List<String> nonTerminals, String terminal) {
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
                        for (var indexOfNonterminal = 0; indexOfNonterminal < productionList.size(); ++indexOfNonterminal)
                            if (productionList.get(indexOfNonterminal).equals(nonterminal)) {
                                if (indexOfNonterminal + 1 == productionList.size()) {
                                    toAdd.addAll(followSet.get(k));
                                } else {
                                    var followSymbol = productionList.get(indexOfNonterminal + 1);
                                    if (grammar.getE().contains(followSymbol))
                                        toAdd.add(followSymbol);
                                    else {
                                        for (var symbol : firstSet.get(followSymbol)) {
                                            if (symbol.equals("epsilon"))
                                                toAdd.addAll(followSet.get(k));
                                            else
                                                toAdd.addAll(firstSet.get(followSymbol));
                                        }
                                    }
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
                parseTable.put(new Pair<String, String>(row, col), new Pair<String, Integer>("err",-1));

        for (var col : columns)
            parseTable.put(new Pair<String, String>(col, col), new Pair<String, Integer>("pop",-1));

        parseTable.put(new Pair<String, String>("$", "$"), new Pair<String, Integer>("acc",-1));

        var productions = grammar.getP();
        this.productionsRhs = new ArrayList<>();
        productions.forEach((k,v) -> {
            var nonterminal = k.iterator().next();
            for(var prod : v)
                    if(!prod.get(0).equals("epsilon"))
                        productionsRhs.add(prod);
                    else {
                        productionsRhs.add(new ArrayList<>(List.of("epsilon", nonterminal)));
                    }
        });

        System.out.println(productionsRhs);

        productions.forEach((k, v) -> {
            var key = k.iterator().next();

            for (var production : v) {
                var firstSymbol = production.get(0);
                if (grammar.getE().contains(firstSymbol))
                    if (parseTable.get(new Pair<>(key, firstSymbol)).getFirst().equals("err"))
                        parseTable.put(new Pair<>(key, firstSymbol), new Pair<>(String.join(" ", production),productionsRhs.indexOf(production)+1));
                    else {
                        try {
                            throw new IllegalAccessException("CONFLICT: Pair "+key+","+firstSymbol);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                else if (grammar.getN().contains(firstSymbol)) {
                    if (production.size() == 1)
                        for (var symbol : firstSet.get(firstSymbol))
                            if (parseTable.get(new Pair<>(key, symbol)).getFirst().equals("err"))
                                parseTable.put(new Pair<>(key, symbol), new Pair<>(String.join(" ", production),productionsRhs.indexOf(production)+1));
                            else {
                                try {
                                    throw new IllegalAccessException("CONFLICT: Pair "+key+","+symbol);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                    else {
                        var i = 1;
                        var nextSymbol = production.get(1);
                        var firstSetForProduction = firstSet.get(firstSymbol);

                        while (i < production.size() && grammar.getN().contains(nextSymbol)) {
                            var firstForNext = firstSet.get(nextSymbol);
                            if (firstSetForProduction.contains("epsilon")) {
                                firstSetForProduction.remove("epsilon");
                                firstSetForProduction.addAll(firstForNext);
                            }

                            i++;
                            if (i < production.size())
                                nextSymbol = production.get(i);
                        }

                        for (var symbol : firstSetForProduction) {
                            if (symbol.equals("epsilon"))
                                symbol = "$";
                            if (parseTable.get(new Pair<>(key, symbol)).getFirst().equals("err"))
                                parseTable.put(new Pair<>(key, symbol), new Pair<>(String.join(" ", production), productionsRhs.indexOf(production) + 1));
                            else {
                                try {
                                    throw new IllegalAccessException("CONFLICT: Pair " + key + "," + symbol);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {
                    var follow = followSet.get(key);
                    for (var symbol : follow) {
                        if (symbol.equals("epsilon")) {
                            if (parseTable.get(new Pair<>(key, "$")).getFirst().equals("err")) {
                                var prod = new ArrayList<>(List.of("epsilon",key));
                                parseTable.put(new Pair<>(key, "$"), new Pair<>("epsilon", productionsRhs.indexOf(prod) + 1));
                            }
                            else {
                                try {
                                    throw new IllegalAccessException("CONFLICT: Pair "+key+","+symbol);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (parseTable.get(new Pair<>(key, symbol)).getFirst().equals("err")) {
                            var prod = new ArrayList<>(List.of("epsilon",key));
                            parseTable.put(new Pair<>(key, symbol), new Pair<>("epsilon", productionsRhs.indexOf(prod) + 1));
                        }
                        else {
                            try {
                                throw new IllegalAccessException("CONFLICT: Pair "+key+","+symbol);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
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

    public String printParseTable() {
        StringBuilder builder = new StringBuilder();
        parseTable.forEach((k, v) -> {
            builder.append(k).append(" -> ").append(v).append("\n");
        });
        return builder.toString();
    }

    public List<Integer> parseSequence(List<String> sequence){
        Stack<String> alpha = new Stack<>();
        Stack<String> beta = new Stack<>();
        List<Integer> result = new ArrayList<>();

        //initialization
        alpha.push("$");
        for(var i=sequence.size()-1;i>=0;--i)
            alpha.push(sequence.get(i));

        beta.push("$");
        beta.push(grammar.getS());

        while(!(alpha.peek().equals("$") && beta.peek().equals("$"))){
            String alphaPeek = alpha.peek();
            String betaPeek = beta.peek();
            Pair<String,String> key = new Pair<>(betaPeek,alphaPeek);
            Pair<String,Integer> value = parseTable.get(key);

            if(!value.getFirst().equals("err")){
                if(value.getFirst().equals("pop")){
                    alpha.pop();
                    beta.pop();
                }
                else {
                    beta.pop();
                    if(!value.getFirst().equals("epsilon")) {
                        String[] val = value.getFirst().split(" ");
                        for (var i = val.length - 1; i >= 0; --i)
                            beta.push(val[i]);
                    }
                    result.add(value.getSecond());
                }
            }
            else {
                System.out.println("Syntax error for key "+key);
                System.out.println("Current alpha and beta for sequence parsing:");
                System.out.println(alpha);
                System.out.println(beta);
                result = new ArrayList<>(List.of(-1));
                return result;
            }
        }

        return result;
    }

    public List<String> getProductionByOrderNumber(int order){
        var production = productionsRhs.get(order-1);
        if(production.contains("epsilon"))
            return List.of("epsilon");
        return production;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public List<List<String>> getProductionsRhs() {
        return productionsRhs;
    }
}
