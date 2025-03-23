import java.sql.*;
public class Quiz {
	private int quiz_id, student_id, score;
	private String question_order;
	private boolean is_done;
	
	Question q = new Question();
	
	// Constructors for quiz
	
	public Quiz(int quizId, int s, boolean is_done,String questionOrder, int studentId) {
		this.quiz_id = quizId;
		this.score = s;
		this.is_done = is_done;
		this.question_order = questionOrder;
		this.student_id = studentId;
	}
	
	public Quiz() {
		this.quiz_id = 0;
		this.score = 0;
		this.is_done = false;
		this.question_order = "";
		this.student_id = 0;
	}
	
	public void makeNewQuiz(int sid, String qo) { // sid = student id, qo = question order
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "insert into quiz (score, is_done, question_order, student_id) values (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, 0);
            ps.setBoolean(2, false);
            ps.setString(3, qo);
            ps.setInt(4, sid);
            ps.executeUpdate();
            System.out.println("----New Quiz Created----");
			ps.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("-------Database connection.");
		}
	}
	
	
}
