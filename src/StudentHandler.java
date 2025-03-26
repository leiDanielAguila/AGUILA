import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StudentHandler {
	Scanner in = new Scanner(System.in);
	Student student = new Student();
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String BLUE = "\033[1;36m";    // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
    
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }
    
    public void makeNewStudent() {
        boolean done = false;		
        while (!done) {
            System.out.println(BLUE + "\n\n\n\n--------Creating an account--------" + RESET);
            try {		
                int studentId = studentIdHandler();
                String firstName = firstNameHandler();
                String lastName = lastNameHandler();
                String password = passwordHandler();
                
                // Hash the password simply using SHA-256
                String hashedPassword = hashPassword(password);
                
                Student student = new Student(studentId, firstName, lastName, hashedPassword);
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");	        	
                String query = "insert into students (student_id, first_name, last_name, password) values (?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(query);
            
                ps.setInt(1, student.getStudentId());
                ps.setString(2, student.getFirstName());
                ps.setString(3, student.getLastName());
                ps.setString(4, student.getPassword()); // hashed password
                ps.executeUpdate();
                System.out.println(GREEN + "\n\n\n\n----Account created please login----" + RESET);
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
	
	
	
    public Student authenticateStudent() {
        boolean done = false;
        boolean returnNull = false;
        while (!done) {
            System.out.println(BLUE + "\n\n\n\n--------Log in--------" + RESET);
            System.out.print("Enter your student id: ");
            int studentId;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
                studentId = in.nextInt();
                in.nextLine();
                
                String checkIdQuery = "SELECT COUNT(*) AS count FROM students WHERE student_id = ?";
                PreparedStatement checkIdStmt = connection.prepareStatement(checkIdQuery);
                checkIdStmt.setInt(1, studentId);
                ResultSet checkIdRs = checkIdStmt.executeQuery();
                
                if (checkIdRs.next() && checkIdRs.getInt("count") == 0) {
                    System.out.println(RED + "-------Student ID does not exist in the database.\n" + RESET);
                    returnNull = true;
                    done = true;
                    continue;
                }
                
                if (studentId <= 0 ) {
                    System.out.println(RED + "-------Student ID must be positive.\n" + RESET);
                    continue;
                }
            } catch (Exception e) {
                 System.out.println("-------Please enter a valid student id.\n");
                 in.nextLine(); 
                 continue;
            }
            System.out.print("Enter your password: ");
            String password = in.nextLine();
            
            if (password.trim().isEmpty()) {
                System.out.println(RED + "-------Student password must not be empty.\n" + RESET);
                continue;
            }
            
            // Retrieve the hashed password from the database for this student id
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");
                String query = "select * from students where student_id = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, studentId);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    String hashedInputPassword = hashPassword(password);
                    
                    if (hashedInputPassword.equals(storedHashedPassword)) {
                        student.setStudentId(studentId);
                        student.setFirstName(rs.getString("first_name"));
                        student.setLastName(rs.getString("last_name"));
                        System.out.println(GREEN + "\n\n\n\n-----Login successful!-----\n" + RESET);
                        done = true;
                    } else {
                        System.out.println(RED + "----Invalid student ID or password. Please try again.----\n" + RESET);
                    }
                }
                rs.close();
                ps.close();
                connection.close();
            } catch (ClassNotFoundException e) {
                System.out.println("JDBC Driver not found.-------");
            } catch (SQLException e) {
                System.out.println("Database connection error.-------");
            }
        }
        if (returnNull) {
            return null;
        } else {
            return student;
        }
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
	                System.out.println(RED + "-------student id can not be less than 1. \n" + RESET);
	                continue;
	            }	        	
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AGUILA", "root", "1234");	        	
	    		String query = "select count(*) from students where student_id = ?"; // check for duplicates
	    		PreparedStatement ps = connection.prepareStatement(query);
	    		ps.setInt(1, input);
	        	ResultSet rs = ps.executeQuery();	      
	            
	        	if (rs.next() && rs.getInt(1) > 0) {
	            	System.out.println(RED + "\n\n\n\n------student id exist in the database select another.\n" + RESET);
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
					System.out.println(RED + "-------first name can not be empty.\n" + RESET);
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
					System.out.println(RED + "-------last name can not be empty.\\n" + RESET);
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
					System.out.println(RED + "-------password can not be empty.\n" + RESET);
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