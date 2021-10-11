package ubb.flcd.Domain;

import java.util.Arrays;

public class HashTable {
    private final int size;
    private final String[] items;


    public HashTable (int size) {
        this.size = size;
        this.items = new String[size];
    }

    private int hash(String key){
        int asciiSum = 0;
        for (int i = 0; i < key.length(); i++){
            asciiSum += key.charAt(i);
        }
        return asciiSum % size;
    }

    public boolean add(String key){
        for (String entry : items){
            if (entry != null && entry.equals(key))
                return false;
        }

        int hashValue = hash(key);
        if (items[hashValue] == null){
            items[hashValue] = key;
            return true;
        }

        int newHashValue = hashValue;
        while (items[newHashValue] != null){
            newHashValue++;
        }
        if (items[newHashValue] == null){
            items[newHashValue] = key;
            return true;
        }

        return false;
    }

    public int search(String key){
        int hashValue = hash(key);
        while (items[hashValue] != null){
            if (items[hashValue].equalsIgnoreCase(key)){
                return hashValue;
            }
            hashValue++;
        }
        return -1;
    }

    public boolean contains(String key){
        int hashValue = hash(key);
        while (items[hashValue] != null){
            if (items[hashValue].equalsIgnoreCase(key)){
                return true;
            }
            hashValue++;
        }
        return false;
    }

    public String toString (){
        return Arrays.toString(items);
    }
}


/*
package ubb.flcd.Domain;

import java.util.ArrayList;

public class HashTable {
    private ArrayList<Pair<String,ArrayList<String>>> items = new ArrayList<>();
    private int size;

    public HashTable(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    private int hash(String key) {
        int sum = 0;
        for(int i=0;i< key.length();++i){
            sum += key.charAt(i);
        }
        return sum % size;
    }

    public boolean add(String key){
        for (String entry : items){
            if (entry != null && entry.equals(key))
                return false;
        }

        int hashValue = hash(key);
        if (items.get(hashValue) == null){
            items.set(hashValue, key);
            return true;
        }

        int newHashValue = hashValue;
        while (items.get(newHashValue)!= null){
            newHashValue++;
        }
        if (items.get(newHashValue) == null){
            items.set(newHashValue, key);
            return true;
        }

        return false;
    }

    public boolean contains(String key){
        int hashValue = hash(key);
        while (items.get(hashValue) != null){
            if (items.get(hashValue).equalsIgnoreCase(key)){
                return true;
            }
            hashValue++;
        }
        return false;
    }

    public String remove(String key){
        items.
    }
}
*/

