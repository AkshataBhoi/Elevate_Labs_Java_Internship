
import java.util.Scanner;

class Questions {

    private String questionText;
    private String[] options;
    private String correctAnswer;

    public Questions(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class QuizFunctions {

    private Questions[] questions;
    private String[] userAnswers;
    private int score = 0;

    public QuizFunctions(Questions[] questions) {
        this.questions = questions;
        this.userAnswers = new String[questions.length];
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Online Quiz!");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQ." + (i + 1) + ":" + questions[i].getQuestionText());
            String options[] = questions[i].getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }
            System.out.print("Enter your answer (option number): ");
            String input = sc.nextLine();
            int answerIndex;
            try {
                answerIndex = Integer.parseInt(input);
                if (answerIndex < 1 || answerIndex > options.length) {
                    System.out.println("Invalid Input");
                    userAnswers[i] = "";
                } else {
                    userAnswers[i] = options[answerIndex - 1];
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
                userAnswers[i] = "";
            }
        }
    }

    public void submitAnswer(int questionIndex, String answer) {
        if (questionIndex >= 0 && questionIndex < userAnswers.length) {
            userAnswers[questionIndex] = answer;
        }
    }

    public void showResults() {
        score = 0;
        for (int i = 0; i < questions.length; i++) {
            if (questions[i].getCorrectAnswer().equalsIgnoreCase(userAnswers[i])) {
                score++;
            }
        }
        System.out.println("Quiz Completed!!!");
        System.out.println("Your Score is: " + score + " out of " + questions.length);
    }

}

public class QuizApp {

    public static void main(String[] args) {

        Questions[] questions = new Questions[]{
            new Questions("Which method is the entry point of a Java program?",
            new String[]{"start()", "main()", "run()", "init()"}, "main()"),
            new Questions("Which of these is NOT a valid access modifier in Java?",
            new String[]{"public", "private", "protected", "package"}, "package"),
            new Questions("Which keyword is used to inherit a class in Java?",
            new String[]{"implement", "extends", "inherits", "instanceof"}, "extends"),
            new Questions("What is the default value of a boolean variable in Java?",
            new String[]{"true", "false", "0", "null"}, "false"),
            new Questions("Which of these is a wrapper class for primitive type int?",
            new String[]{"Integer", "Int", "Number", "Long"}, "Integer")
        };

        QuizFunctions quiz = new QuizFunctions(questions);

        quiz.start();
        quiz.showResults();
    }
}
