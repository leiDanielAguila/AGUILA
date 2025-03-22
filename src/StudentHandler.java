import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentHandler {
	Student st = new Student();
	Scanner in = new Scanner(System.in);
	
	public void makeNewStudent() {
		boolean done = false;
		
		while (!done) {
			System.out.println("--------Creating an account--------");
		}
	}
	
	public int studentIdHandler() {
		boolean done = false;
		int studentId = 0;

		
		while(!done) {
	        System.out.print("Enter student id: ");
	        
	        try {
	        	int input = in.nextInt();
	        	
	        	if (input <= 0) {
	                System.out.println("student id can not be less than 1. \n");
	                continue;
	            }
	        	
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
	        	Statement st = connection.createStatement();
	        	
	    		String query = "select count(*) from students where student_id = ?"; // check for duplicates
	    		PreparedStatement ps = connection.prepareStatement(query);
	    		ps.setInt(1, input);
	        	ResultSet rs = ps.executeQuery();	      
	            
	        	if (rs.next() && rs.getInt(1) > 0) {
	            	System.out.println("student id exist in the database select another.\n");
	            } else {
	                done = true;
	                studentId = input;
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Please enter a valid integer.\n");
	            in.next(); 
	        } catch (ClassNotFoundException e) {
	            System.out.println("JDBC Driver not found.");
	        } catch (SQLException e) {
	            System.out.println("Database connection error.");
	        }
	    }
	    return studentId;
	}
	
	public String firstNameHandler() {
		boolean done = false;
		String firstName = "";
		while(!done) {
			System.out.print("Enter your first name: ");
			try {
				String input = in.nextLine();
				if (input.trim().isEmpty()) {
					System.out.println("first name can not be empty.");
					done = false;
				} else {
					firstName = input.toLowerCase();
					done = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid first name.\n");
				in.next();
			}
		}
		return firstName;
	}
	
	public String lastNameHandler() {
		boolean done = false;
		String lastName = "";
		while(!done) {
			System.out.print("Enter your last name: ");
			try {
				String input = in.nextLine();
				if (input.trim().isEmpty()) {
					System.out.println("last name can not be empty.");
					done = false;
				} else {
					lastName = input.toLowerCase();
					done = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid last name.\n");
				in.next();
			}
		}
		return lastName;
	}
	
	public String passwordHandler() {
		boolean done = false;
		String password = "";
		while(!done) {
			System.out.print("Enter your password: ");
			try {
				String input = in.nextLine();
				if (input.trim().isEmpty()) {
					System.out.println("password can not be empty.");
					done = false;
				} else {
					password = input.toLowerCase();
					done = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid password.\n");
				in.next();
			}
		}
		return password;
	}
}
