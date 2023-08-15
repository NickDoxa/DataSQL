package net.oasisgames.datasql.frames.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JList;
import javax.swing.DefaultListModel;

import net.oasisgames.datasql.database.Connection;
import net.oasisgames.datasql.database.Information;
import net.oasisgames.datasql.frames.FrameControl;
import net.oasisgames.datasql.frames.TableSelect;

public class TableSelectControl {

	/**
	 * @apiNote This is the JFrame linked to this controller class
	 */
	private static TableSelect menu;
	
	/**
	 * @apiNote This method is used to get the single created frame of the MainMenu
	 * @return MainMenu - (JFrame) Returns the main menu JFrame
	 */
	public static TableSelect Singleton() {
		if (menu == null) {
			menu = new TableSelect();
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
		Connection.Singleton().disposeConnection();
		Singleton().closeWindow();
		MainMenuControl.Singleton().openWindow();
		MainMenuControl.setErrorTextMessage("Connection Failed!");
	}
	
	private Information information;
	
	/**
	 * @apiNote This method submits the name of the selected table and opens the next frame
	 * @param name - (String) The name of the table selected
	 * @param nextFrame - (FrameControl Interface) The frame to switch to
	 */
	public void submitTableAndNext(String name, FrameControl nextFrame) {
		Connection.Singleton().setTableNamePostConnect(name);
		Singleton().closeWindow();
		nextFrame.run();
		nextFrame.setPreviousFrame(Singleton());
	}
	
	/**
	 * @apiNote This method returns to the previous frame and closes the current while ending
	 * the current connection
	 * @param previousFrame - (FrameControl Interface) The frame to switch to
	 */
	public void backButton() {
		Singleton().closeWindow();
		Connection.Singleton().disposeConnection();
		Singleton().getPreviousFrame().openWindow();
	}
	
	/**
	 * @apiNote This method begins establishing a connection and opens an Information object
	 * @return Boolean - Returns true if the connection was successful, false if not
	 */
	private boolean establishConnection() {
		Connection.setCreateTableStatus(false);
		information = new Information();
		return information.isConnected();
	}
	
	/**
	 * @apiNote This method gets all the tables from the SQL Database connection after a 
	 * connection has been established
	 * @return String List - The list of names from the SQL Database
	 * @throws SQLException
	 */
	private List<String> getTablesFromSQL() throws SQLException {
		this.connectionSetup();
		return information.getTableNames();
	}
	
	/**
	 * @apiNote This method sets the JList model to tables from the SQL
	 * @see {@link net.oasisgames.datasql.frames.controllers.TableSelectControl#getTablesFromSQL()
	 * getTablesFromSQL() Method}
	 * @param list - (String JList) The JList to setup
	 * @param retreatFrame - (FrameControl Interface) The frame to open if the operation fails
	 */
	public void setTableNameList(JList<String> list, FrameControl retreatFrame) {
		final DefaultListModel<String> model = new DefaultListModel<String>();
		try {
			getTablesFromSQL().stream()
								.filter(name -> !name.isBlank() && !name.isEmpty())
								.collect(Collectors.toList())
								.forEach(name -> model.addElement(name));
			list.setModel(model);
		} catch (SQLException e) {
			Singleton().closeWindow();
			retreatFrame.openWindow();
			Connection.printToConsole("Could not load tables!\nSQL Error:");
			if (Connection.isDebug()) e.printStackTrace();
		}
	}
	
}
