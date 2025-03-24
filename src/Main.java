import java.util.Scanner;
public class Main {
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String BLUE = "\033[1;36m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
    
    public static void main(String[] args) {
    	StudentHandler sh = new StudentHandler();
    	Scanner in = new Scanner(System.in);
    	QuestionHandlers q = new QuestionHandlers();
    	Quiz quiz = new Quiz();
    	boolean done = false; 
    	Student currentStudent = null;
    	Quiz currentQuiz = null;
    	
    	
    	while (!done) {
    		System.out.println(BLUE + "------------IT QUIZ" + RESET);
    		System.out.println("[1] Sign-up\n[2] Log-in\n[3] Exit");
    		System.out.print("Enter your choice: ");
    		int choice = in.nextInt();
    		
    		switch(choice) {
    		case 1:
    			sh.makeNewStudent();
    			break;
    		case 2:
    			currentStudent = sh.authenticateStudent();
    			if (currentStudent != null) {
    				done = true;
    			}  			
    			break;
    		case 3:
    			System.exit(0);
    			break;
    		}
    	}   
    	
    	boolean done1 = false;
    	
    	if (currentStudent != null) {
    		System.out.println("Welcome, "+currentStudent.getFirstName() + " " + currentStudent.getLastName());
    	} 
    	
    	
    	while (!done1) {
    		Quiz checkForQuiz = quiz.checkForExistingQuiz(currentStudent.getStudentId());
    		
    		System.out.println(BLUE + "\n-------Dashboard-------" + RESET);
    		System.out.println("[1] New Quiz\n[2] Continue Quiz\n[3] Exit");
    		System.out.println("Enter choice: ");
    		int choice = in.nextInt();
    		
    		switch (choice) {
    		case 1:
    			if (checkForQuiz != null) {
    				System.out.println(RED + "-----You have an existing quiz, do you want to delete quiz?" + RESET);
    	    		System.out.println("[1] Yes\n[2] No\n");
    	    		System.out.println("Enter choice: ");
    	    		int choice1 = in.nextInt();
    	    		switch (choice1) {
    	    		case 1:
    	    			quiz.deleteQuiz(currentStudent.getStudentId());
    	    			System.out.println(GREEN + "-------Quiz Deleted!" + RESET);
    	    			q.fetchQuestions();
        				quiz.makeNewQuiz(currentStudent.getStudentId(), q.getQuestionIdOrderAsString());
        				System.out.println(GREEN + "-------New quiz created!" + RESET);
        				currentQuiz = quiz.checkForExistingQuiz(currentStudent.getStudentId());
    	    			done1 = true;
    	    			break;
    	    		case 2:
    	    			done1 = false;
    	    			break;
    	    		}
    			} else {
    				q.fetchQuestions();
    				quiz.makeNewQuiz(currentStudent.getStudentId(), q.getQuestionIdOrderAsString());
    				currentQuiz = quiz.checkForExistingQuiz(currentStudent.getStudentId());
    				System.out.println(GREEN + "-------New quiz created!" + RESET);
    				done1 = true;
    			}
    			break;
    		case 2:
    			if (checkForQuiz != null) {
    				System.out.println(GREEN + "-------Continue Quiz" + RESET);
    				currentQuiz = quiz.checkForExistingQuiz(currentStudent.getStudentId());
    				done1 = true;
    			} else {
    				System.out.println(RED + "-------No existing Quiz, start a new one." + RESET);
    				done1 = false;
    			}
    			break;
    		case 3:
    			System.out.println("-------Program exit done!");
    			System.exit(0);
    			break;
    		}	
    	}
    	
    	if (currentQuiz != null) {
        	System.out.println("Starting Quiz #" + currentQuiz.getQuizId() + " for " + currentStudent.getFirstName());
    	} 
    	
    	boolean done3 = false;
    	q.getExistingQuiz(currentStudent.getStudentId());
		q.loadQuestionsFromIds();
    	while (!done3) {
    		q.displayQuestions();
    		System.out.println(BLUE + "\n------Navigation Menu------" + RESET);
    		System.out.println("\n[1] Previous Question\n[2] Next Question\n[3] Answer Question\n[4] Exit\n");
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
    			// answer method
    			break;
    		case 4:
    			// save the fucking progress boiiii
    			System.out.println(GREEN + "Progress Saved!" + RESET);
    			System.exit(0);
    			break;
    		}

    	}
    	
    	in.close();
    }
}
