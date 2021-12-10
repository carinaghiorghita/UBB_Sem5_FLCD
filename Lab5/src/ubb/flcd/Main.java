package ubb.flcd;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Grammar grammar = new Grammar("src/ubb/flcd/Resources/g1.txt");

//        System.out.println(grammar.printNonTerminals());
//        System.out.println(grammar.printTerminals());
//        System.out.println(grammar.printProductions());
//        System.out.println(grammar.printProductionsForNonTerminal("if_stmt"));
//        System.out.println(grammar.printProductionsForNonTerminal("declaration"));
//        System.out.println(grammar.getProductionForNonterminal("declaration"));
//        System.out.println(grammar.checkIfCFG());

        Parser parser = new Parser(grammar);
//        System.out.println(parser.printFirst());
//        System.out.println(parser.printFollow());
        System.out.println(parser.printParseTable());
        List<String> sequence = List.of("(","int",")","+","int");
        System.out.println(parser.parseSequence(sequence));
//        System.out.println(parser.getProductionsRhs());

        ParserOutput parserOutput = new ParserOutput(parser,sequence);
        parserOutput.printTree();
    }
}
