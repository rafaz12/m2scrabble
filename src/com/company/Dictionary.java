package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

public class Dictionary {
    HashSet<String> words ;
    String word;
    public Dictionary(){
        HashSet<String> wordSet = new HashSet<>();
        File file = new File("src/words.txt");
        try{
            Scanner read = new Scanner(file);
            while(read.hasNextLine()){
                String line = read.nextLine().toLowerCase();
                wordSet.add(line);
            }
            read.close();
            this.words = wordSet;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");

        }
    }
    boolean contains(String word){
        return this.words.contains(word.toLowerCase());
    }
}
