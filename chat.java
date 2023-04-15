import java.sql.*;
import java.util.Scanner;


public class chat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scnr = new Scanner(System.in);
		
		
		Login one = new Login();
		
		
	
	try {
		Class.forName("org.postgresql.Driver");
		one.c = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/usersdb",
				"postgres","postgres");
		System.out.println("Connected to the database.");
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	
	//Creating the table for the accounts.
	/*
	try {
		one.stmt = one.c.createStatement();
		String sql = "CREATE TABLE ACCOUNTS" +
		"(ID INT PRIMARY KEY NOT NULL," +
		" USERNAME TEXT NOT NULL," +
		"PASSWORD TEXT NOT NULL)";
		one.stmt.executeUpdate(sql);
		one.stmt.close();
		one.c.close();
		//System.out.println("Table has been created.");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/
	
	System.out.println("Welcome to my chatbox");
	System.out.println("Please select from the following options: Register(R), Login(L), Quit(Q");
	
	String userInput = scnr.next();
	
	
	if(userInput.equals("R")) {
		
		one.Register();
			
	}
	else if(userInput.equals("L")) {
		
		one.LoggingIn();

	}
	
	}
}
