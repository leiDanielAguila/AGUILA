import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Question q = new Question();
         
        boolean routeToLogin = true;
        String studentNumber = null;
        
        
        while(routeToLogin) {
        	System.out.println("\n\n\n\nIT Quiz System");
            System.out.println("[1] Sign Up");
            System.out.println("[2] Login");
            System.out.print("Enter your choice: ");
            int choice = in.nextInt();
            in.nextLine();
            
        	if (choice == 1) {
                signUp(in);
                routeToLogin = true;
            } else if (choice == 2) {
                studentNumber = login(in);
                routeToLogin = false;
            } else {
                System.out.println("Invalid choice. Exiting.");
                System.exit(0);
            }
        }

        if (studentNumber != null) {

            q.fetchRandomQuestions(studentNumber);
            boolean examInProgress = true;
            while (examInProgress) {
                q.displayCurrentQuestion();
                q.recordCurrentQuestion(studentNumber);
                q.Menu();
                int userChoice = in.nextInt();
                in.nextLine();
                
                if (userChoice == 1) {
                	q.prevCurrentQuestion();
                } else if (userChoice == 2) {
                	q.nextCurrentQuestion();
                } else if (userChoice == 3) {
                	examInProgress = false;
                	System.out.println("System exit.");
                } else {
                	System.out.println("Please selecet only from 1-3");
                }
            }
        }
        in.close();
    }
    
    public static String signUp(Scanner in) {
    	Question q = new Question();
        System.out.println("SIGN UP AN ACCOUNT TO TAKE THE EXAM");
        System.out.print("Enter Student Number: ");
        String studentNumber = in.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = in.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Enter Password: ");
        String password = in.nextLine();
        if (q.registerStudent(studentNumber, firstName, lastName, password)) {
            System.out.println("Registration successful!");
            return studentNumber;
        } else {
            System.out.println("Registration failed.");
            return null;
        }
    }

    public static String login(Scanner in) {
    	Question q = new Question();
        System.out.println("LOGIN YOUR ACCOUNT");
        System.out.print("Enter Student Number: ");
        String studentNumber = in.nextLine();
        System.out.print("Enter Password: ");
        String password = in.nextLine();
        if (q.authenticateStudent(studentNumber, password)) {
            System.out.println("Login successful!");
            return studentNumber;
        } else {
            System.out.println("Login failed. Exiting exam.");
            return null;
        }
    }
}
