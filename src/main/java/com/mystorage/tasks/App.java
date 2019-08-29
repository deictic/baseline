package com.mystorage.tasks;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Please input a line");
            String line = scanner.nextLine();
            System.out.printf("User input was: %s%n", line);

            if (line.equals("exit")) exit = true;
        }
    }
}
