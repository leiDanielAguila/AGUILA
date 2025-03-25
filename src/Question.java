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
		
	
 }
