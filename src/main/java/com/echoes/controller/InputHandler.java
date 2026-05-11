package com.echoes.controller;

import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }

    public String prompt(final String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    public void close() {
        scanner.close();
    }
}