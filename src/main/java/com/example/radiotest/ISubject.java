package com.example.radiotest;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for building a subject.
 */
public interface ISubject {
    public void notifyObservers(LoggingPair pair);
    public void registerObserver(IObserver observer);
    public void unregisterObserver(IObserver observer);
}
