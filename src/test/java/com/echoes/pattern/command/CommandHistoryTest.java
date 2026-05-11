package com.echoes.pattern.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CommandHistory Tests")
class CommandHistoryTest {

    private CommandHistory history;

    private static Command stubCommand(final String description) {
        return new Command() {
            @Override public void execute() {}
            @Override public String getDescription() { return description; }
        };
    }

    @BeforeEach
    void setUp() {
        history = new CommandHistory();
    }

    @Test
    @DisplayName("New history is empty")
    void newHistory_isEmpty() {
        assertEquals(0, history.size());
        assertNull(history.getLastCommand());
    }

    @Test
    @DisplayName("record stores a command and increases size")
    void record_increasesSize() {
        history.record(stubCommand("attack"));
        assertEquals(1, history.size());
    }

    @Test
    @DisplayName("getLastCommand returns most recently recorded command")
    void getLastCommand_returnsMostRecent() {
        history.record(stubCommand("first"));
        history.record(stubCommand("second"));
        assertEquals("second", history.getLastCommand().getDescription());
    }

    @Test
    @DisplayName("record ignores null commands")
    void record_ignoresNull() {
        history.record(null);
        assertEquals(0, history.size());
    }

    @Test
    @DisplayName("clear empties the history")
    void clear_emptiesHistory() {
        history.record(stubCommand("cmd"));
        history.clear();
        assertEquals(0, history.size());
        assertNull(history.getLastCommand());
    }

    @Test
    @DisplayName("getHistory returns commands most recent first")
    void getHistory_isOrderedMostRecentFirst() {
        history.record(stubCommand("first"));
        history.record(stubCommand("second"));
        history.record(stubCommand("third"));
        assertEquals("third", history.getHistory().get(0).getDescription());
        assertEquals("first", history.getHistory().get(2).getDescription());
    }

    @Test
    @DisplayName("getHistory returns unmodifiable list")
    void getHistory_isUnmodifiable() {
        history.record(stubCommand("cmd"));
        assertThrows(UnsupportedOperationException.class,
                () -> history.getHistory().add(stubCommand("extra")));
    }
}