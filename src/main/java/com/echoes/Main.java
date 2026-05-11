package com.echoes;

import com.echoes.application.GameApplication;

/**
 * Application entry point for Echoes of the Abyss.
 *
 * <p>This class is intentionally minimal. All initialisation and wiring
 * is delegated to {@link GameApplication}, which acts as the composition
 * root. Keeping this class thin means {@link GameApplication} can be
 * reused or tested without going through the JVM entry point.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class Main {

    private Main() {
        // Utility class — instantiation not permitted
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not currently used)
     */
    public static void main(final String[] args) {
        new GameApplication().start();
    }
}
