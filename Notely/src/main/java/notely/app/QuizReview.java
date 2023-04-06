package notely.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class QuizReview {
    QuizReview() {}

    private static ArrayList<NoteCard> AnswerKey = new ArrayList<>();
    private static ArrayList<String> UserAnswerList = new ArrayList<>();

    public static void addAnswer(NoteCard question, String userAnswer) {
        //term -> question displayed in mc quiz
        //definition -> the correct answer to the question
        //priority num -> =1 if the user got the question right, 0 if not
        //userAnswer is self-explanatory
        AnswerKey.add(question);
        UserAnswerList.add(userAnswer);
    }

    public static int getReviewSize() {return UserAnswerList.size();}
    public static void clearReview() {
        AnswerKey.clear();
        UserAnswerList.clear();
    }

    public static String getQuestion(int i) {return AnswerKey.get(i).getTerm();}
    public static String getCorrectAnswer(int i) {return AnswerKey.get(i).getDefinition();}
    public static String getUserAnswer(int i) {return UserAnswerList.get(i);}
    public static boolean getCorrectness(int i) {
        if (AnswerKey.get(i).getPriorityNum() == 1) {return true;}
        return false;
    }

}
