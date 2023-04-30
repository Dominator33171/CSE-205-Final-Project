import java.util.Arrays;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Table {
	Scanner scnr = new Scanner(System.in);
	Connection c;
	Statement stmt;
	ResultSet resultSet;
	
	String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", 
			"l", "m","n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
			"1", "2", "3","4","5","6","7","8","9","0"};
	
	
    
    private String username;
    private String password;
    private String room;
    
    
    public static String Name;
    static volatile boolean finished = false;
    
    

	
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
		                break;
		            }
		        }
		    }
		    return a;
		}
	
	public void createRoom(String tableName) throws SQLException {
		boolean exists = tableExists(this.c, tableName);
		
		if(!exists) {
			
		
		try {
			
			this.stmt = this.c.createStatement();
			String sql = "CREATE TABLE " +tableName +
			"(ID INT PRIMARY KEY NOT NULL," +
			" USERNAME TEXT NOT NULL," +
			"Message TEXT NOT NULL)";
			this.stmt.executeUpdate(sql);
			this.stmt.close();
			
			
		}catch(Exception e) {
		
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": " + e.getMessage());
			System.exit(0);
		}
		}
	}
	
	public void createRoomsTable() throws SQLException {
		boolean exists = tableExists(this.c, "rooms");
		
		if(!exists) {
		
			try {
				this.stmt = this.c.createStatement();
				String sql = "CREATE TABLE ROOMS" +
				"(ID INT PRIMARY KEY NOT NULL," +
				" ROOM TEXT NOT NULL)";
				this.stmt.executeUpdate(sql);
				this.stmt.close();
				
				
			}catch(Exception e) {
			
				e.printStackTrace();
				System.err.println(e.getClass().getName()+ ": " + e.getMessage());
				System.exit(0);
			}

		}
	}
	
	public void createAccountsTable() throws SQLException {
		boolean exists = tableExists(this.c, "accounts");
		
		if(!exists) {
					
		
			try {
				this.stmt = this.c.createStatement();
				String sql = "CREATE TABLE ACCOUNTS" +
						"(ID INT PRIMARY KEY NOT NULL," +
						" USERNAME TEXT NOT NULL," +
						"PASSWORD TEXT NOT NULL)";
				this.stmt.executeUpdate(sql);
				this.stmt.close();
								
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
				this.username = scnr.nextLine();
				
							
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
			Scanner scnr = new Scanner(System.in);
			String password = null;
			String table = "accounts";
			String column = "username";
			
			System.out.print("Username: ");
			this.username = scnr.nextLine();
			
					
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
							System.out.println("\nPassword is incorrect.\nPlease try again.");
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
		
		public void setUsername(String name) {
			this.username = name;
		}
		
		public String getPassword() {
			return this.password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		//Inserts a new name for a chat room that only contains lower case letters and numbers.
		//It only inserts names for chat rooms that are not already found in the table, rooms.
		public void Create() {
			
			String table = "rooms";
			String insert = "INSERT INTO " +table+ " VALUES (?, ?)";
			String column = "room";
			
			Scanner scnr = new Scanner(System.in);
			
			try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
				
				int count = counter(table);
				
				System.out.print("Please name the chat room.\nThe name can only contain lower case letters and numbers.");
				System.out.println("\n-----------------------------------------");
				
				System.out.print("\nName: ");
				
				

				String name = scnr.nextLine();
				
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
				
				System.out.println("\nWelcome to the chat room called " + name + "!!!");
				System.out.println("You are now viewing this chat window: " + name + "\n");
				this.room = name;
				
				createRoom(name);

				chatFeature();
				
				
				
	            
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
		
		
		public ArrayList<String> inputCheck() {
			ArrayList<String> list = new ArrayList<String>();
			try {
				
				this.stmt = this.c.createStatement();
				this.resultSet = this.stmt.executeQuery("SELECT * FROM " + getRoom());
				
				while( resultSet.next()) {
					
					String nameOther = resultSet.getString("username");
					String input = resultSet.getString("message");
					
					list.add(nameOther + ": " + input);
					
					
				
			
									
				}
				return list;
				}catch(Exception e) {
					e.printStackTrace();
					return list;
					
				}
			

		}
		
		
		public void chatFeature() {
						
			Scanner scnr = new Scanner(System.in);
			ArrayList<String> list = inputCheck();
			
			int count = counter(getRoom());	
			int i = count ;
			
					

			
			    while(true) {
			    	 System.out.print(this.username + ": ");
			    	 
					 String message = scnr.nextLine();
                 
			    				    	

    				 
    				 
    				 
    				 try {
							
							this.stmt = this.c.createStatement();
							this.resultSet = this.stmt.executeQuery("SELECT * FROM " + getRoom());
							
							while( resultSet.next()) {
								int id = resultSet.getInt("id");
								String nameOther = resultSet.getString("username");
								String input = resultSet.getString("message");
								
								if(id>i && (!nameOther.equals(getUsername()))) {
									
									System.out.println(nameOther + ": " + input);
									

								}
												
							}
							i = counter(getRoom()) ;
							}catch(Exception e) {
								e.printStackTrace();
								
							}
    				
    				 
    				 
    			    				
    				 
    				 
    				 
    				

    				 if (message.startsWith("/")) {
    					 
    					 	ArrayList<String> otherList = new ArrayList<String>();
    					 	list = otherList;
    		                handleCommand(getUsername(), message);
    		                break;
    		                
    		                
    		            } else {
    		            	    	                
    	                String insert = "INSERT INTO "+getRoom()+" VALUES (?, ?, ?)";
    	                

    	    			try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
    	    				
    	    				
    	    				

    	    					prepStmt.setInt(1, counter(getRoom()));
    	    					prepStmt.setString(2, this.username);
    	    					prepStmt.setString(3, message);
    	    					
    	    					list.add(getUsername() + ": " + message);
    	    				
    	    					prepStmt.executeUpdate();
    	    				
    	    					
    	    						
    	                        

    	    				    				
    	    			} catch (SQLException e) {
    	    				e.printStackTrace();
    	    			}
    	    			
    	    			    		            }
                 }

    	            
		}
		
                         
		

		public void handleCommand(String username, String command) {
	        if (command.equals("/list")) {
	            // Print a list of users currently in the chat room
	        	ArrayList<String> allUsers = new ArrayList<String>();
	            System.out.println("\n\nUsers in this chat room:\n");
	            
	            
	            try {
					
					this.stmt = this.c.createStatement();
					this.resultSet = this.stmt.executeQuery("SELECT * FROM " +getRoom());
					
					while( resultSet.next()) {
						String name = resultSet.getString("username");
						
						
						
						boolean a = true;
						for(int i = 0; i < allUsers.size(); ++i) {
							if(name.equals(allUsers.get(i))) {
								a = false;
							}
						}
						if(a == true) {
							allUsers.add(resultSet.getString("username"));
						}
										
					}
					for(int i = 0; i < allUsers.size(); i++) {
						System.out.println("-" +allUsers.get(i));
					}
	            }
					catch(Exception e) {
						e.printStackTrace();
						
					}
	            System.out.println();
	            chatFeature();
		
	        }
	         else if (command.equals("/leave")) {
	            // Remove the user from the chat room
	          //  users.put(username, false);
	            System.out.println("\nYou have left the chat room.\n");
	        } else if (command.equals("/history")) {
	            // Print all past messages for the chat room
	        	System.out.println("\n\nChat history: \n");
	            	            
	            try {
					
					this.stmt = this.c.createStatement();
					this.resultSet = this.stmt.executeQuery("SELECT * FROM " + getRoom());
					
					while( resultSet.next()) {
						
						String name = resultSet.getString("username");
						String input = resultSet.getString("message");
						
						System.out.println("-"+name + ": " + input);
										
					}
					}catch(Exception e) {
						e.printStackTrace();
						
					}
	            System.out.println("\n");
	            chatFeature();

	        } else if (command.equals("/help")) {
	            // Print the list of available commands
	            System.out.println("\n\nAvailable commands:\n");
	            System.out.println("/list - Returns a list of users currently in this chat room.");
	            System.out.println("/leave - Exits the chat room.");
	            System.out.println("/history - Prints all past messages for the chat room.");
	            System.out.println("/help - Prints the list of available commands.\n\n");
	            
	            chatFeature();
	        } else {
	            // Invalid command
	            System.out.println("\nInvalid command. Type /help for a list of available commands.\n");
	            chatFeature();
	        }
	    }
		
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
