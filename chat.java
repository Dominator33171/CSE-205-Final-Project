import java.sql.*;
import java.util.Scanner;


public class chat {

	public static void main(String[] args) {
		
		
		Table register = new Table();
		
		
		
		
				
	
	try {
	register.c.close();

	System.out.println("Welcome to my chat room!!!");
	System.out.println();
	
	mainDisplay(register);
	
	}catch(Exception e) {
		
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
		
	
		
	}
	
	
	}
	
	public static void chatDisplay(Table room) throws SQLException{
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
			chatDisplay(room);
			
		}
		else if(selection.equals("A")) {
			update(room);
		}
		
		else if(selection.equals("L")) {
		
		logOut(room);
		
		}
	}
	
	
	public static void mainDisplay(Table name) throws SQLException {
		
		
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
			chatDisplay(name);

		}
		else if (input.equals("Q")) {
			logOut(name);
			name.c.close();
		}

	}
	
	
	public static void update(Table name) throws SQLException{
		Scanner scnr = new Scanner(System.in);
		
		
		
		
			System.out.print("Change (U)sername or (P)assword: ");
			String input = scnr.next();
			
			if (input.equals("U")) {
				
				System.out.print("Enter your new username(enter quit to leave): ");
				String newUser = scnr.next();
				
				if (newUser.equals("quit")) {
					chatDisplay(name);
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
					chatDisplay(name);
				}
				
			}
			
			else if (input.equals("P")){
								
				System.out.print("Enter your new password(enter quit to leave): ");
				String newPassword = scnr.next();
				
				if (newPassword.equals("quit")) {
					chatDisplay(name);
				}
				
				else {
					int idNum = name.number(name.getUsername());
					String preparedUpdate = "UPDATE accounts SET password = ? WHERE id = ? ";
					
					try(PreparedStatement prepstmt = name.c.prepareStatement(preparedUpdate)) {
						prepstmt.setString(1, newPassword);
						prepstmt.setInt(2, idNum);
						prepstmt.executeUpdate();
						System.out.println("\nPassword changed!\n");
						chatDisplay(name);
						
					
						
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
