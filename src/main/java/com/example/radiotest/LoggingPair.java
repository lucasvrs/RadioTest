package com.example.radiotest;

public class LoggingPair {
    public LoggingType type;
    public String entry;
    public String timeStamp;

    public LoggingPair(LoggingType type, String entry, String timeStamp){
        this.type = type;
        this.entry = entry;
        this.timeStamp = timeStamp;
    }
}
