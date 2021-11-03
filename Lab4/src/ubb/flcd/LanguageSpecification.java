package ubb.flcd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LanguageSpecification {
    private final List<String> reservedWords = Arrays.asList("int", "float", "string", "char", "for", "while", "if", "else", "read", "print");
    private final List<String> operators = Arrays.asList("+", "-", "*", "/", "%", "=", "==", "!=", "<", ">", "<=", ">=");
    private final List<String> separators = Arrays.asList("(", ")", ";", "{", "}","[", "]", " ", "\t", "\n");

    private final HashMap<String, Integer> codification = new HashMap<>();

    private final FiniteAutomaton intDFA = new FiniteAutomaton("src/ubb/flcd/Resources/intDFA.in");
    private final FiniteAutomaton identifierDFA = new FiniteAutomaton("src/ubb/flcd/Resources/identifierDFA.in");

    public LanguageSpecification() {
        createCodification();
    }

    private void createCodification() {
        codification.put("identifier", 0);
        codification.put("constant", 1);

        int code = 2;

        for (String reservedWord : reservedWords) {
            codification.put(reservedWord, code);
            code++;
        }

        for (String operator : operators) {
            codification.put(operator, code);
            code++;
        }

        for (String separator : separators) {
            codification.put(separator, code);
            code++;
        }
    }

    public boolean isReservedWord(String token) {
        return reservedWords.contains(token);
    }

    public boolean isOperator(String token) {
        return operators.contains(token);
    }

    public boolean isPartOfOperator(char op) {
        return op == '!' || isOperator(String.valueOf(op));
    }

    public boolean isSeparator(String token) {
        return separators.contains(token);
    }

    public boolean isIdentifier(String token) {
        return identifierDFA.checkSequence(token);
    }

    public boolean isConstant(String token) {
        String charPattern = "^\'[a-zA-Z0-9_?!#*./%+=<>;)(}{ ]\'";
        String stringPattern = "^\"[a-zA-Z0-9_?!#*./%+=<>;)(}{ ]+\"";
        return intDFA.checkSequence(token) ||
                token.matches(charPattern) ||
                token.matches(stringPattern);
    }

    public Integer getCode(String token) {
        return codification.get(token);
    }
}
