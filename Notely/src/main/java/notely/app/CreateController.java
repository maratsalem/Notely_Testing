package notely.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
public class CreateController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String term, definition, studySet;
    private int priorityNum;
    @FXML
    TextArea definitionInput;
    @FXML
    TextArea termInput;
    @FXML
    AnchorPane termList;
    @FXML
    TextArea titleInput;

    public void createSet(String title) throws IOException {
        String fileName = "Notely/src/main/java/notely/app/Notecard/" + title + ".txt";

        File file = new File(fileName);
        System.out.println(fileName);

        file.createNewFile();

    }

    public void createNotecard(ActionEvent event) throws IOException {
        System.out.println(termInput.getText());
        System.out.println(definitionInput.getText());

        term = termInput.getText();
        definition = definitionInput.getText();
        studySet = titleInput.getText();

        System.out.println(studySet);

        createSet(studySet);

        NoteCard nc = new NoteCard(term, definition, 0);
        nc.writeQuestion(studySet, term, definition);

        System.out.println(this.termInput.getText());
        System.out.println(this.definitionInput.getText());

        Label termLabel = new Label(this.term);
        Label defLabel = new Label(this.definition);
        AnchorPane notecard = new AnchorPane(termLabel, defLabel);
        notecard.setPrefHeight(termLabel.getHeight() + defLabel.getHeight() + 20); // Add some padding
        termList.getChildren().add(notecard);
        // Position the notecard below the previously added notecard, if any
        double y = 0;
        if (termList.getChildren().size() > 1) {
            for (Node node : termList.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane previousNotecard = (AnchorPane) node;
                    y += previousNotecard.getHeight();
                }
            }
            y += 10; // Add some padding
        }
        notecard.setLayoutX(0);
        notecard.setLayoutY(y);
        // Position the definition label below the term label
        defLabel.setLayoutY(termLabel.getHeight() + termLabel.getPadding().getTop() + 10); // Add some padding

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
