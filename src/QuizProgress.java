
public class QuizProgress {
	private int quiz_id, question_id;
	private String user_answer;
	private boolean is_correct;
	
	public QuizProgress(int quizId, int questionId, String userAnswer, boolean isCorrect) {
		this.quiz_id = quizId;
		this.question_id = questionId;
		this.user_answer = userAnswer;
		this.is_correct = isCorrect;
	}
	
	public QuizProgress() {
		this.quiz_id = 0;
		this.question_id = 0;
		this.user_answer = "";
		this.is_correct = false;
	}
	
	public int getQuizId() {
		return this.quiz_id;
	}
	
	public int getQuestionId() {
		return this.question_id;
	}
	
	public String getUserAnswer() {
		return this.user_answer;
	}
	
	public boolean getIsCorrect() {
		return this.is_correct;
	}
	
	public void setQuizId(int qid) {
		this.quiz_id = qid;
	}
	
	public void setQuestionId(int qid) {
		this.question_id = qid;
	}
	
	public void setUserAnswer(String a) {
		this.user_answer = a;
	}
	
	public void setIsCorrect(boolean c) {
		this.is_correct = c;
	}
	
}
