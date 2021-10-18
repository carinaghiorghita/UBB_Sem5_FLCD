package ubb.flcd.Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LanguageSpecification {
    private ArrayList<String> operators = new ArrayList<>();
    private ArrayList<String> separators = new ArrayList<>();
    private ArrayList<String> reservedWords = new ArrayList<>();

    private final HashMap<String, Integer> codification = new HashMap<>();

    public LanguageSpecification() {
        readOperators();
        readSeparators();
        readReservedWords();
        createCodification();
    }

    private void readOperators(){
        try {
            File operatorFile = new File("src/ubb/flcd/Utils/operators.txt");
            Scanner reader = new Scanner(operatorFile);

            while(reader.hasNextLine()){
                String line = reader.nextLine();
                operators.add(line);
            }

            reader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readSeparators(){
        try {
            File separatorsFile = new File("src/ubb/flcd/Utils/separators.txt");
            Scanner reader = new Scanner(separatorsFile);

            while(reader.hasNextLine()){
                String line = reader.nextLine();
                separators.add(line);
            }

            reader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void readReservedWords(){
        try {
            File reservedWordsFile = new File("src/ubb/flcd/Utils/reservedWords.txt");
            Scanner reader = new Scanner(reservedWordsFile);

            while(reader.hasNextLine()){
                String line = reader.nextLine();
                separators.add(line);
            }

            reader.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void createCodification(){
        codification.put("identifier", 0);
        codification.put("constant", 1);

        int code = 2;

        for (String operator: operators) {
            codification.put(operator, code);
            code++;
        }

        for (String separator: separators) {
            codification.put(separator, code);
            code++;
        }

        for (String reservedWord: reservedWords) {
            codification.put(reservedWord, code);
            code++;
        }
    }

}
