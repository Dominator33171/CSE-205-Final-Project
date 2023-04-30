package finalPrijects;
import java.sql.*; //with the GUI
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Chat {
	
	private static JPanel mainPanel, chatDisplayPanel, mainDisplayPanel;
	private static JFrame mainFrame, chatDisplayFrame, mainDisplayFrame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Table register = new Table();
		
		
		mainFrame = new JFrame("Celestial Cafe: Main");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		
		mainPanel.setBackground(new Color(49, 62, 88));
		mainPanel.setPreferredSize(new Dimension(550, 550));
		
		mainFrame.getContentPane().add(mainPanel);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	
	try {
		JLabel label;
		label = new JLabel("Welcome to Celestial Cafe!!!");
		
		
		mainPanel.add(label);
		
		mainFrame.getContentPane().add(mainPanel);
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		mainDisplay(register);
		
		}catch(Exception e) {
			
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": " + e.getMessage());
			System.exit(0);
			
		
			
		}
	
	
	
	
	}
	
	private static JButton register, login, quit, join, create, account, logout;
	private static Table n, n2;
	
	public static void chatDisplay(Table room) throws SQLException{
		
		n2 = room;
		
		chatDisplayFrame = new JFrame("Celestial Cafe: Chat Display");
		chatDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chatDisplayPanel = new JPanel();
		
		chatDisplayPanel.setBackground(new Color(49, 62, 88));
		chatDisplayPanel.setPreferredSize(new Dimension(550, 550));
				
		
		JLabel chatDisplayLabel, selectL;
		
		JPanel buttonChatPanel = new JPanel();
		
		buttonChatPanel.setBackground(new Color(89, 102, 164));
		buttonChatPanel.setPreferredSize(new Dimension(550, 50));
		
		chatDisplayLabel = new JLabel("Please select from the following options: Join, Create, Account, Logout");
		selectL = new JLabel("Select: ");
		
		join = new JButton("Join");
		create = new JButton("Create");
		account = new JButton("Account");
		logout = new JButton("Logout");
		
		ButtonListener listener = new ButtonListener();
		join.addActionListener(listener);
		create.addActionListener(listener);
		account.addActionListener(listener);
		logout.addActionListener(listener);
		
		buttonChatPanel.add(chatDisplayLabel);
		buttonChatPanel.add(selectL);
		buttonChatPanel.add(join);
		buttonChatPanel.add(create);
		buttonChatPanel.add(account);
		buttonChatPanel.add(logout);

		
		chatDisplayPanel.add(buttonChatPanel);
		chatDisplayFrame.getContentPane().add(chatDisplayPanel);
		
		chatDisplayFrame.pack();
		chatDisplayFrame.setVisible(true);
		
		
	}
	
	public static void mainDisplay(Table name) {
		
		n = name;
		
		mainDisplayFrame = new JFrame("Celestial Cafe: Main Display");
		mainDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainDisplayPanel = new JPanel();
		
		mainDisplayPanel.setBackground(new Color(49, 62, 88));
		mainDisplayPanel.setPreferredSize(new Dimension(550, 550));
		
		JPanel buttonMainPanel = new JPanel();
		
		buttonMainPanel.setBackground(new Color(176, 155, 199));
		buttonMainPanel.setPreferredSize(new Dimension(550, 50));
		
		
		JLabel mainDisplayLabel;
		
		//JButton register, login, quit;
				
		mainDisplayLabel = new JLabel("Please select from the following options: \n");
		register = new JButton("Register");
		login = new JButton("Login");
		quit = new JButton("Quit");
		
		ButtonListener listener = new ButtonListener();
		register.addActionListener(listener);
		login.addActionListener(listener);
		quit.addActionListener(listener);
		
		buttonMainPanel.add(mainDisplayLabel);
		buttonMainPanel.add(register);
		buttonMainPanel.add(login);
		buttonMainPanel.add(quit);
		
		mainDisplayPanel.add(buttonMainPanel);
		mainDisplayFrame.getContentPane().add(mainDisplayPanel);
		
		mainDisplayFrame.pack();
		mainDisplayFrame.setVisible(true);
	    

	}
	
	private static class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == register) {
				try {
					n.Register();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainDisplay(n);
					
			}
			else if(event.getSource() == login) {
				
				try {
					n.LoggingIn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					chatDisplay(n);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (event.getSource() == quit) {
				try {
					logOut(n);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(event.getSource() == join) {
				n2.Join();
			}
			else if(event.getSource() == create) {
				n2.Create();
				try {
					chatDisplay(n2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(event.getSource() == account) {
				try {
					update(n2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if(event.getSource() == logout) {
				try {
					logOut(n2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
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