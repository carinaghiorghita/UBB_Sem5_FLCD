package ubb.flcd;

import ubb.flcd.Domain.*;

public class Main {

    public static void main(String[] args) {
/*
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
*/
        System.out.println();
        System.out.println("Program 1");
        MyScanner scannerP1 = new MyScanner("src/ubb/flcd/Utils/P1.txt",
                "src/ubb/flcd/Utils/PIF1.txt",
                "src/ubb/flcd/Utils/ST1.txt");
        scannerP1.scan();

        System.out.println("------------------------");
        System.out.println("Program 2");
        MyScanner scannerP2 = new MyScanner("src/ubb/flcd/Utils/P2.txt",
                "src/ubb/flcd/Utils/PIF2.txt",
                "src/ubb/flcd/Utils/ST2.txt");
        scannerP2.scan();


        System.out.println("------------------------");
        System.out.println("Program 3");
        MyScanner scannerP3 = new MyScanner("src/ubb/flcd/Utils/P3.txt",
                "src/ubb/flcd/Utils/PIF3.txt",
                "src/ubb/flcd/Utils/ST3.txt");
        scannerP3.scan();

        System.out.println("------------------------");
        System.out.println("Program with lexical errors");
        MyScanner scannerP1err = new MyScanner("src/ubb/flcd/Utils/P1err.txt",
                "src/ubb/flcd/Utils/PIFerr.txt",
                "src/ubb/flcd/Utils/STerr.txt");
        scannerP1err.scan();
    }
}
