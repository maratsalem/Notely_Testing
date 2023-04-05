module com.example.notely {
    requires javafx.controls;
    requires javafx.fxml;


    opens notely.app to javafx.fxml;
    exports notely.app;
}