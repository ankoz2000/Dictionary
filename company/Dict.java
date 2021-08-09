package com.company;

class Dict implements Dictionary {
    private String path = "default.txt";
    private MyFile f;
    Dict() {
        f = new MyFile(path);
    }

    Dict(String filePath) {
        path = filePath;
        f = new MyFile(path);
    }

    Dict(String regExp, String filePath) {
        path = filePath;
        f = new MyFile(path, regExp);
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
        if(!check(note, getRegExp())) {
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
    public int getQuantityOfLines() {
        return f.getQuantityOfLines();
    }

    @Override
    public String usedNow() {
        return f.getFileName();
    }

    @Override
    public String getRegExp() {
        return f.getRegExp();
    }
}