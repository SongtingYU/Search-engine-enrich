package au.edu.unsw.stackoverflow;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

public class DatabaseConnection {

	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/SOEService?characterEncoding=utf8&useSSL=true";
	private static final String DBUSER = "root";
	private static final String DBPASSWORD = "";
	private Connection connect = null;

	public DatabaseConnection() throws Exception {
		try {
			Class.forName(DBDRIVER);
			this.connect = (Connection) DriverManager.getConnection(DBURL,
					DBUSER, DBPASSWORD);
		} catch (Exception e) {
			throw e;
		}
	}

	public Connection getConnection() {
		return this.connect;
	}

	public void close() throws Exception {
		if (this.connect != null) {
			try {
				this.connect.close();
			} catch (Exception e) {
				throw e;
			}
		}
	}
}