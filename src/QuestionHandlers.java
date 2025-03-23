import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class QuestionHandlers {
	Question question = new Question();
	private List<Question> questionContainer = new ArrayList<Question>();
	private List<Integer> question_id_container = new ArrayList<Integer>();
	private int current_question = 0;
	
	public void nextQuestion() {
		if (current_question < questionContainer.size() - 1) {
			current_question++;	
		} else {
			System.out.println("-------this is the last question.");
		}
	}
	
	public void previousQuestion() {
		if (current_question > 0) {
			current_question-=1;
		} else {
			System.out.println("-------this is the first question.");
		}	
	}

	public void fetchQuestions() {
		int count = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            
            String query = "select * from question order by rand()";
            ResultSet rs = st.executeQuery(query);
            while (rs.next() && count < 10) {
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
            	question_id_container.add(q.getQuestionId());
            	count+=1;

            }
		} catch (Exception e) {
			System.out.println("-------Database Connection error");
		}
	}
	
	public void displayQuestions() {	
		Question q = questionContainer.get(current_question);
        System.out.println("\n\nQuestion #" + q.getQuestionId());
        System.out.println("\n" + q.getQuestion());
        System.out.println("Option A: " + q.getOptionA());
        System.out.println("Option B: " + q.getOptionB());
        System.out.println("Option C: " + q.getOptionC());
        System.out.println("Option D: " + q.getOptionD());
	}
	
	public List<Integer> getQuestionIdOrder() {
		return this.question_id_container;
	}
		
}
