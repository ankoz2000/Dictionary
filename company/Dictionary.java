package com.company;

import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

public interface Dictionary {
    default boolean check(String str, int wordLength) {
        String regexp = String.format("[a-zA-Z]{%d}\s[а-яА-Я]{%d}", wordLength, wordLength);
        return Pattern.matches(regexp, str);
    }

    void read(int start, int end);
    void delete(String key);
    StringBuffer find(String key);
    StringBuffer showContent();
    boolean add(String note);
    int getWordCondition();
    int getQuantityOfLines();

    static boolean isCorrectPath(String nameOfFile) {
        return Pattern.matches("^.+[a-zA-Z]\\.(txt)$", nameOfFile);
    }
    static String[] getAllAvailableDictionaries() {
        file.showAllFiles();
        return file.getAllFilesAsBuffer();
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
    }
    Dict(int wordLenCond, String filePath) {
        wordLenCondition = wordLenCond;
        path = filePath;
        f = new file(path);
        //isOpen = true;
    }

    @Override
    public void read(int start, int end) {
        showContent();
        if(f.getState()) {
            f.readFromFile(start, end);
        }
    }
    @Override
    public StringBuffer showContent() {
        return f.readFull();
    }
    @Override
    public int getQuantityOfLines() {
        return f.getQuantityOfLines();
    }
    @Override
    public void delete(String key) {

    }
    @Override
    public StringBuffer find(String key) {
        ///check() проверка корректности ключа?
        StringBuffer result = f.find(key);
        return result;
    }
    @Override
    public boolean add(String note) {
        if(!check(note, getWordCondition())) {
            System.out.println("Error! Your string doesn't match dictionary conditions.");
            return false;
        }
        if(f.getState()) {
            f.writeToFile(note);
            return true;
        } else return false;
    }
    @Override
    public int getWordCondition() {
        return wordLenCondition;
    }
}

