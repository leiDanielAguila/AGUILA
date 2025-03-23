
public class Main {
    public static void main(String[] args) {
    	StudentHandler s = new StudentHandler();
    	QuestionHandlers q = new QuestionHandlers();
//    	System.out.println("test");
    	q.fetchQuestions();
    	//q.displayQuestions();
    	System.out.println(q.getQuestionIdOrder());
    }
}
