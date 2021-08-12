package com.company;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.regex.Pattern;

public class MyMap extends TreeMap<String, String> implements Dictionary<MyMap> {
    private String name;
    private String regExp;
    TreeMap<String, String> map;

    MyMap() {
        name = "default";
        map = new TreeMap<>();
    }

    MyMap(String name) {
        this.name = name;
        map = new TreeMap<>();
    }

    MyMap(String regExp, String name) {
        this.name = name;
        this.regExp = regExp;
        map = new TreeMap<>();
    }

    Set<Map.Entry<String, String>> getMapAsSet() {
        return map.entrySet();
    }

    @Override
    public MyMap create(String regExp, String dictionaryName) {
        return new MyMap(regExp, dictionaryName);
    }

    @Override
    public MyMap open(String name) {
        return new MyMap(name);
    }

    @Override
    public void delete(String key) throws Exception {
        Set<Map.Entry<String, String>> mapSet = getMapAsSet();
        for(Map.Entry<String, String> note : mapSet) {
            if (key.equals(note.getKey())) {
                map.remove(key);
            }
        }
    }

    @Override
    public int getQuantityOfNotes() throws Exception {
        Set<Map.Entry<String, String>> mapSet = getMapAsSet();
        int count = 0;
        for(Map.Entry<String, String> note : mapSet) {
            count += 1;
        }
        return count;
    }

    @Override
    public StringBuffer find(String key) throws Exception {
        Set<Map.Entry<String, String>> mapSet = getMapAsSet();
        StringBuffer outputString = new StringBuffer();
        String result = null;
        for(Map.Entry<String, String> note : mapSet) {
            if (key.equals(note.getKey()))
                result = note.getKey() + " " + note.getValue();
                outputString.append(result);
        }
        return outputString;
    }

    @Override
    public StringBuffer showContent() throws Exception {
        Set<Map.Entry<String, String>> mapSet = getMapAsSet();
        StringBuffer outputBuffer = new StringBuffer();
        for(Map.Entry<String, String> note : mapSet) {
            String outputString = note.getKey() + " " + note.getValue();
            outputBuffer.append(outputString);
        }
        return outputBuffer;
    }

    @Override
    public String add(String note) throws Exception {
        if (Pattern.matches(regExp, note)) {
            String key, value;
            String[] entry;
            entry = note.split(" ");
            key = entry[0];
            value = entry[1];
            map.put(key, value);
            return "New note added";
        }
        return "Note not added. Check dictionary condition.";
    }

    @Override
    public String usedNow() {
        return name;
    }

    @Override
    public String getRegExp() throws Exception {
        return regExp;
    }
}
