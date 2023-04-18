import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Chat {

	private static ChatRoom room;
	private static Login one;
	static Statement stmt;
	
	public Chat() {
		room = new ChatRoom();
		one = new Login();
		stmt = null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Scanner scnr = new Scanner(System.in);
		//String userInput = null;
		
	
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

	
	mainDisplay();
	
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
		
		else if(selection.equals("A")) {
			update();
		}
		
		else if(selection.equals("L"));
		logOut();
	}
	
	public static void mainDisplay() {
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Welcome to my chat room!!!");
		System.out.println();
		
		System.out.println("Please select from the following options: ");
		System.out.println("Register(R), Login(L), Quit(Q)");
		
		System.out.println("-----------------------------------------");
		System.out.println();
		System.out.print("Select: ");
		
		String input = scnr.next();
		
		if(input.equals("R")) {
			
			one.Register();
				
		}
		else if(input.equals("L")) {
			
			one.LoggingIn();
			chatDisplay(room);

		}
		
		else if (input.equals("Q")) {
			logOut();
		}
	}
	
	public static void update() {
		Scanner scnr = new Scanner(System.in);
		try {
			System.out.print("Change (U)sername or (P)assword: ");
			String input = scnr.next();
			
			if (input.equals("U")) {
				System.out.print("Enter your current username: ");
				String currUser = scnr.next();
				
				System.out.print("Enter your new username(enter quit to leave): ");
				String newUser = scnr.next();
				
				if (newUser.equals("quit")) {
					chatDisplay(room);
				}
				
				else if (one.duplicate(newUser)) {
					System.out.println("Username already exists. Please choose a different username.");
					update();
				}
				
				else {
					String updateUser = "UPDATE accounts SET name = newUser WHERE name = currUser";
					stmt.executeQuery(updateUser);
					System.out.println("Username changed!");
					chatDisplay(room);
				}
				
			}
			
			else {
				System.out.print("Enter your current password: ");
				String currPassword = scnr.next();
				
				System.out.print("Enter your new password(enter quit to leave): ");
				String newPassword = scnr.next();
				
				if (newPassword.equals("quit")) {
					chatDisplay(room);
				}
				
				else {
					String updatePassword = "UPDATE accounts SET password = newPassword WHERE password = currPassword";
					stmt.executeQuery(updatePassword);
					System.out.println("Password changed!");
					chatDisplay(room);
				}
			}
		}
		
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void logOut() {
		mainDisplay();
	}
	
}
