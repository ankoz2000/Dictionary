package com.company;
import java.util.Scanner;

// Пока делаю ключ-значения интовые, позже переделаю на String

public class Console implements Options {
    private int key;
    private int value;
    private String filePath;
    private int option;

    private void makeChoice(String answer) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a number: ");
        option = in.nextInt(); // проверки корректности выбора значений меню (Добавить)
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
            default: {
                /* Default process */
                break;
            }
        }
    }

    private static void showMenu() {
        System.out.println("MENU:");
        System.out.println();
        System.out.println("");

    }

    public static void main(String[] args) {
        //while(true) {
            showMenu();
        //}
    }

}
