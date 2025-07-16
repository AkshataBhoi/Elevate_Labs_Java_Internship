import java.sql.*;
import java.util.*;

public class QuizDAO {

    public List<Question> getRandomQuestions(Connection conn) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT question_text, option_a, option_b, option_c, option_d, correct_option FROM questions ORDER BY RAND() LIMIT 5";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String q = rs.getString("question_text");

                List<String> opts = Arrays.asList(
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d")
                );

                // Collections.shuffle(opts); // shuffle options
                String correct = rs.getString("correct_option");

                questions.add(new Question(q, opts, correct));
            }
        }
        return questions;
    }
}
