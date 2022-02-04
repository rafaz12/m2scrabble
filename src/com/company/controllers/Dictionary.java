package com.company.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
    HashSet<String> words;

    public Dictionary() {
        HashSet<String> wordSet = new HashSet<>();
        File file = new File("C:\\\\Users\\\\User\\\\Downloads\\\\m2scrabble\\\\words.txt");
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine().toLowerCase();
                wordSet.add(line);
            }
            read.close();
            this.words = wordSet;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");

        }
    }

    public boolean contains(String word) {
        return this.words.contains(word.toLowerCase());
    }
}