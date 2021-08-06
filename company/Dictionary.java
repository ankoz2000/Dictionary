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
    void add(String note);
    int getWordCondition();
}

class Dict implements Dictionary {
    int wordLenCondition; // 4 - Значение по умолчанию
    Dict() {
        wordLenCondition = 4;
    }
    Dict(int wordLenCond) {
        wordLenCondition = wordLenCond;
    }
    /* Dict(int wordLenCond, String str) Конструктор с начальной записью? */
    public void showContent() {

    }
    public void delete(int key) {

    }
    public void find(int key) {

    }
    public void add(String note) {
        if(!check(note, getWordCondition())) {
            System.out.println("Error! Your string doesn't match dictionary conditions.");
        }
    }
    public int getWordCondition() {
        return wordLenCondition;
    }
}

