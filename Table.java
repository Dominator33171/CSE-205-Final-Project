import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.util.*;

public class Table {
	Scanner scnr = new Scanner(System.in);
	Connection c;
	Statement stmt;
	ResultSet resultSet;
	
	String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", 
			"l", "m","n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
			"1", "2", "3","4","5","6","7","8","9","10"};
	
	private static ArrayList<String> chatHistory = new ArrayList<>();
    private static HashMap<String, Boolean> users = new HashMap<>();

	
	public Table() {
		this.c = null;
		this.stmt = null;
		this.resultSet = null;

		
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/usersdb",
					"postgres","postgres");
			System.out.println("Connected to the database.");
		}catch(Exception e) {
		
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": " + e.getMessage());
			System.exit(0);
		}


	}
	
	/*
	public void creatTable(String name) {
		IF NOT EXISTS accounts;
		
		Begin
		END
		try {
			this.stmt = this.c.createStatement();
			String sql = "CREATE TABLE name" +
			"(ID INT PRIMARY KEY NOT NULL," +
			" USERNAME TEXT NOT NULL," +
			"PASSWORD TEXT NOT NULL)";
			this.stmt.executeUpdate(sql);
			this.stmt.close();
			this.c.close();
			//System.out.println("Table has been created.");
			
		}catch(Exception e) {
		
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": " + e.getMessage());
			System.exit(0);
		}
	}
*/
		public int counter(String table) {
				
				try {
				int count = 0;
				this.stmt = this.c.createStatement();
				this.resultSet = this.stmt.executeQuery("SELECT * FROM " + table);
				
				while( resultSet.next()) {
					count++;
				}
				return count;
				}catch(Exception e) {
					e.printStackTrace();
					return -1;
				}
			}
		
		public void Register(String table) {
			String column = "username";
			
			String insert = "INSERT INTO accounts VALUES (?, ?, ?)";
			
			try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
				
				int count = counter(table);
				
				System.out.print("Username: ");
				String name = scnr.next();
				
							
				if(duplicate(name, table, column)==false) {
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
					Register(table);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public boolean duplicate(String name, String table, String column) {
			boolean a = false;
			try {
				
				this.stmt = this.c.createStatement();
				this.resultSet = this.stmt.executeQuery("SELECT * FROM " + table);
				
				while( this.resultSet.next() && a == false) {
					if(this.resultSet.getString(column).equals(name)) {
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
		
		public void LoggingIn() {
			try {
			String name = null; 
			String password = null;
			String table = "accounts";
			String column = "username";
			
			System.out.print("Username: ");
			name = scnr.next();
			
					
			if(duplicate(name, table,column )==true) {
				
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
		//Inserts a new name for a chat room that only contains lower case letters and numbers.
		//It only inserts names for chat rooms that are not already found in the table, rooms.
		public void Create(String table) {
			
			
			String insert = "INSERT INTO " +table+ " VALUES (?, ?)";
			String column = "room";
			
			try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
				
				int count = counter(table);
				
				System.out.print("Please name the chat room.\nThe name can only contain lower case letters and numbers.");
				System.out.println("\n-----------------------------------------");
				System.out.print("\nName: ");

				String name = scnr.next();
				
				if(criteria(name)==true) {
				
					if(duplicate(name, table, column)==false) {
						
		
						prepStmt.setInt(1, count);
						prepStmt.setString(2, name);
						
					
						prepStmt.executeUpdate();
						System.out.println();
						System.out.println("Sucessfully created new chat room.\nWelecome to " + name + "!!!");
						System.out.println();
					}
					else {
						System.out.println();
						System.out.println("This chat room already exists.");
						
						System.out.println();
						Create(table);
					}
				}
				else {
					System.out.println("\nThe chat room name does not meet the criteria.\n");	
					Create(table);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//Checks to see if the chat name only contains lower case letters and numbers.
		public boolean criteria(String Name) {
			boolean b = true;
			String arr[] = new String [Name.length()];
			for(int x = 0; x < Name.length();x++) {
				arr[x] = Name.substring(x,x+1);
				
			}
			
			String other[] = new String[Name.length()];
			
			
			for(int x = 0; x < arr.length; x++) {
				for(int j = 0; j < this.letters.length; j++) {
						if(arr[x].equals(this.letters[j])) {
							other[x] = arr[x];
							
						}
						
					}
			}
			if(Arrays.equals(arr, other)) {
				b = true;
			}
			else {
				b = false;
			}
			
			return b;
		}

		public void Join() {
			try {
			String name = null; 
			String table = "rooms";
			String column = "room";
			
			System.out.print("Please enter the name of the chat room.");
			System.out.println("\n-----------------------------------------");
			System.out.print("\nName: ");
			name = scnr.next();
			
					
			if(duplicate(name, table, column)==true) {
				
				System.out.println("\nWelcome to the chat room called " + name + "!!!");
				
				String message = scnr.nextLine();

	            // Check if the message is a command
	            if (message.startsWith("/")) {
	                handleCommand(name, message);
	            } else {
	                // Add the message to the chat history
	                chatHistory.add(name + ": " + message);

	                // Print the message to all users in the chat room
	                for (String user : users.keySet()) {
	                    if (users.get(user)) {
	                        System.out.println(name + ": " + message);
	                    }
	                }
	            }
	        }
	    
		
			else {
				System.out.println("\nChat room does not exist or name was not entered correctly.\n");
				System.out.println();
				Join();
			}
			}catch(Exception e) {
				e.printStackTrace();
				
			}
	}

		public void handleCommand(String username, String command) {
	        if (command.equals("/list")) {
	            // Print a list of users currently in the chat room
	            System.out.println("Users in this chat room:");
	            for (String user : users.keySet()) {
	                if (users.get(user)) {
	                    System.out.println(user);
	                }
	            }
	        } else if (command.equals("/leave")) {
	            // Remove the user from the chat room
	            users.put(username, false);
	            System.out.println("You have left the chat room.");
	        } else if (command.equals("/history")) {
	            // Print all past messages for the chat room
	            for (String message : chatHistory) {
	                System.out.println(message);
	            }
	        } else if (command.equals("/help")) {
	            // Print the list of available commands
	            System.out.println("Available commands:");
	            System.out.println("/list - Returns a list of users currently in this chat room.");
	            System.out.println("/leave - Exits the chat room.");
	            System.out.println("/history - Prints all past messages for the chat room.");
	            System.out.println("/help - Prints the list of available commands.");
	        } else {
	            // Invalid command
	            System.out.println("Invalid command. Type /help for a list of available commands.");
	        }
	    }


	
}
