package com.echoes.controller;

import java.util.Scanner;

/**
 * Handles user input from the console.
 *
 * <p>This class provides methods to read input from the user, with support
 * for prompting and trimming whitespace. It encapsulates the Scanner for
 * standard input to make input handling testable and reusable.</p>
 */
public class InputHandler {

    private final Scanner scanner;

    /**
     * Constructs a new input handler using standard input.
     */
    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the trimmed input line, or an empty string if no input is available
     */
    public String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }

    /**
     * Prompts the user with a message and reads their response.
     *
     * @param prompt the message to display to the user
     * @return the trimmed input line
     */
    public String prompt(final String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    /**
     * Closes the input handler and releases resources.
     */
    public void close() {
        scanner.close();
    }
}