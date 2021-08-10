package com.company;

import java.util.regex.Pattern;

public interface Dictionary {
    default boolean check(String str, String regExp) { return Pattern.matches(regExp, str); }

    void delete(String key) throws Exception;
    int getQuantityOfLines() throws Exception;
    StringBuffer find(String key) throws Exception;
    StringBuffer showContent() throws Exception;
    String add(String note) throws Exception;
    String usedNow();
    String getRegExp() throws Exception;

}
