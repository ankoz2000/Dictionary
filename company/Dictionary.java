package com.company;

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
    String add(String note);
    int getWordCondition();
    int getQuantityOfLines();
    String usedNow();

    static boolean isCorrectPath(String nameOfFile) {
        return Pattern.matches("^.+[a-zA-Z]\\.(txt)$", nameOfFile);
    }
    static String[] getAllAvailableDictionaries() {
        Utils.showAllFiles();
        return Utils.getAllFilesAsBuffer();
    }
}
