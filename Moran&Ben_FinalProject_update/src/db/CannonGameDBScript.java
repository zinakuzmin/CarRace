package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import org.apache.ibatis.jdbc.ScriptRunner;

/**
 * run a sql script for building database table's and inserting initial database information.
 * 
 * @author Moran Tenzer & Ben Nave
 *
 */
public class CannonGameDBScript {
	
	/**
	 * runs the sql script , txt file -> CannonGameDBuilder.txt
	 */
	public static void runScript() {
		String initDB = "CannonGameDBuilder.txt";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			new ScriptRunner(DriverManager.getConnection("jdbc:mysql://localhost:3306?useSSL=false", "scott", "tiger"))
					.runScript(new BufferedReader(new FileReader(initDB)));
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
