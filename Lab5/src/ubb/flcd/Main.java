package ubb.flcd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static List<String> readText(String filename) {
        List<String> sequence = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null) {
                var symbols = List.of(line.split(" "));
                sequence.addAll(symbols);
                line = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sequence;
    }

    public static List<String> readPIF(String filename){
        try {
            List<String> tokens = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null){
                List<String> tokenAndPosition = Arrays.asList(line.split(" "));
                if(!tokenAndPosition.get(3).equals("-1")) {
                    if(tokenAndPosition.get(0).contains("\"") || tokenAndPosition.get(0).contains("'") || !Pattern.matches("[a-zA-Z]+",tokenAndPosition.get(0)))
                        tokens.add("constant");
                    else
                        tokens.add("identifier");
                }
                else
                    tokens.add(tokenAndPosition.get(0).strip());
                line = reader.readLine();
            }
            reader.close();
            return tokens;
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

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
        System.out.println(parser.printFirst());
        System.out.println(parser.printFollow());
        System.out.println(parser.printParseTable());
//        List<String> sequence = List.of("(","int",")","+","int");
        List<String> sequence = readText("src/ubb/flcd/Resources/seq.txt");
//        List<String> sequence = readPIF("src/ubb/flcd/Resources/PIF.txt");
        System.out.println(parser.parseSequence(sequence));
//        System.out.println(parser.getProductionsRhs());

        ParserOutput parserOutput = new ParserOutput(parser,sequence,"src/ubb/flcd/Resources/out1.txt");
        parserOutput.printTree();
    }
}
