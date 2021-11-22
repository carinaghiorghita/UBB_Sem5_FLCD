package ubb.flcd;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class Parser {
    private Grammar grammar;
    private HashMap<String, Set<String>> firstSet;
    private HashMap<String, Set<String>> followSet;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        this.firstSet = new HashMap<>();
        this.followSet = new HashMap<>();

        generateFirst();
        generateFollow();
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
                var productionsWithNonterminalInRhs = new HashSet<>();
                var allProductions = grammar.getP().values();
                for (var productions : allProductions) {
                    for (var eachProduction : productions)
                        if (eachProduction.contains(nonterminal))
                            productionsWithNonterminalInRhs.add(eachProduction);
                }
                System.out.print(productionsWithNonterminalInRhs);
                for (var production : productionsWithNonterminalInRhs) {
                    //int index =
                }
            }
        }
    }
}
