package com.company;

public interface Dictionary {
    public void check(); // default private ?
    public void showContent();
    public void delete(int key);
    public void find(int key);
    public void add(String note);
}

