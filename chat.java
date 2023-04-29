import java.sql.*;
import java.util.Scanner;


public class chat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Scanner scnr = new Scanner(System.in);
		//String userInput = null;
		
		Table register = new Table();
		
		
		
		
		
		
	/*
	try {
		Class.forName("org.postgresql.Driver");
		cOther = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/usersdb",
				"postgres","postgres");
		System.out.println("Connected to the database.");
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	
	//Creates the table for the accounts.
	
	try {
		stmt = cOther.createStatement();
		String sql = "CREATE TABLE HISTORY" +
		"(ID INT PRIMARY KEY NOT NULL," +
		" USERNAME TEXT NOT NULL," +
		"MESSAGE TEXT NOT NULL)";
		stmt.executeUpdate(sql);
		stmt.close();
		cOther.close();
		//System.out.println("Table has been created.");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/
	
	//Connecting to the database for Rooms table;
		/*
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
	*/
	
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

	try {
	System.out.println("Welcome to my chat room!!!");
	System.out.println();
	
	mainDisplay(register);
	
	}catch(Exception e) {
		
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
		
	
		
	}
	
	
	}
	
	public static void chatDisplay(Table room, String table) throws SQLException{
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
			chatDisplay(room, table);
			
		}
		else if(selection.equals("A")) {
			update(room);
		}
		
		else if(selection.equals("L")) {
		logOut(room);
		}
	}
	
	
	public static void mainDisplay(Table name) throws SQLException {
		
		String tableOne = "accounts";
		String tableTwo = "rooms";
		
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("Please select from the following options: ");
		System.out.println("Register(R), Login(L), Quit(Q)");
		
		System.out.println("-----------------------------------------");
		System.out.println();
		System.out.print("Select: ");
		
		String input = scnr.next();
		
		System.out.println();
		
		
		if(input.equals("R")) {
			
			name.Register();
			mainDisplay(name);
				
		}
		else if(input.equals("L")) {
			
			name.LoggingIn();
			chatDisplay(name, tableTwo);

		}
		else if (input.equals("Q")) {
			logOut(name);
		}

	}
	/*
	public static void update(Table name) {
		Scanner scnr = new Scanner(System.in);
		String tableName = "accounts";
		String column = "username";
		
		try {
			System.out.print("Change (U)sername or (P)assword: ");
			String input = scnr.next();
			
			if (input.equals("U")) {
				System.out.print("Enter your current username: ");
				String currUser = scnr.next();
				
				System.out.print("Enter your new username(enter quit to leave): ");
				String newUser = scnr.next();
				
				if (newUser.equals("quit")) {
					chatDisplay(name, tableName);
				}
				
				else if (name.duplicate(newUser, tableName,column )) {
					System.out.println("Username already exists. Please choose a different username.");
					update(name);
				}
				
				else {
					String updateUser = "UPDATE accounts SET " +name +" = newUser WHERE "+name+" = currUser";
					name.stmt.executeQuery(updateUser);
					System.out.println("Username changed!");
					chatDisplay(name,tableName);
				}
				
			}
			
			else {
				System.out.print("Enter your current password: ");
				String currPassword = scnr.next();
				
				System.out.print("Enter your new password(enter quit to leave): ");
				String newPassword = scnr.next();
				
				if (newPassword.equals("quit")) {
					chatDisplay(name, tableName);
				}
				
				else {
					String updatePassword = "UPDATE accounts SET password = " +newPassword+" WHERE password = "+currPassword;
					name.stmt.executeQuery(updatePassword);
					System.out.println("Password changed!");
					chatDisplay(name, tableName);
				}
			}
		}
		
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	*/
	
	public static void update(Table name) throws SQLException{
		Scanner scnr = new Scanner(System.in);
		String tableName = "accounts";
		String column = "username";
		
		
		
			System.out.print("Change (U)sername or (P)assword: ");
			String input = scnr.next();
			
			if (input.equals("U")) {
				
				System.out.print("Enter your new username(enter quit to leave): ");
				String newUser = scnr.next();
				
				if (newUser.equals("quit")) {
					chatDisplay(name, tableName);
				}
				
				else if (name.duplicate(newUser, "accounts","username" )) {
					System.out.println("Username already exists. Please choose a different username.");
					update(name);
				}
				
				else {
					int idNum = name.number(name.getUsername());
					String preparedUpdate = "UPDATE accounts SET username = ? WHERE id = ? ";
					
					try(PreparedStatement prepstmt = name.c.prepareStatement(preparedUpdate)) {
						prepstmt.setString(1, newUser);
						prepstmt.setInt(2, idNum);
						prepstmt.executeUpdate();
					
						
					}catch(SQLException e) {
						e.printStackTrace();
					}
					
					System.out.println("Username changed!");
					chatDisplay(name,tableName);
				}
				
			}
			
			else if (input.equals("P")){
								
				System.out.print("Enter your new password(enter quit to leave): ");
				String newPassword = scnr.next();
				
				if (newPassword.equals("quit")) {
					chatDisplay(name, tableName);
				}
				
				else {
					int idNum = name.number(name.getUsername());
					String preparedUpdate = "UPDATE accounts SET password = ? WHERE id = ? ";
					
					try(PreparedStatement prepstmt = name.c.prepareStatement(preparedUpdate)) {
						prepstmt.setString(1, newPassword);
						prepstmt.setInt(2, idNum);
						prepstmt.executeUpdate();
						System.out.println("\nPassword changed!\n");
						chatDisplay(name, tableName);
						
					
						
					}catch(SQLException e) {
						e.printStackTrace();
					}

				}
			}
			
			else {
				System.out.println("Incorrect input. Please enter \"U\" or \"P\".");
				update(name);
			}
		
		
	}
	//Possible error
	public static void logOut(Table Name) throws SQLException{
		mainDisplay(Name);
	}
	
		
}
