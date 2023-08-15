package net.oasisgames.datasql.frames.controllers;

import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import net.oasisgames.datasql.database.Connection;
import net.oasisgames.datasql.database.Information;
import net.oasisgames.datasql.frames.FrameControl;
import net.oasisgames.datasql.frames.TableCreate;

public class TableCreateControl {

	/**
	 * @apiNote This is the JFrame linked to this controller class
	 */
	private static TableCreate menu;
	
	/**
	 * @apiNote This method is used to get the single created frame of the MainMenu
	 * @return MainMenu - (JFrame) Returns the main menu JFrame
	 */
	public static TableCreate Singleton() {
		if (menu == null) {
			menu = new TableCreate();
		}
		return menu;
	}
	
	/**
	 * @apiNote This method connects to the database without creating a table
	 */
	public void connectionSetup() {
		if (establishConnection()) {
			Connection.printToConsole("Connection Successful!");
			return;
		}
		Connection.printToConsole("Connection Failed!");
		Singleton().closeWindow();
		MainMenuControl.Singleton().openWindow();
		MainMenuControl.setErrorTextMessage("Connection Failed!");
	}
	
	/**
	 * @param keyDataType - (String) Type selection from JComboBox for the key value
	 * @param keyName - (String) Name from the JTextField for the key value
	 * @param dataTypes - (String ListModel) List of data types from the JList of values
	 * @param valueNames - (String ListModel) List of value names from the JList of values
	 * @param defaultTable - (Boolean) Whether or not to use the default table name
	 * @param tableName - (String?) The string used for the table name IF defaultTable is false
	 * @apiNote This method closes the main frame and opens up the database if all the information
	 * passes correctly. It is checked by the checkWindow() method and refers to the
	 * {@link net.oasisgames.datasql.database.Information Information}
	 * and {@link net.oasisgames.datasql.database.Connection Connection}
	 * class to determine if a suitable outcome has been determined.
	 */
	public boolean submitResults(String keyDataType, String keyName, 
			ListModel<String> dataTypes, ListModel<String> valueNames, 
			boolean defaultTable, String tableName, FrameControl frame) {
		this.connectionSetup();
		if (!checkWindow(keyDataType, keyName, dataTypes, valueNames, defaultTable, tableName)) 
			return false;
		String[] convertedNames = this.convertListModel(valueNames, false);
		String[] convertedTypes = this.convertListModel(dataTypes, true);
		if (!Connection.setValueArray(keyDataType, keyName, 
				convertedTypes, convertedNames))
			return false;
		if (!defaultTable) Connection.setTableName(tableName);
		Connection.setPrimaryKeyName(keyName.toUpperCase());
		Singleton().closeWindow();
		Connection.Singleton().createNewTable(Connection.getTableName());
		frame.setPreviousFrame(Singleton());
		frame.run();
		return true;
	}
	
	/**
	 * @apiNote This method begins establishing a connection and opens an Information object
	 * @return Boolean - Returns true if the connection was successful, false if not
	 */
	private boolean establishConnection() {
		Connection.setCreateTableStatus(false);
		Information info = new Information();
		return info.isConnected();
	}
	
	/**
	 * @apiNote This method converts String ListModels intoo String arrays.
	 * @param list - (String ListModel) The model from the JList component
	 * @param types - (Boolean) Whether or not it is a type array or name array
	 * @return String Array - Returns a string array of the List Model items.
	 */
	private String[] convertListModel(ListModel<String> list, boolean types) {
		String[] output = new String[list.getSize()];
		for (int i = 0; i < list.getSize(); i++) {
			output[i] = types ? 
					Information.convertStringToSQL(list.getElementAt(i)) : list.getElementAt(i);
		}
		return output;
	}
	
	/**
	 * @apiNote This boolean based method is used to check if the window is ready for submission!
	 * @param keyDataType - (String) Type selection from JComboBox for the key value
	 * @param keyName - (String) Name from the JTextField for the key value
	 * @param dataTypes - (String ListModel) List of data types from the JList of values
	 * @param valueNames - (String ListModel) List of value names from the JList of values
	 * @param defaultTable - (Boolean) Whether or not to use the default table name
	 * @param tableName - (String?) The string used for the table name IF defaultTable is false
	 * @return Boolean - Returns true if the information provided is correct, false if it is not.
	 */
	private boolean checkWindow(String keyDataType, String keyName, 
			ListModel<String> dataTypes, ListModel<String> valueNames, 
			boolean defaultTable, String tableName) {
		if (keyDataType.isEmpty() || keyDataType.isBlank()) return false;
		if (keyName.isEmpty() || keyName.isBlank()) return false;
		if (dataTypes.getSize() < 1 || valueNames.getSize() < 1) return false;
		if (!Information.properTypeNamingConv(keyName)) return false;
		for (int i = 0; i < valueNames.getSize(); i++) {
			if (!Information.properTypeNamingConv(valueNames.getElementAt(i))) return false;
			else continue;
		}
		if (!defaultTable) {
			if (tableName.isEmpty() || tableName.isBlank()) return false;
			try {
				if (Connection.Singleton().tableExists(tableName)) return false;
			} catch (SQLException e) {
				Connection.printToConsole("Error checking current tables!\nSQL Error:");
				if (Connection.isDebug()) e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 * @apiNote This method is used to get the front end results of adding a new value.
	 * @param name - (String) The name of the value from the text field.
	 * @param type - (String) The data type of the value from the menu.
	 * @return String Array - Returns a string array with a length of 2. The first string is the
	 * modified value name, the second is the modified value type. <b>NOTE:</b> These modifications are
	 * purely design, they do not work in an SQL prepared statement.
	 */
	public String[] addNewValue(String name, String type) {
		String[] output = new String[2];
		if (name.isBlank() || name.isEmpty()) return null;
		if (type.isBlank() || type.isEmpty()) return null;
		if (valueNameExists(name)) return null;
		output[0] = name.toUpperCase();
		output[1] = type.toUpperCase();
		return output;
	}
	
	/**
	 * @apiNote This method is used to check if a value name already exists within the front
	 * end JFrame list.
	 * @param name - (String) The name of the value to be added
	 * @return Boolean - Returns true if the name exists and false if not
	 */
	private boolean valueNameExists(String name) {
		DefaultListModel<String> list = Singleton().valueNameRawList;
		for (int i = 0; i < list.getSize(); i++) {
			String compare = list.getElementAt(i);
			if (compare.toLowerCase().equals(name.toLowerCase())) return true;
		}
		return false;
	}
	
}
