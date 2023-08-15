package net.oasisgames.datasql.database;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Nick Doxa
 * @apiNote This class is written to provide a suitable way to communicate information
 * between the SQL database and the application. Think of this class as the bridge
 * between SQL and JFrame, with a heap of static methods to help along the way.
 * @see {@link net.oasisgames.datasql.database.Connection Connection}
 *
 */
public class Information {

	private final Connection connection;
	private boolean connected;
	/**
	 * @apiNote This class is linked to the
	 * {@link net.oasisgames.datasql.database.Connection Connection Class}
	 * which is instantiated and begins a 
	 * {@link net.oasisgames.datasql.database.Connection#Singleton() Singleton}
	 * connection on object creation.
	 * @see
	 * {@link net.oasisgames.datasql.database.Connection#Connection(String) Connection Constructor}
	 * for more information on what to do before instantiating this class.
	 */
	public Information() {
		connection = Connection.Singleton();
		connected = connection.isConnected();
	}
	
	/**
	 * @param status - (Boolean) True for connected, false for failed connection
	 */
	public void setConnectionStatus(boolean status) {
		connected = status;
	}
	
	/**
	 * @return Boolean - Returns true if connected, false if not
	 */
	public boolean isConnected() {
		return connected;
	}
	
	/**
	 * @apiNote This method gets all the table names from the database and converts them to a 
	 * list
	 * @return String List - The table names in list format
	 * @throws SQLException
	 */
	public List<String> getTableNames() throws SQLException {
		return Arrays.asList(connection.getAllTables());
	}
	
	/**
	 * @apiNote Helpful static utility made to convert raw SQL strings into prepared statement ready
	 * strings
	 * @param str - (String) The raw SQL data type
	 * @return String - Returns the data type in SQL Format
	 */
	public static String convertStringToSQL(final String str) {
		String output;
		switch (str.toLowerCase().charAt(0)) {
			//STRING
			case 's':
				output = "VARCHAR(100)";
				break;
			//CHAR
			case 'c':
				output = "CHAR(10)";
				break;
			//BOOLEAN
			case 'b':
				output = "BOOLEAN";
				break;
			//FLOAT
			case 'f':
				output = "DOUBLE(100, 10)";
				break;
			//INTEGER
			case 'i':
				output = "INTEGER(100)";
				break;
			default:
				output = "VARCHAR(100)";
				break;
		}
		return output;
	}
	
	/**
	 * @apiNote Static reference to all data types in the SQL Database
	 */
	private static final String SQLDataTypes[] = {"BOOLEAN", 
			"BOOL", "INT", "INTEGER", "VARCHAR", "CHAR",
			"FLOAT", "DOUBLE", "TEXT", "BIGINT", "YEAR",
			"DATETIME", "TIME", "DATE", "TIMESTAMP", "DEC",
			"DECIMAL", "DOUBLE PRECISION", "BIT", "BYTE",
			"TINYINT", "MEDIUMTEXT", "MEDIUMINT", "SMALLINT",
			"BLOB", "TINYBLOB", "TINYTEXT", "MEDIUMBLOB",
			"LONGTEXT", "LONGBLOB", "ENUM", "SET"};
	
	/**
	 * @apiNote Static reference to all banned characters for value names
	 */
	private final static char[] bannedChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
			'-', '_', '+', '=', '[', ']', '{', '}', '|', '\\', '/', '\'', '"', ':', ';', '`', 
			'~', ',', '.', '<', '>', '?', '	', ' '};
	
	/**
	 * @apiNote This method is a static utility meant for testing a String to see if
	 * it is properly named as to not disturb the sequence of data sharing.
	 * @param str - (String) The string that you are checking.
	 * @return Boolean - Returns true if the naming convention is correct, false if it is not.
	 */
	public static boolean properTypeNamingConv(String str) {
		str = str.toUpperCase();
		for (String types : SQLDataTypes) {
			if (str == types) return false;
		}
		for (char c : str.toCharArray()) {
			for (char b : bannedChars) {
				if (c == b) return false;
			}
		}
		return true;
	}
}
