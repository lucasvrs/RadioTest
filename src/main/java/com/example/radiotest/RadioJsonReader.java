package com.example.radiotest;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class RadioJsonReader {
    public static ObservableList<Radio> read() {
        JSONObject obj;
        try{
            obj = (JSONObject) new JSONParser().parse(new FileReader("target/classes/com/example/radiotest/radio-list.json"));
        } catch (Exception e) {
            return FXCollections.observableArrayList();
        }
        ObservableList<Radio> radioList = FXCollections.observableArrayList();
        JSONArray radios = (JSONArray) obj.get("radios");
        for (Object radio : radios) {
            JSONObject r = (JSONObject) radio;
            String name = (String) r.get("name");
            String imageName = (String) r.get("imageName");
            Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/" + imageName)));
            JSONObject freq = (JSONObject) r.get("frequency");
            double minFrequency = (double) freq.get("min");
            double maxFrequency = (double) freq.get("max");
            double valFrequency = (double) freq.get("value");
            JSONObject vol = (JSONObject) r.get("volume");
            double minVolume = (double) vol.get("min");
            double maxVolume = (double) vol.get("max");
            double valVolume = (double) vol.get("value");
            Radio res = new Radio(image, name, (float) minFrequency, (float) maxFrequency, (float) minVolume, (float) maxVolume);
            res.setFrequency((float) valFrequency);
            res.setVolume((float) valVolume);
            radioList.add(res);
        }
        return radioList;
    }
}
