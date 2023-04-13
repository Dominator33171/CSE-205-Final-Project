import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class Database {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scnr = new Scanner(System.in);
		Connection c = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		
		/*String Name;
		String Password;
		int Count = 0;
		
	*/
	
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
	
	//Creatibnng the table for the chat.
	/*
	try {
		stmt = c.createStatement();
		String sql = "CREATE TABLE CHAT" +
		"(ID INT PRIMARY KEY NOT NULL," +
		" USERNAME TEXT NOT NULL," +
		"PASSWORD TEXT NOT NULL)";
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
		//System.out.println("Table has been created.");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/
	
	//Creating a table with null values
	/*
	try {
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(0,'null', 'null');";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(1,'null', 'null');";		
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(2,'null', 'null');";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(3,'null', 'null');";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(4,'null', 'null');";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(5,'null', 'null');";
		stmt.executeLargeUpdate(sql);

		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(6,'null', 'null');";
		stmt.executeLargeUpdate(sql);

		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(7,'null', 'null');";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(8,'null', 'null');";
		stmt.executeLargeUpdate(sql);

		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(9,'null', 'null');";
		stmt.executeLargeUpdate(sql);

		sql = "INSERT INTO Chat("
				+ "ID,USERNAME,PASSWORD) " +
				"VALUES(10,'null', 'null');";
		stmt.executeLargeUpdate(sql);


		
		stmt.close();
		c.commit();
		c.close();
		System.out.println("Just added 11 elements to the table");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/
	
	
	//String update2 = "UPDATE chat SET password = ? WHERE id = ? ";
	
	//String update1 = "UPDATE chat SET username = ? WHERE id = ? ";
	
	//String stmtInsert = "INSERT INTO users VALUES(?, ?, ?)";
		
	
	//int number = 0;
	
	System.out.println("Welcome to my chatbox");
	System.out.println("Please select from the following options: Register(R), Login(L), Quit(Q");
	
	String userInput = scnr.next();
	/*
	number++;
	System.out.print("Username: ");
	String name = scnr.next();
	System.out.print("Password: ");
	String password = scnr.next();
	
	*/
	
	if(userInput.equals("R")) {
		String update1 = "UPDATE chat SET username = ? WHERE id = ? ";
		String update2 = "UPDATE chat SET password = ? WHERE id = ? ";

			try(PreparedStatement prepStmt = c.prepareStatement(update1)){
				Scanner other = new Scanner(System.in);
				System.out.print("Username: ");
				String name = other.next();
				prepStmt.setString(1, name);
				prepStmt.setInt(2, 0);
			    prepStmt.executeUpdate();
				
				other.close();
				
			
			}catch(Exception e) {
				
				e.printStackTrace();
				System.err.println(e.getClass().getName()+ ": " + e.getMessage());
				System.exit(0);
			}

			
			try(PreparedStatement OtherStmt = c.prepareStatement(update2)){
				Scanner scanner = new Scanner(System.in);
				System.out.print("Password: ");
				String password = scanner.next();
				OtherStmt.setString(1, password);
				OtherStmt.setInt(2, 0);
				//resultSet = prepStmt.executeQuery();
				OtherStmt.executeUpdate();

				
				scanner.close();
			
			}catch(Exception e) {
				
				e.printStackTrace();
				System.err.println(e.getClass().getName()+ ": " + e.getMessage());
				System.exit(0);
			}



	
	}
	}	
	
	// Creating table
	/*
	try {
		stmt = c.createStatement();
		String sql = "CREATE TABLE COMPANY" +
		"(ID INT PRIMARY KEY NOT NULL," +
		" NAME TEXT NOT NULL," +
		"AGE INT NOT NULL," +
		" ADDRESS CHAR(5), "+
		" SALARY REAL)";
		stmt.executeUpdate(sql);
		stmt.close();
		c.close();
		System.out.println("Table has been created.");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/
	
	/*
	try {
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sql = "INSERT INTO COMPANY("
				+ "ID,NAME,AGE,ADDRESS,SALARY) " +
				"VALUES(1,'Mike', 37, 'Cali', 2.00);";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO COMPANY("
				+ "ID,NAME,AGE,ADDRESS,SALARY) " +
				"VALUES(2,'Tina', 37, 'California', 20000.00);";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO COMPANY("
				+ "ID,NAME,AGE,ADDRESS,SALARY) " +
				"VALUES(3,'Sam', 37, 'California', 20000.00);";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO COMPANY("
				+ "ID,NAME,AGE,ADDRESS,SALARY) " +
				"VALUES(4,'Bob', 37, 'California', 20000.00);";
		stmt.executeLargeUpdate(sql);
		
		sql = "INSERT INTO COMPANY("
				+ "ID,NAME,AGE,ADDRESS,SALARY) " +
				"VALUES(5,'Sarah', 37, 'California', 20000.00);";
		stmt.executeLargeUpdate(sql);

		
		stmt.close();
		c.commit();
		c.close();
		System.out.println("Just added 5 elements to the table");
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	
	*/
	//Select
	/*
	try {
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("select * from company;");
		
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String address = rs.getString("address");
			float salary = rs.getFloat("salary");
			
			System.out.println("ID: " + id);
			System.out.println("Name: " + name);
			System.out.println("Salary: " + salary);
			System.out.println();
		}
		
		rs = stmt.executeQuery("select name, age from company where salary < 10.00;");
		
		while(rs.next()) {
			
			String name = rs.getString("name");
			int age = rs.getInt("age");
			
			System.out.println("Name: " + name);
			System.out.println("Age: " + age);
			
			System.out.println();
		}
		
		
		System.out.println("Done...");
		rs.close();
		stmt.close();
		c.close();
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	
	*/
	//Update
	/*
	try {
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sql = "UPDATE COMPANY set SALARY = 30000.00 where ID = 3;";
		stmt.executeUpdate(sql);
		c.commit();
		
		ResultSet rs = stmt.executeQuery("select * from company;");
		
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String address = rs.getString("address");
			float salary = rs.getFloat("salary");
			
			System.out.println("ID: " + id);
			System.out.println("Name: " + name);
			System.out.println("Salary: " + salary);
			System.out.println();
		}
		rs.close();
		stmt.close();
		c.close();
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/
	
	//Delete
	/*
	try {
		c.setAutoCommit(false);
		stmt = c.createStatement();
		String sql = "DELETE from COMPANY where ID = 4;";
		stmt.executeUpdate(sql);
		c.commit();
		
		ResultSet rs = stmt.executeQuery("select * from company;");
		
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int age = rs.getInt("age");
			String address = rs.getString("address");
			float salary = rs.getFloat("salary");
			
			System.out.println("ID: " + id);
			System.out.println("Name: " + name);
			System.out.println("Salary: " + salary);
			System.out.println();
		}
		rs.close();
		stmt.close();
		c.close();
		
	}catch(Exception e) {
	
		e.printStackTrace();
		System.err.println(e.getClass().getName()+ ": " + e.getMessage());
		System.exit(0);
	}
	*/


	
	/*
	public static void LoggingIn(String name, String password) {
		String update1 = "UPDATE chat SET username = ? WHERE id = ? ";
		
		int count = 1;
		Scanner scnr = new Scanner(System.in);
		
	
		System.out.println("Welcome to my chatbox");
		System.out.println("Please select from the following options: Register(R), Login(L), Quit(Q");
		
		String userInput = scnr.next();
		
		System.out.print("Username: ");
		name = scnr.next();
		System.out.print("Password: ");
		password = scnr.next();
		
		try(PreparedStatement prepStmt = connection.prepareStatement(update1)){
			prepStmt.setString(parameterIndex: count, x: name);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		

	}
	*/
}


