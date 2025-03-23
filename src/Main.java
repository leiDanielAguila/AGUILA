import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
    	StudentHandler s = new StudentHandler();
    	QuestionHandlers q = new QuestionHandlers();
    	Scanner in = new Scanner(System.in);
    	boolean done = false;
    	q.fetchQuestions();
    	while (!done) {
    		
    		q.displayQuestions();
    		System.out.println("\n[1] Previous\n[2] Next\n[3] Exit");
    		System.out.print("Enter choice:");
    		int choice = in.nextInt();
    		
    		switch (choice) {
    			case 1:
    				q.previousQuestion();
    				break;
    			case 2:
    				q.nextQuestion();
    				break;
    			case 3:
    				done = true;
    				break;
    		}
    	}
    	
    	//q.displayQuestions();
    	//System.out.println(q.getQuestionIdOrder());
    }
}
