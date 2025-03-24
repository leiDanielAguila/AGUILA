import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
    	StudentHandler sh = new StudentHandler();
    	Scanner in = new Scanner(System.in);
    	QuestionHandlers q = new QuestionHandlers();
    	Quiz quiz = new Quiz();
    	boolean done = false; 
    	Student currentStudent = null;
    	
    	while (!done) {
    		System.out.println("------------IT QUIZ");
    		System.out.println("[1] Sign-up\n[2] Log-in\n[3] Exit");
    		System.out.print("Enter your choice: ");
    		int choice = in.nextInt();
    		
    		switch(choice) {
    		case 1:
    			sh.makeNewStudent();
    			break;
    		case 2:
    			currentStudent = sh.authenticateStudent();
    			done = true;
    			break;
    		case 3:
    			done = true;
    			break;
    		}
    	}   
    	
    	boolean done1 = false;
    	
    	System.out.println("Welcome, "+currentStudent.getFirstName());
    	
    	
    	while (!done1) {
    		Quiz checkForQuiz = quiz.checkForExistingQuiz(currentStudent.getStudentId());
    		
    		System.out.println("\n-------Dashboard-------");
    		System.out.println("[1] New Quiz\n[2] Continue Quiz\n[3] Exit");
    		System.out.println("Enter choice: ");
    		int choice = in.nextInt();
    		
    		switch (choice) {
    		case 1:
    			if (checkForQuiz != null) {
    				System.out.println("-----You have an existing quiz, do you want to delete quiz?");
    	    		System.out.println("[1] Yes\n[2] No\n");
    	    		System.out.println("Enter choice: ");
    	    		int choice1 = in.nextInt();
    	    		switch (choice1) {
    	    		case 1:
    	    			quiz.deleteQuiz(currentStudent.getStudentId());
    	    			System.out.println("-------Quiz Deleted!");
    	    			q.fetchQuestions();
        				quiz.makeNewQuiz(currentStudent.getStudentId(), q.getQuestionIdOrderAsString());
        				System.out.println("-------New quiz created!");
    	    			done1 = true;
    	    			break;
    	    		case 2:
    	    			done1 = false;
    	    			break;
    	    		}
    			} else {
    				q.fetchQuestions();
    				quiz.makeNewQuiz(currentStudent.getStudentId(), q.getQuestionIdOrderAsString());
    				System.out.println("-------New quiz created!");
    				done1 = true;
    			}
    			break;
    		case 2:
    			if (checkForQuiz != null) {
    				System.out.println("-------Continue Quiz");
    				done1 = true;
    			} else {
    				System.out.println("-------No existing Quiz, start a new one.");
    				done1 = false;
    			}
    			break;
    		case 3:
    			System.out.println("-------Program exit done!");
    			done1 = true;
    			break;
    		}
    		
    		
    	}
    	
    	
    	in.close();
    }
}
