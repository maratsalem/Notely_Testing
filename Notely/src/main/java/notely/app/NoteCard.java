package notely.app;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class NoteCard {
    private String term;
    private String definition;
    private int priorityNum;

    public NoteCard(){
        term = "";
        definition = "";
        priorityNum = 0;
    }
    public NoteCard(String term, String definition, int priorityNum){
        this.term = term;
        this.definition = definition;
        this.priorityNum = priorityNum;
    }
    public void setTerm(String term){
        this.term = term;
    }
    public void setDefinition(String definition){
        this.definition = definition;
    }
    public void setPriorityNum(int priorityNum){
        this.priorityNum = priorityNum;
    }
    public String getTerm(){
        return term;
    }
    public String getDefinition(){
        return definition;
    }
    public int getPriorityNum(){
        return priorityNum;
    }
    public void writeQuestion (String title, String term, String definition) {
        String fileName = "Notely/src/main/java/notely/app/Notecard/" + title + ".txt";
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

            data.add(term);
            data.add(definition);

            for (int i = 0; i < data.size(); i++)
                writer.write(data.get(i) + "\n");
            writer.close();

        } catch (FileNotFoundException e1) {
            System.out.printf ("\nSet with title \"%s\" does not exist.\n", title);
            System.out.println ("Exiting function!");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    //for notecard display:
    // 1: need to open correct set and then traverse through it
    // 2: need to have an on event action for "previous" and "next" buttons so that
    //      readFromNotecard AND randomlyRead are not being run every time one is pressed
    //      new method: on event action would get the next (or prev) term/definition in the list
    //      that has already been randomized,
    //      potential solution: select a "set" through the GUI, then on selection read and randommize notecards.
    //      then, take randomized array list and store in a new method that allows for traversal of the
    //      index term/def using GUI buttons
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
        temp = "src/main/java/notely/app/Notecard/" + notecardName + ".txt";
        try {
            FileInputStream file = new FileInputStream(temp);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                questions.add(scanner.nextLine());
                if (scanner.hasNextLine())
                    answers.add(scanner.nextLine());
            }
            System.out.println(questions);
            System.out.println(answers);

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
