package ubb.flcd.Domain;

import java.util.ArrayList;

public class SymbolTable {
    private ArrayList<ArrayList<String>> items;
    private int size;

    public SymbolTable(int size) {
        this.size = size;
        this.items = new ArrayList<>();
        for(int i=0;i<size;++i) this.items.add(new ArrayList<>());
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

        int hashValue = hash(key);

        if(!items.get(hashValue).contains(key)){
            items.get(hashValue).add(key);
            return true;
        }
        return false;
    }

    public boolean contains(String key){
        int hashValue = hash(key);

        return items.get(hashValue).contains(key);
    }

    public Pair<Integer, Integer> getPosition(String key){
        if (this.contains(key)){
            int listPosition = this.hash(key);
            int listIndex = 0;
            for(String el:this.items.get(listPosition)) {
                if (!el.equals(key))
                    listIndex++;
                else
                    break;
            }

            return new Pair<>(listPosition, listIndex);
        }
        return null;
    }

    public boolean remove(String key){
        int hashValue = hash(key);

        if(items.get(hashValue).contains(key)){
            items.get(hashValue).remove(key);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "items=" + items +
                ", size=" + size +
                '}';
    }
}

