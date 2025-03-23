import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
    	StudentHandler s = new StudentHandler();
    	QuestionHandlers q = new QuestionHandlers();
    	Scanner in = new Scanner(System.in);
    	boolean login = false;
    	boolean makeQuiz = false;
    	
    	while (!login) {
    		System.out.println("------QUIZ------");
    		System.out.println("\n[1] sign up\n[2] login\n[3] Exit");
    		System.out.print("Enter choice:");
    		int login_choice = in.nextInt();
    		
    		switch (login_choice) {
    			case 1:
    				s.makeNewStudent();
    				break;
    			case 2:
    				System.out.println("Welcome, " + s.authenticateStudent().getFirstName());
    				break;
    			case 3:
    				login = true;
    				break;
    		}
    		System.out.println("------MENU------");
    		System.out.println("\n[1] New Quiz\n[2] Exit\n");
    		System.out.print("Enter choice:");
    		int quiz_choice = in.nextInt();
    		
    	}
    	
    	//q.displayQuestions();
    	//System.out.println(q.getQuestionIdOrder());
    }
}
