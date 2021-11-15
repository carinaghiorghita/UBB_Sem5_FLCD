package ubb.flcd;

public class Main {

    public static void main(String[] args) {
	    Grammar grammar = new Grammar();

        System.out.println(grammar.printNonTerminals());
        System.out.println(grammar.printTerminals());
        System.out.println(grammar.printProductions());
        System.out.println(grammar.printProductionsForNonTerminal("if_stmt"));
        System.out.println(grammar.printProductionsForNonTerminal("declaration"));
        System.out.println(grammar.checkIfCFG());
    }
}
