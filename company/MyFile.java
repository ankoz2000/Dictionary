package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MyFile {
    private File fileDescriptor;
    private String filepath;
    private String configString; // example: $cfg::regExp1\n
    private PrintWriter writer;
    private FileReader reader;

    MyFile(String filepath) throws Exception {
        setFilePath(filepath);
        // Дефолтное значение длины слов - 4
        setConfigString(String.format("[a-zA-Z]{%d}\\s[а-яА-Я]{%d}",
                Utils.defaultWordLength,
                Utils.defaultWordLength
        ));
        openFile();
        String regExp = getRegExp();
        if(regExp == null && !Pattern.matches(Utils.configStringStartWith, regExp)) {
            String line = Utils.getDefaultRegExp();
            writeConfigString(line);
        }
    }

    MyFile(String filepath, String configString) throws Exception{
        setFilePath(filepath);
        setConfigString(configString);
        openFile();
        if (getRegExp().equals("")) {
            writeConfigString(configString);
        }
    }

    protected void setConfigString(String configString) {
        this.configString = configString;
    }

    protected void setFilePath(String filepath) { this.filepath = filepath; }

    private void closeFile() {
        writer.close();
    }

    private void writeConfigString(String configString) throws Exception{
        writeFirstString("$cfg::" + configString + "\n");
    }

    private void openFile() throws Exception {
        try {
            fileDescriptor = new File(filepath);

            if (fileDescriptor.createNewFile()) {
                writeConfigString(configString);
            }
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
    }

    private void writeFirstString(String configString) throws Exception {
        try {
            writer = new PrintWriter(fileDescriptor);
        } catch (IOException exception) {
            throw new Exception("WriteFirstStringException");
        }
        writer.write(configString);
        closeFile();
    }

    public void add(String newString) throws Exception{
        openFile();
        try {
            writer = new PrintWriter(new FileWriter(fileDescriptor, true));
        } catch (IOException exception) {
            throw new Exception("AddingNewStringException");
        }
        writer.println(newString);

        closeFile();
    }

    public StringBuffer readFullFile() throws Exception{
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer outputBuffer = new StringBuffer("\nOut:\n");
        try {
            bufferedReader.readLine(); // пропускаем конфигурационную строку
            String lines = bufferedReader.readLine();
            while (lines != null) {
                outputBuffer.append(lines).append("\n");
                lines = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
        return outputBuffer;
    }

    protected StringBuffer find(String key) throws Exception{
        StringBuffer outputBuffer = new StringBuffer("Out: ");
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
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
            throw new Exception("FileAccessException");
        }
        return outputBuffer;
    }

    protected void deleteString(String key) throws Exception{
        /* Работает */
        File sourceFile = new File(fileDescriptor.getAbsolutePath()); // Ссылочная переменная
        ArrayList<String> stringArrayList = new ArrayList<>();
        BufferedReader bufferedReader;
        PrintWriter writer;

        try {
            reader = new FileReader(sourceFile);
            bufferedReader = new BufferedReader(reader);
        } catch (FileNotFoundException FNFE) {
            throw new Exception("FileAccessException");
        }
        try {
            int i = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.split(" ")[0].equals(key)) {
                    String resultLine = line + "\n";
                    stringArrayList.add(i++, resultLine);
                }
            }
        } catch (IOException ioException) {
            throw new Exception("FileAccessException");
        }
        try {
            bufferedReader.close();
            writer = new PrintWriter(sourceFile);
            boolean flagIfRewrite = true; // Если перезаписываем файл...
            for (String line : stringArrayList) {
                if (flagIfRewrite) {
                    writer.write(line);
                    flagIfRewrite = false;
                }
                else writer.append(line);
            }
        } catch (IOException ioException) {
            throw new Exception("FileAccessException");
        }
        try {
            writer.close();
        } catch (NullPointerException nullPointerException) {
            throw new Exception("FileNotExistsException");
        }
    }

    protected int getQuantityOfLines() throws Exception{
        try {
            reader = new FileReader(filepath);
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        int linesCount = 0;
        try {
            bufferedReader.readLine(); // пропускаем конфигурационную строку
            String line = bufferedReader.readLine();
            while (line != null) {
                linesCount += 1;
                line = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
        return linesCount;
    }

    protected String getFileName() { return fileDescriptor.getName(); }

    protected String getRegExp() throws Exception{
        String line;
        try {
            reader = new FileReader(fileDescriptor);
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            line = bufferedReader.readLine();
            if (line == null) return Utils.getDefaultRegExp();
        } catch (IOException exception) {
            throw new Exception("FileAccessException");
        }
        return line;
    }
}
