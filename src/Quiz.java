import java.sql.*;
public class Quiz {
	private int quiz_id, student_id, score, last_answered_question;
	private String question_order;
	private boolean is_done;
	Question q = new Question();
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String BLUE = "\033[1;36m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset

	
	// Constructors for quiz
	
	public Quiz(int quizId, int s, boolean is_done,String questionOrder, int studentId, int laq) {
		this.quiz_id = quizId;
		this.score = s;
		this.is_done = is_done;
		this.question_order = questionOrder;
		this.student_id = studentId;
		this.last_answered_question =laq;
	}
	
	public Quiz() {
		this.quiz_id = 0;
		this.score = 0;
		this.is_done = false;
		this.question_order = "";
		this.student_id = 0;
		this.last_answered_question = 0;
	}
	
	public void setLastQuestionAnswered(int laq) {
		this.last_answered_question = laq;
	}
	
	public int getLastQuestionAnswered() {
		return this.last_answered_question;
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
            String query = "insert into quiz (score, is_done, question_order, student_id, last_answered_question) values (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            Quiz quiz1 = new Quiz();
            
            quiz1.setQuizScore(0);
            quiz1.setIsDone(false);
            quiz1.setQuestionOrder(qo);
            quiz1.setStudentId(sid);
            quiz1.setLastQuestionAnswered(0);
            
            ps.setInt(1, quiz1.getQuizScore());
            ps.setBoolean(2, quiz1.getIsDone());
            ps.setString(3, quiz1.getQuestionOrder());
            ps.setInt(4, quiz1.getStudentId());
            ps.setInt(5, quiz1.getLastQuestionAnswered());
            ps.executeUpdate();
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
            			rs.getInt("student_id"),
            			rs.getInt("last_answered_question")
            			);                  	
            }            
		} catch (Exception e) {
			System.out.println("this-------Database connection error." + e);
		}
		return quiz;
	}
	
	public void deleteQuiz(int sid) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "delete from quiz where student_id = ?";            
            PreparedStatement ps = connection.prepareStatement(query);
            	
            String progressQuery = "DELETE FROM quiz_progress WHERE quiz_id IN (SELECT quiz_id FROM quiz WHERE student_id = ?)";
            PreparedStatement ps2 = connection.prepareStatement(progressQuery);
            ps2.setInt(1, sid);
            ps2.executeUpdate();

            ps.setInt(1, sid);
            ps.executeUpdate();
              
		} catch (Exception e) {
			System.out.println("-------Database connection error.");
		}
	}
	
	public void quizIsDone(int sid) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");

	        String query = "UPDATE quiz SET is_done = true WHERE student_id = ? AND is_done = false";
	        PreparedStatement ps = connection.prepareStatement(query);

	        ps.setInt(1, sid);

	        int rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println(GREEN + "Quiz marked as completed" + RESET);
	        } 
	        
	        ps.close();
	        connection.close();
	        
	    } catch (Exception e) {
	        System.out.println("-------Database connection error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
		
	public void submitQuiz(int studentId, int quizId, int score) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "UPDATE quiz SET is_done = TRUE, score = ? WHERE student_id = ? AND quiz_id = ?";            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, score);
            ps.setInt(2, studentId);
            ps.setInt(3, quizId);
            ps.executeUpdate();     
            System.out.println(GREEN + "---QUIZ SUBMITTED!---" + RESET);
		} catch (Exception e) {
			System.out.println(RED + "-----error in submit quiz" + RESET + e);
		}
	}
	
	public int getScore(int quizId) {
		int score = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "SELECT COUNT(*) as correct_answers FROM quiz_progress WHERE is_correct = TRUE AND quiz_id = ?"; 
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	score = rs.getInt("correct_answers");
            }
            rs.close();
            connection.close();
            ps.close();
		} catch (Exception e) {
			System.out.println(RED + "-----error in get score" + RESET + e);
		}
		return score;
	}
}
