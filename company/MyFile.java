package com.company;
import java.io.*;

public class MyFile {
    private File fileDescriptor;
    private String filepath;
    private String configString; // example: $cfg::regExp1::regExp2\n
    private PrintWriter writer;
    private FileReader reader;
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

    private void setFilePath(String filepath) { this.filepath = filepath; } // private?

    private void closeFile() {
        setStateClose();
        writer.close();
    }

    private void writeConfigString(String configString) {
        add("$cfg::" + configString + "\n");
    }

    private void openFile() {
        try {
            fileDescriptor = new File(filepath);

            if (fileDescriptor.createNewFile()) {
                System.out.println("File created successfully!");
                writeConfigString(configString);
            } else {
                System.out.println("Action done!");
            }
        } catch (IOException exception) {
            System.out.println("Error while opening/creating file: " + exception);
        }
    }

    public void add(String newString) {
        try {
            writer = new PrintWriter(fileDescriptor);
        } catch (IOException exception) {
            System.out.println("Error while writing to file: " + exception);
        }
        writer.println(newString);
    }

    public StringBuffer readFromFile(int start, int end) { // Сколько позиций читать?
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer outputBuffer = new StringBuffer("\nOut:\n");
        /* Чтение из файла информации */
        if (start == end) end += 1; // Если задали промежуток (3, 3) то прочтёт только 3 строку
        for(int i = start; i < end; i++) {
            try {
                outputBuffer.append(bufferedReader.readLine());
            } catch (IOException exception) {
                System.out.println("End of file reached: " + exception);
            }
        }
            System.out.println(); // Нужна проверка на возвращаемый NULL
        try{
            bufferedReader.close();
        } catch (IOException exception) {
            System.out.println("Readable file cannot be closed");
        }
        return outputBuffer;
    }

    public StringBuffer readFullFile() {
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer outputBuffer = new StringBuffer("\nOut:\n");
        try {
            String lines = bufferedReader.readLine();
            while (lines != null) {
                outputBuffer.append(lines);
                lines = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            System.out.println("Error while readFull(): " + exception);
        }
        return outputBuffer;
    }

    protected StringBuffer find(String key) {
        StringBuffer outputBuffer = new StringBuffer("Out: ");
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String line = bufferedReader.readLine();
            while (line != null) {

                if (line.split(" ")[0].equals(key))
                    outputBuffer.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            System.out.println("Error while find(): " + exception);
        }
        return outputBuffer;
    }

    protected void deleteString(String key) {
        File outputFile = new File("Dist.txt");
        BufferedReader bufferedReader = null;
        try {
            reader = new FileReader(filepath);
            bufferedReader = new BufferedReader(reader);
            PrintWriter pw = new PrintWriter(outputFile);
        } catch (FileNotFoundException FNFE) {
            System.out.println("Error with delete(): " + FNFE);
        }
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.split(" ")[0].equals(key)) {
                    writer.write(line);
                }
            }
            bufferedReader.close();
        } catch (IOException IOE) {
            System.out.println("Error with delete(): " + IOE);
            writer.close();
            String nameOfFile = fileDescriptor.getName();
            fileDescriptor.delete();
            outputFile.renameTo(new File(nameOfFile));
        }
    }

    protected int getQuantityOfLines() {
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        int linesCount = 0;
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                linesCount += 1;
                line = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            System.out.println("Error counting file lines: " + exception);
        }
        return linesCount;
    }

    protected String getFileName() { return fileDescriptor.getName(); }

    protected String getRegExp() {
        String line = "";
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            line = bufferedReader.readLine();
        } catch (IOException exception) {
            System.out.println("Error counting file lines: " + exception);
        }
        return line.replaceAll("^\\$cfg::", "");
    }
}
