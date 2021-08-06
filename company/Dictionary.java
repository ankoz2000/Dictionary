package com.company;

import java.util.regex.Pattern;

public interface Dictionary {
    default boolean check(String str, int wordLength) {
        int len = str.length();
        String regexp = String.format("[a-zA-Z]{%d}", wordLength);
        return Pattern.matches(regexp, str);
    }
    void showContent();
    void delete(int key);
    void find(int key);
    boolean add(String note);
    int getWordCondition();
    static boolean isCorrectPath(String nameOfFile) {
        return Pattern.matches("^.+[a-zA-Z]\\.(txt)$", nameOfFile);
    }
}

class Dict implements Dictionary {
    private int wordLenCondition = 4; // 4 - Значение по умолчанию
    private String path;
    //static boolean isOpen = false;
    private file f;
    Dict() {
        //isOpen = true;
    }
    Dict(int wordLenCond) {
        wordLenCondition = wordLenCond;
        f = new file();
        //isOpen = true;
        f.openFile();
    }
    Dict(String filePath) {
        path = filePath;
        //isOpen = true;
        f = new file(path);
        f.openFile();
    }
    Dict(int wordLenCond, String filePath) {
        wordLenCondition = wordLenCond;
        path = filePath;
        f = new file(path);
        //isOpen = true;
    }
    /* Dict(int wordLenCond, String str) Конструктор с начальной записью? */
    public void showContent() {

    }
    public void delete(int key) {

    }
    public void find(int key) {

    }
    public boolean add(String note) {
        if(!check(note, getWordCondition())) {
            System.out.println("Error! Your string doesn't match dictionary conditions.");
            return false;
        }
        f.writeToFile(note);
        return true;
    }
    public int getWordCondition() {
        return wordLenCondition;
    }
}

