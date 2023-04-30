package finalPrijects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.*;

public class ChatRoom {

	Scanner scnr = new Scanner(System.in);
	Connection c;
	Statement stmt;
	ResultSet resultSet;
	
	
	String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", 
			"l", "m","n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
			"1", "2", "3","4","5","6","7","8","9","10"};
	
	
	public ChatRoom() {
		c = null;
		stmt = null;
		resultSet = null;
	}
	
	public int counter() {
		
		try {
		int count = 0;
		this.stmt = this.c.createStatement();
		this.resultSet = this.stmt.executeQuery("SELECT * FROM rooms");
		
		while( resultSet.next()) {
			count++;
		}
		return count;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}


	public void Create() {
		
		
		String insert = "INSERT INTO rooms VALUES (?, ?)";
		
		try(PreparedStatement prepStmt = this.c.prepareStatement(insert)){
			
			int count = counter();
			
			System.out.print("Please name the chat room.\nThe name can only contain lower case letters and numbers.");
			System.out.println("\n-----------------------------------------");
			System.out.print("\nName: ");

			String name = scnr.next();
			
			if(criteria(name)==true) {
			
				if(duplicate(name)==false) {
					
	
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

	public boolean duplicate(String name) {
		boolean a = false;
		try {
			
			this.stmt = this.c.createStatement();
			this.resultSet = this.stmt.executeQuery("SELECT * FROM rooms");
			
			while( this.resultSet.next() && a == false) {
				if(this.resultSet.getString("room").equals(name)) {
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
	
	public void Join() {
		try {
		String name = null; 
		
		System.out.print("Please enter the name of the chat room.");
		System.out.println("\n-----------------------------------------");
		System.out.print("\nName: ");
		name = scnr.next();
		
				
		if(duplicate(name)==true) {
			
			System.out.println("\nWelcome to the chat room called " + name + "!!!");
			System.out.println("\nYou are now viewing this chat window: " + name); // new change "need to run if it works" also need to find way to get username
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
}
