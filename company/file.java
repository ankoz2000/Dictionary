package com.company;
import javax.imageio.IIOException;
import java.io.*;
import java.util.regex.Pattern;

public class file {
    private File fd;
    private String filepath;
    private PrintWriter pw;
    private FileReader rd;
    private boolean isOpen = false;
    static File folder = new File("./");

    file() { filepath = "default.txt"; }

    file(String filepath) {
        setFilePath(filepath);
        openFile();
        setStateOpened();
    }

    public boolean getState() {
        return isOpen;
    }

    private void setStateOpened() {
        isOpen = true;
    }

    public void setFilePath(String filepath) { this.filepath = filepath; } // private?

    private void closeFile() { pw.close(); }

    public void openFile() {
        try {
            fd = new File(filepath);

            if (fd.createNewFile()) {
                System.out.println("File created successfully!");
            } else {
                System.out.println("Action done!");
            }
        } catch (IOException exception) {
            System.out.println("Error while opening/creating file: " + exception);
        }
    }

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
}
