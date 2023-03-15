package notely.app;

import java.util.*;
import java.io.*;

public class fileRead {
    private static ArrayList<Integer> indicesOfSet = new ArrayList<>();
    private static ArrayList<String> questions = new ArrayList<>();
    private static ArrayList<String> answers = new ArrayList<>();
    public static void randomlyReadFromList() {
        for (int i = 0; i < questions.size(); i++)
            indicesOfSet.add(i);
        Collections.shuffle(indicesOfSet);
        traverseSet();
    }
    public static void traverseSet(){
        for (int i = 0; i < indicesOfSet.size(); i++) {
            System.out.println(questions.get(indicesOfSet.get(i)));
            System.out.println(answers.get(indicesOfSet.get(i)));
        }
    }
    public static void readFromNotecard (String notecardName, boolean randomize) {
        String temp;
        temp = "Notely/src/main/java/notely/app/Notecard/" + notecardName + ".txt";
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

            randomlyReadFromList();
            //randomlyReadFromList(questions, answers);

            file.close();
        } catch (FileNotFoundException e1) {
            System.out.println("\nSet with title " + notecardName + " does not exist.");
            System.out.println ("Exiting function!");
        } catch (IOException e2) {
            e2.printStackTrace();
        }

    }


}