package com.company;
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
        if(Dictionary.isCorrectPath(line)) {
            String path = line;
            System.out.println("Would you like to specify word length? " +
                    "If no, default word length will be 4.");
            getYesOrNo();
            if(Options.values()[option] == Options.NO)
                dict = new Dict(line);
            else {
                System.out.println("Word length:");
                inputInt();
                int wordLength = option;
                dict = new Dict(wordLength, path);
            }
        } else {
            System.out.println("Could not to create dictionary. Check symbols in name of file.");
            System.out.println("Example: default.txt (nums aren't allowed)");
        }
    }

    private void openDict() {
        System.out.println("What dictionary do you want to open?");
        String[] dicts = Dictionary.getAllAvailableDictionaries();
        System.out.println("Input a number of file:");
        inputInt();
        dict = new Dict(dicts[option - 1]); // Смещение на 1 (индексация массива)
        // Как узнать, какая спецификация словаря?
    }

    private void writeToDict() {
        System.out.println("Input string you want to add");
        inputStr();
        dict.add(line);
    }

    private void showAllLines() {
        int linesCount = dict.getQuantityOfLines();
        System.out.println("");
        System.out.println("Quantity of lines: " + linesCount);
        StringBuffer content = dict.showContent();
        System.out.println(content);
    }

    private void readFromDict() {
        showAllLines();
        /* Выборка диапазонов чтения */
    }

    private void add() {
        if (dict == null) {
            System.out.println("");
            System.out.println("Firstly choose an existing file or create new.");
            System.out.println("");
        } else {
            System.out.println("Input string you want to add:");
            if (inputStr())
                dict.add(line);
        }
    }

    private void find() {
        System.out.println("Input key to find note: ");
        inputStr();
        StringBuffer result = dict.find(line);
        System.out.println("");
        System.out.println(result);
        System.out.println("");
    }

    private void delete() {
        System.out.println("Input key to delete note: ");
        inputStr();
        dict.delete(line);
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
            System.out.println("");
            System.out.println("There are no function with this number. Try another number");
            System.out.println("");
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
            System.out.println("Firstly elect dictionary.");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        Console c = new Console();
        while(true) {
            c.showMenu();
        }
    }

}
