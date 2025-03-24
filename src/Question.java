import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Question {
	private int question_id, current_question = 0;
	private String question, option_a, option_b, option_c, option_d, correct_answer;
	private List<Question> questionList = new ArrayList<>();
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String BLUE = "\033[1;36m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
	// Constructors 
	
	public Question(int qid, String q, String oa, String ob, String oc, String od, String ca) {
		this.question_id = qid;
		this.question = q;
		this.option_a = oa;
		this.option_b = ob;
		this.option_c = oc;
		this.option_d = od;
		this.correct_answer = ca;
	}
	
	public Question() { 
		this.question_id = 0;
		this.question = "";
		this.option_a = "";
		this.option_b = "";
		this.option_c = "";
		this.option_d = "";
		this.correct_answer = "";
	}
	
	public void nextCurrentQuestion() {
        if (questionList.size() == 0) return;
        if (current_question < questionList.size() - 1) {
            current_question++;
        } else {
            System.out.println("\n\n\nThis is the last question.");
        }
    }

    public void prevCurrentQuestion() {
        if (questionList.size() == 0) return;
        if (current_question > 0) {
            current_question--;
        } else {
            System.out.println("\n\n\nThis is the first question.");
        }
    }

	
	// getters
    
    public int getQuestionCount() {
    	return this.questionList.size();
    }
    
	public int getCurrentQuestion() {
		return this.current_question;
	}
	
	public int getQuestionId() {
		return this.question_id;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public String getOptionA() {
		return this.option_a;
	}
	
	public String getOptionB() {
		return this.option_b;
	}
	
	public String getOptionC() {
		return this.option_c;
	}
	
	public String getOptionD() {
		return this.option_d;
	}
	
	public String getCorrectAnswer() {
		return this.correct_answer;
	}
	
	// setters
	
	public void setCurrentQuestion(int x) {
		this.current_question = x;
	}
	
	public void setQuestionId(int x) {
		this.question_id = x;
	}
	
	public void setQuestion(String s) {
		this.question = s;
	}
	
	public void setOptionA(String o) {
		this.option_a = o;
	}
	
	public void setOptionB(String o) {
		this.option_b = o;
	}
	
	public void setOptionC(String o) {
		this.option_c = o;
	}
	
	public void setOptionD(String o) {
		this.option_d = o;
	}
	
	public void setCorrectAnswer(String a) {
		this.correct_answer = a;
	}
	
	public void Menu() {
		System.out.print("\nMenu:\r\n" 
		+ "[1] Previous Question\r\n" 
		+ "[2] Next Question\r\n" 
		+ "[3] Exit\r\n" 
		+ "\n Enter input: "); 
	}
	
	public void fetchRandomQuestions(String studentNumber) {
        questionList.clear();
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            String query = "SELECT * FROM question WHERE question_id NOT IN "
                    + "(SELECT question_id FROM student_progress WHERE studentNumber = '" + studentNumber + "') "
                    + "ORDER BY RAND()";
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
                questionList.add(q);
            }
            rs.close();
            st.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("Error fetching questions: " + e);
        }
    }

    public void displayCurrentQuestion() {
        if (questionList.size() == 0) {
            System.out.println("\n\n\nNo new questions available. You have answered all questions.");
            return;
        }
        Question q = questionList.get(current_question);
        System.out.println("\n\nQuestion #" + q.getQuestionId());
        System.out.println("\n" + q.getQuestion());
        System.out.println("Option A " + q.getOptionA());
        System.out.println("Option B " + q.getOptionB());
        System.out.println("Option C " + q.getOptionC());
        System.out.println("Option D " + q.getOptionD());
    }

	public void recordCurrentQuestion(String studentNumber) {
        if (questionList.size() == 0) return;
        Question currentQ = questionList.get(current_question);
        int qID = currentQ.getQuestionId();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            String query = "INSERT IGNORE INTO student_progress (studentNumber, question_id) VALUES ('" 
                    + studentNumber + "', " + qID + ")";
            st.executeUpdate(query);
            st.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("Error recording question progress: " + e);
        }
    }
	
	public boolean registerStudent(String studentNumber, String firstName, String lastName, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            String query = "INSERT INTO students (studentNumber, firstName, lastName, password) VALUES ('"
                    + studentNumber + "', '" + firstName + "', '" + lastName + "', '" + password + "')";
            int rows = st.executeUpdate(query);
            connection.close();
            return rows > 0;
        } catch(Exception e) {
            System.out.println("Error during registration: " + e);
            return false;
        }
    }
	
	public boolean authenticateStudent(String studentNumber, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
            Statement st = connection.createStatement();
            String query = "SELECT * FROM students WHERE studentNumber = '" + studentNumber
                    + "' AND password = '" + password + "'";
            ResultSet rs = st.executeQuery(query);
            boolean isAuthenticated = rs.next();
            connection.close();
            return isAuthenticated;
        } catch(Exception e) {
            System.out.println("Error during authentication: " + e);
            return false;
        }
    }
 }
