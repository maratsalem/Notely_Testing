package com.example;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import notely.app.CreateController;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CreateSetTest {

    private CreateController createController;
    private final String testSetName = "testSet";
    private final String testFolderName = "testFolder";
    private final String testFilePath = "Notely/src/main/java/notely/app/Notecard/testSet.txt";

    @BeforeEach
    void setUp() throws InterruptedException {
        MockitoAnnotations.openMocks(this);
        createController = new CreateController();

        // Initialize JavaFX components
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            createController.titleInputC = new TextField();
            createController.folderInputC = new TextField();
            createController.titleInputC.setText(testSetName);
            createController.folderInputC.setText(testFolderName);
            latch.countDown();
        });
        latch.await(3, TimeUnit.SECONDS); // Wait for JavaFX thread to complete
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(testFilePath)); // Cleanup test file
    }

    @Test
    void testCreateSet_Success() throws IOException {
        File testFile = new File(testFilePath);
        assertFalse(testFile.exists()); // Ensure file does not exist initially

        boolean result = createController.createSet(testSetName, testFolderName);

        assertTrue(result);
        assertTrue(testFile.exists()); // File should be created
        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(2, lines.size());
        assertEquals(testSetName, lines.get(0)); // Title should be on the first line
        assertEquals(testFolderName, lines.get(1)); // Folder name should be on the second line
    }

    @Test
    void testCreateSet_AlreadyExists() throws IOException {
        File testFile = new File(testFilePath);
        testFile.createNewFile(); // Simulate existing file

        boolean result = createController.createSet(testSetName, testFolderName);

        assertFalse(result);
    }

    @Test
    void testCreateSet_InvalidInputs() throws IOException {
        Platform.runLater(() -> {
            createController.titleInputC.setText("");
            createController.folderInputC.setText("");
        });

        boolean result = createController.createSet("", ""); // Empty title and folder
        assertFalse(result);
    }
}