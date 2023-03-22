package notely.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

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
    @FXML
    TextArea folderNameInput;
    @FXML
    Label textLabel;
    @FXML
    Label topLabel;
    @FXML
    Label titleLabel;
    @FXML
    Button viewButton;
    @FXML
    Button leftButton;
    @FXML
    Button rightButton;
    @FXML
    Button flipButton;
    @FXML
    Button flipLearnButton;
    @FXML
    Button correctButton;
    @FXML
    Button incorrectButton;
    @FXML
    TextField fileField;
    private String txt;
    private static String file = "";
    private int index = 0;
    private int flip = 0;
    private int arraySize;
    private NoteCard noteCard = new NoteCard();
    private FilePath filePath = new FilePath();
    private ArrayList<NoteCard> currentStudySet = new ArrayList<>();
    private ArrayList<String> fileName = new ArrayList<>();
    Queue<NoteCard> queue1 = new LinkedList<>();
    Queue<NoteCard> queue2 = new LinkedList<>();
    Queue<NoteCard> queue3 = new LinkedList<>();
    Queue<NoteCard> top = new LinkedList<>();
    Queue<NoteCard> middle = new LinkedList<>();
    Queue<NoteCard> bottom = new LinkedList<>();

    public void SwitchToCreateScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("create.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToStudyScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("View.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToHomeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    @FXML
    public void SwitchToViewScene(ActionEvent event) throws IOException {
        if (!fileField.getText().isEmpty()) {
            file = fileField.getText().toString();
            filePath.setFileName(file);
            System.out.println(file + "This code got passed 5"); //Testing
            System.out.println(filePath.getFileName() + "This code got passed 5.5"); //Testing
        }
        if (!file.isEmpty()) {
            root = FXMLLoader.load(getClass().getResource("ViewScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    public void SwitchToLearnScene(ActionEvent event) throws IOException {
        if (!fileField.getText().isEmpty()) {
            file = fileField.getText();
            filePath.setFileName(file);
            System.out.println(file);
            fileName.add(file);
        }
        if (!file.isEmpty()) {
            root = FXMLLoader.load(getClass().getResource("learnScreen.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void CreateScene() {
        welcomeText.setText("");
    }

    @FXML
    protected void StudyScene() {
        welcomeText.setText("");
    }

    @FXML
    protected void SettingsScene() {
        welcomeText.setText("");
    }

    @FXML
    public void updateLabel(KeyEvent event) throws IOException {
        System.out.print(file + "This code got passed 9"); //Testing
        readFile(file);
        System.out.println(currentStudySet.get(index).getTerm());
        arraySize = currentStudySet.size();
        txt = currentStudySet.get(index).getTerm();
        textLabel.setText(txt);
        leftButton.setDisable(false);
        rightButton.setDisable(false);
        flipButton.setDisable(false);
    }

    @FXML
    protected void onFlipClick(ActionEvent event) throws IOException {
        readFile(file);
        if (flip == 0) {
            txt = currentStudySet.get(index).getDefinition();
            topLabel.setText("Definition");
            flip = 1;
        } else {
            txt = currentStudySet.get(index).getTerm();
            topLabel.setText("Term");
            flip = 0;
        }
        textLabel.setText(txt);
    }

    @FXML
    protected void onLeftClick(ActionEvent event) throws IOException {
        if (index <= 0) {
            index = 0;
        } else {
            index = index - 1;
        }
        flip = 0;
        topLabel.setText("Term");
        txt = currentStudySet.get(index).getTerm();
        textLabel.setText(txt);
    }

    @FXML
    protected void onRightClick(ActionEvent event) throws IOException {
        if (index >= (arraySize - 1)) {
            txt = currentStudySet.get(arraySize - 1).getTerm();

            index = arraySize - 1;
            flip = 0;
            topLabel.setText("Term");
        } else {
            index = index + 1;
            flip = 0;
            topLabel.setText("Term");
            txt = currentStudySet.get(index).getTerm();
        }
        textLabel.setText(txt);
    }

    @FXML
    public void loadLabel(KeyEvent event) throws IOException {
        readFile(file);
        for (int i = 0; i < currentStudySet.size(); i++) {
            sortNoteCards(i);
            //queue1.add(currentStudySet.get(i));
        }
        shuffleNoteCards();
        arraySize = currentStudySet.size();
        txt = queue1.element().getTerm();
        //txt = currentStudySet.get(index).getTerm();
        textLabel.setText(txt);
        incorrectButton.setDisable(false);
        correctButton.setDisable(false);
        flipLearnButton.setDisable(false);
    }

    public void sortNoteCards(int i) {
        int max = 9;
        int min = 1;
        int rand = (int) (Math.random() * max) + min;
        if (rand >= 1 && rand <= 3) {
            top.add(currentStudySet.get(i));
        } else if (rand > 3 && rand <= 6) {
            middle.add(currentStudySet.get(i));
        } else if (rand > 6 && rand <= 9) {
            bottom.add(currentStudySet.get(i));
        } else {
            middle.add(currentStudySet.get(i));
        }
    }

    public void shuffleNoteCards() {
        int max = 3;
        int min = 1;
        int rand = (int) (Math.random() * max) + min;
        if (rand == 1) {
            while (!bottom.isEmpty() || !middle.isEmpty()) {
                if (!middle.isEmpty()) {
                    top.add(middle.element());
                    middle.remove();
                } else if (!bottom.isEmpty()) {
                    top.add(bottom.element());
                    bottom.remove();
                }
            }
            while (!top.isEmpty()) {
                queue1.add(top.element());
                top.remove();
            }
        } else if (rand == 2) {
            while (!top.isEmpty() || !middle.isEmpty()) {
                if (!middle.isEmpty()) {
                    bottom.add(middle.element());
                    middle.remove();
                } else if (!top.isEmpty()) {
                    bottom.add(top.element());
                    top.remove();
                }
            }
            while (!bottom.isEmpty()) {
                queue1.add(bottom.element());
                bottom.remove();
            }
        } else if (rand == 3) {
            while (!top.isEmpty() || !bottom.isEmpty()) {
                if (!top.isEmpty()) {
                    middle.add(top.element());
                    top.remove();
                } else if (!bottom.isEmpty()) {
                    middle.add(bottom.element());
                    bottom.remove();
                }
            }
            while (!middle.isEmpty()) {
                queue1.add(middle.element());
                middle.remove();
            }
        } else {
            while (!top.isEmpty() || !bottom.isEmpty()) {
                if (!bottom.isEmpty()) {
                    middle.add(bottom.element());
                    bottom.remove();
                }
                if (!top.isEmpty()) {
                    middle.add(top.element());
                    top.remove();
                }
            }
            while (!middle.isEmpty()) {
                queue1.add(middle.element());
                middle.remove();
            }
        }
    }

    @FXML
    protected void onFlipQueue(ActionEvent event) throws IOException {
        int x = 0;
        if (!queue1.isEmpty()) {
            x = 1;
        } else if (!queue2.isEmpty()) {
            x = 2;
        } else if (!queue3.isEmpty()) {
            x = 3;
        } else {
            x = 4;
        }
        switch (x) {
            case 1:
                if (flip == 0) {
                    txt = queue1.element().getDefinition();
                    topLabel.setText("Definition");
                    flip = 1;
                } else {
                    txt = queue1.element().getTerm();
                    topLabel.setText("Term");
                    flip = 0;
                }
                textLabel.setText(txt);
                break;
            case 2:
                if (flip == 0) {
                    txt = queue2.element().getDefinition();
                    topLabel.setText("Definition");
                    flip = 1;
                } else {
                    txt = queue2.element().getTerm();
                    topLabel.setText("Term");
                    flip = 0;
                }
                textLabel.setText(txt);
                break;
            case 3:
                if (flip == 0) {
                    txt = queue3.element().getDefinition();
                    topLabel.setText("Definition");
                    flip = 1;
                } else {
                    txt = queue3.element().getTerm();
                    topLabel.setText("Term");
                    flip = 0;
                }
                textLabel.setText(txt);
                break;
            case 4:
                break;
        }
    }

    @FXML
    protected void correctAnswer(ActionEvent event) throws IOException {
        if (!queue1.isEmpty()) {
            if (queue1.element().getPriorityNum() == 3) {
                System.out.println("Queue1 " + queue1.element().getTerm()); //Testing
                queue1.element().setPriorityNum(2);
                queue2.add(queue1.element()); // change to queue1 to prevent adding only the first element
                queue1.remove();
                if (!queue1.isEmpty()) {
                    txt = queue1.element().getTerm(); // need if to catch when queue is empty
                    textLabel.setText(txt);
                } else {
                    txt = queue2.element().getTerm();
                    textLabel.setText(txt);
                }
                topLabel.setText("Term");
                flip = 0;
            }
        } else if (!queue2.isEmpty()) {
            if (queue2.element().getPriorityNum() == 2) {
                System.out.println("Queue2 " + queue2.element().getTerm()); //Testing
                queue2.element().setPriorityNum(1);
                queue3.add(queue2.element());
                queue2.remove();
                if (!queue2.isEmpty()) {
                    txt = queue2.element().getTerm();
                    textLabel.setText(txt);
                } else {
                    txt = queue3.element().getTerm();
                    textLabel.setText(txt);
                }
                topLabel.setText("Term");
                flip = 0;
            }
        } else if (!queue3.isEmpty()) {
            if (queue3.element().getPriorityNum() == 1) {
                System.out.println("Queue3 " + queue3.element().getTerm()); //Testing
                queue3.element().setPriorityNum(0);
                queue3.remove();
                if (!queue3.isEmpty()) {
                    txt = queue3.element().getTerm();
                    textLabel.setText(txt);
                    topLabel.setText("Term");
                    flip = 0;
                } else {
                    textLabel.setText("Hooray! You Finished");
                    topLabel.setText(" ");
                    flipLearnButton.setDisable(true);
                    correctButton.setDisable(true);
                    incorrectButton.setDisable(true);
                }
            }
        } else {
            System.out.println("A note card with an unknown priority code: " + currentStudySet.get(index).getPriorityNum() + " was added to the familar queue\n");
            queue2.add(currentStudySet.get(index));
        }
    }

    @FXML
    protected void incorrectAnswer(ActionEvent event) throws IOException { // revise
        if (!queue1.isEmpty()) {
            if (queue1.element().getPriorityNum() == 3) {
                queue1.add(queue1.element());
                queue1.remove();
                txt = queue1.element().getTerm();
                textLabel.setText(txt);
            }
        } else if (!queue2.isEmpty()) {
            if (queue2.element().getPriorityNum() == 2) {
                queue2.add(queue2.element());
                queue2.remove();
                txt = queue2.element().getTerm();
                textLabel.setText(txt);
            }
        } else if (!queue3.isEmpty()) {
            if (queue3.element().getPriorityNum() == 1) {
                queue3.add(queue3.element());
                queue3.remove();
                txt = queue3.element().getTerm();
                textLabel.setText(txt);
            }
        } else {
            System.out.println("A note card with an unknown priority code: " + currentStudySet.get(index).getPriorityNum() + " was added to the familar queue\n");
            queue2.add(currentStudySet.get(index));
        }
        topLabel.setText("Term");
        flip = 0;
    }

    public void createSet(String title) throws IOException {
        String filePathName = "Notely/src/main/java/notely/app/Notecard/" + title + ".txt";
        File fileMake = new File(filePathName);

        if(fileMake.createNewFile()){
            FileInputStream fileReading = new FileInputStream (filePathName);
            Scanner reader = new Scanner(fileReading);
            ArrayList<String> data = new ArrayList<>();
            while (reader.hasNextLine())
                data.add(reader.nextLine());
            reader.close();

            FileOutputStream fileWriting = new FileOutputStream(filePathName);
            PrintWriter writer = new PrintWriter(fileWriting, true);

            if (titleInput.getText() == null || folderNameInput.getText() == null){
                titleInput.setPromptText("You must enter a set title.");
                folderNameInput.setPromptText("You must enter folder name.");
            }
            data.add(Objects.requireNonNull(titleInput.getText()));
            data.add(Objects.requireNonNull(folderNameInput.getText()));

            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();
        } else { //Testing
            System.out.print("File already exists, will not write fileName and folder into textfile.");
        }
    }

    public void createNotecard(ActionEvent event) throws IOException {
        System.out.println(termInput.getText()); //Testing
        System.out.println(definitionInput.getText()); //Testing

        term = termInput.getText();
        definition = definitionInput.getText();
        studySet = titleInput.getText();

        System.out.println(studySet); //Testing

        createSet(studySet);

        NoteCard nc = new NoteCard(term, definition, 0, 0);
        nc.writeQuestion(studySet, term, definition);

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

    public void readFile(String fileNAMEWORKS) throws IOException { //Reads a txt file to fill arraylists with words to be guessed.
        System.out.println(fileNAMEWORKS + "This code got passed 2"); //Testing
        String term = "";
        String filePathOS = "Notely/src/main/java/notely/app/Notecard/" + fileNAMEWORKS + ".txt";
        String definition = "";
        String currentLine;
        String title = "";
        String folder = "";
        int priority = 3;

            FileReader fr = new FileReader(filePathOS);
            BufferedReader brin = new BufferedReader(fr);
            int index = 0;
            while ((currentLine = brin.readLine()) != null) {
                String line = currentLine;
                if (index == 0) {
                    title = currentLine;
                    titleLabel.setText(title);
                    index++;
                } else if (index == 1) {
                    folder = currentLine;
                    index++;
                } else if (index > 1) {
                    int tokenNumber = 0;
                    StringTokenizer tokens = new StringTokenizer(line, "@");
                    while (tokens.hasMoreTokens()) {
                        String token = tokens.nextToken();
                        term = token;
                        token = tokens.nextToken();
                        definition = token;
                        tokenNumber++;
                        noteCard = new NoteCard(term, definition, priority, tokenNumber);
                        currentStudySet.add(noteCard);
                    }
                }
            }
            brin.close();
        }
}
