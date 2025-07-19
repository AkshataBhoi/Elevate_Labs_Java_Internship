
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private Stage primaryStage;
    private String currentUsername;
    private String css;
    private TextField nameField;

    // for quizQuestions and options
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private List<Question> q;
    private ToggleGroup optionsGroup;
    private Label questionLabel;
    private List<RadioButton> optionButtons;
    private Label timerLabel = new Label("Time: 00:00");
    private long startTime;
    private Map<Integer, String> userAnswers = new HashMap<>();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        stage.setTitle("Online JavaFx Quiz App");

        //Load Css File 
        css = App.class.getResource("/styles.css").toExternalForm();

        showPage1();
        stage.show();
    }

    // Page 1: Welcome Page
    private void showPage1() {
        Label welcomeMsg = new Label("Welcome To Online Java Quiz");
        welcomeMsg.getStyleClass().add("label");
        Label LoginMsg = new Label("Click To Start!!!");
        LoginMsg.getStyleClass().add("label");

        Button getStarted = new Button("Get Started!");
        getStarted.getStyleClass().add("button");

        getStarted.setOnAction(e -> showPage2());

        VBox layout1 = new VBox(20);
        // layout1.setStyle("-fx-alignment: center; -fx-padding: 30;-fx-background-color: skyblue;");
        layout1.getStyleClass().add("vbox");
        layout1.getChildren().addAll(welcomeMsg, LoginMsg, getStarted);

        Scene scene1 = new Scene(layout1, 600, 500);
        scene1.getStylesheets().add(css);
        primaryStage.setScene(scene1);

    }

    // Page 2: Form Page
    private void showPage2() {
        Label nameLabel = new Label("Name:");
        nameLabel.getStyleClass().add("label");

        nameField = new TextField("admin123");
        nameField.getStyleClass().add("text-field");

        HBox nameRow = new HBox(15); // spacing between label and field
        nameRow.getChildren().addAll(nameLabel, nameField);
        nameRow.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label");

        PasswordField passwordField = new PasswordField();
        passwordField.setText("123456");
        passwordField.getStyleClass().add("password-field");

        HBox passwordBox = new HBox(5);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        Button login = new Button("Login");
        login.getStyleClass().add("button");
        login.setOnAction(e -> {
            // Check first before doing anything else
            if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || (!nameField.getText().equals("admin123") || !passwordField.getText().equals("123456"))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Validation");
                alert.setHeaderText(null);
                alert.setContentText("Please enter both name and password.");
                alert.showAndWait();
                return; // stop execution if validation fails
            }

            // If validation passes, proceed
            currentUsername = nameField.getText();
            try {
                QuizDAO dao = new QuizDAO();
                questions = dao.getRandomQuestions(DBConnection.getConnection());
                showQuizPage(questions);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            showPage3(); // optionally remove this if showQuizPage() already transitions
        });

        VBox layout2 = new VBox(20); // spacing between rows
        layout2.getStyleClass().add("vbox");
        layout2.getChildren().addAll(nameRow, passwordBox, login);

        Scene scene2 = new Scene(layout2, 600, 500);
        scene2.getStylesheets().add(css);
        primaryStage.setScene(scene2);
    }

    //  Page 3: Start Quiz
    private void showPage3() {
        Label welcomeUser = new Label("Welcome " + currentUsername + "!");
        welcomeUser.getStyleClass().add("label");

        Button quizStart = new Button("Quiz Start");
        quizStart.setOnAction(e -> {
            currentUsername = nameField.getText(); // Save username
            try {
                QuizDAO dao = new QuizDAO();  // DAO must be correctly imported
                List<Question> questions = dao.getRandomQuestions(DBConnection.getConnection());
                showQuizPage(questions);      // Pass questions after fetching
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        VBox layout3 = new VBox(15);
        // layout2.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-border-width: 2px;");
        layout3.getStyleClass().add("vbox");
        layout3.getChildren().addAll(welcomeUser, quizStart);

        Scene scene3 = new Scene(layout3, 600, 500);
        scene3.getStylesheets().add(css);
        primaryStage.setScene(scene3);
    }

    // Page 4 : Dynamic Quiz Questions
    private void showQuizPage(List<Question> quizQuestions) {
        this.questions = quizQuestions;
        startTime = System.currentTimeMillis();
        userAnswers.clear(); // Clear previous answers for new quiz
        currentQuestionIndex = 0;

        // Header with Timer 
        Label quizHeader = new Label("Java Quiz");
        quizHeader.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        timerLabel.setPadding(new Insets(10));
        HBox topBox = new HBox(10);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(quizHeader, spacer, timerLabel);

        // Questions And Options
        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 18px; -fx-padding: 10;");

        optionsGroup = new ToggleGroup();
        optionButtons = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RadioButton rb = new RadioButton();
            rb.setToggleGroup(optionsGroup);
            rb.setWrapText(true);
            rb.setStyle("-fx-font-size: 14px;");
            optionButtons.add(rb);
        }

        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().addAll(questionLabel);
        centerBox.getChildren().addAll(optionButtons);

        //Navigation button
        Button previous = new Button("Previous");
        Button next = new Button("Next");
        Button submit = new Button("Submit");

        previous.setOnAction(e -> {
            if (currentQuestionIndex > 0) {
                // Save current answer before moving
                RadioButton selectedRadio = (RadioButton) optionsGroup.getSelectedToggle();
                if (selectedRadio != null) {
                    String selectedAnswer = selectedRadio.getText();
                    userAnswers.put(currentQuestionIndex, selectedAnswer);
                }
                currentQuestionIndex--;
                showQuestion(currentQuestionIndex);
            }
        });

        next.setOnAction(e -> {
            // Save current answer before moving
            RadioButton selectedRadio = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedRadio != null) {
                String selectedAnswer = selectedRadio.getText();
                userAnswers.put(currentQuestionIndex, selectedAnswer); // Store the answer
            }
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                showQuestion(currentQuestionIndex);
            }
        });

        submit.setOnAction(e -> {
            // Save current answer before submitting
            RadioButton selectedRadio = (RadioButton) optionsGroup.getSelectedToggle();
            if (selectedRadio != null) {
                String selectedAnswer = selectedRadio.getText();
                userAnswers.put(currentQuestionIndex, selectedAnswer); // Store the answer
            }
            showPage5();
        });

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(15));
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setSpacing(40); // Add spacing between buttons
        bottomBox.getChildren().addAll(previous, next, submit);

        // Combine all
        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(centerBox);
        root.setBottom(bottomBox);

        Scene quizScene = new Scene(root, 700, 700);
        primaryStage.setScene(quizScene);
        primaryStage.show();

        showQuestion(0); // first question visible 
        startTimer();

        // Apply improved CSS styling
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f8ff, #e6f2ff);");
        centerBox.setStyle("-fx-background-color: white; -fx-background-radius: 16px; -fx-padding: 32px; -fx-effect: dropshadow(gaussian, #b0c4de, 12, 0.2, 0, 4);");
        for (RadioButton rb : optionButtons) {
            rb.setStyle("-fx-font-size: 16px; -fx-padding: 8px 0; -fx-background-radius: 8px; -fx-cursor: hand;");
        }
        previous.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-margin: 50px; -fx-background-radius: 8px; -fx-cursor: hand;");
        next.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-margin: 50px;  -fx-background-radius: 8px; -fx-cursor: hand;");
        submit.setStyle("-fx-background-color: #4b30e2; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-margin: 50px;  -fx-background-radius: 8px; -fx-cursor: hand;");
        quizHeader.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 10 0 20 0;");
        questionLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10 0 20 0; -fx-text-fill: #34495e;");
    }

    // Questions List fetching
    private void showQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText("Q" + (index + 1) + ": " + q.getQuestion());
        List<String> opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons.get(i).setText(opts.get(i));
            optionButtons.get(i).setSelected(false);
        }
        // Restore previous selection if exists
        String previouslySelected = userAnswers.get(index);
        if (previouslySelected != null) {
            for (RadioButton rb : optionButtons) {
                if (rb.getText().equals(previouslySelected)) {
                    rb.setSelected(true);
                    break;
                }
            }
        }
    }

    // Timer for Quiz
    private void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long now = System.currentTimeMillis();
            long elapsed = (now - startTime) / 1000;
            long minutes = elapsed / 60;
            long seconds = elapsed % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //  Page 5: End Quiz
    private void showPage5() {

        int score = calculateScore(questions, userAnswers);
        System.out.println("Your Score: " + score + " out of " + questions.size());

        long timeTaken = (System.currentTimeMillis() - startTime) / 1000;

        Label quizHeader = new Label("🎉 Quiz Completed!");
        quizHeader.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label scoreLabel = new Label(currentUsername + " your score is " + score);
        scoreLabel.setStyle("-fx-font-size: 28px;");

        Label timeLabel = new Label("Time Taken is " + timeTaken + " Seconds");
        timeLabel.setStyle("-fx-font-size: 16px;");

        Button newQuiz = new Button("Start New Quiz");
        newQuiz.setOnAction(e -> {
            try {
                QuizDAO dao = new QuizDAO();  // DAO must be correctly imported
                List<Question> questions = dao.getRandomQuestions(DBConnection.getConnection());
                showQuizPage(questions);      // Pass questions after fetching
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button logout = new Button("Logout");
        logout.setStyle("--fx-alignment: top-right; -fx-padding: 10;");
        logout.setOnAction(e -> {
            showPage1();
        });

        VBox layout5 = new VBox(20);
        // layout2.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-border-width: 2px;");
        layout5.getStyleClass().add("vbox");
        layout5.getChildren().addAll(quizHeader, scoreLabel, timeLabel, newQuiz, logout);

        Scene scene5 = new Scene(layout5, 600, 600);
        scene5.getStylesheets().add(css);
        primaryStage.setScene(scene5);

        // Apply improved CSS styling
        layout5.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f8ff, #e6f2ff);");
        quizHeader.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-padding: 10 0 20 0;");
        scoreLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #34495e; -fx-padding: 10 0 10 0;");
        timeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #555; -fx-padding: 10 0 20 0;");
        newQuiz.setStyle("-fx-background-color: #4682b4; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 24px; -fx-background-radius: 8px; -fx-cursor: hand;");
        logout.setStyle("-fx-background-color: #4b30e2; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 24px; -fx-background-radius: 8px; -fx-cursor: hand;");
    }

    private int calculateScore(List<Question> questions, Map<Integer, String> userAnswers) {
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String correct = q.getCorrectAnswer();
            String selected = userAnswers.get(i);
            System.out.println("Q" + (i + 1) + ": correct='" + correct + "', selected='" + selected + "'");
            System.out.println("Options: " + q.getOptions());
            if (correct != null && selected != null && correct.trim().equalsIgnoreCase(selected.trim())) {
                score = score + 1;
            }
        }

        return score;
    }

    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println(" Connected successfully to MySQL!");
        } else {
            System.out.println(" Connection failed.");
        }

        launch(args);
    }
}
