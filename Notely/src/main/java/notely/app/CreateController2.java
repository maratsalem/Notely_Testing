package notely.app;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.*;
import java.util.*;

public class CreateController2 {
    @FXML
    public TextField titleInputC;
    @FXML
    public TextField folderInputC;
    @FXML
    public TextField titleImport;
    @FXML
    public TextField folderImport;
    @FXML
    public TextArea pasteArea;

    static String fontStyle = "System";
    public static boolean darkMode = false;
    private String themeSelect = "normalTheme.css";
    private Stage stage;
    private Scene scene;
    private Parent root;

    public String checkPath(String checkingPathString) {
        String fileMacPath = "./src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";
        String fileWindowsPath = "../src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";
        String checkPathString = "Notely/src/main/java/notely/app/Notecard/" + checkingPathString + ".txt";

        if (new File(fileMacPath).exists()) {
            checkPathString = fileMacPath;
        } else if (new File(fileWindowsPath).exists()) {
            checkPathString = fileWindowsPath;
        }
        return checkPathString;
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

                if (titleInputC != null && !titleInputC.getText().equals("") && !folderInputC.getText().equals("")) {
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
        if (writeArrayList != null) {
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
                    if (!Objects.equals(writeArrayList.get(i), "") && !Objects.equals(writeArrayList.get(i + 1), "")) {
                        data.add(writeArrayList.get(i) + "@" + writeArrayList.get(i + 1));
                    }
                }

                for (int i = 0; i < data.size(); i++)
                    writer.write(data.get(i) + "\n");
                writer.close();
            } catch (FileNotFoundException ignored) {
            }
        }
    }

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
            }
        }
        if (folder.isEmpty()) {
            folderImport.setPromptText("You must enter a folder name.");
            folderCheck = 1;
        }

        if (text.isEmpty() || text.isBlank()) {
            pasteArea.setText("");
            pasteArea.setPromptText("You must enter text.");
            textCheck = 1;
        } else {
            StringTokenizer lines = new StringTokenizer(pasteArea.getText(), "\n");
            ArrayList<String> textArea = new ArrayList<>();
            while (lines.hasMoreTokens()) {
                String token = lines.nextToken();
                textArea.add(token);
            }
            for (String entry : textArea) {
                StringTokenizer tokens = new StringTokenizer(entry, "@");
                if (!entry.contains("@") || tokens.countTokens() != 2) {
                    pasteArea.setPromptText("Invalid format. Each line must have one '@'.");
                    pasteArea.setText("");
                    textCheck = 1;
                    break;
                }
            }

            if (titleCheck == 0 && folderCheck == 0 && textCheck == 0) {
                writeToSetNameFolder(title);
                ArrayList<String> data = new ArrayList<>();
                data.add(title);
                data.add(folder);
                data.addAll(textArea);

                File file = new File(saveImportPath);
                FileOutputStream fileWriting = new FileOutputStream(file);
                PrintWriter writer = new PrintWriter(fileWriting, true);

                for (String entry : data) {
                    writer.write(entry + "\n");
                }
                writer.close();
            }
        }
    }
}
