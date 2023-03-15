package notely.app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;

public class MainSceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String term, definition;
    private int priorityNum;
    @FXML
    TextArea definitionInput;
    @FXML
    TextArea termInput;

    public void createNotecard(ActionEvent event) throws IOException {
        System.out.println(termInput.getText());
        System.out.println(definitionInput.getText());

        term = termInput.getText();
        definition = definitionInput.getText();

        NoteCard nc = new NoteCard(term, definition, 0);
        nc.writeQuestion("psychology", term, definition);

    }

    public void switchToNotecardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NotecardScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
