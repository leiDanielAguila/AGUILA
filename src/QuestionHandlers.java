import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class QuestionHandlers {
	Question question = new Question();
	private List<Question> questionContainer = new ArrayList<Question>();
	private List<Integer> question_id_container = new ArrayList<Integer>();
	private int current_question = 0;
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String BLUE = "\033[1;36m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
	
	public void nextQuestion() {
		if (current_question < questionContainer.size() - 1) {
			current_question++;	
		} else {
			System.out.println(RED + "------------------this is the last question." + RESET);
		}
	}
	
	public void previousQuestion() {
		if (current_question > 0) {
			current_question-=1;
		} else {
			System.out.println(RED + "------------------this is the first question." + RESET);
		}	
	}

	public void fetchQuestions() {
		int count = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            
            String query = "select distinct * from question order by rand()";
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
		if (questionContainer.size() == 0) return;
		
		Question q = questionContainer.get(current_question);
		System.out.println("\n\nNo. " + (current_question + 1 )+ "/" + questionContainer.size());
        System.out.println(BLUE + "\nQuestion #" + q.getQuestionId() + RESET);
        System.out.println("\n" + q.getQuestion());
        System.out.println("Option A: " + q.getOptionA());
        System.out.println("Option B: " + q.getOptionB());
        System.out.println("Option C: " + q.getOptionC());
        System.out.println("Option D: " + q.getOptionD());
	}
	
	public void updateCurrentQuestion(int quizId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");            
            String query = "select last_answered_question from quiz where quiz_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, quizId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
            	current_question = rs.getInt("last_answered_question");     
            }
            
		} catch (Exception e) {
			System.out.println(RED + "-----error in updating current question" + RESET + e);
		}
	}
	
	public boolean checkIfQuestionAnswered (int quizId) {
		boolean isAnswered = false;
		try {			
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
        	Question q = questionContainer.get(current_question);
        	QuizProgress qp = new QuizProgress();
        	String query = "select * from quiz_progress where quiz_id = ? and question_id = ?";        	        	        	
        	PreparedStatement ps = connection.prepareStatement(query);
        	ps.setInt(1, quizId);
        	ps.setInt(2, q.getQuestionId());
        	ResultSet rs = ps.executeQuery();
        	        	        	
        	if (rs.next()) {
        		isAnswered = true;
        	}
        	rs.close();
        	connection.close();
        	ps.close();
		} catch (Exception e) {
			System.out.println(RED + "-----error in check question answer" + RESET + e);
		}
		return isAnswered;
	}
	
	public void answerQuestion(int quizId, String userAnswer) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
        	Question q = questionContainer.get(current_question);
        	QuizProgress qp = new QuizProgress();
        	
        	qp.setQuizId(quizId);
        	qp.setUserAnswer(userAnswer);        	
        	if (userAnswer.equals(questionContainer.get(current_question).getCorrectAnswer())) {
        		qp.setIsCorrect(true); // check for later because all is_correct is false in db
        	}
        	qp.setQuestionId(q.getQuestionId());
        	String query1 = "insert into quiz_progress (quiz_id,question_id,user_answer,is_correct) values (?,?,?,?)";
        	PreparedStatement ps = connection.prepareStatement(query1);
        	
        	ps.setInt(1, qp.getQuizId());
        	ps.setInt(2, qp.getQuestionId());
        	ps.setString(3, qp.getUserAnswer());
        	ps.setBoolean(4, qp.getIsCorrect());
        	ps.executeUpdate();
        	System.out.println(GREEN + "\nQuestion #" +q.getQuestionId() + " answered." + RESET);
        	nextQuestion();
        	String query2 = "update quiz set last_answered_question = ? where quiz_id = ?";
        	PreparedStatement ps1 = connection.prepareStatement(query2);
        	int index = questionContainer.indexOf(q);
        	ps1.setInt(1, index);
        	ps1.setInt(2, quizId);
			ps.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(RED + "-----error in answer question" + RESET + e);
		}
	}
	
//	public void displayUnansweredQuestions(int quizId) {
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
//        	String query = "select * from from quiz_progress where quiz_id = ? and user_answer = ?";
//		} catch (Exception e) {
//			System.out.println(RED + "-----error in displaying unanswered questions" + RESET + e);
//		}
//	}
	
	public void populateQuizProgress(int quizId) {
		
	}
	
	public int getQuestionid() {
		Question q = questionContainer.get(current_question);
		int index = questionContainer.indexOf(q);
		return index;
	}
	
	public List<Integer> getQuestionIdOrder() {
		return this.question_id_container;
	}
	
	public String getQuestionIdOrderAsString() { // for storing to database
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < question_id_container.size(); i++) {
	        sb.append(question_id_container.get(i));

	        if (i < question_id_container.size() - 1) {
	            sb.append(",");
	        }
	    }
	    return sb.toString();
	}
	
	public void setQuestionIdOrderFromString(String idString) { // for geting from database
		question_id_container.clear();
		if (idString != null && !idString.isEmpty()) {
			String[] idArray = idString.split(",");
			for (String id : idArray) {
				question_id_container.add(Integer.parseInt(id.trim()));
			}
		}
	}

	public void loadQuestionsFromIds() {
	    questionContainer.clear(); 
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
	        
	        String placeholders = String.join(",", Collections.nCopies(question_id_container.size(), "?"));
	        String query = "SELECT * FROM question WHERE question_id IN (" + placeholders + ")";
	        
	        PreparedStatement ps = connection.prepareStatement(query);
	        

	        for (int i = 0; i < question_id_container.size(); i++) {
	            ps.setInt(i + 1, question_id_container.get(i));
	        }
	        
	        ResultSet rs = ps.executeQuery();

	        Map<Integer, Question> questionMap = new HashMap<>();
	        
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
	            questionMap.put(q.getQuestionId(), q);
	        }

	        for (Integer id : question_id_container) {
	            if (questionMap.containsKey(id)) {
	                questionContainer.add(questionMap.get(id));
	            }
	        }
	        
	    } catch (Exception e) {
	        System.out.println("-------Error loading questions: ");
	    }
	}
	
	public void getExistingQuiz(int sid) {
		Quiz quiz = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            String query = "select * from quiz where student_id = ?";            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, sid);
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
			System.out.println("-------Database connection.");
		}
		
		if (quiz != null) {
            setQuestionIdOrderFromString(quiz.getQuestionOrder());
        } else {
            System.out.println(RED + "------No existing quiz found for student ID: " + sid + "-----" + RESET);
            question_id_container.clear();
        }
	}
	
	
		
}
