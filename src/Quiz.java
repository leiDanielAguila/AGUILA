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
	
	public void setQuizScore(int s) {
		this.score = s;
	}
	
	public int getQuizScore() {
		return this.score;
	}
	
	public void setQuizId(int qid) {
		this.quiz_id = qid;
	}
	
	public int getQuizId() {
		return this.quiz_id;
	}
	
	public void setStudentId(int sid) {
		this.student_id = sid;
	}

	public int getStudentId() {
		return this.student_id;
	}
	
	public void setIsDone(boolean is_done) {
		this.is_done = is_done;
	}
	
	public boolean getIsDone() {
		return this.is_done;
	}
	
	public void setQuestionOrder(String qo) {
		this.question_order = qo;
	}
	
	public String getQuestionOrder() {
		return this.question_order;
	}
	
	
	public void makeNewQuiz(int sid, String qo) { // sid = student id, qo = question order
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "insert into quiz (score, is_done, question_order, student_id) values (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            Quiz quiz1 = new Quiz();
            
            quiz1.setQuizScore(0);
            quiz1.setIsDone(false);
            quiz1.setQuestionOrder(qo);
            quiz1.setStudentId(sid);
            
            ps.setInt(1, quiz1.getQuizScore());
            ps.setBoolean(2, quiz1.getIsDone());
            ps.setString(3, quiz1.getQuestionOrder());
            ps.setInt(4, quiz1.getStudentId());
            ps.executeUpdate();
            System.out.println("----New Quiz Created----");
			ps.close();
			connection.close();
		} catch (Exception e) {
			System.out.println("-------Database connection.");
		}
	}
	
	
	public Quiz checkForExistingQuiz(int sid) {
		Quiz quiz = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "select * from quiz where student_id = ? and is_done = ?";            
            PreparedStatement ps = connection.prepareStatement(query);


            ps.setInt(1, sid);
            ps.setBoolean(2, false);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	quiz = new Quiz(
            			rs.getInt("quiz_id"),
            			rs.getInt("score"),
            			rs.getBoolean("is_done"),
            			rs.getString("question_order"),
            			rs.getInt("student_id")            		
            			);                  	
            }            
		} catch (Exception e) {
			System.out.println("-------Database connection.");
		}
		return quiz;
	}
	
	public void quizIsDone(int sid, boolean qIsDone) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");

	        String query = "UPDATE quiz SET is_done = ? WHERE student_id = ? AND is_done = false";
	        PreparedStatement ps = connection.prepareStatement(query);

	        ps.setBoolean(1, qIsDone);
	        ps.setInt(2, sid);

	        int rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("Quiz marked as " + (qIsDone ? "completed" : "incomplete"));
	        } else {
	            System.out.println("No quiz found to update");
	        }
	        
	        ps.close();
	        connection.close();
	        
	    } catch (Exception e) {
	        System.out.println("-------Database connection error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
}
