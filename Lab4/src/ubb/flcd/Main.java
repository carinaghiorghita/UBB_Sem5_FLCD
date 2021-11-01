package ubb.flcd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FiniteAutomation fa = new FiniteAutomation("D:\\fac\\sem5\\lftc\\UBB_Sem5_FLCD\\Lab4\\src\\ubb\\flcd\\FA.in ");

        System.out.println("FA read from file.");
        printMenu();
        System.out.println("Your option: ");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        while(option != 0) {
            switch (option) {
                case 1 -> System.out.println(fa.writeStates());
                case 2 -> System.out.println(fa.writeAlphabet());
                case 3 -> System.out.println(fa.writeFinalStates());
                case 4 -> System.out.println(fa.writeTransitions());
                case 5 -> {
                    System.out.println("Your sequence: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String sequence = scanner.nextLine();

                    if(fa.checkSequence(sequence))
                        System.out.println("Sequence is valid");
                    else
                        System.out.println("Invalid sequence");
                }
            }
            System.out.println();
            printMenu();
            System.out.println("Your option: ");
            option = scanner.nextInt();
        }
    }

    private static void printMenu(){
        System.out.println("1. Print states.");
        System.out.println("2. Print alphabet.");
        System.out.println("3. Print final states.");
        System.out.println("4. Print transitions.");
        System.out.println("5. Check if sequence is accepted by FA.");
        System.out.println("0. Exit");
    }
}
