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

    //DELETE QUESTION HAS NOT BEEN INTEGRATED YET!!!
    public static void deleteQuestion (String title) {
        String fileName = "Notely/src/main/java/notely/app/Notecard/" + title + ".txt";
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
            System.out.printf("\nSet with title \"%s\" does not exist.\n", title);
            System.out.println ("Exiting function!");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    //manually editing the text file will cause index errors

}
