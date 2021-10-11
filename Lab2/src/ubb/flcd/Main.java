package ubb.flcd;

import ubb.flcd.Domain.*;

public class Main {

    public static void main(String[] args) {
        HashTable hashTable = new HashTable(16);

        hashTable.add("abc");
        hashTable.add("cba");
        hashTable.add("a");
        hashTable.add("ab");

        System.out.println(hashTable.contains("a"));
        System.out.println(hashTable.contains("abc"));
        System.out.println(hashTable.contains("cba"));
        System.out.println(hashTable.contains("d"));
        System.out.println(hashTable.contains("bca"));
    }
}
