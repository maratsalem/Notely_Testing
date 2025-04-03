package com.example;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import notely.app.CreateController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateControllerImportTest {

    private CreateController controller;
    private final String testSetName = "testImportSet";
    private final String testFolderName = "testImportFolder";
    private final String testFilePath = "Notely/src/main/java/notely/app/Notecard/testImportSet.txt";

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
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller = new CreateController();
            controller.titleImport = new TextField();
            controller.folderImport = new TextField();
            controller.pasteArea = new TextArea();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @AfterEach
    void tearDown() throws IOException {
        try {
            Files.deleteIfExists(Path.of(testFilePath));
        } catch (IOException e) {
            System.err.println("Warning: Could not delete test file: " + e.getMessage());
        }
    }

    @Test
    void testSaveImport_ExistingSetName() throws InterruptedException, IOException {
        // Create test file first
        Files.write(Path.of(testFilePath), "existing content".getBytes());

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.titleImport.setText(testSetName);
            controller.folderImport.setText(testFolderName);
            controller.pasteArea.setText("term1@definition1");

            try {
                controller.saveImport(null);
                latch.countDown();
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals("A set with the name: " + testSetName + " already exists.",
                controller.titleImport.getPromptText());
    }

    @Test
    void testSaveImport_InvalidFormat() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.titleImport.setText(testSetName);
            controller.folderImport.setText(testFolderName);
            controller.pasteArea.setText("term1definition1"); // Missing @

            try {
                controller.saveImport(null);
                latch.countDown();
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals("There is a line that is missing the @.", controller.pasteArea.getPromptText());
    }

    @Test
    void testSaveImport_EmptyFields() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.titleImport.setText("");
            controller.folderImport.setText("");
            controller.pasteArea.setText("");

            try {
                controller.saveImport(null);
                latch.countDown();
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals("You must enter a set name.", controller.titleImport.getPromptText());
        assertEquals("You must enter a folder name.", controller.folderImport.getPromptText());
        assertEquals("You must enter text.", controller.pasteArea.getPromptText());
    }
}