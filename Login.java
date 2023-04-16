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
	
	//Counts the number of items within the data table called accounts.
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
	
	/*Inputs a new username with password into the data table(accounts). The username will only be inputted if 
	 * it does not already exist in the data table (accounts);
	*/
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
				System.out.println();
				System.out.println("Sucessfully registered.");
				System.out.println();
			}
			else {
				System.out.println();
				System.out.println("Username already exists.\nPlease enter a different username.");
				
				System.out.println();
				Register();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Returns true if the username already exists within the data table(accounts).
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
	
	/*Checks whether or not the username and password inputted by the user exists in the data table.*/
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
							System.out.println("\nWelcome " + name + "!!!");
							System.out.println();
						}
						else {
							System.out.println("Password is incorrect.\nPlease try again.");
							System.out.println();
							LoggingIn();
						}
				}
			else {
				System.out.println("\nUsername is inccorect.\n");
				System.out.println("Please enter your username.");
				System.out.println("-----------------------------------------\n");
				
				
				LoggingIn();
			}
			}catch(Exception e) {
				e.printStackTrace();
				
			}
	}
	
	
}
