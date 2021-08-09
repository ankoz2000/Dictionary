package com.company;

class Dict implements Dictionary {
    private int wordLenCondition = 4; // 4 - Значение по умолчанию
    private String path = "default.txt";
    private MyFile f;
    Dict() {
        f = new MyFile(path);
    }

    Dict(int wordLenCond) {
        wordLenCondition = wordLenCond;
        f = new MyFile();
    }

    Dict(String filePath) {
        path = filePath;
        f = new MyFile(path);
    }

    Dict(int wordLenCond, String filePath) {
        wordLenCondition = wordLenCond;
        path = filePath;
        f = new MyFile(path);
    }

    @Override
    public void read(int start, int end) {
        showContent();
        if(f.getState()) {
            f.readFromFile(start, end);
        }
    }

    @Override
    public String add(String note) {
        if(!check(note, getWordCondition())) {
            return "Error! Your string doesn't match dictionary conditions.";
        }
        if(f.getState()) {
            f.addToFile(note);
            return "String successfully added to file.";
        } else return "Unhandled error...";
    }

    @Override
    public void delete(String key) {
        f.delete(key);
    }

    @Override
    public StringBuffer find(String key) {
        ///check() проверка корректности ключа?
        return f.find(key);
    }

    @Override
    public StringBuffer showContent() {
        return f.readFull();
    }

    @Override
    public int getWordCondition() {
        return wordLenCondition;
    }

    @Override
    public int getQuantityOfLines() {
        return f.getQuantityOfLines();
    }

    @Override
    public String usedNow() {
        return f.getFileName();
    }
}