import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
    	StudentHandler s = new StudentHandler();
    	Student student = new Student();
    	QuestionHandlers q = new QuestionHandlers();
    	Quiz quiz = new Quiz();
    	Scanner in = new Scanner(System.in);
    	boolean done = false;

    	while (!done) {
    		
 
    		System.out.println("------QUIZ------");
    		System.out.println("\n[1] sign up\n[2] login\n[3] Exit");
    		System.out.print("Enter choice:");
    		int login_choice = in.nextInt();
    		
    		switch (login_choice) {
    			case 1:
    				s.makeNewStudent();
    				break;
    			case 2:
    				s.authenticateStudent();
    				System.out.println("Welcome, " + s.getStudent().getFirstName());
    				done = true;
    				break;
    			case 3:
    				done = true;
    				break;
    		}
    		
    		Quiz checkForQuiz = quiz.checkForExistingQuiz(s.getStudent().getStudentId());
    		
    		if (checkForQuiz != null) {
    			System.out.println("\n\n\n------Resuming Quiz------");
    			
    		} else {
    			System.out.println("\n\n\n------MENU------");
        		System.out.println("\n[1] New Quiz\n[2] Exit\n");
        		System.out.print("Enter choice:");
        		int quiz_choice = in.nextInt();
        		switch (quiz_choice) {
        			case 1:
        				q.fetchQuestions();
        				quiz.makeNewQuiz(s.getStudent().getStudentId(), q.getQuestionIdOrderAsString());
        				done = true;
        				break;
        			case 2:
        				done = true;
        				break;
        		}
    		}
  		
    	}
    	in.close();
    }
}
