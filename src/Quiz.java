import java.sql.*;
public class Quiz {
	private int quiz_id, student_id, score;
	private String question_order;
	private boolean is_done;
	
	// Constructors for quiz
	
	public Quiz(int quizId, int s, String questionOrder, int studentId) {
		this.quiz_id = quizId;
		this.score = s;
		this.question_order = questionOrder;
		this.student_id = studentId;
	}
	
	public Quiz() {
		this.quiz_id = 0;
		this.score = 0;
		this.question_order = "";
		this.student_id = 0;
	}
	
	public void makeNewQuiz(int sid, String qo) { // sid = student id, qo = question order
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "insert into quiz ()";
		} catch (Exception e) {
			System.out.println("-------Database connection.");
		}
	}
}
