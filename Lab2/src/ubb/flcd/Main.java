package ubb.flcd;

import ubb.flcd.Domain.*;

public class Main {

    public static void main(String[] args) {
        HashTable hashTable = new HashTable(16);

        System.out.println(hashTable.add("abc"));
        System.out.println(hashTable.add("cba"));
        System.out.println(hashTable.add("a"));
        System.out.println(hashTable.add("ab"));
        System.out.println(hashTable.add("ab"));
        System.out.println("---------------------------");

        System.out.println(hashTable.contains("a"));
        System.out.println(hashTable.contains("abc"));
        System.out.println(hashTable.contains("cba"));
        System.out.println(hashTable.contains("d"));
        System.out.println(hashTable.contains("bca"));
        System.out.println("---------------------------");

        System.out.println(hashTable.remove("a"));
        System.out.println(hashTable.remove("ad"));
    }
}
