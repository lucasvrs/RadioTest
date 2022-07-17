package com.example.radiotest;

import javafx.collections.FXCollections;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A logger holding its data in an arraylist and writing its output into a text file (to be included).
 */
public class TxtLogger implements ILogger{
    private List<LoggingPair> log = new ArrayList<>();
    private String fileName;

    public TxtLogger(String fileName){
        this.fileName = fileName;
    }

    @Override
    public List<LoggingPair> getEntries() {
        return log;
    }

    @Override
    public List<String> getErrors() {
        List<String> res = new ArrayList<>();
        for(LoggingPair e : log){
            if(e.type == LoggingType.Error) res.add(e.timeStamp + ": " + e.entry);
        }
        return res;
    }

    @Override
    public List<String> getMessages() {
        List<String> res = new ArrayList<>();
        for(LoggingPair e : log){
            if(e.type == LoggingType.Message) res.add(e.timeStamp + ": " + e.entry);
        }
        return res;
    }

    @Override
    public List<String> getWarnings() {
        List<String> res = new ArrayList<>();
        for(LoggingPair e : log){
            if(e.type == LoggingType.Warning) res.add(e.timeStamp + ": " + e.entry);
        }
        return res;
    }

    @Override
    public LoggingPair lastEntry() {
        return log.get(log.size() - 1);
    }

    public String getFileName(){
        return fileName;
    }

    @Override
    public void addEntry(String entry, LoggingType type) {
        LoggingPair pair = new LoggingPair(type, entry, new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss").format(new Date()));
        log.add(pair);
    }

    @Override
    public void addError(String error) {
        LoggingPair pair = new LoggingPair(LoggingType.Error, error, new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss").format(new Date()));
        log.add(pair);
    }

    @Override
    public void addMessage(String message) {
        LoggingPair pair = new LoggingPair(LoggingType.Message, message, new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss").format(new Date()));
        log.add(pair);
    }

    @Override
    public void addWarning(String warning) {
        LoggingPair pair = new LoggingPair(LoggingType.Warning, warning, new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss").format(new Date()));
        log.add(pair);
    }
}
