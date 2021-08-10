package com.company;

import java.util.regex.Pattern;

public interface Dictionary {
    default boolean check(String str, String regExp) { return Pattern.matches(regExp, str); }

    void read(int start, int end);
    void delete(String key);
    StringBuffer find(String key);
    StringBuffer showContent();
    String add(String note);
    int getQuantityOfLines();
    String usedNow();
    String getRegExp();

}
