package com.example.radiotest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class MainController implements IObserver{
    private TxtLogger logger = new TxtLogger("log.txt");
    @FXML
    private ComboBox<Radio> radioComboBox;
    @FXML
    private GridPane inspectorControls;
    @FXML
    private GridPane main;
    @FXML
    private Pane radioViewFx;
    @FXML
    private Slider radioFreqSlider;
    @FXML
    private Slider radioVolSlider;
    @FXML
    private Spinner<Double> radioFreqSpinner;
    @FXML
    private Spinner<Double> radioVolSpinner;
    @FXML
    private TextField radioIdField;
    @FXML
    private TextField radioNameField;
    @FXML
    private VBox mainLogPane;

    @FXML
    private void changeRadio(Radio radio){
        Image image = radio.getImage();
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, true, false));
        radioViewFx.setBackground(new Background(bgImage));
        radioIdField.setText(radio.getId());
        radioNameField.setText(radio.getName());
        radioFreqSlider.setMin(radio.getMinFrequency());
        radioFreqSlider.setMax(radio.getMaxFrequency());
        radioFreqSlider.setValue(radio.getFrequency());
        radioFreqSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(radio.getMinFrequency(), radio.getMaxFrequency(), radio.getFrequency(), 0.1));
        radioVolSlider.setMin(radio.getMinVolume());
        radioVolSlider.setMax(radio.getMaxVolume());
        radioVolSlider.setValue(radio.getVolume());
        radioVolSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(radio.getMinVolume(), radio.getMaxVolume(), radio.getVolume(), 0.1));
        inspectorControls.setDisable(!radio.isOn());
    }

    @FXML
    private void onComboIndexChange(){
        changeRadio(radioComboBox.getValue());
    }

    @FXML
    private void onQuit(){
        Platform.exit();
        System.exit(0);
    }

    private void populateRadioComboBox(){
        radioComboBox.setItems(RadioJsonReader.read());
        radioComboBox.setConverter(new StringConverter<Radio>() {
            @Override
            public String toString(Radio radio) {
                return radio.getName();
            }

            @Override
            public Radio fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    private void toggleRadioPower(){
        radioComboBox.getValue().togglePower();
        inspectorControls.setDisable(!radioComboBox.getValue().isOn());
    }

    @FXML
    public void initialize(){
        main.getStylesheets().add(Objects.requireNonNull(Main.class.getResource("style/main.css")).toExternalForm());
        populateRadioComboBox();
        for(Radio radio : radioComboBox.getItems()){
            radio.registerObserver(this);
        }
        radioComboBox.setValue(radioComboBox.getItems().get(0));
        changeRadio(radioComboBox.getItems().get(0));
        radioFreqSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            radioFreqSpinner.getValueFactory().setValue((Double) newValue);
            double res = (double) newValue;
            radioComboBox.getValue().setFrequency((float) res);
        });
        radioFreqSpinner.getEditor().textProperty().addListener(((observableValue, oldValue, newValue) -> {
            radioFreqSlider.setValue(Double.parseDouble(newValue.replace(",", ".")));
            radioComboBox.getValue().setFrequency(Float.parseFloat(newValue.replace(",", ".")));
        }));
        radioVolSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            radioVolSpinner.getValueFactory().setValue((Double) newValue);
            double res = (double) newValue;
            radioComboBox.getValue().setVolume((float) res);
        });
        radioVolSpinner.getEditor().textProperty().addListener(((observableValue, oldValue, newValue) -> {
            radioVolSlider.setValue(Double.parseDouble(newValue.replace(",", ".")));
            radioComboBox.getValue().setVolume(Float.parseFloat(newValue.replace(",", ".")));
        }));
        inspectorControls.setDisable(true);
    }

    @Override
    public void update(LoggingPair pair) {
        logger.addEntry(pair.entry, pair.type);
        Label newLabel = new Label(logger.lastEntry().timeStamp + ": [" + radioComboBox.getValue().getName() + "] " + logger.lastEntry().entry);
        mainLogPane.getChildren().add(newLabel);
    }
}