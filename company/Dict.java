package com.company;

class Dict implements Dictionary {
    private String path = "default.txt";
    private MyFile file;
    Dict() {
        file = new MyFile(path);
    }

    Dict(String filePath) {
        path = filePath;
        file = new MyFile(path);
    }

    Dict(String regExp, String filePath) {
        path = filePath;
        file = new MyFile(path, regExp);
    }

    @Override
    public void read(int start, int end) {
        showContent();
        if(file.getState()) {
            file.readFromFile(start, end);
        }
    }

    @Override
    public String add(String note) {
        if(!check(note, getRegExp())) {
            return "Error! Your string doesn't match dictionary conditions.";
        }
        if(file.getState()) {
            file.add(note);
            return "String successfully added to file.";
        } else return "Unhandled error...";
    }

    @Override
    public void delete(String key) {
        file.deleteString(key);
    }

    @Override
    public StringBuffer find(String key) {
        ///check() проверка корректности ключа?
        return file.find(key);
    }

    @Override
    public StringBuffer showContent() {
        return file.readFullFile();
    }

    @Override
    public int getQuantityOfLines() {
        return file.getQuantityOfLines();
    }

    @Override
    public String usedNow() {
        return file.getFileName();
    }

    @Override
    public String getRegExp() {
        return file.getRegExp();
    }
}