import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class QuestionHandlers {
	Question question = new Question();
	private List<Question> questionContainer = new ArrayList<Question>();

	public void fetchQuestions() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            
            String query = "select * from question order by rand()";
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
            	questionContainer.add(q);

            }
		} catch (Exception e) {
			System.out.println("-------Database Connection error");
		}
	}
	
	public void displayQuestions() {
		
		for (int x=0; x < questionContainer.size(); x++) {
			Question q = questionContainer.get(x);
	        System.out.println("\n\nQuestion #" + q.getQuestionId());
	        System.out.println("\n" + q.getQuestion());
	        System.out.println("Option A: " + q.getOptionA());
	        System.out.println("Option B: " + q.getOptionB());
	        System.out.println("Option C: " + q.getOptionC());
	        System.out.println("Option D: " + q.getOptionD());
		}
	}
}
