package notely.app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import notely.app.MultipleChoice;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class CreateController {
    @FXML VBox createVbox;
    @FXML ScrollPane scrollPane;
    @FXML AnchorPane firstCreateAPane;
    @FXML AnchorPane termList;
    @FXML TextArea definitionInput;
    @FXML TextArea termInput;
    @FXML TextArea titleInput;
    @FXML TextArea folderNameInput;
    @FXML TextField titleInputC;
    @FXML TextField folderInputC;
    @FXML ComboBox fileField;
    @FXML ComboBox fileFieldDelete;
    @FXML Label textLabel;
    @FXML Label topLabel;
    @FXML Label titleLabel;
    @FXML Button viewButton;
    @FXML Button leftButton;
    @FXML Button rightButton;
    @FXML Button flipButton;
    @FXML Button flipLearnButton;
    @FXML Button correctButton;
    @FXML Button incorrectButton;
    @FXML Button createSceneButton;
    @FXML ComboBox comboBoxLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String studySet;
    private String folderName;
    private String txt;
    private static String file = "";
    private int arraySize;
    private int labelCounter = 2;
    private int index = 0;
    private int flip = 0;
    private NoteCard noteCard = new NoteCard();
    private ArrayList<NoteCard> currentStudySet = new ArrayList<>();
    private ArrayList<String> fileName = new ArrayList<>();
    Queue<NoteCard> queue1 = new LinkedList<>();
    Queue<NoteCard> queue2 = new LinkedList<>();
    Queue<NoteCard> queue3 = new LinkedList<>();
    Queue<NoteCard> top = new LinkedList<>();
    Queue<NoteCard> middle = new LinkedList<>();
    Queue<NoteCard> bottom = new LinkedList<>();
    private String term;
    private String definition;

    public String checkPath(String checkingPathString){

        String fileMacPath = "./src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";
        String fileWindowsPath = "../src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";
        String checkPathString = "Notely/src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";

        if (new File(fileMacPath).exists()) { // ./  for MACOS and ../ for Windows
            System.out.println("FileMac");
            checkPathString = fileMacPath;
        } else if (new File(fileWindowsPath).exists()) {
            System.out.println("FileWindows");
            checkPathString = fileWindowsPath;
        }
        return checkPathString;
    }

    @FXML
    public void SwitchToCreateScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToMainScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //required to demo old main scene
    private int setNumber = 1;

    public void createNotecard(ActionEvent event) throws IOException {
        System.out.println(termInput.getText()); //Testing
        System.out.println(definitionInput.getText()); //Testing

        term = termInput.getText();
        definition = definitionInput.getText();
        studySet = titleInput.getText();

        System.out.println(studySet); //Testing

        createSet2(studySet, folderNameInput.getText());

        NoteCard nc = new NoteCard(term, definition, 0, 0);
        nc.writeQuestion(studySet, term, definition);

        Label labelSetNumber = new Label("Card " + setNumber++ + ":");
        labelSetNumber.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        Label labelDef = new Label("Definition:");
        labelDef.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        Label defLabel = new Label(this.definition);
        defLabel.setStyle("-fx-text-fill: white;");
        Label labelTerm = new Label("Term:");
        labelTerm.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        Label termLabel = new Label(this.term);
        termLabel.setStyle("-fx-text-fill: white;");

        double labelSetNumberY = 0; // top
        double labelTermY = labelSetNumberY + labelSetNumber.getHeight() + labelSetNumber.getPadding().getTop() + 10; // add padding
        double termLabelY = labelTermY + labelTerm.getHeight() + labelTerm.getPadding().getTop() + 10; // add padding
        double labelDefY = termLabelY + termLabel.getHeight() + termLabel.getPadding().getTop() + 10; // add padding
        double defLabelY = labelDefY + labelDef.getHeight() + labelDef.getPadding().getTop() + 10; // add padding

        AnchorPane notecard = new AnchorPane(labelSetNumber, labelTerm, termLabel, labelDef, defLabel);
        notecard.setPrefHeight(termLabelY + termLabel.getHeight() + 20); // Add some padding
        termList.getChildren().add(notecard);

        // Position the notecard below the previously added notecard, if any
        double y = 0;
        if (termList.getChildren().size() > 1) {
            for (Node node : termList.getChildren()) {
                if (node instanceof AnchorPane) {
                    AnchorPane previousNotecard = (AnchorPane) node;
                    y += previousNotecard.getBoundsInParent().getHeight() + 10; // Add some padding
                }
            }
        } else {
            y += 10; // Add extra padding for the first notecard
        }
        notecard.setLayoutX(0);
        notecard.setLayoutY(y);

        // Position the labels within the notecard
        labelSetNumber.setLayoutY(labelSetNumberY);
        labelTerm.setLayoutY(labelTermY);
        termLabel.setLayoutY(termLabelY);
        labelDef.setLayoutY(labelDefY);
        defLabel.setLayoutY(defLabelY);
    }


    @FXML
    public void SwitchToStudyScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToSettingScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("deleteSetScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToHomeScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToNotecardScene(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NotecardScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToHomeScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Connected to ammarTesting.fxml. Pls ignore but do not remove.
    @FXML
    protected void displaySetContents (ActionEvent event) throws IOException {
        if (comboBoxLabel.getValue() == null || comboBoxLabel.getValue().equals(""))
            System.out.println(111);
        System.out.println(comboBoxLabel.getValue());
    }

    //Again I am testing something, do not remove.
    /*@FXML
    public void ammarTestingScene (MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ammarTesting.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }*/

    @FXML
    protected void displaySetsList (Event event) throws IOException {
        fileField.getItems().clear();
        fileField.setPromptText(null);
        FileInputStream file = new FileInputStream(checkPath("setNames"));
        Scanner scanner = new Scanner(file);
        fileField.getItems().add("");
        while (scanner.hasNextLine()) {
            fileField.getItems().add(scanner.nextLine());
            if (scanner.hasNextLine())
                fileField.getItems().add(scanner.nextLine());
        }
    }

    @FXML
    public void SwitchToViewScene(MouseEvent event) throws IOException {
        if (fileField.getValue() == null || fileField.getValue().toString().equals("")) {
            fileField.setPromptText("Please select a set");
            fileField.getItems().add(null);
            fileField.getItems().set(0, "Please select a set");
        } else {
            file = fileField.getValue().toString();
            if (!checkPath(file).isEmpty()) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    public void SwitchToLearnScene(MouseEvent event) throws IOException {
        if (fileField.getValue() == null || fileField.getValue().toString().equals("")) {
            fileField.setPromptText("Please select a set");
            fileField.getItems().add(null);
            fileField.getItems().set(0, "Please select a set");
        } else {
            file = fileField.getValue().toString();
            if (!checkPath(file).isEmpty()) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learnScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void switchToQuizScene(MouseEvent event) throws IOException {
        if (fileField.getValue() == null || fileField.getValue().toString().equals("")) {
            fileField.setPromptText("Please select a set");
            fileField.getItems().add(null);
            fileField.getItems().set(0, "Please select a set");
        } else {
            file = fileField.getValue().toString();
            if (!checkPath(file).isEmpty()) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("QuizPlaceholder.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    public void updateLabel(KeyEvent event) throws IOException {
        //System.out.print(file + "This code got passed 9"); //Testing
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
    protected void onFlipClick(MouseEvent event) throws IOException {
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
    protected void onLeftClick(MouseEvent event) throws IOException {
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
    protected void onRightClick(MouseEvent event) throws IOException {
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

    public void sortNoteCards(int index) {
        int max = 9;
        int min = 1;
        int rand = (int) (Math.random() * max) + min;
        if (rand >= 1 && rand <= 3) {
            top.add(currentStudySet.get(index));
        } else if (rand > 3 && rand <= 6) {
            middle.add(currentStudySet.get(index));
        } else if (rand > 6 && rand <= 9) {
            bottom.add(currentStudySet.get(index));
        } else {
            middle.add(currentStudySet.get(index));
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
    protected void onFlipQueue(MouseEvent event) throws IOException {
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
    protected void correctAnswer(MouseEvent event) throws IOException {
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
    protected void incorrectAnswer(MouseEvent event) throws IOException { // revise
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

    public void createSet2(String title, String folderName2) throws IOException {
        String filePathName = checkPath(title);
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
            if(new File(filePathName).exists()) {
                writeToSetNameFolder(title);
            }
            data.add(Objects.requireNonNull(titleInput.getText()));
            data.add(Objects.requireNonNull(folderNameInput.getText()));

            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();
        }
    }

    public boolean createSet(String title, String folderName2) throws IOException {
        String createSetPath = checkPath(title);
        boolean returnVal = false;

        if (new File(createSetPath).exists()) {
            titleInputC.setText("That set already exists. Please enter a different set name.");
            returnVal = false;
        } else if (!createSetPath.equals("Notely/src/main/java/notely/app/Notecard/.txt")) {
            File fileMake = new File(createSetPath);
            if (fileMake.createNewFile()) {
                FileInputStream fileReading = new FileInputStream(createSetPath);
                Scanner reader = new Scanner(fileReading);
                ArrayList<String> data = new ArrayList<>();
                while (reader.hasNextLine())
                    data.add(reader.nextLine());
                reader.close();

                FileOutputStream fileWriting = new FileOutputStream(createSetPath);
                PrintWriter writer = new PrintWriter(fileWriting, true);

                if(titleInput != null){
                    data.add(Objects.requireNonNull(titleInput.getText()));
                    data.add(Objects.requireNonNull(folderNameInput.getText()));
                } else if (titleInputC != null){
                    data.add(Objects.requireNonNull(titleInputC.getText()));
                    data.add(Objects.requireNonNull(folderInputC.getText()));
                }

                for (int i = 0; i < data.size(); i++)
                    writer.write(data.get(i) + "\n");
                writer.close();
                returnVal = true;
            }
        } else {
            titleInputC.setPromptText("You must enter a set title.");
            folderInputC.setPromptText("You must enter a folder name.");
            returnVal = false;
        }
        return returnVal;
    }

    public void writeToSetNameFolder(String setName1) throws FileNotFoundException {

        String dropDownSetPath = checkPath("setNames");

        File ff = new File(dropDownSetPath);
        FileInputStream fileReading = new FileInputStream(ff);
        Scanner reader = new Scanner(fileReading);
        ArrayList<String> setArrayList = new ArrayList<>();
        while (reader.hasNextLine())
            setArrayList.add(reader.nextLine());
        reader.close();

        FileOutputStream fileWriting = new FileOutputStream(ff);
        PrintWriter writer = new PrintWriter(fileWriting, true);

        setArrayList.add(setName1);

        for (int i = 0; i < setArrayList.size(); i++)
            writer.write(setArrayList.get(i) + "\n");
        writer.close();
    }

    public void writeToTextFile(ArrayList<String> writeArrayList, String setName, String folderOfSet) throws IOException {
        studySet = setName;
        folderName = folderOfSet;

        String writeFilePath = checkPath(setName);

        try {
            File file = new File(writeFilePath);
            FileInputStream fileReading = new FileInputStream(file);
            Scanner reader = new Scanner(fileReading);
            ArrayList<String> data = new ArrayList<>();
            while (reader.hasNextLine())
                data.add(reader.nextLine());
            reader.close();

            FileOutputStream fileWriting = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(fileWriting, true);

            for (int i = 0; i < writeArrayList.size(); i += 2) {
                if(writeArrayList.get(i) != "" && writeArrayList.get(i+1) != "") {
                    data.add(writeArrayList.get(i) + "@" + writeArrayList.get(i + 1));
                }
            }
            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();

        } catch (FileNotFoundException e1) {

        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void displaySet2() throws FileNotFoundException {
        fileFieldDelete.getItems().clear();
        fileFieldDelete.setPromptText(null);
        FileInputStream file = new FileInputStream(checkPath("setNames"));
        Scanner scanner = new Scanner(file);
        fileFieldDelete.getItems().add("");
        while (scanner.hasNextLine()) {
            fileFieldDelete.getItems().add(scanner.nextLine());
            if (scanner.hasNextLine())
                fileFieldDelete.getItems().add(scanner.nextLine());
        }
    }
    @FXML
    public void deleteFromSetNameFolder(MouseEvent event) throws IOException {
        // initialize the setName file, the file we want to delete, and the string we are deleting
        File setNameFile = new File(checkPath("setNames"));
        File deleteFile = new File(checkPath(fileFieldDelete.getValue().toString()));
        String deleteName = fileFieldDelete.getValue().toString();

        //delete the set the user no longer wants
        deleteFile.delete();

        //create a reader to get the current setTerms
        BufferedReader sr1 = new BufferedReader(new InputStreamReader(new FileInputStream(setNameFile)));
        //array to hold all the set names in the current setNames text file
        ArrayList<String> setNameArray = new ArrayList<>();

        //fills the array with the set names
        Scanner scanner = new Scanner(setNameFile);
        while (scanner.hasNextLine()) {
            setNameArray.add(scanner.nextLine());
        } sr1.close();
        //finds the string to delete
        for(int i = 0; i < setNameArray.size(); i++){
            if(setNameArray.get(i).equals(deleteName)){
                setNameArray.remove(i);
            }
        }
        //delete the old set file and create a new one
        setNameFile.delete();
        File newSetNameFile = new File(checkPath("setNames"));
        newSetNameFile.createNewFile();

        //write the array that stores the set names into the new setName file
        PrintWriter sw1 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(newSetNameFile)));
        for(int i = 0; i < setNameArray.size(); i++){
            sw1.write(setNameArray.get(i) + "\n");
        } sw1.close();
    }

    public void readFile(String fileNAMEWORKS) throws IOException { //Reads a txt file to fill arraylists with words to be guessed.
        System.out.println(fileNAMEWORKS + "This code got passed 2"); //Testing
        String term = "";
        String filePathOS = checkPath(fileNAMEWORKS);
        String definition = "";
        String currentLine;
        String title = "";
        String folder = "";
        int priority = 3;

        System.out.println("\n\n" + filePathOS);
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

    @FXML public void createSceneDynamic(MouseEvent event) throws IOException {
        TextField termField = new TextField();
        TextField defField = new TextField();
        Label numberTermLabel = new Label();
        termField.setPromptText("Enter your term here");
        defField.setPromptText("Enter your definition here");
        numberTermLabel.setText(String.valueOf(labelCounter));
        termField.setPrefSize(257, 40);
        defField.setPrefSize(257,40);

        AnchorPane newInsertField = new AnchorPane(termField, defField, numberTermLabel);
        labelCounter++;

        createVbox.getChildren().add(newInsertField);
        newInsertField.setMinSize(708,100);

        if (createVbox.getChildren().size() > 1) {
            for (Node node : createVbox.getChildren()) {
                if (node instanceof AnchorPane previousAdded) {
                    numberTermLabel.setLayoutX(60);
                    termField.setLayoutX(70);
                    defField.setLayoutX(390);
                    termField.setLayoutY(30);
                    defField.setLayoutY(30);
                    numberTermLabel.setLayoutY(10);
                }
            }
        }
    }
    @FXML
    public void onSaveButton(MouseEvent event) throws IOException {
        //Get set name and folder name
        studySet = titleInputC.getText();
        folderName = folderInputC.getText();
        //make an array to store strings
        ArrayList<String> textList = new ArrayList<>();
        // observes all children in the Vbox - aka panes
        ObservableList<Node> anchorpaneList = createVbox.getChildren();
        // uses the observable list of anchorPanes to find each pane, then to find textfields in each pane
        for (Node node : anchorpaneList) {
            if (node instanceof AnchorPane) {
                // gets children of anchorPanes - aka the textfields
                ObservableList<Node> textFieldList = ((AnchorPane) node).getChildren();

                // loops through each textfield existing in the panes
                for (Node textFieldNode : textFieldList) {
                    if (textFieldNode instanceof TextField) {
                        // get text from each instance of a textfield inside the anchorpane and then store it in an array
                        String textFieldText = ((TextField) textFieldNode).getText();
                        if(textFieldText != null){
                            textList.add(textFieldText);
                        }
                    }
                }
            }
        }

        if(createSet(studySet, folderName)) {
            writeToSetNameFolder(studySet);
            writeToTextFile(textList, studySet, folderName);
            switchToHomeScene(event);
        }
    }

    @FXML
    AnchorPane helpScreen;
    @FXML
    AnchorPane importPane;
    @FXML
    TextField titleImport;
    @FXML
    TextField folderImport;
    @FXML
    TextArea pasteArea;
    @FXML
    Button saveImportButton;
    @FXML
    Button helpButton;
    @FXML
    Label importTitle;
    @FXML
    public void onImportButton(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Import.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void closePopUp(MouseEvent event) throws IOException {
        helpScreen.setVisible(false);
        titleImport.setVisible(true);
        folderImport.setVisible(true);
        pasteArea.setVisible(true);
        saveImportButton.setVisible(true);
        helpButton.setVisible(true);
        importTitle.setVisible(true);
    }
    @FXML
    public void helpPopUp(MouseEvent event) throws IOException {
        helpScreen.setVisible(true);
        titleImport.setVisible(false);
        folderImport.setVisible(false);
        pasteArea.setVisible(false);
        saveImportButton.setVisible(false);
        helpButton.setVisible(false);
        importTitle.setVisible(false);
    }
    @FXML
    public void saveImport(MouseEvent event) throws IOException {
        String title = titleImport.getText();
        String folder = folderImport.getText();

        String saveImportPath = checkPath(title);
        writeToSetNameFolder(title);

        Scanner scanner = new Scanner(System.in);
        try {
            ArrayList<String> data = new ArrayList<>();
            data.add(title);
            data.add(folder);
            data.add(pasteArea.getText());
            File file = new File(saveImportPath);
            FileOutputStream fileWriting = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(fileWriting, true);

            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();

        } catch (FileNotFoundException e1) {
            System.out.printf("\nSet with title \"%s\" does not exist.\n", studySet);
            System.out.println("Exiting function!");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        switchToHomeScene(event);
    }
    @FXML
    public void multipleChoiceSetUp(MouseEvent event) throws IOException {
        readFile(file);
        MultipleChoice quiz = new MultipleChoice();
        for (int i = 0; i < currentStudySet.size(); i++) {
            quiz.addNoteCard(currentStudySet.get(i));
        }
        quiz.shuffleList();
        quiz.Quiz();
    }

}