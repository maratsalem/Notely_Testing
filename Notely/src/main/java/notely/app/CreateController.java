package notely.app;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class CreateController {
    @FXML VBox createVbox;
    @FXML ScrollPane scrollPane;
    @FXML AnchorPane firstCreateAPane;
    @FXML AnchorPane termList;
    @FXML TextField titleInputC;
    @FXML TextField folderInputC;
    @FXML ComboBox fileField;
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
    @FXML TextField setNumber;
    @FXML TextField newTerm;
    @FXML TextField newDef;
    @FXML
    Button topLeftAnswer;
    @FXML
    Button topRightAnswer;
    @FXML
    Button bottomLeftAnswer;
    @FXML
    Button reviewButton;
    @FXML
    Button bottomRightAnswer;
    @FXML
    Button MCSetupButton;
    @FXML
    Label multChoiceQuestion;
    @FXML
    Label questionTracker;
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
    @FXML ComboBox fontStylesComboBox;
    @FXML CheckBox darkModeCheck;
    static String fontStyle = "System";
    static boolean darkMode = false;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String txt;
    private String themeSelect ="normalTheme.css";
    private static String file = "";
    private int arraySize;
    private int labelCounter = 2;
    private int index = 0;
    private int flip = 0;
    private boolean spacePressed = false;
    private int keyCheck = 0;
    private ArrayList<NoteCard> currentStudySet = new ArrayList<>();
    Queue<NoteCard> queue1 = new LinkedList<>();
    Queue<NoteCard> queue2 = new LinkedList<>();
    Queue<NoteCard> queue3 = new LinkedList<>();
    Queue<NoteCard> top = new LinkedList<>();
    Queue<NoteCard> middle = new LinkedList<>();
    Queue<NoteCard> bottom = new LinkedList<>();
    MultipleChoice quiz = new MultipleChoice();

    //path checking + switching scene methods
    public String checkPath(String checkingPathString){

        String fileMacPath = "./src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";
        String fileWindowsPath = "../src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";
        String checkPathString = "Notely/src/main/java/notely/app/Notecard/" + checkingPathString + ".txt"; //default

        if (new File(fileMacPath).exists()) { // ./  for MACOS and ../ for Windows
            //System.out.println("FileMac");
            checkPathString = fileMacPath;
        } else if (new File(fileWindowsPath).exists()) {
            //System.out.println("FileWindows");
            checkPathString = fileWindowsPath;
        }
        return checkPathString;
    }

    public void checkingTheme(boolean checking){
        if(checking){
            themeSelect = "darkTheme.css";
        } else {
            themeSelect = "normalTheme.css";
        }
    }

    @FXML
    public void SwitchToCreateScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToSettingsScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SettingsScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.show();
    }


    @FXML
    public void SwitchToMainScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.show();
    }
    @FXML
    public void SwitchToStudyScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudyScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.show();
        spacePressed = false;
        keyCheck = 0;
    }

    @FXML
    public void SwitchToDeleteSetScene(MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DeleteScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.show();
    }

    @FXML
    public void SwitchToHomeScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.show();
    }
    @FXML
    public void switchToHomeScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomeScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.show();
    }

    //more methods for switiching to scenes + a method to display all the current study sets within a combobox
    //so the user can select one from the appropriate scenes
    @FXML
    protected void displaySetsList () throws IOException {
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
            fileField.setPromptText("Please select a set.");
            fileField.getItems().add(null);
            fileField.getItems().set(0, "Please select a set.");
        } else {
            file = fileField.getValue().toString();
            if (!checkPath(file).isEmpty()) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                scene.getStylesheets().add(fontStyle + ".css");
                checkingTheme(darkMode);
                scene.getStylesheets().add(themeSelect);
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
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LearnScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                scene.getStylesheets().add(fontStyle + ".css");
                checkingTheme(darkMode);
                scene.getStylesheets().add(themeSelect);
                stage.show();
            }
        }
    }

    public void switchToQuizScene(MouseEvent event) throws IOException {
        quizBackPane.setVisible(true);
        if (fileField.getValue() == null || fileField.getValue().toString().equals("")) {
            fileField.setPromptText("Please select a set");
            fileField.getItems().add(null);
            fileField.getItems().set(0, "Please select a set");
        } else {
            file = fileField.getValue().toString();
            if (!checkPath(file).isEmpty()) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Quiz.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                checkingTheme(darkMode);
                scene.getStylesheets().add(themeSelect);
                scene.getStylesheets().add(fontStyle + ".css");
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    @FXML
    public void displayFontStyles() {
        fontStylesComboBox.getItems().clear();
        fontStylesComboBox.getItems().addAll("System", "Verdana", "Comic Sans MS", "Algerian", "Times New Roman");
    }

    @FXML
    public void applySettings(MouseEvent event) throws IOException {
        if (!(fontStylesComboBox.getValue() == null)) {
            fontStyle = fontStylesComboBox.getValue().toString();
            fontStylesComboBox.setPromptText(fontStyle);
            fontStyle = fontStyle.replace(" ", "_");
        }
        if(darkModeCheck.isSelected()){
            darkMode = true;
            themeSelect = "darkTheme.css";
        } else {
            darkMode = false;
            themeSelect = "normalTheme.css";
        }

        SwitchToSettingsScene(event);
    }

    //start of methods used to display notecards on the learn and view scene
    @FXML
    public void updateLabel() throws IOException {
        if(!buttonPressed()){
            readFile(file);
            System.out.println(currentStudySet.get(index).getTerm());
            arraySize = currentStudySet.size();
            txt = currentStudySet.get(index).getTerm();
            textLabel.setText(txt);
            leftButton.setDisable(false);
            rightButton.setDisable(false);
            flipButton.setDisable(false);
        }
    }

    //necessary in order to stop the user from locking the set they are studying while viewing or learning
    // if they press a button while studying their set
    public boolean buttonPressed() {
        // System.out.println("This is in my boolean check."); //Testing
            if (keyCheck < 1) {
                spacePressed = false;
                keyCheck++;
            } else {
                spacePressed = true;
            }
        return spacePressed;
    }

    //Reads a txt file to fill arraylists with words to be guessed
    public void readFile(String readFileNameInput) throws IOException {
        String term = "";
        String filePathOS = checkPath(readFileNameInput);
        String definition = "";
        String currentLine = "";
        String title = "";
        String folder = "";
        int priority = 3;

        //System.out.println(filePathOS); //Testing
        FileReader fr = new FileReader(filePathOS);
        BufferedReader brin = new BufferedReader(fr);
        int index = 0;

        while ((currentLine = brin.readLine()) != null) {
            if (index == 0) {
                title = currentLine;
                titleLabel.setText(title);
                index++;
            } else if (index == 1) {
                folder = currentLine;
                index++;
            } else if (index > 1) {
                int tokenNumber = 0;
                StringTokenizer tokens = new StringTokenizer(currentLine, "@");
                while (tokens.hasMoreTokens()) {
                    String token = tokens.nextToken();
                    term = token;
                    token = tokens.nextToken();
                    definition = token;
                    tokenNumber++;
                    NoteCard noteCard = new NoteCard(term, definition, priority, tokenNumber);
                    currentStudySet.add(noteCard);
                }
            }
        }
        brin.close();
    }
    @FXML
    protected void onFlipClick() throws IOException {
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
    protected void onLeftClick() {
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
    protected void onRightClick() {
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
    public void loadLabel() throws IOException {
        boolean testBoolean = buttonPressed();
        if (!testBoolean) {
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
    protected void onFlipQueue() {
        int x;
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
    protected void correctAnswer() {
        if (!queue1.isEmpty()) {
            if (queue1.element().getPriorityNum() == 3) {
                //System.out.println("Queue1 " + queue1.element().getTerm()); //Testing
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
    protected void incorrectAnswer() { // revise
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

    //methods below deal w the creation of sets, deletion of sets, and editing sets as well
    // as the files that go along w them
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

                if (titleInputC != null && !titleInputC.getText().equals("") && !folderInputC.getText().equals("")){
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

    //used to add a created set name into a text file for the drop-down menu to be able to access
    public void writeToSetNameFolder(String createSetName) throws FileNotFoundException {
        String dropDownSetPath = checkPath("setNames");

        File wtSetNameFolder = new File(dropDownSetPath);
        FileInputStream fileReading = new FileInputStream(wtSetNameFolder);
        Scanner reader = new Scanner(fileReading);
        ArrayList<String> setArrayList = new ArrayList<>();
        while (reader.hasNextLine())
            setArrayList.add(reader.nextLine());
        reader.close();

        FileOutputStream fileWriting = new FileOutputStream(wtSetNameFolder);
        PrintWriter writer = new PrintWriter(fileWriting, true);

        setArrayList.add(createSetName);

        for (int i = 0; i < setArrayList.size(); i++)
            writer.write(setArrayList.get(i) + "\n");
        writer.close();
    }

    public void writeToTextFile(ArrayList<String> writeArrayList, String setName, String folderOfSet) {

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

            //uses arrayList created by create scece to create an array with proper term/def format
            for (int i = 0; i < writeArrayList.size(); i += 2) {
                if(!Objects.equals(writeArrayList.get(i), "") && !Objects.equals(writeArrayList.get(i + 1), "")) {
                    data.add(writeArrayList.get(i) + "@" + writeArrayList.get(i + 1));
                }
            }

            //uses the previously created array to write into the text file
            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();

        } catch (FileNotFoundException ignored) {

        }
    }

    @FXML public void createSceneDynamic() {
        //initalize a bunch of new scene objects and their size + positions
        TextField termField = new TextField();
        TextField defField = new TextField();
        Label numberTermLabel = new Label();

        termField.setPromptText("Enter your term here");
        defField.setPromptText("Enter your definition here");
        numberTermLabel.setText(String.valueOf(labelCounter));

        termField.setPrefSize(257, 40);
        defField.setPrefSize(257,40);

        //positions
        numberTermLabel.setLayoutX(60);
        termField.setLayoutX(70);
        defField.setLayoutX(390);
        termField.setLayoutY(30);
        defField.setLayoutY(30);
        numberTermLabel.setLayoutY(10);

        //add above objects into an anchorpane for ease of positioning
        AnchorPane newInsertField = new AnchorPane(termField, defField, numberTermLabel);

        //used to keep track of how many terms a user adds to the scene
        labelCounter++;

        //adds newInsertField to the createScene w proper sizing
        newInsertField.setMinSize(708,100);
        createVbox.getChildren().add(newInsertField);

    }

    @FXML
    public void onSaveButton(MouseEvent event) throws IOException {
        //Get set name and folder name
        String studySet = titleInputC.getText();
        String folderName = folderInputC.getText();
        //make an array to store strings
        ArrayList<String> textList = new ArrayList<>();
        // list of all children in the Vbox - aka panes
        ObservableList<Node> anchorPaneList = createVbox.getChildren();

        // uses anchorPaneList to find each anchor pane in the Vbox
        for (Node anchPaneNode : anchorPaneList) {
            if (anchPaneNode instanceof AnchorPane) {

               //list of all the textFields within each of the anchor panes
                ObservableList<Node> textFieldList = ((AnchorPane) anchPaneNode).getChildren();

                // uses the textFieldList to find instances of textFields in the anchor panes
                // (two per ancpane - term and def)
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

        //creates the set the user wants - if the set already exists, the info is not written
        //to a textFile till the user enters a valid set name
        if(createSet(studySet, folderName)) {
            writeToSetNameFolder(studySet);
            writeToTextFile(textList, studySet, folderName);
            switchToHomeScene(event);
        }
    }
    @FXML
    public void onImportButton(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Import.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(fontStyle + ".css");
        checkingTheme(darkMode);
        scene.getStylesheets().add(themeSelect);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void closePopUp() {
        helpScreen.setVisible(false);
        titleImport.setVisible(true);
        folderImport.setVisible(true);
        pasteArea.setVisible(true);
        saveImportButton.setVisible(true);
        helpButton.setVisible(true);
        importTitle.setVisible(true);
    }
    @FXML
    public void helpPopUp() {
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
        String text = pasteArea.getText();
        String saveImportPath = checkPath(title);
        int titleCheck = 0;
        int folderCheck = 0;
        int textCheck = 0;

        if (title.isEmpty()) {
            titleImport.setPromptText("You must enter a set name.");
            titleCheck = 1;
        } else {
            if (new File(saveImportPath).exists()) {
                titleImport.setPromptText("A set with the name: " + title + " already exists.");
                titleImport.setText("");
                titleCheck = 1;
            } else {
                titleCheck = 0;
            }
        }
        if (folder.isEmpty()) {
            folderImport.setPromptText("You must enter a folder name.");
            folderCheck = 1;
        } else {
            folderCheck = 0;
        }
        if (text.isEmpty() || text.isBlank()) {
            pasteArea.setText("");
            pasteArea.setPromptText("You must enter text.");
            textCheck = 1;
        } else {
            StringTokenizer lines = new StringTokenizer(pasteArea.getText(), "\n");
            ArrayList<String> textArea = new ArrayList<>();
            for (int i = 0; i < lines.countTokens(); i++) {
                String token = lines.nextToken();
                System.out.println(token);
                textArea.add(token);
            }
            for (int i = 0; i < textArea.size(); i++) {
                StringTokenizer tokens = new StringTokenizer(textArea.get(i), "@");
                System.out.print(tokens.countTokens());
                if (!textArea.get(i).contains("@")) {
                    pasteArea.setPromptText("There is a line that is missing the @.");
                    pasteArea.setText("");
                    textCheck = 1;
                    break;
                } else if (tokens.countTokens() == 0) {
                    pasteArea.setPromptText("There is a line that is missing the term and definition.");
                    pasteArea.setText("");
                    textCheck = 1;
                    break;
                } else if (tokens.countTokens() == 1) {
                    pasteArea.setPromptText("There is a term or definition missing from a line.");
                    pasteArea.setText("");
                    textCheck = 1;
                    break;
                } else if (tokens.countTokens() > 2) {
                    pasteArea.setPromptText("There are multiple @ in one line");
                    pasteArea.setText("");
                    textCheck = 1;
                    break;
                } else {
                    textCheck = 0;
                }
            }
            if (titleCheck == 0 && folderCheck == 0 && textCheck == 0) {
                writeToSetNameFolder(title);
                ArrayList<String> data = new ArrayList<>();
                data.add(title);
                data.add(folder);
                data.add(pasteArea.getText());
                File file = new File(saveImportPath);
                FileOutputStream fileWriting = new FileOutputStream(file);
                PrintWriter writer = new PrintWriter(fileWriting, true);

                for (int i = 0; i < data.size(); i++) {
                    writer.write(data.get(i) + "\n");
                }
                writer.close();
                switchToHomeScene(event);
            }
        }
    }
    @FXML
    protected void displayTermsAndDefinitions() {
        if (fileField.getValue() == null) {
            fileField.setPromptText("Please select a Set");
            return;
        }
        String setName = fileField.getValue().toString();
        if (!checkPath(setName).isEmpty()) {
            String fileName = checkPath(setName);
            File file = new File(fileName);
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    int counter = 1;

                    // Clear existing notecards from termList
                    termList.getChildren().clear();

                    while ((line = br.readLine()) != null) {
                        if (counter > 2) {
                            String[] parts = line.split("@");
                            if (parts.length >= 2) {
                                AnchorPane notecard = new AnchorPane();

                                Label setNumberLabel = new Label("Notecard " + (counter - 2));
                                setNumberLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                                Label termLabel = new Label("Term " + (counter - 2) + ": " + parts[0]);
                                termLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                                Label definitionLabel = new Label("Definition " + (counter - 2) + ": " + parts[1]);
                                definitionLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");

                                Line separatorLine = new Line();
                                separatorLine.setStartX(0);
                                separatorLine.setStartY(85);
                                separatorLine.setEndX(400);
                                separatorLine.setEndY(85);
                                separatorLine.setStroke(Color.web("#ff970f"));
                                if(darkMode){
                                    separatorLine.setStroke(Color.web("#313170"));
                                }

                                notecard.getChildren().addAll(setNumberLabel, termLabel, definitionLabel, separatorLine);

                                // Position the notecard within termList
                                double y = (counter - 3) * 80;
                                notecard.setLayoutX(0);
                                notecard.setLayoutY(y);

                                setNumberLabel.setLayoutX(10);
                                setNumberLabel.setLayoutY(10);
                                termLabel.setLayoutX(10);
                                termLabel.setLayoutY(40);
                                definitionLabel.setLayoutX(10);
                                definitionLabel.setLayoutY(60);

                                termList.getChildren().add(notecard); // add notecard to termList
                            }
                        }
                        counter++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //below methods used to edit a set

    @FXML Label deleteLabel;
    @FXML
    public void deleteSet() throws IOException {
        // initialize the setName file, the file we want to delete, and the string we are deleting
        File setNameFile = new File(checkPath("setNames"));
        File deleteFile = new File(checkPath(fileField.getValue().toString()));
        String deleteName = fileField.getValue().toString();

        //delete the set the user no longer wants

        if(deleteFile.delete()) {
            deleteLabel.setText(deleteName + " has been successfully deleted.");
        } else if (fileField.getValue() == null){
            deleteLabel.setText("Please select a set to delete.");
        } else {
            deleteLabel.setText(deleteName + " has not been deleted.");
        }

        BufferedReader readingSetNames = new BufferedReader(new InputStreamReader(new FileInputStream(setNameFile)));

        //array to hold all the set names in the current setNames text file
        ArrayList<String> setNameArray = new ArrayList<>();

        //fills the array with the set names
        Scanner scanner = new Scanner(setNameFile);
        while (scanner.hasNextLine()) {
            setNameArray.add(scanner.nextLine());
        }
        readingSetNames.close();

        //finds the string to delete
        for (int i = 0; i < setNameArray.size(); i++) {
            if (setNameArray.get(i).equals(deleteName)) {
                setNameArray.remove(i);
            }
        }
        //delete the old set file and create a new one
        setNameFile.delete();
        File newSetNameFile = new File(checkPath("setNames"));
        newSetNameFile.createNewFile();

        //write the array that stores the set names into the new setName file
        PrintWriter writingSetNames = new PrintWriter(new OutputStreamWriter(new FileOutputStream(newSetNameFile)));
        for (int i = 0; i < setNameArray.size(); i++) {
            writingSetNames.write(setNameArray.get(i) + "\n");
        }
        writingSetNames.close();
    }
        @FXML
    protected void saveChanges() throws IOException {
            String setName = fileField.getValue().toString();
            String fileName = checkPath(setName);
            File file = new File(fileName);

            // Read the contents of the text file
            List<String> lines = Files.readAllLines(file.toPath());
            int changeIndex = 0;

            // Calculate the start index of the selected set
            if (setNumber != null && !setNumber.getText().isEmpty()) {
                // new line
                try {
                    changeIndex = Integer.parseInt(setNumber.getText());
                } catch (NumberFormatException e) {
                    setNumber.setText("Please enter a number value.");
                }

                if(changeIndex > (lines.size() - 2) || changeIndex <= 0){
                    setNumber.setText("Please enter a valid number.");
                } else {
                    int startIndex = changeIndex + 1;

                    // Update the selected term and definition with the new values
                    if(newTerm.getText().isEmpty() || newDef.getText().isEmpty()){
                        lines.remove(startIndex);
                    } else {
                        lines.set(startIndex, newTerm.getText() + "@" + newDef.getText());
                    }

                    // Save the updated contents back to the text file
                    Files.write(file.toPath(), lines);

                    //reads and writes the current notecards in a file to the right hand side of the screen
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        int counter = 1;

                        // Clear existing notecards from termList
                        termList.getChildren().clear();

                        while ((line = br.readLine()) != null) {
                            if (counter > 2) {
                                String[] parts = line.split("@");
                                if (parts.length >= 2) {
                                    AnchorPane notecard = new AnchorPane();

                                    Label setNumberLabel = new Label("Notecard " + (counter - 2));
                                    setNumberLabel.setStyle("-fx-text-fill: white");
                                    Label termLabel = new Label("Term " + (counter - 2) + ": " + parts[0]);
                                    termLabel.setStyle("-fx-text-fill: white");
                                    Label definitionLabel = new Label("Definition " + (counter - 2) + ": " + parts[1]);
                                    definitionLabel.setStyle("-fx-text-fill: white");

                                    notecard.getChildren().addAll(setNumberLabel, termLabel, definitionLabel);

                                    // Position the notecard within termList
                                    double y = (counter - 3) * 80;
                                    notecard.setLayoutX(0);
                                    notecard.setLayoutY(y);

                                    setNumberLabel.setLayoutX(10);
                                    setNumberLabel.setLayoutY(10);
                                    termLabel.setLayoutX(10);
                                    termLabel.setLayoutY(40);
                                    definitionLabel.setLayoutX(10);
                                    definitionLabel.setLayoutY(60);

                                    termList.getChildren().add(notecard); // add notecard to termList
                                }
                            }
                            counter++;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    //methods below are used for multiple choice testing
    int quizSize; //for later function?? - allowing the user to set quiz size
    QuizReview review = new QuizReview();

    @FXML
    public void multipleChoiceSetUp() throws IOException {
        quiz.reset();
        readFile(file);
        selectAnotherSet.setVisible(false);

        for (int i = 0; i < currentStudySet.size(); i++) {
            quiz.addNoteCard(currentStudySet.get(i));
        }

        quiz.shuffleList();
        //update GUI
        topLeftAnswer.setDisable(false);
        topRightAnswer.setDisable(false);
        bottomLeftAnswer.setDisable(false);
        bottomRightAnswer.setDisable(false);
        MCSetupButton.setDisable(true);
        topLeftAnswer.setOpacity(1);
        bottomLeftAnswer.setOpacity(1);
        topRightAnswer.setOpacity(1);
        bottomRightAnswer.setOpacity(1);
        MCSetupButton.setOpacity(0);
        reviewButton.setDisable(true);
        reviewButton.setOpacity(0);
        updateQuizQuestion();
        // quiz.Quiz(); //for testing purposes
    }
    @FXML
    public void TLMultipleChoiceAnswer() {mChoiceAnswer(topLeftAnswer.getText());}
    @FXML
    public void TRMultipleChoiceAnswer() {mChoiceAnswer(topRightAnswer.getText());}
    @FXML
    public void BLMultipleChoiceAnswer() {mChoiceAnswer(bottomLeftAnswer.getText());}
    @FXML
    public void BRMultipleChoiceAnswer() {mChoiceAnswer(bottomRightAnswer.getText());}
    public void mChoiceAnswer(String answer) {
        boolean isCorrect = quiz.answer(answer);
        System.out.println(answer);
        System.out.println(multChoiceQuestion.getText());
        System.out.println(isCorrect);
        int correctnessValue = 0; //this is kinda spaghetti code, but this passes the questions correctness to the review
        if (isCorrect) {correctnessValue = 1;} //0 means wrong, 1 means right

        //adds the answer to the review list so the user can check if they got it right or wrong
        NoteCard question = new NoteCard(quiz.getQuestion(), quiz.getAnswer1(), correctnessValue, 0);

        review.addAnswer(question, answer);


        //update GUI to next question
        quiz.nextQuestion();
        updateQuizQuestion();
    }

    public void endQuiz() {
        quiz.endQuiz();
        multChoiceQuestion.setText("Quiz Complete. Score: " + quiz.getNumberCorrect() + "/" + quiz.getQuizSize() + " (" + Math.round(quiz.getQuizScore()) + "%)");
        reviewScore.setText("   Score: " + quiz.getNumberCorrect() + "/" + quiz.getQuizSize() + " (" + Math.round(quiz.getQuizScore()) + "%)");
        MCSetupButton.setDisable(true);
        topLeftAnswer.setDisable(true);
        topRightAnswer.setDisable(true);
        bottomLeftAnswer.setDisable(true);
        bottomRightAnswer.setDisable(true);
        topLeftAnswer.setOpacity(0.5);
        bottomLeftAnswer.setOpacity(0.5);
        topRightAnswer.setOpacity(0.5);
        bottomRightAnswer.setOpacity(0.5);

        reviewButton.setDisable(false);
        reviewButton.setOpacity(1);

        quiz.reset();
    }

    public void updateQuizQuestion() {
        if (quiz.getQuizCompletion()) {endQuiz();}
        else {
            //update question (top field)
            multChoiceQuestion.setText(quiz.getQuestion());
            //randomize answer list
            quiz.setAnswerList();
            ArrayList<String> bufferList = new ArrayList<>();
            bufferList.add(quiz.getAnswer1());
            bufferList.add(quiz.getAnswer2());
            bufferList.add(quiz.getAnswer3());
            bufferList.add(quiz.getAnswer4());

            ArrayList<String> answerList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int randindex = (int) (Math.random() * bufferList.size());
                String dummy = bufferList.get(randindex);
                bufferList.remove(randindex);
                answerList.add(dummy);
            }

            //set button text
            topLeftAnswer.setText(answerList.get(0));
            topRightAnswer.setText(answerList.get(1));
            bottomLeftAnswer.setText(answerList.get(2));
            bottomRightAnswer.setText(answerList.get(3));

            //set question tracker text
            questionTracker.setText("" + (quiz.getQuestionIndex()+1) + "/" + quiz.getQuizSize());
        }
    }

    //MULTIPLE CHOICE END


    //MULTIPLE CHOICE REVIEW
    @FXML AnchorPane reviewPane;
    @FXML Label reviewTitle;
    @FXML Label reviewScore;
    @FXML Label reviewSet;

    @FXML Button selectAnotherSet;
    @FXML AnchorPane quizBackPane = new AnchorPane();
    @FXML void reviewButtonClicked() {
        reviewSet.setText("    " + titleLabel.getText());
        titleLabel.setText("");
        reviewPane.setDisable(false);
        reviewPane.setOpacity(1);
        reviewTitle.setOpacity(1);
        quizBackPane.setVisible(false);
        selectAnotherSet.setVisible(true);

        for (int i = 0; i < review.getReviewSize(); i++) {
            generateReview(i);
        }

        review.clearReview();
    }
    public void generateReview(int i) {

        Label answerLabel = new Label("Answer:");
        Label userAnswerLabel = new Label("Your Answer:");
        Label dynamicAnswerLabel = new Label(review.getCorrectAnswer(i));
        Label dynamicUserAnswerLabel = new Label(review.getUserAnswer(i));
        Label numberTermLabel = new Label("Question " + (i+1) + ": " + review.getQuestion(i));

        dynamicAnswerLabel.setPrefSize(309, 44);
        dynamicUserAnswerLabel.setPrefSize(309, 44);

        Pane answerPane = new Pane(dynamicAnswerLabel);
        answerPane.setStyle("-fx-background-radius: 17; -fx-background-color: #ffffffff;");
        if(darkMode){
            dynamicAnswerLabel.setStyle("-fx-text-fill: black;");
        }
        dynamicAnswerLabel.setLayoutX(5);
        dynamicAnswerLabel.setLayoutY(0);

        Pane userAnswerPane = new Pane(dynamicUserAnswerLabel);
        dynamicUserAnswerLabel.setLayoutX(5);
        dynamicUserAnswerLabel.setLayoutY(0);
        userAnswerPane.setStyle("-fx-background-radius: 17; -fx-background-color: #ff7878;");
        if(darkMode){
            dynamicAnswerLabel.setStyle("-fx-text-fill: black;");
        }
        if (review.getCorrectness(i)) {userAnswerPane.setStyle("-fx-background-radius: 17; -fx-background-color: #45f25f;");}

        answerPane.setPrefSize(309, 44);
        userAnswerPane.setPrefSize(309, 44);

        AnchorPane newInsertField = new AnchorPane(answerLabel, userAnswerLabel, answerPane, userAnswerPane, numberTermLabel);

        newInsertField.setMinSize(708,120);
        createVbox.getChildren().add(newInsertField);

        if (createVbox.getChildren().size() > 1) {
            for (Node node : createVbox.getChildren()) {
                if (node instanceof AnchorPane previousAdded) {
                    numberTermLabel.setLayoutX(previousAdded.getLayoutX() + 25);

                    answerLabel.setLayoutX(previousAdded.getLayoutX() + 35);
                    userAnswerLabel.setLayoutX(previousAdded.getLayoutX() + 355);

                    answerPane.setLayoutX(previousAdded.getLayoutX() + 40);
                    userAnswerPane.setLayoutX(previousAdded.getLayoutX() + 365);

                    answerPane.setLayoutY(answerPane.getHeight() + answerPane.getPadding().getTop() + 50);
                    userAnswerPane.setLayoutY(answerPane.getHeight() + answerPane.getPadding().getTop() + 50);

                    numberTermLabel.setLayoutY(answerPane.getHeight() + answerPane.getPadding().getTop() + 10);
                    answerLabel.setLayoutY(answerPane.getHeight() + answerPane.getPadding().getTop() + 35);
                    userAnswerLabel.setLayoutY(answerPane.getHeight() + answerPane.getPadding().getTop() + 35);
                    newInsertField.setLayoutY(previousAdded.getLayoutY());
                }
            }
        }
    }

}