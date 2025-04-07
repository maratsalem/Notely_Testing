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
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class CreateControl {
    @FXML VBox createVbox;
    @FXML ScrollPane scrollPane;
    @FXML AnchorPane firstCreateAPane;
    @FXML AnchorPane termList;
    @FXML
    public TextField titleInputC;
    @FXML
    public TextField folderInputC;
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
    public TextField titleImport;
    @FXML
    public TextField folderImport;
    @FXML
    public TextArea pasteArea;
    @FXML
    Button saveImportButton;
    @FXML
    Button helpButton;
    @FXML
    Label importTitle;
    @FXML ComboBox fontStylesComboBox;
    @FXML CheckBox darkModeCheck;
    static String fontStyle = "System";
    public static boolean darkMode = false;
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

    public void writeToTextFile(ArrayList<String> writeArrayList, String setName, String folderOfSet) {

        String writeFilePath = checkPath(setName);
        if(writeArrayList != null) {
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
                    if (!Objects.equals(writeArrayList.get(i), "") && !Objects.equals(writeArrayList.get(i + 1), "")) {
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

            }
        }
    }


}