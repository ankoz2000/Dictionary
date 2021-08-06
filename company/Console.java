package com.company;
import java.util.Scanner;



// Пока делаю ключ-значения интовые, позже переделаю на String

public class Console extends Options{
    private int key;
    private int value;
    private String filePath;
    private int option;
    final private int maxAnswer = 9;
    final private int minAnswer = 0;
    Option op;

    private boolean intInput() {
        Scanner in = new Scanner(System.in);
        do {
            option = in.nextInt(); // проверки корректности выбора значений меню
            if (isNotCorrect())
                return false;
        } while (isNotCorrect());
        return true;
    }

    private void makeChoice() {
        System.out.println("Input a number: ");
        System.out.print(">> ");
        while(!intInput()) {
            System.out.println("Incorrect choice. Try again.");
        }
        decider();
    }

    private boolean isNotCorrect() { return (option < minAnswer || option > maxAnswer); }

    private void getYesOrNo() {
        Scanner in = new Scanner(System.in);
        System.out.println("Do you agree? 1 - Yes, 0 - No: ");
        while(!intInput()) {
            System.out.println("Incorrect choice. Try again.");
        }
    }

    private void decider() {
        switch (Option.values()[option]) {
            case NO: {
                break;
            }
            case YES: {
                break;
            }
            case READ: {
                break;
            }
            case WRITE: {
                break;
            }
            case FIND: {
                break;
            }
            case ADD: {
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
    }


    private void showMenu() {
        System.out.println("MENU:");
        System.out.println("0 - NO, 1 - YES");
        System.out.println("2 - READ, 3 - WRITE, 4 - FIND");
        System.out.println("5 - ADD, 6 - SHOW, 7 - CHOOSE");
        System.out.println("8 - DELETE, 9 - QUIT");
        makeChoice();
    }

    public static void main(String[] args) {
        Console c = new Console();
        //while(true) {
            c.showMenu();
        //}
    }

}
