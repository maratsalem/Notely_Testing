package com.example;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.*;
import notely.app.CreateController;
import notely.app.CreateController2;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CreateSetTest {

    private CreateController2 CreateController;
    private final String testSetName = "testSet";
    private final String testFolderName = "testFolder";
    private final String testFilePath = "Notely/src/main/java/notely/app/Notecard/testSet.txt";

    @BeforeAll
    public static void initJFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout waiting for JavaFX initialization");
        }
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CreateController = new CreateController2();

        // Initialize JavaFX components
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            CreateController.titleInputC = new TextField();
            CreateController.folderInputC = new TextField();
            CreateController.titleInputC.setText(testSetName);
            CreateController.folderInputC.setText(testFolderName);
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout waiting for JavaFX component setup");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        // Cleanup test file
        try {
            Files.deleteIfExists(Path.of(testFilePath));
        } catch (IOException e) {
            System.err.println("Warning: Could not delete test file: " + e.getMessage());
        }
    }

    @Test
    void testCreateSet_Success() throws IOException, InterruptedException {
        File testFile = new File(testFilePath);
        assertFalse(testFile.exists()); // Ensure file does not exist initially

        CountDownLatch testLatch = new CountDownLatch(1);
        boolean[] result = new boolean[1];

        Platform.runLater(() -> {
            try {
                result[0] = CreateController.createSet(testSetName, testFolderName);
                testLatch.countDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assertTrue(testLatch.await(5, TimeUnit.SECONDS), "Test timed out");
        assertTrue(result[0]);
        assertTrue(testFile.exists()); // File should be created

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(2, lines.size());
        assertEquals(testSetName, lines.get(0)); // Title should be on the first line
        assertEquals(testFolderName, lines.get(1)); // Folder name should be on the second line
    }

    @Test
    void testCreateSet_AlreadyExists() throws IOException, InterruptedException {
        File testFile = new File(testFilePath);
        testFile.createNewFile(); // Simulate existing file

        CountDownLatch testLatch = new CountDownLatch(1);
        boolean[] result = new boolean[1];

        Platform.runLater(() -> {
            try {
                result[0] = CreateController.createSet(testSetName, testFolderName);
                testLatch.countDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assertTrue(testLatch.await(5, TimeUnit.SECONDS), "Test timed out");
        assertFalse(result[0]);
    }

    @Test
    void testCreateSet_InvalidInputs() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        boolean[] result = new boolean[1];

        Platform.runLater(() -> {
            CreateController.titleInputC.setText("");
            CreateController.folderInputC.setText("");
            try {
                result[0] = CreateController.createSet("", ""); // Empty title and folder
                latch.countDown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Test timed out");
        assertFalse(result[0]);
    }
}