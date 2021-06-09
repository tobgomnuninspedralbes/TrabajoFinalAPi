package main.java.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DatabaseConnection {
	String url = "";
	String user = "";
	String password = "";
	
	static final String DB_NAME = "saber_y_ganar";
	static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	static final String USERNAME = "nouusuari";
	static final String PASS = "Usuario_123454321";
	
	public static Connection getConnection() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(USERNAME);
            dataSource.setPassword(PASS);
            dataSource.setURL(URL);
            return dataSource.getConnection();
        } catch (SQLException ex) {
        	System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return null;
	}
}
