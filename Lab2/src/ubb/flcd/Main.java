package ubb.flcd;

import ubb.flcd.Domain.*;

public class Main {

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(16);

        System.out.println(symbolTable.add("abc"));
        System.out.println(symbolTable.add("cba"));
        System.out.println(symbolTable.add("a"));
        System.out.println(symbolTable.add("ab"));
        System.out.println(symbolTable.add("ab"));
        System.out.println("---------------------------");

        System.out.println(symbolTable.contains("a"));
        System.out.println(symbolTable.contains("abc"));
        System.out.println(symbolTable.contains("cba"));
        System.out.println(symbolTable.contains("d"));
        System.out.println(symbolTable.contains("bca"));
        System.out.println("---------------------------");

        System.out.println(symbolTable.remove("a"));
        System.out.println(symbolTable.remove("ad"));
        System.out.println("---------------------------");

        System.out.println(symbolTable);
    }
}
