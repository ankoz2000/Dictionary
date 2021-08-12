package com.company;

import java.util.regex.Pattern;

public interface Dictionary<T> {
    default boolean check(String str, String regExp) { return Pattern.matches(regExp, str); }
    T create(String regExp, String dictionaryName) throws Exception;
    T open(String name) throws Exception;
    void delete(String key) throws Exception;
    int getQuantityOfNotes() throws Exception;
    StringBuffer find(String key) throws Exception;
    StringBuffer showContent() throws Exception;
    String add(String note) throws Exception;
    String usedNow();
    String getRegExp() throws Exception;

}
