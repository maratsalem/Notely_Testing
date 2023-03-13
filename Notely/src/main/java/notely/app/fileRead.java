package notely.app;

import java.util.*;
import java.io.*;

public class fileRead {

    //Check if empty
    public static void readFromNotecard (String notecardName, boolean randomize) {
        String temp;
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        temp = "src/main/java/notely/app/Notecard/" + notecardName + ".txt";
        try {
            FileInputStream file = new FileInputStream(temp);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                questions.add(scanner.nextLine());
                if (scanner.hasNextLine())
                    answers.add(scanner.nextLine());
            }
            if (questions.size() == 0) {
                System.out.println ("No notes found");
                return;
            }
            System.out.println ();
            if (!randomize)
                readFromList(questions, answers);
            else
                randomlyReadFromList(questions, answers);
            file.close();
        } catch (FileNotFoundException e1) {
            System.out.println("\nSet with title " + notecardName + " does not exist.");
            System.out.println ("Exiting function!");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void readFromList (ArrayList<String> questions, ArrayList<String> answers) {
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i));
            System.out.println(answers.get(i));
        }
    }

    public static void randomlyReadFromList(ArrayList<String> questions, ArrayList<String> answers) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++)
            indices.add(i);
        Collections.shuffle(indices);
        for (int i = 0; i < indices.size(); i++) {
            System.out.println(questions.get(indices.get(i)));
            System.out.println(answers.get(indices.get(i)));
        }
    }

 /*
 check difference 'File' and 'FileInputStream'
 */


}