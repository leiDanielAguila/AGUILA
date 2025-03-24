import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentHandler {
	Scanner in = new Scanner(System.in);
	Student student = new Student();
	
	public void makeNewStudent() { // make new student
		boolean done = false;		
		while (!done) {
			System.out.println("\n\n\n\n--------Creating an account--------");
			try {		
				int studentId = studentIdHandler();
				String firstName = firstNameHandler();
				String lastName = lastNameHandler();
				String password = passwordHandler();
				
				Student student = new Student(studentId, firstName, lastName, password);
				
				Class.forName("com.mysql.cj.jdbc.Driver");
	        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");	        	
				String query = "insert into students (student_id, first_name, last_name, password) values (?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(query);
			
				ps.setInt(1, student.getStudentId());
				ps.setString(2, student.getFirstName());
				ps.setString(3, student.getLastName());
				ps.setString(4, student.getPassword());
				ps.executeUpdate();
				System.out.println("\n\n\n\n----Account created please login----");
				done = true;
				ps.close();
				connection.close();
			} catch (ClassNotFoundException e) {
	            System.out.println("JDBC Driver not found.-------");
	        } catch (SQLException e) {
	            System.out.println("Database connection error.-------");
	        }
		}
	}
	
	public Student authenticateStudent() { // validate a login attempt
		boolean done = false;		
		while (!done) {
			System.out.println("\n\n\n\n--------Log in--------");
			System.out.print("Enter your student id: ");
			int studentId;
			try {
				studentId = in.nextInt();
				in.nextLine();
				if (studentId <= 0 ) {
	                System.out.println("-------Student ID must be positive.\n");
	                continue;
				}
			} catch (InputMismatchException e) {
				 System.out.println("-------Please enter a valid student id.\n");
		         in.nextLine(); 
		         continue;
			}
			System.out.print("Enter your password: ");
			String password;
			password = in.nextLine();
			
			if (password.trim().isEmpty()) {
				System.out.println("-------Student password must not be empty.\n");
				continue;
			}
			
			
			student.setStudentId(studentId);
			student.setPassword(password);
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
	        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
				String query = "select * from students where student_id = ? and password = ?";
				PreparedStatement ps = connection.prepareStatement(query);
				
				ps.setInt(1, student.getStudentId());
				ps.setString(2, student.getPassword());
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
					String first_name = rs.getString("first_name");
					student.setFirstName(first_name);
					System.out.println("\n\n\n\n-----Login successful!-----\n");
					done = true;
				} else {
					System.out.println("----Invalid student ID or password. Please try again.----\n");
	            }
				rs.close();
	            ps.close();
	            connection.close();
			} catch (ClassNotFoundException e) {
	            System.out.println("JDBC Driver not found.-------");
	        } catch (SQLException e) {
	            System.out.println("Database connection error.-------");
	        } catch (InputMismatchException e) {
	            System.out.println("-------Please enter a valid student id.\n");
	            in.next(); 
	        }
			
		}
		return student;
	}
	
	public Student getStudent() { // for returning authenticated student
		return this.student;
	}
	
	public int studentIdHandler() {
		boolean done = false;
		int studentId = 0;
		while(!done) {
	        System.out.print("Enter student id: ");	        
	        try {
	        	int input = in.nextInt();
	        	in.nextLine();	        	
	        	if (input <= 0) {
	                System.out.println("-------student id can not be less than 1. \n");
	                continue;
	            }	        	
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");	        	
	    		String query = "select count(*) from students where student_id = ?"; // check for duplicates
	    		PreparedStatement ps = connection.prepareStatement(query);
	    		ps.setInt(1, input);
	        	ResultSet rs = ps.executeQuery();	      
	            
	        	if (rs.next() && rs.getInt(1) > 0) {
	            	System.out.println("\n\n\n\n------student id exist in the database select another.\n");
	            } else {
	                done = true;
	                studentId = input;
	            }
	        	ps.close();
	        	connection.close();
	        } catch (InputMismatchException e) {
	            System.out.println("-------Please enter a valid integer.\n");
	            in.next(); 
	        } catch (ClassNotFoundException e) {
	            System.out.println("JDBC Driver not found.-------");
	        } catch (SQLException e) {
	            System.out.println("Database connection error.-------");
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
					System.out.println("-------first name can not be empty.\n");
					done = false;
				} else {
					firstName = input.toLowerCase();
					done = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("-------Please enter a valid first name.\n");
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
					System.out.println("-------last name can not be empty.\\n");
					done = false;
				} else {
					lastName = input.toLowerCase();
					done = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("-------Please enter a valid last name.\n");
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
					System.out.println("-------password can not be empty.\n");
					done = false;
				} else {
					password = input.toLowerCase();
					done = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("-------Please enter a valid password.\n");
				in.next();
			}
		}
		return password;
	}
}