package com.company;
import java.util.Scanner;



// Пока делаю ключ-значения интовые, позже переделаю на String

public class Console implements Options {
    private int key;
    private int value;
    private String filePath;
    private int option;
    final private int maxAnswer = 9;
    final private int minAnswer = 0;

    private void makeChoice() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        do {
            option = in.nextInt(); // проверки корректности выбора значений меню
            if (option < minAnswer || option > maxAnswer)
                System.out.println("Incorrect choice. Try again.");
        } while (option < minAnswer || option > maxAnswer);
        decider();
    }

    private void decider() {
        switch (option) {
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
        System.out.println("");
        System.out.println("");
        makeChoice();
    }

    public static void main(String[] args) {
        Console c = new Console();
        //while(true) {
            c.showMenu();
        //}
    }

}
