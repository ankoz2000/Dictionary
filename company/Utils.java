package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Utils {
    static int defaultWordLength = 4;
    static File folder = new File("./");
    static String configStringStartWith = "^\\$cfg::";

    static public ArrayList<String> showAllFiles() {
        int i = 0;
        ArrayList<String> fileEntryList = new ArrayList<>();
        for(File fileEntry : folder.listFiles()) {
            if(fileEntry.isFile() && (getFileExtension(fileEntry).equals("txt"))) {
                fileEntryList.add((++i) + ". " + fileEntry.getName());
            }
        }
        return fileEntryList;
    }

    static ArrayList<String> getAllAvailableDictionaries() {
        return Utils.showAllFiles();
    }

    static int countQuantityOfTXT() {
        int quantityOfFiles = 0;
        for(File fileEntry : folder.listFiles())
            if(isTXT(fileEntry))
                ++quantityOfFiles;
        return quantityOfFiles;
    }

    static boolean isCorrectPath(String nameOfFile) {
        return Pattern.matches("^.+[a-zA-Z]\\.(txt)$", nameOfFile);
    }

    static boolean isTXT(File fileEntry) {
        return fileEntry.isFile() && (getFileExtension(fileEntry).equals("txt"));
    }


    static public String getDefaultRegExp() {
        return String.format("[a-zA-Z]{%d}\\s[а-яА-Я]{%d}",
                defaultWordLength,
                defaultWordLength
        );
    }

    static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1); // Вырезать все знаки после точки
        else return "";
    }

    static public String[] getAllFilesAsBuffer() {
        String[] outputBuffer = new String[countQuantityOfTXT()];
        int cycleCounter = 0;
        for(File fileEntry : folder.listFiles()) {
            if(isTXT(fileEntry)) {
                String nameOfFile = fileEntry.getName();
                outputBuffer[cycleCounter] = nameOfFile;
                cycleCounter += 1;
            }
        }
        return outputBuffer;
    }
}
