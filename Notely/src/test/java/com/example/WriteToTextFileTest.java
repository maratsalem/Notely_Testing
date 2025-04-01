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

    @BeforeEach
    void setUp() throws IOException {
            // Ensure the file path exists
            Path filePath = Path.of("Notely/src/main/java/notely/app/Notecard/testFileName.txt");
            Files.createDirectories(filePath.getParent());

            // Write default values to the file
            List<String> initialLines = List.of(setName, folderName);
            Files.write(filePath, initialLines);
    }

    @AfterEach
    void tearDown() throws IOException, InterruptedException {
        Files.deleteIfExists(Path.of("Notely/src/main/java/notely/app/Notecard/testTitleName.txt"));
        Thread.sleep(100); //for OS issues
    }

    @Test
    void testWriteToTextFile_AppendsCorrectly() throws IOException {
        // Prepare new term-definition pairs
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("NewTerm");
        newEntries.add("NewDefinition");



        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";

        // Read the file contents after ensuring it exists
        List<String> lines = Files.readAllLines(Path.of(testFilePath));

        // Debugging output (print contents to verify correct read)
        System.out.println("Write File test " + lines);

        // Assertions
        assertEquals(3, lines.size()); // Expecting 3 lines: title, folder, and term@def pair
        assertEquals("testFileName", lines.get(0)); // Title should be at line 1
        assertEquals("testFolderName", lines.get(1)); // Folder name should be at line 2
        assertEquals("NewTerm@NewDefinition", lines.get(2)); // Term-definition pair at line 3
    }


    @Test
    void noNullDefinition() throws IOException {

        // Prepare new term-definition pairs
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("NewTerm");
        newEntries.add("");

        writeToTextFile.writeToTextFile(newEntries, "testFileName", "TestFolderName");

        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";

        // Read the file contents
        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        System.out.println("Null def test" + lines);

        // Assertions
        assertEquals("testFileName", lines.get(0)); // Title should be at line 1
        assertEquals("testFolderName", lines.get(1)); // Folder name should be at line 2
        assertEquals(2, lines.size()); // file will not write any information
    }

    @Test
    void noNullTerm() throws IOException {

        // Prepare new term-definition pairs
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("");
        newEntries.add("NewDefinition");


        writeToTextFile.writeToTextFile(newEntries, setName, folderName);
        String testFilePath = "Notely/src/main/java/notely/app/Notecard/testFileName.txt";

        // Read the file contents
        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        System.out.println("Null term test " + lines);

        // Assertions
        assertEquals("testFileName", lines.get(0)); // Title should be at line 1
        assertEquals("testFolderName", lines.get(1)); // Folder name should be at line 2
        assertEquals(2, lines.size()); // file will not write any information
    }

}
