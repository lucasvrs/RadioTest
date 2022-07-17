module com.example.radiotest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires json.simple;

    opens com.example.radiotest to javafx.fxml;
    exports com.example.radiotest;
}