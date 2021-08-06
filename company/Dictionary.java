package com.company;

public interface Dictionary {
    int dict1condition = 4;
    int dict2condition = 5;
    default public boolean check(String str) {
        int len = str.length();
        if(len != dict1condition && len != dict2condition) {
            return false;
        }
        return true;
    }; // default private ?
    void showContent();
    void delete(int key);
    void find(int key);
    void add(String note);
}

class Dict implements Dictionary {
    public void showContent() {

    }
    public void delete(int key) {

    }
    public void find(int key) {

    }
    public void add(String note) {

    }
}

