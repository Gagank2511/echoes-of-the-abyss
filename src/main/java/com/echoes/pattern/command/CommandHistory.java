package com.echoes.pattern.command;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Maintains a bounded history of executed {@link Command}s.
 *
 * <p>Part of the <em>Command</em> design pattern. The history can be
 * inspected for display, statistics, or future undo and replay features.
 * A capacity cap prevents unbounded memory growth over long play sessions.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class CommandHistory {

    /** Maximum number of commands retained before the oldest is evicted. */
    private static final int MAX_HISTORY = 50;

    /** LIFO deque — most recent command is always at the head. */
    private final Deque<Command> history = new ArrayDeque<>();

    /**
     * Records an executed command. If the history is at capacity, the oldest
     * entry is evicted to make room for the new one.
     *
     * @param command the command to record — null is silently ignored
     */
    public void record(final Command command) {
        if (command == null) return;
        if (history.size() >= MAX_HISTORY) {
            ((ArrayDeque<Command>) history).removeLast();
        }
        history.push(command);
    }

    /**
     * Returns the most recently recorded command.
     *
     * @return the last command, or null if the history is empty
     */
    public Command getLastCommand() {
        return history.isEmpty() ? null : history.peek();
    }

    /**
     * Returns an ordered snapshot of all recorded commands, most recent first.
     *
     * @return unmodifiable list of commands
     */
    public List<Command> getHistory() {
        return Collections.unmodifiableList(List.copyOf(history));
    }

    /** @return the number of commands currently stored */
    public int size() { return history.size(); }

    /** Clears all recorded commands from the history. */
    public void clear() { history.clear(); }
}
