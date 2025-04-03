package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import notely.app.CreateController;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriteToTextFileTest {

    private final CreateController writeToTextFile = new CreateController();
    private final String setName = "testFileName";
    private final String folderName = "testFolderName";
    private final String logFilePath = "Notely/src/main/java/notely/app/Notecard/test_output.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Ensure the file path exists
        Path filePath = Path.of("Notely/src/main/java/notely/app/Notecard/testFileName.txt");
        Files.createDirectories(filePath.getParent());

        // Write default values to the file
        List<String> initialLines = List.of(setName, folderName);
        Files.write(filePath, initialLines);

        // Ensure the log file exists
        Files.createFile(Path.of(logFilePath));
    }

    @AfterEach
    void tearDown() throws IOException, InterruptedException {
        Files.deleteIfExists(Path.of("Notely/src/main/java/notely/app/Notecard/testFileName.txt"));
        Files.deleteIfExists(Path.of(logFilePath));
        Thread.sleep(100); // Prevent OS file handling issues
    }

    private void logTestResult(String testName, List<String> fileContents) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write("Test: " + testName + "\n");
            writer.write("File Contents:\n");
            for (String line : fileContents) {
                writer.write(line + "\n");
            }
            writer.write("------------------------------------------------------\n");
        }
    }

    @Test
    void testWriteToTextFile_AppendsCorrectly() throws IOException {
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("NewTerm");
        newEntries.add("NewDefinition");

        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";
        List<String> lines = Files.readAllLines(Path.of(testFilePath));

        logTestResult("testWriteToTextFile_AppendsCorrectly", lines);

        assertEquals(3, lines.size());
        assertEquals("testFileName", lines.get(0));
        assertEquals("testFolderName", lines.get(1));
        assertEquals("NewTerm@NewDefinition", lines.get(2));
    }

    @Test
    void noNullDefinition() throws IOException {
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("NewTerm");
        newEntries.add("");

        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";
        List<String> lines = Files.readAllLines(Path.of(testFilePath));

        logTestResult("noNullDefinition", lines);

        assertEquals(2, lines.size());
    }

    @Test
    void noNullTerm() throws IOException {
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("");
        newEntries.add("NewDefinition");

        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";
        List<String> lines = Files.readAllLines(Path.of(testFilePath));

        logTestResult("noNullTerm", lines);

        assertEquals(2, lines.size());
    }

    @Test
    void testSpecialCharacters() throws IOException {
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("Term@Special");
        newEntries.add("Definition\nWithNewLine");

        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";
        List<String> lines = Files.readAllLines(Path.of(testFilePath));

        logTestResult("testSpecialCharacters", lines);

        assertEquals(4, lines.size());
    }

    @Test
    void testWhitespaceHandling() throws IOException {
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("   SpacedTerm   ");
        newEntries.add("   SpacedDefinition   ");

        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";
        List<String> lines = Files.readAllLines(Path.of(testFilePath));

        logTestResult("testWhitespaceHandling", lines);

        assertEquals(3, lines.size());
        assertEquals("   SpacedTerm   @   SpacedDefinition   ", lines.get(2));
    }
}
