package notely.app.fileOperator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class fileWrite {

    public static void createNotecard (String title) {
        String fileName = "src/main/java/notely.app/fileOperator/Notecard" + title + ".txt";
        try {
            File file = new File(fileName);
            if (file.createNewFile())
                System.out.printf("\nNotecard created with title \"%s\"\n", title);
            else
                System.out.println("\nNotecard already exists");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeQuestion (String title) {
        String fileName = "src/Notecard/" + title + ".txt";
        Scanner scanner = new Scanner(System.in);
        try {

            FileInputStream fileReading = new FileInputStream (fileName);
            Scanner reader = new Scanner(fileReading);
            ArrayList<String> data = new ArrayList<>();
            while (reader.hasNextLine())
                data.add(reader.nextLine());
            reader.close();

            FileOutputStream fileWriting = new FileOutputStream(fileName);
            PrintWriter writer = new PrintWriter(fileWriting, true);
            String loopAnswer = "y";
            while (loopAnswer.equals("y") || loopAnswer.equals("Y")) {
                System.out.print ("\nEnter your question: ");
                data.add(scanner.nextLine());
                System.out.print ("Enter your options: ");
                data.add(scanner.nextLine());
                System.out.print ("Do you want to enter another question? Y/n: ");
                loopAnswer = scanner.nextLine();
            }
            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();

        } catch (FileNotFoundException e1) {
            System.out.printf ("\nNotecard with title \"%s\" does not exist.\n", title);
            System.out.print ("Would you like to create a notecard first? y/n: ");
            String createAnswer = scanner.nextLine();
            if (createAnswer.equals("y") || createAnswer.equals("Y")) {
                createNotecard(title);
                writeQuestion(title);
            }
            else {
                System.out.println ("Exiting function!");
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void deleteQuestion (String title) {
        String fileName = "src/Notecard/" + title + ".txt";
        Scanner scanner = new Scanner(System.in);
        try {

            FileInputStream fileReading = new FileInputStream (fileName);
            Scanner reader = new Scanner(fileReading);
            ArrayList<String> data = new ArrayList<>();
            while (reader.hasNextLine())
                data.add(reader.nextLine());
            reader.close();

            if (data.size() == 0) {
                System.out.println ("\nNo notes found to delete.");
                return;
            }

            FileOutputStream fileWriting = new FileOutputStream(fileName);
            PrintWriter writer = new PrintWriter(fileWriting, true);

            int input = -999;
            System.out.print("\nEnter question number: ");
            input = scanner.nextInt();
            while (!(input >= 1 && input <= (data.size()/2))) {
                System.out.print ("Please enter a valid number.");
                System.out.println("\nEnter question number: ");
                input = scanner.nextInt();
            }
            for (int i = 0; i < data.size(); i++) {
                if (i != (2 * input - 2) && i != (2 * input - 1))
                    writer.write(data.get(i) + "\n");
            }
            writer.close();

        } catch (FileNotFoundException e1) {
            System.out.printf ("\nNotecard with title \"%s\" does not exist.\n", title);
            System.out.print ("Would you like to create a notecard first? y/n: ");
            String createAnswer = scanner.nextLine();
            if (createAnswer.equals("y") || createAnswer.equals("Y")) {
                createNotecard(title);
                writeQuestion(title);
            }
            else {
                System.out.println ("Exiting function!");
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

/*    public void testingWrite() {
        try {
            FileInputStream input = new FileInputStream("test1.txt");
            Scanner scanner = new Scanner(input);
            String data = "";
            while (scanner.hasNextLine())
                data += scanner.nextLine() + "\n";
            scanner.close();

            FileOutputStream file = new FileOutputStream("test1.txt");
            PrintWriter writer = new PrintWriter(file);
            String[] substrings = data.split("\n");
            for (int i = 0; i < substrings.length; i++) {
                if (i == 1)
                    substrings[1] = "5678";
                writer.write(substrings[i] + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    } */

}