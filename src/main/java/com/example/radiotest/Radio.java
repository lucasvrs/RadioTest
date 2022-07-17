package com.example.radiotest;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Radio implements ISubject {
    /*Member Variables*/
    private boolean powerIsOn       =   false;
    private float   frequency       =   89.8f;      //fm in MHz
    private float   maxFrequency    =   112.0f;
    private float   maxVolume       =   100.0f;
    private float   minFrequency    =   86.0f;
    private float   minVolume       =   0.0f;
    private float   volume          =   0.0f;
    private Image   image;
    private List<IObserver> observerList = new ArrayList<>();
    private String  id              =   "Radio_1";
    private String  name            =   "Radio";

    /*Private Methods*/
    private void orderMinMaxFreq(){
        if(minVolume > maxVolume){
            float tmp = minVolume;
            minVolume = maxVolume;
            maxVolume = tmp;
        }
    }

    private void orderMinMaxVol(){
        if(minFrequency > maxFrequency){
            float tmp = minFrequency;
            minFrequency = maxFrequency;
            maxFrequency = tmp;
        }
    }

    /*Public Methods*/
    public Radio(Image image){
        this.image = image;
    }

    public Radio(Image image, String name){
        this.image = image;
        this.name = name;
    }

    public Radio(Image image, String name, float minFrequency, float maxFrequency, float minVolume, float maxVolume){
        this.image = image;
        this.name = name;
        this.minFrequency = minFrequency;
        this.maxFrequency = maxFrequency;
        this.minVolume = minVolume;
        this.maxVolume = maxVolume;
    }

    public boolean isOn(){return powerIsOn;}

    public boolean changeFrequency(float df){
        float tmp = (frequency += df);
        if(tmp < minFrequency){
            notifyObservers(new LoggingPair(LoggingType.Warning, "Frequency is already at its minimum.", ""));
            return false;
        } else if(tmp > maxFrequency){
            notifyObservers(new LoggingPair(LoggingType.Warning, "Frequency is already at its maximum.", ""));
            return false;
        }
        frequency = tmp;
        return true;
    }

    public boolean changeVolume(float dv){
        float tmp = (volume += dv);
        if(tmp < minVolume){
            notifyObservers(new LoggingPair(LoggingType.Warning, "Volume is already at its minimum.", ""));
            return false;
        } else if(tmp > maxVolume){
            notifyObservers(new LoggingPair(LoggingType.Warning, "Volume is already at its maximum.", ""));
            return false;
        }
        volume = tmp;
        return true;
    }

    public float getFrequency(){return frequency;}

    public float getMaxFrequency(){return maxFrequency;}

    public float getMaxVolume(){return maxVolume;}

    public float getMinFrequency(){return minFrequency;}

    public float getMinVolume(){return minVolume;}

    public float getVolume(){return volume;}

    public Image getImage(){return image;}

    public String getId(){return id;}

    public String getName(){return name;}

    public void setFrequency(float v){
        frequency = (v <= maxFrequency && v >= minFrequency) ? v : minFrequency;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setMaxFrequency(float max){
        maxFrequency = max;
        orderMinMaxFreq();
    }

    public void setMaxVolume(float max){
        maxVolume = max;
        orderMinMaxVol();
    }

    public void setMinFrequency(float min){
        minFrequency = min;
        orderMinMaxFreq();
    }

    public void setMinVolume(float min){
        minVolume = min;
        orderMinMaxVol();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setVolume(float v){
        volume = (v <= maxVolume && v >= minVolume) ? v : minVolume;
    }

    public void togglePower(){
        powerIsOn = !powerIsOn;
        String res = (powerIsOn) ? "Turned on radio." : "Turned off radio.";
        notifyObservers(new LoggingPair(LoggingType.Message, res, ""));
    }

    @Override
    public void notifyObservers(LoggingPair pair) {
        for(IObserver o : observerList){
            o.update(pair);
        }
    }

    @Override
    public void registerObserver(IObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void unregisterObserver(IObserver observer) {
        observerList.remove(observer);
    }
}

