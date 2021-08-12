package com.company;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console {
    private int option;
    private String line;
    private Dict dict;

    private boolean inputInt() {
        Scanner in = new Scanner(System.in);
        System.out.print(">> ");
        try {
            if(in.hasNextInt()) // проверки корректности выбора значений меню
                option = in.nextInt();
            else return false;
        } catch (InputMismatchException exception) {
            System.out.println("Incorrect number. Check range of nums you're allowed to use.");
        }
        return true;
    }

    private boolean inputStr() {
        Scanner in = new Scanner(System.in);
        System.out.print(">> ");
        try {
            line = in.nextLine();
        } catch (InputMismatchException exception) {
            System.out.println("Incorrect symbols. Try again!");
        }
        return true;
    }

    private void makeChoice() {
        System.out.println("Input a number: ");
        while(!inputInt()) {
            System.out.println("Incorrect choice. Options may be in range [0, 9]. Try again.");
        }
        decider();
    }

    private void createDict() {
        System.out.println("Write your diction file path or name of file: ");
        inputStr();
        if(Utils.isCorrectPath(line)) {
            String path = line;
            System.out.println("Would you like to specify regular expression for dictionary you're creating? " +
                    "If no, default regular expression will be " + Utils.getDefaultRegExp());
            getYesOrNo();
            if(Options.values()[option] == Options.NO)
                try {
                    dict = dict.create(line);
                } catch (Exception creatingException) {
                    System.out.println("Opening exception. " +
                            "Is your input filename correct? (Example: default.txt)" + creatingException);
                }
            else {
                System.out.println("Regular expression: (separator of words = \\s)");
                inputStr();
                try {
                    dict = dict.create(line, path);
                } catch (Exception creatingException) {
                    System.out.println("Opening exception. " +
                            "Is your input filename correct? (Example: default.txt)" + creatingException);
                }
            }
        } else {
            System.out.println("Could not to create dictionary. Check symbols in name of file.");
            System.out.println("Example: default.txt (nums aren't allowed)");
        }
    }

    private void openDict() {
        System.out.println("What dictionary do you want to open?");
        ArrayList<String> availableDicts = Utils.getAllAvailableDictionaries();
        for(String dict : availableDicts)
            System.out.println(dict);
        String[] dicts = Utils.getAllFilesAsBuffer();
        System.out.println("Input a number of file:");
        inputInt();
        try {
            dict = dict.open(dicts[option - 1]); // Смещение на 1 (индексация массива)
        } catch (Exception openingException) {
            System.out.println("Opening exception. " +
                    "Are you using existing file and access not denied? " + openingException);
        }
        try {
            System.out.println("Спецификация словаря: " + dict.getRegExp());
        } catch (Exception openingException) {
            System.out.println("Opening exception. " +
                    "Are you using existing file and access not denied? " + openingException);
        }
    }

    private void showAllLines() {
        int linesCount = 0;
        try {
            linesCount = dict.getQuantityOfNotes();
        } catch (Exception openingException) {
            System.out.println("Opening exception. " +
                    "Are you using existing file and access not denied? " + openingException);
        }

        System.out.println();
        System.out.println("Quantity of lines: " + linesCount);
        StringBuffer content = null;
        try {
            content = dict.showContent();
        } catch (Exception fileAccessException) {
            System.out.println();
        }
        System.out.println(content);
    }

    private void readFromDict() {
        showAllLines();
        /* Выборка диапазонов чтения */
    }

    private void add() {
        if (dict == null) {
            System.out.println();
            System.out.println("Firstly choose an existing file or create new.");
            System.out.println();
        } else {
            System.out.println("Input string you want to add:");
            if (inputStr())
                try {
                    System.out.println(dict.add(line));
                } catch (Exception fileAccessException) {
                    System.out.println("File access exception. " +
                            "Make sure you work with not used and existing file.");
                }
        }
    }

    private void find() {
        System.out.println("Input key to find note: ");
        inputStr();
        StringBuffer result = new StringBuffer();
        try {
            result = dict.find(line);
        } catch (Exception openingException) {
            System.out.println("Opening exception. " +
                    "Are you using existing file and access not denied? " + openingException);
        }

        System.out.println();
        System.out.println(result);
        System.out.println();
    }

    private void delete() {
        System.out.println("Input key to delete note: ");
        inputStr();
        try {
            dict.delete(line);
        } catch (Exception openingException) {
            System.out.println("Opening exception. " +
                    "Are you using existing file and access not denied? " + openingException);
        }
    }
    private void dictUsedNow() {
        System.out.println(dict.usedNow());
    }

    private void getYesOrNo() {
        System.out.println("Do you agree? (y/n)(д/н): ");
        while(!inputStr()) {
            System.out.println("Incorrect choice. Try again.");
        }
        if(Pattern.matches("[yд]?", line))
            option = Options.YES.ordinal();
        else if (Pattern.matches("[nн]?", line)) option = Options.NO.ordinal();
    }

    private void decider() {
        try {
            switch (Options.values()[option]) {
                case CREATE: {
                    createDict();
                    break;
                }
                case OPEN: {
                    openDict();
                    break;
                }
                case READ: {
                    if (dict == null) throw new NullPointerException();
                    readFromDict();
                    break;
                }
                case ADD: {
                    if (dict == null) throw new NullPointerException();
                    add();
                    break;
                }
                case FIND: {
                    if (dict == null) throw new NullPointerException();
                    find();
                    break;
                }
                case SHOW_ALL: {
                    if (dict == null) throw new NullPointerException();
                    showAllLines();
                    break;
                }
                case CHOOSED: {
                    if (dict == null) throw new NullPointerException();
                    dictUsedNow();
                    break;
                }
                case DELETE: {
                    if (dict == null) throw new NullPointerException();
                    delete();
                    break;
                }
                case QUIT: {
                    System.out.println("Exit...");
                    System.exit(0);
                }
                default: {
                    /* Default process */
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ofbException) {
            System.out.println();
            System.out.println("There are no function with this number. Try another number");
            System.out.println();
        }
    }


    private void showMenu() {
        System.out.println("MENU:");
        System.out.println("0 - CREATE NEW DICTIONARY, 1 - OPEN EXISTING DICTIONARY");
        System.out.println("2 - READ, 3 - ADD, 4 - FIND");
        System.out.println("5 - SHOW, 6 - CHOOSE, 7 - DELETE");
        System.out.println("8 - QUIT");
        try {
            makeChoice();
        } catch (NullPointerException e) {
            System.out.println("Firstly elect dictionary." + e);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Console c = new Console();
        while(true) {
            c.showMenu();
        }
    }

}
