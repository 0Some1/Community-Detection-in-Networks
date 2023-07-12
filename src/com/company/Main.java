package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Select one of these :");
        System.out.println("0. Community detection");
        System.out.println("1. Community detection plus version");
        System.out.println("2. Show the Charts");
        System.out.println("3. App that use Community detection");
        int select;
        while (true) {
            select = inputAnInt(0, 3);
            switch (select) {
                case 0:
                    InputForCDG.run(false);
                    break;
                case 1:
                    InputForCDG.run(true);
                    break;
                case 2:
                    TimeAndChart.run();
                    break;
                case 3:
                    CreateGraph.run();
                    break;
                default:
                    break;

            }
        }


    }


    public static int inputAnInt(int min, int max) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            while (true) {
                String str_input = scanner.next();
                if (!isInteger(str_input)) {
                    System.out.println(str_input + " is not an integer ,retry : ");
                } else {
                    if (Integer.parseInt(str_input) <= max && Integer.parseInt(str_input) >= min) {
                        int input = Integer.parseInt(str_input);
                        return input;
                    }

                    System.out.println(str_input + " must be at least " + min + " and maximum " + max + " ,retry : ");
                }
            }
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        } catch (NullPointerException var3) {
            return false;
        }
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                (new ProcessBuilder(new String[]{"cmd", "/c", "cls"})).inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (InterruptedException | IOException var1) {
        }

    }
}