public class Student {
	private int student_id;
	private String first_name, last_name, password;
	
	// Student Constructors
	public Student(int sid, String fn, String ln, String p) {
		this.student_id = sid;
		this.first_name = fn;
		this.last_name = ln;
		this.password = p;
	}
	
	public Student() {
		this.student_id = 0;
		this.first_name = "";
		this.last_name = "";
		this.password = "";
	}
	
	// getter methods
	
	public int getStudentId() {
		return this.student_id;
	}
	public String getFirstName() {
		return this.first_name;
	}
	public String getLastName() {
		return this.last_name;
	}
	public String getPassword() {
		return this.password;
	}
	
	// setter methods
	
	public void setStudentId(int sid) {
		this.student_id = sid;
	}
	public void setFirstName(String fn) {
		this.first_name = fn;
	}
	public void setLastName(String ln) {
		this.last_name = ln;
	}
	public void setPassword(String p) {
		this.password = p;
	}
}
