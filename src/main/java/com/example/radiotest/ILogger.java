package com.example.radiotest;

import java.util.List;

/**
 * Interface for building a logger.
 */
public interface ILogger {
    public List<LoggingPair> getEntries();
    public List<String> getErrors();
    public List<String> getMessages();
    public List<String> getWarnings();
    public LoggingPair lastEntry();
    public void addEntry(String entry, LoggingType type);
    public void addError(String error);
    public void addMessage(String message);
    public void addWarning(String warning);
}
