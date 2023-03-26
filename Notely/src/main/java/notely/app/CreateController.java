package notely.app;

import javafx.collections.ObservableList;
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

import java.io.*;
import java.util.*;

public class CreateController {
    @FXML
    VBox createVbox = new VBox();
    @FXML ScrollPane scrollPane = new ScrollPane();
    @FXML
    AnchorPane termList;
    @FXML
    TextArea definitionInput;
    @FXML
    TextArea termInput;
    @FXML
    TextArea titleInput;
    @FXML
    TextArea folderNameInput;
    @FXML
    TextField fileField;
    @FXML
    TextField titleInputC;
    @FXML
    TextField folderInputC;
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
    Button createSceneButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String studySet;
    private String folderName;
    @FXML
    private TextField firstTermField;
    @FXML
    private TextField firstDefField;
    @FXML
    private Label firstNumLabel;
    @FXML
    AnchorPane firstCreateAPane;
    private String txt;
    private static String file = "";
    private int arraySize;
    private int labelCounter = 2;
    private int index = 0;
    private int flip = 0;
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

    @FXML
    public void SwitchToCreateScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToMainScene(MouseEvent event) throws IOException {            //remove code
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void SwitchToStudyScene(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View.fxml")));
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

    @FXML
    public void SwitchToViewScene(MouseEvent event) throws IOException {
        if (!fileField.getText().isEmpty()) {
            file = fileField.getText();
            filePath.setFileName(file);
            //System.out.println(file + "This code got passed 5"); //Testing
            //System.out.println(filePath.getFileName() + "This code got passed 5.5"); //Testing
        }
        if (!file.isEmpty()) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ViewScreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void SwitchToLearnScene(MouseEvent event) throws IOException {
        if (!fileField.getText().isEmpty()) {
            file = fileField.getText();
            filePath.setFileName(file);
            System.out.println(file);
            fileName.add(file);
        }
        if (!file.isEmpty()) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("learnScreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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

    public boolean createSet(String title, String folderName2) throws IOException {
        String fileMacPath2 = "./src/main/java/notely/app/Notecard/" + title + ".txt";
        String fileWindowsPath2 = "../src/main/java/notely/app/Notecard/" + title + ".txt";
        String createSetPath = "Notely/src/main/java/notely/app/Notecard/" + title + ".txt";
        boolean returnVal = false;

        if (new File(fileMacPath2).exists()) { // ./  for MACOS and ../ for Windows
            System.out.println("FileMac");
            createSetPath = fileMacPath2;
        } else if (new File(fileWindowsPath2).exists()) {
            System.out.println("FileWindows");
            createSetPath = fileWindowsPath2;
        }

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

                data.add(Objects.requireNonNull(titleInputC.getText()));
                data.add(Objects.requireNonNull(folderInputC.getText()));

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
        String fileMacPath4 = "./src/main/java/notely/app/Notecard/setNames";
        String fileWindowsPath4 = "../src/main/java/notely/app/Notecard/setNames";
        String dropDownSetPath = "Notely/src/main/java/notely/app/Notecard/setNames";

        if (new File(fileMacPath4).exists()) { // ./  for MACOS and ../ for Windows
            System.out.println("FileMac");
            dropDownSetPath = fileMacPath4;
        } else if (new File(fileWindowsPath4).exists()) {
            System.out.println("FileWindows");
            dropDownSetPath = fileWindowsPath4;
        }

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

        String fileMacPath3 = "./src/main/java/notely/app/Notecard/" + setName + ".txt";
        String fileWindowsPath3 = "../src/main/java/notely/app/Notecard/" + setName + ".txt";
        String writeFilePath = "Notely/src/main/java/notely/app/Notecard/" + setName + ".txt";
        boolean returnVal = false;

        if(new File(fileMacPath3).exists()){ // ./  for MACOS and ../ for Windows
            System.out.println("FileMac");
            writeFilePath = fileMacPath3;
        }
        else if (new File(fileWindowsPath3).exists()){
            System.out.println("FileWindows");
            writeFilePath = fileWindowsPath3;
        }

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
    public boolean readFile(String fileNAMEWORKS) throws IOException { //Reads a txt file to fill arraylists with words to be guessed.
        System.out.println(fileNAMEWORKS + "This code got passed 2"); //Testing
        String term = "";
        String filePathOS = "Notely/src/main/java/notely/app/Notecard/" + fileNAMEWORKS + ".txt";
        String fileMacPath = "./src/main/java/notely/app/Notecard/" + fileNAMEWORKS + ".txt";
        String fileWindowsPath = "../src/main/java/notely/app/Notecard/" + fileNAMEWORKS + ".txt";
        String definition = "";
        String currentLine;
        String title = "";
        String folder = "";
        int priority = 3;
        boolean returnVal2 = false;

        if(new File(fileMacPath).exists()){ // ./  for MACOS and ../ for Windows
            //System.out.print("FileMac");
            filePathOS = fileMacPath;
            returnVal2 = true;
        }
        else if (new File(fileWindowsPath).exists()){
            //System.out.print("FileWindows");
            filePathOS = fileWindowsPath;
            returnVal2 = true;
        } else {
            returnVal2 = false;
        }

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
        return returnVal2;
    }
    @FXML
    public void createSceneDynamic(MouseEvent event) throws IOException {
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

        if (createVbox.getChildren().size() > 1) {
            for (Node node : createVbox.getChildren()) {
                if (node instanceof AnchorPane previousAdded) {
                    numberTermLabel.setLayoutX(previousAdded.getLayoutX() + 60);
                    termField.setLayoutX(previousAdded.getLayoutX() + 70);
                    defField.setLayoutX(previousAdded.getLayoutX() + 390);
                    termField.setLayoutY(termField.getHeight() + termField.getPadding().getTop() + 30);
                    defField.setLayoutY(termField.getHeight() + termField.getPadding().getTop() + 30);
                    numberTermLabel.setLayoutY(termField.getHeight() + termField.getPadding().getTop() + 10);
                    newInsertField.setLayoutY(previousAdded.getLayoutY());
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
        String fileMacPath5 = "./src/main/java/notely/app/Notecard/" + title + ".txt";
        String fileWindowsPath5 = "../src/main/java/notely/app/Notecard/" + title + ".txt";
        String saveImportPath = "Notely/src/main/java/notely/app/Notecard/" + title + ".txt";

        if (new File(fileMacPath5).exists()) { // ./  for MACOS and ../ for Windows
            System.out.println("FileMac");
            saveImportPath = fileMacPath5;
        } else if (new File(fileWindowsPath5).exists()) {
            System.out.println("FileWindows");
            saveImportPath = fileWindowsPath5;
        }

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

}