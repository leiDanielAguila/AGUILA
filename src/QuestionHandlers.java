import java.sql.*;
public class QuestionHandlers {
	Question question = new Question();

	public void getQuestions() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            
            String query = "select * from question";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
            	Question q = new Question(
                        rs.getInt("question_id"),
                        rs.getString("question"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_answer")
                    );
                System.out.println("\n\nQuestion #" + q.getQuestionId());
                System.out.println("\n" + q.getQuestion());
                System.out.println("Option A: " + q.getOptionA());
                System.out.println("Option B: " + q.getOptionB());
                System.out.println("Option C: " + q.getOptionC());
                System.out.println("Option D: " + q.getOptionD());
            }
		} catch (Exception e) {
			System.out.println("-------Database Connection error");
		}
	}
}
