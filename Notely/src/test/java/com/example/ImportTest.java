package com.example;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.*;
import notely.app.CreateController;
import notely.app.CreateController2;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ImportTest {

    private CreateController2 controller;
    private final String testSetName = "testImportSet";
    private final String testFolderName = "testImportFolder";
    private final Path testFile = Path.of("Notely/src/main/java/notely/app/Notecard/testImportSet.txt");

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller = new CreateController2();
            controller.titleImport = new TextField();
            controller.folderImport = new TextField();
            controller.pasteArea = new TextArea();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteTestFileIfExists();
    }

    private void deleteTestFileIfExists() throws IOException {
        try {
            Files.deleteIfExists(testFile);
        } catch (IOException e) {
            System.err.println("Warning: Could not delete test file: " + e.getMessage());
        }
    }

    private void assertFileUnchanged(String expectedContent) throws IOException {
        assertTrue(Files.exists(testFile), "Expected file to exist.");
        assertEquals(expectedContent, Files.readString(testFile), "File content mismatch.");
    }

    @Test
    void testSaveImport_ExistingSetName() throws InterruptedException, IOException {
        String existingContent = "existing content";
        Files.write(testFile, existingContent.getBytes());

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.titleImport.setText(testSetName);
            controller.folderImport.setText(testFolderName);
            controller.pasteArea.setText("term1@definition1");

            try {
                controller.saveImport(null);
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        assertEquals("A set with the name: " + testSetName + " already exists.",
                controller.titleImport.getPromptText());

        assertFileUnchanged(existingContent);
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
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        assertEquals("Invalid format. Each line must have one '@'.", controller.pasteArea.getPromptText());
        assertEquals(testSetName, controller.titleImport.getText());
        assertEquals(testFolderName, controller.folderImport.getText());
        assertFalse(Files.exists(testFile));
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
            } catch (IOException e) {
                fail("IOException occurred: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS));

        assertEquals("You must enter a set name.", controller.titleImport.getPromptText());
        assertEquals("You must enter a folder name.", controller.folderImport.getPromptText());
        assertEquals("You must enter text.", controller.pasteArea.getPromptText());

        assertEquals("", controller.titleImport.getText());
        assertEquals("", controller.folderImport.getText());
        assertEquals("", controller.pasteArea.getText());
        assertFalse(Files.exists(testFile));
    }
}
