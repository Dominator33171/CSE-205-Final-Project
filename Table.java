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
    
    private String username;
    private String password;
    private String room;
    

	
	public Table() {
		this.c = null;
		this.stmt = null;
		this.resultSet = null;
		
		this.username = null;

		
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
	
	
	public boolean tableExists(Connection c, String tableName) throws SQLException {
		    boolean a = false;
		    try (ResultSet rs = c.getMetaData().getTables(null, null, tableName, null)) {
		        while (rs.next()) { 
		            String name = rs.getString("TABLE_NAME");
		            if (name != null && name.equals(tableName)) {
		                a = true;
		                System.out.println("Exists");
		                break;
		            }
		        }
		    }
		    return a;
		}
	
	public void createRoom(String tableName) throws SQLException {
		boolean exists = tableExists(this.c, tableName);
		
		if(exists) {
			System.out.println("This table already exists");
		}
		else {
			
		
		try {
			
			this.stmt = this.c.createStatement();
			String sql = "CREATE TABLE " +tableName +
			"(ID INT PRIMARY KEY NOT NULL," +
			" USERNAME TEXT NOT NULL," +
			"Message TEXT NOT NULL)";
			this.stmt.executeUpdate(sql);
			this.stmt.close();
			//this.c.close();
			//System.out.println("Table has been created.");
			
		}catch(Exception e) {
		
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": " + e.getMessage());
			System.exit(0);
		}
		}
	}
	
	public void createRoomsTable() throws SQLException {
		boolean exists = tableExists(this.c, "rooms");
		
		if(exists) {
		//	System.out.println("This table already exists");
		}
		else {
			
		
			try {
				this.stmt = this.c.createStatement();
				String sql = "CREATE TABLE ROOMS" +
				"(ID INT PRIMARY KEY NOT NULL," +
				" ROOM TEXT NOT NULL)";
				this.stmt.executeUpdate(sql);
				this.stmt.close();
				//this.c.close();
			//	System.out.println("Table has been created.");
				
			}catch(Exception e) {
			
				e.printStackTrace();
				System.err.println(e.getClass().getName()+ ": " + e.getMessage());
				System.exit(0);
			}

		}
	}
	
	public void createAccountsTable() throws SQLException {
		boolean exists = tableExists(this.c, "accounts");
		
		if(exists) {
			//System.out.println("This table already exists");
		}
		else {
			
		
			try {
				this.stmt = this.c.createStatement();
				String sql = "CREATE TABLE ACCOUNTS" +
						"(ID INT PRIMARY KEY NOT NULL," +
						" USERNAME TEXT NOT NULL," +
						"PASSWORD TEXT NOT NULL)";
				this.stmt.executeUpdate(sql);
				this.stmt.close();
				//this.c.close();
				//System.out.println("Table has been created.");
				
			}catch(Exception e) {
			
				e.printStackTrace();
				System.err.println(e.getClass().getName()+ ": " + e.getMessage());
				System.exit(0);
			}

		}
	}

	
	
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
		
		public void Register() throws SQLException{
			
			createAccountsTable();
			String column = "username";
			String table = "accounts";
			
			String insert = "INSERT INTO accounts VALUES (?, ?, ?)";
			
			try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
				
				int count = counter(table);
				
				System.out.print("Username: ");
				this.username = scnr.next();
				
							
				if(duplicate(this.username, table, column)==false) {
					System.out.print("Password: ");
					String password = scnr.next();

					prepStmt.setInt(1, count);
					prepStmt.setString(2, this.username);
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
		
		public void LoggingIn() throws SQLException {
			createAccountsTable();
			
			try {
			String password = null;
			String table = "accounts";
			String column = "username";
			
			System.out.print("Username: ");
			this.username = scnr.next();
			
					
			if(duplicate(this.username, table,column )==true) {
				
				System.out.print("Password: ");
				password = scnr.next();
				String correctPassword = null;
				
				boolean check = false;
				
				this.stmt = this.c.createStatement();
				this.resultSet = this.stmt.executeQuery("SELECT * FROM accounts");
				
				while( this.resultSet.next() && check == false) {
					if(this.resultSet.getString("username").equals(this.username)) {
						 correctPassword = resultSet.getString("password");
						 
						 check = true;
					}
					
				}
				

				if(password.equals(correctPassword)) {
							System.out.println("\nWelcome " + this.username + "!!!");
							this.password = password;
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
		
		public String getUsername() {
			return this.username;
		}
		
		public String getPassword() {
			return this.password;
		}
		//Inserts a new name for a chat room that only contains lower case letters and numbers.
		//It only inserts names for chat rooms that are not already found in the table, rooms.
		public void Create() {
			
			String table = "rooms";
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
						System.out.println("Sucessfully created new chat room.");
						System.out.println();
						
					}
					else {
						System.out.println();
						System.out.println("This chat room already exists.");
						
						System.out.println();
						Create();
						
					}
				}
				else {
					System.out.println("\nThe chat room name does not meet the criteria.\n");	
					Create();
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
				
				System.out.println("\nWelcome to the chat room called " + name + "!!!\n");
				System.out.println("You are now viewing this chat window: " + name + "\n");
				this.room = name;
				
				createRoom(name);

				chatFeature(this.username, name);
				
				
				
	            
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
		
		
		public String getRoom() {
			return this.room;
		}
		public void chatFeature(String name, String table) {
			//Check if the message is a command
			System.out.print(name + ": ");
			String message = scnr.next();
			
			 users.put(name, true);
            if (message.startsWith("/")) {
                handleCommand(name, message);
            } else {
                // Add the message to the chat history
                chatHistory.add(name + ": " + message);
                

                // Print the message to all users in the chat room
                /*
                for (String user : users.keySet()) {
                    if (users.get(user)) {
                        System.out.println(name + ": " + message);
                    }
                }
                */
                
                String insert = "INSERT INTO "+getRoom()+" VALUES (?, ?, ?)";
    			
    			try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
    				
    				int count = counter(table);
    				
    				

    					prepStmt.setInt(1, count);
    					prepStmt.setString(2, this.username);
    					prepStmt.setString(3, message);
    				
    					prepStmt.executeUpdate();
    				
    				
    				    				
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    			
    			
                chatFeature(name, getRoom());
            }
		}

		public void handleCommand(String username, String command) {
	        if (command.equals("/list")) {
	            // Print a list of users currently in the chat room
	            System.out.println("Users in this chat room:");
	            
	            
	            try {
					
					this.stmt = this.c.createStatement();
					this.resultSet = this.stmt.executeQuery("SELECT * FROM " +getRoom());
					
					while( resultSet.next()) {
						int id = resultSet.getInt("id");
						String name = resultSet.getString("username");
						String input = resultSet.getString("message");
						
						
						
						boolean a = false;
						for(int i = 0; i < chatHistory.size(); i++) {
							if(i!= 0 && chatHistory.get(i).equals(chatHistory.get(i-1))) {
								a = false;
							}
						}
						if(a == true) {
							chatHistory.add(resultSet.getString("username"));
						}
										
					}
					for(int i = 0; i < chatHistory.size(); i++) {
						System.out.println(chatHistory.get(i));
					}
	            }
					catch(Exception e) {
						e.printStackTrace();
						
					}
		
	        }
	         else if (command.equals("/leave")) {
	            // Remove the user from the chat room
	            users.put(username, false);
	            System.out.println("You have left the chat room.");
	        } else if (command.equals("/history")) {
	            // Print all past messages for the chat room
	        	System.out.println("\nChat history: \n");
	            	            
	            try {
					
					this.stmt = this.c.createStatement();
					this.resultSet = this.stmt.executeQuery("SELECT * FROM " + getRoom());
					
					while( resultSet.next()) {
						int id = resultSet.getInt("id");
						String name = resultSet.getString("username");
						String input = resultSet.getString("message");
						
						System.out.println(name + ": " + input);
										
					}
					}catch(Exception e) {
						e.printStackTrace();
						
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
		/*
		public void updatePart(String name) {
			String update1 = "UPDATE accounts SET username = ? WHERE id = ? ";
			
			try(PreparedStatement result = c.prepareStatement(update1)){
				result.setString(id: 0, username: "other");
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			try(stmt = c.Statement(update1)){
				
				String test = "test";
				String pass = "temp";
				
				
				prepStmt.setInt(1, 11);
				prepStmt.setString(2, test);
				prepStmt.setString(3, pass);
				
				prepStmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		*/
		public int number(String input) {
			int count = 0;
			try {
				
				this.stmt = this.c.createStatement();
				this.resultSet = this.stmt.executeQuery("SELECT * FROM accounts");
				
				while( resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("username");
					if(input.equals(name)) {
						count = id;
					}
					
									
				}
				return count;
				}catch(Exception e) {
					e.printStackTrace();
					count = -1;
					return count;
				}
		}
		
		


	
}
