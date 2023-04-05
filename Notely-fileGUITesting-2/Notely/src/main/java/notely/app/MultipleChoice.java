package notely.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MultipleChoice {
    private static ArrayList<NoteCard> NoteCardList = new ArrayList<>();
    private static ArrayList<NoteCard> bufferList = new ArrayList<>();
    private static ArrayList<String> answerList = new ArrayList<>();
    private static String question = "";
    private static int questionindex = 0;
    private static ArrayList<Integer> questionType = new ArrayList<>(); //determines whether the answer type should be terms (0) or definitions (1)
    private static boolean QuizComplete = false;

    MultipleChoice() {}
    public static void generateNoteCardList() {
        //placeholder for filereading
        NoteCardList.clear();
        questionType.clear();
        NoteCard n1 = new NoteCard("t1", "d1", 3, 0);
        NoteCard n2 = new NoteCard("t2", "d2", 3, 0);
        NoteCard n3 = new NoteCard("t3", "d3", 3, 0);
        NoteCard n4 = new NoteCard("t4", "d4", 3, 0);
        NoteCard n5 = new NoteCard("t5", "d5", 3, 0);
        NoteCardList.add(n1);
        NoteCardList.add(n2);
        NoteCardList.add(n3);
        NoteCardList.add(n4);
        NoteCardList.add(n5);
        shuffleList();
    }
    public static void addNoteCard(NoteCard notecard) {
        NoteCardList.add(notecard);
    }

    public static void shuffleList() {          //fill bufferList with random index numbers of notecardlist, copy it back
        Random rnd = new Random();
        int QuizSize = NoteCardList.size();

        for(int i = 0; i < QuizSize; i++){
            int randindex = rnd.nextInt(NoteCardList.size());
//            System.out.println(randindex);
            NoteCard dummy = NoteCardList.get(randindex);
            NoteCardList.remove(randindex);
            bufferList.add(dummy);
            //System.out.println(randomizedList.get(i).getTerm());

            questionType.add(rnd.nextInt(2));               //randomizes question type
        }
        NoteCardList.clear();
        for (int i = 0; i < QuizSize; i++) {NoteCardList.add(bufferList.get(i));}
        bufferList.clear();
    }

    public static String getQuestion() {
        if (questionType.get(questionindex) == 0) {
            //when question type is 0, you have to pick from 4 terms
            System.out.println("question type: definition");
            return NoteCardList.get(questionindex).getDefinition();
        }
        else {
            //when question type is 1, you have to pick from 4 definitions
            System.out.println("question type: term");
            return NoteCardList.get(questionindex).getTerm();
        }
    }
    public static void setAnswerList() {
        answerList.clear();
        bufferList.clear();
        Random rnd = new Random();
        int QuizSize = NoteCardList.size();
        for (int i = 0; i < QuizSize; i++) {bufferList.add(NoteCardList.get(i));}

        //the answer is always going to be at index 0
        if (questionType.get(questionindex) == 0) {
            //when question type is 0, you have to pick from 4 terms
            answerList.add(bufferList.get(questionindex).getTerm());
        }
        else {
            //when question type is 1, you have to pick from 4 definitions
            answerList.add(bufferList.get(questionindex).getDefinition());
        }

        bufferList.remove(questionindex);

        for(int i = 0; i < 3; i++){
            //pulls three random words from the notecard list
            int randindex = rnd.nextInt(bufferList.size());
            NoteCard dummy = bufferList.get(randindex);
            bufferList.remove(randindex);

            if (questionType.get(questionindex) == 0) {
                //when question type is 0, you have to pick from 4 terms
                answerList.add(dummy.getTerm());
            }
            else {
                //when question type is 1, you have to pick from 4 definitions
                answerList.add(dummy.getDefinition());
            }
        }
    }

    public static boolean answer(String userAnswer) {
        //more front-end focused
        if ((questionType.get(questionindex) == 0) && (userAnswer.compareTo(NoteCardList.get(questionindex).getTerm()) == 0)) {
            return true;
        }
        if ((questionType.get(questionindex) == 1) && (userAnswer.compareTo(NoteCardList.get(questionindex).getDefinition()) == 0)) {
            return true;
        }
        return false;
    }
    public static int getQuestionIndex() {return questionindex;}
    public static void nextQuestion() {
        questionindex++;
        if (questionindex >= NoteCardList.size()) {endQuiz();}
    }

    private static void endQuiz(){
        QuizComplete = true;
        questionType.clear();
        questionindex = 0;
        answerList.clear();
        bufferList.clear();
        NoteCardList.clear();
    }

    public static void Quiz() {
        Scanner scanner = new Scanner(System.in);
//        generateNoteCardList();
        shuffleList();
        QuizComplete = false;
        System.out.println(getQuestion());
        setAnswerList();
        System.out.println(answerList.get(0) + "  " + answerList.get(1) + "  " + answerList.get(2) + "  " + answerList.get(3));
        String userAnswer = scanner.nextLine();;
        if (answer(userAnswer)) {nextQuestion();}
        while (!QuizComplete && userAnswer.compareTo("@") != 0) {
            System.out.println(getQuestion());
            setAnswerList();
            System.out.println(answerList.get(0) + "  " + answerList.get(1) + "  " + answerList.get(2) + "  " + answerList.get(3));
            userAnswer = scanner.nextLine();
            if (answer(userAnswer)) {nextQuestion();}
        }


    }



//    public static void main(String[] args){
//        Quiz();
//    }

}