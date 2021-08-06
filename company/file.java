package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;

public class file {
    private File fd;
    private String filepath;
    private PrintWriter pw;
    private FileReader rd;
    private boolean isOpen = false;

    file() { filepath = "default.txt"; }

    file(String filepath) {
        setFilePath(filepath);
        openFile();
    }

    public void setFilePath(String filepath) { this.filepath = filepath; } // private?

    public void openFile() {
        try {
            fd = new File(filepath);

            if (fd.createNewFile()) {
                System.out.println("File created successfully!");
            } else {
                System.out.println("The named file already exists");
            }
        } catch (IOException exception) {
            System.out.println("Error while opening/creating file: " + exception);
        }
    }

    private void closeFile() { pw.close(); }

    public void writeToFile(String str) {
        openFile();
        try {
            pw = new PrintWriter(fd);
        } catch (IOException exception) {
            System.out.println("Error while writing to file: " + exception);
        }
        pw.println(str);
        closeFile();
    }

    public void readFromFile(int n) { // Сколько позиций читать?
        try {
            rd = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader br = new BufferedReader(rd);
        /* Чтение из файла информации */
        String tempStr;
        for(int i = 0; i < n; i++)
            try {
                tempStr = br.readLine();
            } catch (IOException exception) {
                System.out.println("End of file reached: " + exception);
            }
            System.out.println(); // Нужна проверка на возвращаемый NULL
        try{
            br.close();
        } catch (IOException exception) {
            System.out.println("Readable file cannot be closed");
        }
    }


}
