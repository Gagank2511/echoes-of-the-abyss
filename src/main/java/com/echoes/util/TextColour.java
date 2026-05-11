package com.echoes.util;

/**
 * ANSI escape-code constants for coloured terminal output.
 *
 * <p>Use {@link #wrap(String, String)} to apply a colour and automatically
 * append {@link #RESET} so that subsequent terminal text is not affected.
 * On terminals that do not support ANSI sequences the raw escape characters
 * will appear as text, but the game remains fully playable.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class TextColour {

    public static final String RESET   = "\u001B[0m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";
    public static final String BOLD    = "\u001B[1m";

    private TextColour() {
        // Utility class — instantiation not permitted
    }

    /**
     * Wraps {@code text} with the given ANSI colour code and appends
     * {@link #RESET} so subsequent output is unaffected.
     *
     * @param text       the text to colour
     * @param colourCode an ANSI escape sequence from this class
     * @return the coloured string ready for printing
     */
    public static String wrap(final String text, final String colourCode) {
        return colourCode + text + RESET;
    }

    /**
     * Convenience method — applies bold formatting to the given text.
     *
     * @param text the text to embolden
     * @return the bolded string
     */
    public static String bold(final String text) {
        return wrap(text, BOLD);
    }
}
