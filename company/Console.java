package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

// Пока делаю ключ-значения интовые, позже переделаю на String

public class Console {
    private int option;
    private String line;
    final private Scanner in = new Scanner(System.in);
    final private int maxAnswer = 9;
    final private int minAnswer = 0;
    Options op;
    private Dict dict;

    private boolean inputInt() {
        Scanner in = new Scanner(System.in);
        System.out.print(">> ");
        try {
            if(in.hasNextInt()) // проверки корректности выбора значений меню
                option = in.nextInt();
            else return false;
        } catch (InputMismatchException exception) {
            System.out.println("Incorrect symbols. Try again!");
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

    private boolean inputYesOrNo() {
        Scanner in = new Scanner(System.in);
        do {
            try {
                line = in.nextLine();
            } catch (InputMismatchException exception) {
                System.out.println("Incorrect symbols. Try again!");
            }
        } while (isYesOrNo());
        return true;
    }

    private void makeChoice() {
        System.out.println("Input a number: ");
        while(!inputInt()) {
            System.out.println("Incorrect choice. Try again.");
        }
        decider();
    }

    //private boolean isNotCorrect() { return (option < minAnswer || option > maxAnswer); }
    private boolean isYesOrNo() { return Pattern.matches("[ynдн]?", line); }
    //private boolean isNameOfFileCorrect() { return Pattern.matches("[a-zA-Z]\\.txt", line); }

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
            System.out.println("Could not to create dictionary.");
        }
    }

    private void getYesOrNo() {
        System.out.println("Do you agree? (y/n)(д/н): ");
        while(!inputStr()) {
            System.out.println("Incorrect choice. Try again.");
        }
        if(Pattern.matches("[yд]?", line))
            option = 10;
        else if (Pattern.matches("[nн]?", line)) option = 11;
    }

    private void decider() {
        try {
            switch (Options.values()[option]) {
                case CREATE: {
                    createDict();
                    break;
                }
                case OPEN: {
                    System.out.println("What file do you want to open?");
                    file.showAllFiles();
                    break;
                }
                case READ: {
                    System.out.println("you would like open an existing dictionary. Please input it's own path.");
                    System.out.println(">> ");

                    break;
                }
                case WRITE: {
                    System.out.println("Input string you want to add");
                    break;
                }
                case FIND: {
                    break;
                }
                case ADD: {
                    if (dict == null) {
                        System.out.println("");
                        System.out.println("Firstly choose an existing file or create new.");
                        System.out.println("");
                    } else {
                        System.out.println("Input string you want to add:");
                        if (inputStr())
                            if (dict.add(line))
                                System.out.println("Firstly choose an existing file or create new.");
                    }
                    break;
                }
                case SHOW: {
                    break;
                }
                case CHOOSE: {
                    break;
                }
                case DELETE: {
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
        System.out.println("2 - READ, 3 - WRITE, 4 - FIND");
        System.out.println("5 - ADD, 6 - SHOW, 7 - CHOOSE");
        System.out.println("8 - DELETE, 9 - QUIT");
        makeChoice();
    }

    public static void main(String[] args) {
        Console c = new Console();
        while(true) {
            c.showMenu();
        }
    }

}
