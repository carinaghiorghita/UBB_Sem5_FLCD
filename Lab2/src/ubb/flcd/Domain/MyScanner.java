package ubb.flcd.Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyScanner {
    private String filename;
    private LanguageSpecification ls = new LanguageSpecification();
    int capacity = 100; //todo modifica pe cate tokens ai in language, cred
    private SymbolTable symbolTable = new SymbolTable(capacity);

    public MyScanner(String filename) {
        this.filename = filename;
        //readTokens();
        //readSeparators();
        //scan();
    }


    /*private void scan(){
        try {
            File separatorsFile = new File("src/ubb/flcd/Utils/program.txt");
            Scanner reader = new Scanner(separatorsFile);

            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] tokens = line.split(" ");

                for(int i=0;i< tokens.length;++i){

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public boolean isPartOfOperator(char c){

    }

    private ArrayList<String> tokenize(String line){
        ArrayList<String> tokens = new ArrayList<>();

        for(int i=0;i<line.length();++i){
            if (isPartOfOperator(line.charAt(i))) {

            }
        }

        return tokens;
    }

}
