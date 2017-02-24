package zrace.server.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * run a sql script for building database tables and inserting initial data
 * 
 * @author Zina Kuzmin
 *
 */	

public class ZRaceGameDBScript {

	/**
	 * run the sql script ZRaceDBBuilder.txt
	 */
	public static void runScript() {
		String sqlScript = "ZRaceDBBuilder.txt";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			new ScriptRunner(DriverManager.getConnection("jdbc:mysql://localhost:3306?useSSL=false", "scott", "tiger"))
					.runScript(new BufferedReader(new FileReader(sqlScript)));
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

}
