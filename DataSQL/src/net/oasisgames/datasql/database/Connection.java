package net.oasisgames.datasql.database;

import java.util.Arrays;

/**
 * 
 * @author Nick Doxa
 * @apiNote This class (Connection.java) is written to extend the
 * {@link net.oasisgames.datasql.database.SQL SQL API} and setup the application for connecting.
 * It also simultaneously sets up the table, and prepares for both receiving and sending data.
 * @see {@link net.oasisgames.datasql.database.Information Information}
 */
public class Connection extends SQL {
	
	/**
	 * @apiNote There may only be one active Connection class at a time thus creating only one
	 * active connection to the SQL Database at a time. This singular (or singleton) connection
	 * can be accessed through
	 */
	private static Connection singleton;
	
	/**
	 * @return Connection - Singleton output of the Connection class. 
	 * Prevents multiple connections overlapping.
	 */
	public static synchronized Connection Singleton() {
		if (singleton == null) {
			singleton = new Connection();
		}
		return singleton;
	}
	
	/**
	 * @apiNote This method discards the active Connection singleton and allows for a replacement
	 * to be instatiated.
	 */
	public static synchronized void disposeSingleton() {
		singleton = null;
	}

	/**
	 * @apiNote This private boolean determines whether or not the debug messages are sent.
	 */
	private static boolean debugMode = true;
	/**
	 * @param debug - (boolean) True to show debug messages, False to hide them.
	 */
	public static void setDebug(boolean debug) {
		debugMode = debug;
		setPrintStatements(debugMode);
		String outcome = debug ? "ENABLED" : "DISABLED";
		System.out.println("DEBUG " + outcome + "!");
	}
	
	/**
	 * @return Boolean - Returns true if debugMode is on and false if debugMode is off.
	 */
	public static boolean isDebug() {
		return debugMode;
	}
	
	/**
	 * @apiNote the Strings that makeup the login credentials
	 * <br><b><u>The default inputs are:</u></b><br>
	 * <ul>
	 * 	<li><b>Host</b> - localhost</li>
	 * 	<li><b>Port</b> - 3306</li>
	 * 	<li><b>Database</b> - datasql</li>
	 * 	<li><b>Username</b> - root</li>
	 * 	<li><b>Password</b> - <i>null</i></li>
	 * </ul>
	 */
	private static String 
		   host = "localhost",
		   port = "3306",
		   database = "datasql",
		   username = "root",
		   password = "";
	
	public final static String
			default_host = "localhost",
			default_port = "3306",
			default_database = "datasql",
			default_username = "root",
			default_password = "";
	
	/**
	 * @apiNote The table name for the SQL Database.
	 */
	private static String connectionTableName = "datasql";
	
	/**
	 * @apiNote The default name if one is not written in.
	 */
	private static final String defaultName = "datasql";
	
	/**
	 * @apiNote public access for {@link net.oasisgames.datasql.database.Connection#defaultName defaultName parameter}
	 * @return String - default table name.
	 */
	public static final String getDefaultTable() {
		return defaultName;
	}
	
	/**
	 * @apiNote If left with no parameters the default login credentials will be used.
	 * The default database name is datasql.
	 */
	private static String[] login() {
		return new String[] {
			host,
			port,
			database,
			username,
			password
		};
	}
	
	/**
	 * @apiNote This method disconnects from the connection and disposes the Singleton
	 */
	public void disposeConnection() {
		Connection.disposeSingleton();
		disconnect();
	}
	
	/**
	 * @apiNote This is the static String reference for the primary key name
	 */
	private static String primaryKeyName;
	
	/**
	 * @apiNote This method statically sets the primary key name for the class
	 * @param keyName - (String) The name of the primary key
	 */
	public static void setPrimaryKeyName(String keyName) {
		Connection.primaryKeyName = keyName;
	}
	
	/**
	 * @apiNote this method gets the static value of the primary key name
	 * @return String - The name of the primary key
	 */
	private static String getPrimaryKeyName() {
		return primaryKeyName;
	}
	
	private static boolean createTable;
	
	/**
	 * @apiNote This method sets the boolean createTable which determines if the connection
	 * automatically creates a table or not
	 * @param createTable - (Boolean) Whether or not to create the table upon connection
	 */
	public static void setCreateTableStatus(boolean createTable) {
		Connection.createTable = createTable;
	}
	
	/**
	 * @apiNote This method gets and returns the createTable boolean which can be set using the
	 * static method 
	 * {@link net.oasisgames.datasql.database.Connection#setCreateTableStatus(boolean) 
	 * setCreateTableStatus(boolean)}
	 * @return Boolean - Returns the current createTable boolean.
	 */
	public static boolean getCreateTableStatus() {
		return createTable;
	}
	
	/**
	 * @apiNote Set the login credentials for the SQL Database. <b>Must be called before the object
	 * is created!</b>
	 * @param host (String) - The host name
	 * @param port (String) - The host port
	 * @param database (String) - The name of the database inwhich the data is being stored. <i>Not the table name!</i>
	 * @param username (String) - The user's login name for the database
	 * @param password (String) - The user's login password for the database
	 * @return Boolean - Returns true if the credentials were accepted, false if they were not.
	 */
	public static boolean setLoginCredentials(String host, String port, String database, 
			String username, String password) {
		if (host.isEmpty() || host.isBlank()) return false;
		if (port.isEmpty() || port.isBlank()) return false;
		if (database.isEmpty() || database.isBlank()) return false;
		if (username.isEmpty() || username.isBlank()) return false;
		Connection.host = host;
		Connection.port = port;
		Connection.database = database;
		Connection.username = username;
		if (password == null) {
			Connection.password = "";
			return true;
		}
		Connection.password = password;
		return true;
	}
	
	/**
	 * @param name - (String) The name of the table (changes the default)
	 * @apiNote by default the table name is datasql
	 */
	public static void setTableName(String name) {
		connectionTableName = name.toLowerCase();
	}
	
	/**
	 * @apiNote This method gets the current static table name from the Connection data
	 * @return String - The static table name in Connection data
	 */
	public static String getTableName() {
		return connectionTableName;
	}
	
	/**
	 * @apiNote This method creates a new table and sets the table name in the SQL super class
	 * @param name - (String) The name of the table to create
	 */
	public void createNewTable(String name) {
		super.tableName = name.toLowerCase();
		createTable(primaryKeyName);
	}
	
	/**
	 * @apiNote This method allows you to set the table name in the SQL super class after a
	 * connection has been established.
	 * @param name - (String) The name of the table
	 */
	public void setTableNamePostConnect(String name) {
		super.tableName = name.toLowerCase();
	}
	
	/**
	 * @apiNote This method sets the key value pairs for table setup. <b>This must be called
	 * before the object is created.</b>
	 * @param keyValueType - (String) The raw value type of the key value
	 * @param keyValueName - (String) The name of the key value 
	 * @param types - (String Array) The array of data types
	 * @param names - (String Array) The array of value names
	 * @return Boolean - Returns true if the data types and value names match up correctly
	 * and create functioning value pairs.
	 */
	public static boolean setValueArray(String keyValueType, String keyValueName, 
			String[] types, String[] names) {
		keyValueName = keyValueName.toUpperCase();
		keyValueType = Information.convertStringToSQL(keyValueType);
		//region DEBUG
			printToConsole("\nKey Value Type: " + keyValueType +
						  	"\nKey Value Name: " + keyValueName);
			if (Connection.isDebug()) System.out.print("Value Types: ");
			Arrays.stream(types).forEach((s) -> {
				if (Connection.isDebug()) System.out.print("[" + s + "] ");
			});
			if (Connection.isDebug()) System.out.print("\nValue Names: ");
			Arrays.stream(names).forEach((s) -> {
				if (Connection.isDebug()) System.out.print("[" + s + "] ");
			});
			if (Connection.isDebug()) System.out.println();
		//endregion
		if (keyValueType.isBlank() || keyValueType.isEmpty()) return false;
		if (keyValueName.isBlank() || keyValueName.isEmpty()) return false;
		if (types.length < 1 || names.length < 1) return false;
		if (types.length != names.length) return false;
		String[] output = new String[names.length+1];
		output[0] = keyValueName + " " + keyValueType;
		for (int i = 0; i < names.length; i++) {
			output[i+1] = names[i].toUpperCase() + " " + types[i].toUpperCase();
		}
		valueArray = output;
		//region DEBUG
			if (Connection.isDebug()) System.out.println("RESULTS:");
			Arrays.stream(output).forEach(s -> {
				String[] pair = s.split(" ", 2);
				if (Connection.isDebug()) System.out.println("[" + pair[0] + "," + pair[1] + "]");
			});
		//endregion
		return true;
	}

	/**
	 * @apiNote The static array of values used to set and get the value pairs from the forms
	 */
	private static String[] valueArray;
	
	
	/**
	 * <html><body>
	 * <h1 id="title">Connection Object:</h1>
	 * <p id="desc">This object <strong>MUST NOT</strong> be instantiated using the Constructor.
	 * In order to maintain the Singleton, please access the
	 * {@link net.oasisgames.datasql.database.Connection#Singleton() Singleton Getter Method.}
	 * This method will create an object if one does not already exist and it will get the current
	 * object if one does already exist. This helps maintain one Connection and no overlap in the
	 * event of a new thread or database exceptions.
	 * </body><style>
	 *	#title {
	 *	  font-size: 14px;
	 *    color: #00F59F;
	 *	}
	 *	#desc {
	 *	  font-size: 13px;
	 *	  color: #E9FF6E;
	 *	}
	 * </style></html>
	 * @apiNote <strong>WARNING:</strong> Before instantiating this class 
	 * into an object keep in mind:
	 * <ul>
	 * 	<li>You must set the login credentials statically</li>
	 * 	<li>You must set the valueKeys statically</li>
	 * 	<li>You must set the primaryKeyName</li>
	 * 	<li>If you want a different table name than the default you must change it statically</li>
	 * </ul>
	 * 
	 * @param i - (Byte) This determines the connection will automatically create a table
	 */
	public Connection(byte i) {
		super(connectionTableName, getPrimaryKeyName(), login(), getCreateTableStatus());
	}
	
	/**
	 * <html><body>
	 * <h1 id="title">Connection Object:</h1>
	 * <p id="desc">This object <strong>MUST NOT</strong> be instantiated using the Constructor.
	 * In order to maintain the Singleton, please access the
	 * {@link net.oasisgames.datasql.database.Connection#Singleton() Singleton Getter Method.}
	 * This method will create an object if one does not already exist and it will get the current
	 * object if one does already exist. This helps maintain one Connection and no overlap in the
	 * event of a new thread or database exceptions.
	 * </body><style>
	 *	#title {
	 *	  font-size: 14px;
	 *    color: #00F59F;
	 *	}
	 *	#desc {
	 *	  font-size: 13px;
	 *	  color: #E9FF6E;
	 *	}
	 * </style></html>
	 * @apiNote <strong>WARNING:</strong> Before instantiating this class 
	 * into an object keep in mind:
	 * <ul>
	 * 	<li>You must set the login credentials statically</li>
	 * 	<li>You must set the valueKeys statically</li>
	 * 	<li>You must set the primaryKeyName</li>
	 * 	<li>If you want a different table name than the default you must change it statically</li>
	 * </ul>
	 * 
	 */
	public Connection() {
		super(login());
	}
	
	/**
	 * @param primaryKeyValue
	 * @param credentials - (String Array) The login credentials for the database
	 * @deprecated
	 * @apiNote This constructor method is deprecated. Instead use
	 * {@link net.oasisgames.datasql.database.Connection#setLoginCredentials(String, String, String, String, String) setLoginCredentials()}
	 * which is accessed <b>statically</b> and must be used before object instance is created.
	 */
	public Connection(String primaryKeyValue, String[] credentials, boolean createTable) {
		super(connectionTableName, primaryKeyValue, credentials, createTable);
		if (singleton == null) {
			singleton = this;
		}
	}
	
	/**
	 * @apiNote This method is an overriden abstract method from the
	 * {@link net.oasisgames.datasql.database.SQL#createKeyValueArray() SQL API} designed to
	 * create and return the values needed to make a prepared statement for the SQL Database
	 * @return String Array - Returns a string array containing the key value types and names
	 */
	@Override
	protected String[] createKeyValueArray() {
		return valueArray;
	}
	
}
