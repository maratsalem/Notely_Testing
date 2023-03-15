package notely.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

import static notely.app.NoteCard.readFromNotecard;

public class NotecardSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String term, definition;
    @FXML
    Button nextButton;
    @FXML
    Button previousButton;
    @FXML
    Button flipButton;
    public void switchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void selectSet(ActionEvent event) throws IOException{
        // set button, getText, set text to var and then pass through readFrom
        readFromNotecard();
    }

    public void noteCardForward(ActionEvent event) throws IOException {

    }
    public void noteCardBackwards(ActionEvent event) throws IOException{

    }



}
