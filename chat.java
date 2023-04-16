import java.sql.*;
import java.util.Scanner;


public class chat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Scanner scnr = new Scanner(System.in);
		//String userInput = null;
		
		Login one = new Login();
		ChatRoom room = new ChatRoom();
		
	
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
	
	//Creates the table for the accounts.
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
	
	//Connecting to the database for Rooms table;
	try {
		Class.forName("org.postgresql.Driver");
		room.c = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/usersdb",
				"postgres","postgres");
		System.out.println("Connected to the database.");
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	
	
	//Creates the table for the chat rooms.
	/*
	try {
		room.stmt = room.c.createStatement();
		String sql = "CREATE TABLE ROOMS" +
		"(ID INT PRIMARY KEY NOT NULL," +
		" ROOM TEXT NOT NULL)";
		room.stmt.executeUpdate(sql);
		room.stmt.close();
		room.c.close();
		System.out.println("Table has been created.");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/

	
	System.out.println("Welcome to my chat room!!!");
	System.out.println();
	
	String input = mainDisplay();
	if(input.equals("R")) {
		
		one.Register();
			
	}
	else if(input.equals("L")) {
		
		one.LoggingIn();
		chatDisplay(room);

	}
	
	}
	
	public static void chatDisplay(ChatRoom room) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please select from the following options:\n(J)Join, (C)Create, (A)Account, (L)Logout\n-----------------------------------------\n");
		
		System.out.print("Select: ");
		String selection = scnr.next();
		System.out.println();
		if(selection.equals("J")) {
			room.Join();
		}
		else if(selection.equals("C")) {
			room.Create();
		}
	}
	
	public static String mainDisplay() {
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Please select from the following options: ");
		System.out.println("Register(R), Login(L), Quit(Q)");
		
		System.out.println("-----------------------------------------");
		System.out.println();
		System.out.print("Select: ");
		
		String input = scnr.next();
		
		System.out.println();
		return input;
	}
	
}
