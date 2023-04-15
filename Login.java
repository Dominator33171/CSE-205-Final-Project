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
			
						
			if(duplicate(name)==false) {
				System.out.print("Password: ");
				String password = scnr.next();

				prepStmt.setInt(1, count);
				prepStmt.setString(2, name);
				prepStmt.setString(3, password);
			
				prepStmt.executeUpdate();
				System.out.println("Sucessfully registered.");
				System.out.println();
			}
			else {
				System.out.println("Username already exists.");
				System.out.println();
				Register();
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
			
			while( this.resultSet.next() && a == false) {
				if(this.resultSet.getString("username").equals(name)) {
					a = true;
				}
				else {
					a =false;
				}
			}
			
			}catch(Exception e) {
				e.printStackTrace();
				
			}
		return a;
	}
	
	// Not finished with this method yet.
	public void LoggingIn() {
			try {
			String name = null; 
			String password = null;
			System.out.print("Username: ");
			name = scnr.next();
			
					
			if(duplicate(name)==true) {
				
				System.out.print("Password: ");
				password = scnr.next();
				String correctPassword = null;
				
				boolean check = false;
				
				this.stmt = this.c.createStatement();
				this.resultSet = this.stmt.executeQuery("SELECT * FROM accounts");
				
				while( this.resultSet.next() && check == false) {
					if(this.resultSet.getString("username").equals(name)) {
						 correctPassword = resultSet.getString("password");
						 
						 check = true;
					}
					
				}
				

				if(password.equals(correctPassword)) {
							System.out.println("Login successful");
							System.out.println();
						}
						else {
							System.out.println("Password is incorrect.");
							System.out.println();
							LoggingIn();
						}
				}
			else {
				System.out.println("Username is inccorect.");
				System.out.println();
				LoggingIn();
			}
			}catch(Exception e) {
				e.printStackTrace();
				
			}
	}
	
	
}
