package com.company;
import java.io.*;
import java.util.regex.Pattern;

public class MyFile {
    private File fileDescriptor;
    private String filepath;
    private String configString; // example: $cfg::regExp1\n
    private PrintWriter writer;
    private FileReader reader;
    private boolean isOpen = false;


    MyFile() { filepath = "default.txt"; }

    MyFile(String filepath) {
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
        setStateOpened();
    }

    MyFile(String filepath, String configString) {
        setFilePath(filepath);
        setConfigString(configString);
        openFile();
        if (getRegExp().equals("")) {
            writeConfigString(configString);
        }
        setStateOpened();
    }

    private void setStateOpened() {
        isOpen = true;
    }

    private void setStateClose() {
        isOpen = false;
    }

    protected void setConfigString(String configString) {
        this.configString = configString;
    }

    protected void setFilePath(String filepath) { this.filepath = filepath; }

    private void closeFile() {
        setStateClose();
        writer.close();
    }

    private void writeConfigString(String configString) {
        writeFirstString("$cfg::" + configString + "\n");
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

    private void writeFirstString(String configString) {
        try {
            writer = new PrintWriter(fileDescriptor);
        } catch (IOException exception) {
            System.out.println("Error while writing to file: " + exception);
        }
        writer.write(configString);
        closeFile();
    }

    public void add(String newString) {
        openFile();
        try {
            writer = new PrintWriter(new FileWriter(fileDescriptor, true));
        } catch (IOException exception) {
            System.out.println("Error while writing to file: " + exception);
        }
        writer.println(newString);

        closeFile();
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
            System.out.println(); // Нужна проверка на возвращаемый NULL ?
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
            bufferedReader.readLine(); // пропускаем конфигурационную строку
            String lines = bufferedReader.readLine();
            while (lines != null) {
                outputBuffer.append(lines).append("\n");
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
        /* Работает */
        File outputFile = new File("Dist.txt");
        File sourceFile = new File(fileDescriptor.getAbsolutePath()); // Ссылочная переменная
        BufferedReader bufferedReader = null;
        PrintWriter writer = null;

        try {
            reader = new FileReader(sourceFile);
            bufferedReader = new BufferedReader(reader);
            writer = new PrintWriter(outputFile);
        } catch (FileNotFoundException FNFE) {
            System.out.println("Error with delete(): " + FNFE);
        }
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.split(" ")[0].equals(key)) {
                    String resultLine = line + "\n";
                    writer.write(resultLine);
                }
            }
            bufferedReader.close();
        } catch (IOException IOE) {
            System.out.println("Error with delete(): " + IOE);
        }
        try {
            writer.close();
        } catch (NullPointerException nullPointerException) {
            System.out.println("NullPonterException: " + nullPointerException);
        }

        String absoluteFilePath = fileDescriptor.getAbsolutePath();
        System.out.println("file name: " + absoluteFilePath);
        /* ***** */
        /* Не работает */
        if(sourceFile.delete())
            System.out.println("String successfully deleted");
        else System.out.println("Not deleted");
        System.out.println(absoluteFilePath);
        if(outputFile.renameTo(sourceFile))
            System.out.println("Successfully renamed");
        else System.out.println("Not renamed");
        /* ***** */
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
            bufferedReader.readLine(); // пропускаем конфигурационную строку
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
            reader = new FileReader(fileDescriptor);
        } catch (IOException exception) {
            System.out.println("Error while reading file: " + exception);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            line = bufferedReader.readLine();
            if (line == null) return Utils.getDefaultRegExp();
        } catch (IOException exception) {
            System.out.println("Error finding RegExp: " + exception);
        }
        return line;
    }
}
