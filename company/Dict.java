package com.company;

class Dict implements Dictionary<Dict> {
    private String path = "default.txt";
    private MyFile file;
    Dict() throws Exception {
        file = new MyFile(path);
    }

    Dict(String filePath) throws Exception{
        path = filePath;
        file = new MyFile(path);
    }

    Dict(String regExp, String filePath) throws Exception{
        path = filePath;
        file = new MyFile(path, regExp);
    }

    @Override
    public Dict create(String regExp, String dictionaryName) throws Exception{
        return new Dict(regExp, dictionaryName);
    }

    public Dict create(String dictionaryName) throws Exception{
        return new Dict(dictionaryName);
    }

    @Override
    public Dict open(String name) throws Exception{
        return create(name);
    }

    @Override
    public void delete(String key) throws Exception{
        file.deleteString(key);
    }

    @Override
    public int getQuantityOfNotes() throws Exception {
        return file.getQuantityOfLines();
    }

    @Override
    public String add(String note) throws Exception {
        String regexp = getRegExp().replaceAll("^\\$cfg::", "");
        if(!check(note, regexp)) {
            return "Error! Your string doesn't match dictionary conditions.";
        }
        file.add(note);
        return "String successfully added to file.";
    }

    @Override
    public StringBuffer find(String key) throws Exception{
        return file.find(key);
    }

    @Override
    public StringBuffer showContent() throws Exception {
        return file.readFullFile();
    }

    @Override
    public String usedNow() {
        return file.getFileName();
    }

    @Override
    public String getRegExp() throws Exception {
        return file.getRegExp();
    }
}