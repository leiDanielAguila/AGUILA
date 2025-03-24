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
    			boolean quizDone = false;
    			System.out.println("\n\n\n------Resuming Quiz------");
    			q.getExistingQuiz(checkForQuiz.getStudentId());
    			q.loadQuestionsFromIds();
    			while (!quizDone) {
    				q.displayQuestions();
        			System.out.println("\n------Navigation Menu------");
            		System.out.println("\n[1] Previous Question\n[2] Next Question\n[3] Exit\n");
            		System.out.print("Enter choice:");
        			int nav_choice = in.nextInt();
        			
        			switch (nav_choice) {
        			case 1:
        				q.previousQuestion();
        				quizDone = false;
        				break;
        			case 2:
        				q.nextQuestion();
        				quizDone = false;
        				break;
        			case 3:
        				System.out.println("Exited quiz. ---Progress saved.");
        				quizDone = true;
        				break;
        			}
    			}
    			
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
