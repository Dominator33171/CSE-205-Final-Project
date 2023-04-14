import java.sql.*;
import java.util.Scanner;
import java.util.Scanner;

public class Login {
	Scanner scnr = new Scanner(System.in);
	Connection c;
	Statement stmt;
	ResultSet resultSet;
	
	
	public Login() {
		c = null;
		stmt = null;
		resultSet = null;
	}
	
	public int counter() {
		
		try {
		int count = 0;
		this.stmt = this.c.createStatement();
		this.resultSet = this.stmt.executeQuery("SELECT * FROM accounts");
		
		while( resultSet.next()) {
			count++;
		}
		return count;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void Register() {
		
		
		String insert = "INSERT INTO accounts VALUES (?, ?, ?)";
		
		try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
			
			int count = counter();
			
			System.out.print("Username: ");
			String name = scnr.next();
			
			System.out.print("Password: ");
			String password = scnr.next();
			
			if(duplicate(name)==false) {
			prepStmt.setInt(1, count);
			prepStmt.setString(2, name);
			prepStmt.setString(3, password);
			
			prepStmt.executeUpdate();
			}
			else {
				System.out.println("Username already exists.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean duplicate(String name) {
		boolean a = false;
		try {
			
			this.stmt = this.c.createStatement();
			this.resultSet = this.stmt.executeQuery("SELECT * FROM accounts");
			
			while( resultSet.next() && a != true) {
				if(this.resultSet.getString("username")==name) {
					a = true;
				}
				else {
					a =false;
				}
			}
			
			}catch(Exception e) {
				e.printStackTrace();
				a =false;
			}
		return a;
	}
	
	/* Not finished with this method yet.
	public void LoggingIn() {
		
	}
	*/
	
}
