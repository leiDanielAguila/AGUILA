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
        	System.out.println("Starting Quiz Code#" + currentQuiz.getQuizId() + " for " + currentStudent.getFirstName());
    	} 
    	
    	boolean done3 = false;
    	q.getExistingQuiz(currentStudent.getStudentId());
		q.loadQuestionsFromIds();
		q.updateCurrentQuestion(currentQuiz.getQuizId());
    	while (!done3) {
    		if (q.getQuestionNumber() == q.getContainerCount()) {
    			System.out.println(RED + "------------------this is the last question." + RESET);
    		}
    		q.storeAnsweredQuestions(currentQuiz.getQuizId());
    		q.displayAnsweredQuestions();
    		q.displayQuestions();
    		System.out.println(BLUE + "\n------Navigation Menu------" + RESET);
    		System.out.println("\n[1] Previous Question\n[2] Next Question\n[3] Answer Question\n[4] Submit Quiz\n[5] Exit\n");
    		System.out.print(BLUE + "Enter Navigation choice:" + RESET);
    		int choice;
    		
    		while (!in.hasNextInt()) {
    			System.out.println(RED + "Invalid input. Please enter a number." + RESET);
    			System.out.print(BLUE+ "Enter Navigation choice:" + RESET);
    	        in.next();
    		}
    		choice = in.nextInt();
    		in.nextLine();
    		switch (choice) {
    		case 1:
    			q.previousQuestion();
    			break;
    		case 2:
    			q.nextQuestion();
    			break;
    		case 3:
    			// answer method
    			boolean quizAnswered = q.checkIfQuestionAnswered(currentQuiz.getQuizId());
    			
    			if (quizAnswered) {
    				System.out.print(RED + "-----Question is already answered." + RESET);
    				q.nextQuestion();
    				done3 = false;
    			} else {
    				System.out.print(GREEN + "-----Enter your answer: " + RESET);
        			String userAnswer = in.nextLine();
        			if (userAnswer.trim().isEmpty()) {
        				System.out.println(RED + "answer can not be empty." + RESET);
        				done3 = false;
        			} else {
        				q.answerQuestion(currentQuiz.getQuizId(), userAnswer.toUpperCase());
        				
        			}
    			}
    			
    			break;
    		case 4: 
    			if (q.getAnsweredCount() < q.getContainerCount()) {
    				System.out.println(RED + "You have questions left unanswered, will you still submit?" + RESET);
    				System.out.println("[1] YES\n[2] NO");
    				System.out.print(BLUE + "Enter submit choice: " + RESET);
    				int submitChoice = in.nextInt();
    				in.nextLine();
    				
    				switch (submitChoice) {
    				case 1:
    					int score = quiz.getScore(currentQuiz.getQuizId());
    					quiz.submitQuiz(currentStudent.getStudentId(), currentQuiz.getQuizId(), score);
    					System.out.println(GREEN + "--------FINAL RESULT-------" + RESET);
    					System.out.println("Student: " + currentStudent.getFirstName() +" "+ currentStudent.getLastName());
    					System.out.println("Score: " + score + "/" + q.getContainerCount());
    					String result = (score > 7) ? GREEN + "PASSED" + RESET :RED + "FAILED" + RESET;
    					System.out.println("Result: " + result);
    					System.exit(0);
    					break;
    				case 2:
    					done3 = false;
    					break;
    				default:
    					System.out.println(RED + "Invalid choice. Please enter a number between 1 and 2." + RESET);
    	    	        done3 = false;
    				}
    			} else {
    				System.out.println(RED + "Submit Quiz?" + RESET);
    				System.out.println("[1] YES\n[2] NO");
    				System.out.print(BLUE + "Enter submit choice: " + RESET);
    				int submitChoice = in.nextInt();
    				in.nextLine();
    				
    				switch (submitChoice) {
    				case 1:
    					int score = quiz.getScore(currentQuiz.getQuizId());
    					quiz.submitQuiz(currentStudent.getStudentId(), currentQuiz.getQuizId(), score);
    					System.out.println(GREEN + "--------FINAL RESULT-------" + RESET);
    					System.out.println("Student: " + currentStudent.getFirstName() +" "+ currentStudent.getLastName());
    					System.out.println("Score: " + score + "/" + q.getContainerCount());
    					String result = (score > 7) ? GREEN + "PASSED" + RESET :RED + "FAILED" + RESET;
    					System.out.println("Result: " + result);
    					System.exit(0);
    					break;
    				case 2:
    					done3 = false;
    					break;
    				default:
    					System.out.println(RED + "Invalid choice. Please enter a number between 1 and 2." + RESET);
    	    	        done3 = false;
    				}
    			}
    			break;
    		case 5:
    			// save the fucking progress boiiii    			
    			System.out.println(GREEN + "--------Progress Saved!" + RESET);
    			System.exit(0);
    			break;
    		default:
    	        System.out.println(RED + "Invalid choice. Please enter a number between 1 and 5." + RESET);
    	        done3 = false;
    		}

    	}
    	
    	in.close();
    }
}
