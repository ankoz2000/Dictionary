package com.company;

import java.io.File;

public class Utils {
    static File folder = new File("./");

    static public void showAllFiles() { // Переписать. Файл не знает что мы работаем с консолью???
        int i = 0;
        for(File fileEntry : folder.listFiles()) {
            if(fileEntry.isFile() && (getFileExtension(fileEntry).equals("txt"))) {
                System.out.print(++i + ". ");
                System.out.println(fileEntry.getName());
            }
        }
    }

    static public String[] getAllFilesAsBuffer() {
        String[] buff = new String[countQuantityOfTXT()];
        int i = 0;
        for(File fileEntry : folder.listFiles()) {
            if(isTXT(fileEntry)) {
                String nameOfFile = fileEntry.getName();
                buff[i] = nameOfFile;
                i += 1;
            }
        }
        return buff;
    }

    static boolean isTXT(File fileEntry) {
        return fileEntry.isFile() && (getFileExtension(fileEntry).equals("txt"));
    }

    static int countQuantityOfTXT() {
        int quantityOfFiles = 0;
        for(File fileEntry : folder.listFiles())
            ++quantityOfFiles;
        return  quantityOfFiles;
    }

    static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1); // Вырезать все знаки после точки
        else return "";
    }
}
