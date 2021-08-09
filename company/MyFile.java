package com.company;
import java.io.*;

public class MyFile {
    private File fd;
    private String filepath;
    private String configString; // example: $cfg::regExp1::regExp2\n
    private PrintWriter pw;
    private FileReader rd;
    private boolean isOpen = false;


    MyFile() { filepath = "default.txt"; }

    MyFile(String filepath) {
        setFilePath(filepath);
        // Дефолтное значение длины слов - 4
        setConfigString(String.format("$cfg::[a-zA-Z]{%d}\s[а-яА-Я]{%d}",
                Utils.defaultWordLength,
                Utils.defaultWordLength
        ));
        openFile();
        setStateOpened();
    }

    MyFile(String filepath, String configString) {
        setFilePath(filepath);
        setConfigString(configString);
        openFile();
        setStateOpened();
    }

    public boolean getState() {
        return isOpen;
    }

    private void setStateOpened() {
        isOpen = true;
    }

    private void setStateClose() {
        isOpen = false;
    }

    private void setConfigString(String configString) {
        this.configString = configString;
    }

    public void setFilePath(String filepath) { this.filepath = filepath; } // private?

    private void closeFile() {
        setStateClose();
        pw.close();
    }

    private void writeConfigString(String configString) {
        addToFile("$cfg::" + configString + "\n");
    }

    public void openFile() {
        try {
            fd = new File(filepath);

            if (fd.createNewFile()) {
                System.out.println("File created successfully!");
                writeConfigString(configString);
            } else {
                System.out.println("Action done!");
            }
        } catch (IOException exception) {
            System.out.println("Error while opening/creating file: " + exception);
        }
    }

    public void addToFile(String str) {
        try {
            pw = new PrintWriter(fd);
        } catch (IOException exception) {
            System.out.println("Error while writing to file: " + exception);
        }
        pw.println(str);
    }

    public StringBuffer readFromFile(int s, int e) { // Сколько позиций читать?
        try {
            rd = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader br = new BufferedReader(rd);
        StringBuffer buff = new StringBuffer("\nOut:\n");
        /* Чтение из файла информации */
        if (s == e) e += 1; // Если задали промежуток (3, 3) то прочтёт только 3 строку
        for(int i = s; i < e; i++) {
            try {
                buff.append(br.readLine());
            } catch (IOException exception) {
                System.out.println("End of file reached: " + exception);
            }
        }
            System.out.println(); // Нужна проверка на возвращаемый NULL
        try{
            br.close();
        } catch (IOException exception) {
            System.out.println("Readable file cannot be closed");
        }
        return buff;
    }

    public StringBuffer readFull() {
        try {
            rd = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader br = new BufferedReader(rd);
        StringBuffer buff = new StringBuffer("\nOut:\n");
        try {
            String lines = br.readLine();
            while (lines != null) {
                buff.append(lines);
                lines = br.readLine();
            }
        } catch (IOException exception) {
            System.out.println("Error while readFull(): " + exception);
        }
        return buff;
    }

    protected StringBuffer find(String key) {
        StringBuffer result = new StringBuffer("Out: ");
        try {
            rd = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader br = new BufferedReader(rd);
        try {
            String line = br.readLine();
            while (line != null) {

                if (line.split(" ")[0].equals(key))
                    result.append(line);
                line = br.readLine();
            }
        } catch (IOException exception) {
            System.out.println("Error while find(): " + exception);
        }
        return result;
    }

    public void delete(String key) {
        File outputFile = new File("Dist.txt");
        BufferedReader br = null;
        try {
            rd = new FileReader(filepath);
            br = new BufferedReader(rd);
            PrintWriter pw = new PrintWriter(outputFile);
        } catch (FileNotFoundException FNFE) {
            System.out.println("Error with delete(): " + FNFE);
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!line.split(" ")[0].equals(key)) {
                    pw.write(line);
                }
            }
            br.close();
        } catch (IOException IOE) {
            System.out.println("Error with delete(): " + IOE);
        }
        pw.close();
        fd.delete();
        outputFile.renameTo(new File(fd.getName()));
    }

    protected int getQuantityOfLines() {
        try {
            rd = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader br = new BufferedReader(rd);
        int linesCount = 0;
        try {
            String line = br.readLine();
            while (line != null) {
                linesCount += 1;
                line = br.readLine();
            }
        } catch (IOException exception) {
            System.out.println("Error counting file lines: " + exception);
        }
        return linesCount;
    }

    public String getFileName() { return fd.getName(); }

    public String getRegExp() {
        String line = "";
        try {
            rd = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader br = new BufferedReader(rd);
        try {
            line = br.readLine();
        } catch (IOException exception) {
            System.out.println("Error counting file lines: " + exception);
        }
        return line.replaceAll("^\\$cfg::", "");
    }
}
