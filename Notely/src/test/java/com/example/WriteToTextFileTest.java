package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import notely.app.CreateController;
import notely.app.CreateController2;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class WriteToTextFileTest {

    private final CreateController2 writeToTextFile = new CreateController2();
    private final String setName = "testFileName";
    private final String folderName = "testFolderName";
    private final Path testFilePath = Path.of("Notely/src/main/java/notely/app/Notecard/testFileName.txt");

    @BeforeEach
    void setUp() throws IOException {
        // Ensure the directory exists
        Files.createDirectories(testFilePath.getParent());

        // Write default values to the file
        List<String> initialLines = List.of(setName, folderName);
        Files.write(testFilePath, initialLines);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void testWriteToTextFile_AppendsCorrectly() throws IOException {
        // Arrange
        ArrayList<String> newEntries = new ArrayList<>();
        newEntries.add("NewTerm");
        newEntries.add("NewDefinition");

        // Act
        writeToTextFile.writeToTextFile(newEntries, setName, folderName);

        // Assert
        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(3, lines.size(), "File should have original 2 lines plus new entry");
        assertEquals(setName, lines.get(0), "First line should remain unchanged");
        assertEquals(folderName, lines.get(1), "Second line should remain unchanged");
        assertEquals("NewTerm@NewDefinition", lines.get(2), "New entry should be properly formatted");
    }

    @Test
    void testWriteToTextFile_EmptyTermOrDefinition_ShouldNotAppend() throws IOException {
        // Test empty definition
        ArrayList<String> emptyDefEntries = new ArrayList<>();
        emptyDefEntries.add("TermOnly");
        emptyDefEntries.add("");
        writeToTextFile.writeToTextFile(emptyDefEntries, setName, folderName);
        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(2, lines.size(), "Should not append when definition is empty");

        // Test empty term
        ArrayList<String> emptyTermEntries = new ArrayList<>();
        emptyTermEntries.add("");
        emptyTermEntries.add("DefinitionOnly");
        writeToTextFile.writeToTextFile(emptyTermEntries, setName, folderName);
        lines = Files.readAllLines(testFilePath);
        assertEquals(2, lines.size(), "Should not append when term is empty");

        // Test both empty
        ArrayList<String> bothEmptyEntries = new ArrayList<>();
        bothEmptyEntries.add("");
        bothEmptyEntries.add("");
        writeToTextFile.writeToTextFile(bothEmptyEntries, setName, folderName);
        lines = Files.readAllLines(testFilePath);
        assertEquals(2, lines.size(), "Should not append when both term and definition are empty");
    }

    @Test
    void testWriteToTextFile_MultipleEntries() throws IOException {
        // Arrange
        ArrayList<String> multipleEntries = new ArrayList<>();
        multipleEntries.add("Term1");
        multipleEntries.add("Definition1");
        multipleEntries.add("Term2");
        multipleEntries.add("Definition2");

        // Act
        writeToTextFile.writeToTextFile(multipleEntries, setName, folderName);

        // Assert
        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(4, lines.size(), "Should append multiple entries correctly");
        assertEquals("Term1@Definition1", lines.get(2), "First entry should be correct");
        assertEquals("Term2@Definition2", lines.get(3), "Second entry should be correct");
        System.out.println("Multiple test" + lines);
    }

    @Test
    void testWriteToTextFile_SpecialCharacters() throws IOException {
        // Arrange
        ArrayList<String> specialEntries = new ArrayList<>();
        specialEntries.add("Term@WithAt");
        specialEntries.add("Definition");
        specialEntries.add("Term");
        specialEntries.add("Defi@nition");

        // Act
        writeToTextFile.writeToTextFile(specialEntries, setName, folderName);

        // Assert
        List<String> lines = Files.readAllLines(testFilePath);
        assertEquals(4, lines.size(), "Should handle special characters");
        assertEquals("Term@WithAt@Definition", lines.get(2), "Should handle @ in term");
        assertEquals("Term@Defi@nition", lines.get(3), "Should handle @ in definition");
    }


    @Test
    void testWriteToTextFile_NewlinesInDefinition() throws IOException {
        // Arrange
        ArrayList<String> newlineEntries = new ArrayList<>();
        newlineEntries.add("Term");
        newlineEntries.add("Line1\nLine2");

        // Act
        writeToTextFile.writeToTextFile(newlineEntries, setName, folderName);

        // Assert
        String content = Files.readString(testFilePath);
        assertTrue(content.contains("Term@Line1\nLine2"), "Should preserve newlines in definition");
    }

    @Test
    void testWriteToTextFile_EmptyInputList() throws IOException {
        // Arrange
        ArrayList<String> emptyList = new ArrayList<>();
        List<String> originalContent = Files.readAllLines(testFilePath);

        // Act
        writeToTextFile.writeToTextFile(emptyList, setName, folderName);

        // Assert
        List<String> newContent = Files.readAllLines(testFilePath);
        assertEquals(originalContent, newContent, "File should remain unchanged with empty input");
    }

    @Test
    void testWriteToTextFile_NullInput() throws IOException {
        // Arrange
        List<String> originalContent = Files.readAllLines(testFilePath);

        // Act
        writeToTextFile.writeToTextFile(null, setName, folderName);

        // Assert
        List<String> newContent = Files.readAllLines(testFilePath);
        assertEquals(originalContent, newContent, "File should remain unchanged with null input");
    }

    @Test
    void testWriteToTextFile_FileNotFound() {
        // Arrange
        String nonExistentSet = "nonExistentFile";
        ArrayList<String> entries = new ArrayList<>();
        entries.add("Term");
        entries.add("Definition");

        // Act & Assert
        assertDoesNotThrow(() -> writeToTextFile.writeToTextFile(entries, nonExistentSet, folderName),
                "Should handle FileNotFoundException gracefully");
    }
}