package notely.app;

import notely.app.NoteCard;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class DataStructure {
        String term;
        String definition;
        int priority;

        Queue<NoteCard> queue1 = new LinkedList<>();
        Queue<NoteCard> queue2 = new LinkedList<>();
        Queue<NoteCard> queue3 = new LinkedList<>();
        NoteCard card = new NoteCard();
        Scanner keyboard = new Scanner(System.in);
public void Add(){
        System.out.println("Enter term: ");
        term = keyboard.nextLine();

        System.out.println("Enter definition: ");
        definition = keyboard.nextLine();

        System.out.println("Enter priority: ");
        priority = keyboard.nextInt();

        card = new NoteCard(term, definition, priority);
        Sort(priority);
}
        public void Sort(int priority){
        if(priority == 3){
                queue1.add(card);
        }
        else if (priority == 2) {
                queue2.add(card);
        }
        else if (priority == 3){
                queue3.add(card);
        }
        else {
                System.out.println("A note card with an unknown priority code: " + priority + " was added to the familar queue\n");
                        queue2.add(card);
        }
        Print();
        }
        public void Print(){
        for(NoteCard Card: queue1){
                System.out.println(Card.getTerm() + " " + Card.getDefinition() + " " + Card.getPriorityNum());
        }
        }
}
