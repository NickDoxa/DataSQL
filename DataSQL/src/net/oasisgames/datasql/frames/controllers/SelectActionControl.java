package net.oasisgames.datasql.frames.controllers;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import net.oasisgames.datasql.database.Connection;
import net.oasisgames.datasql.files.FileManager;
import net.oasisgames.datasql.files.FileWriter;
import net.oasisgames.datasql.frames.FrameControl;
import net.oasisgames.datasql.frames.SelectAction;

public class SelectActionControl {

	/**
	 * @apiNote This is the JFrame linked to this controller class
	 */
	private static SelectAction menu;
	
	/**
	 * @apiNote This method is used to get the single created frame of the MainMenu
	 * @return MainMenu - (JFrame) Returns the main menu JFrame
	 */
	public static SelectAction Singleton() {
		if (menu == null) {
			menu = new SelectAction();
		}
		return menu;
	}
	
	private static FrameControl lastFrame;
	
	public static void setLastFrame(FrameControl frame) {
		lastFrame = frame;
	}
	
	public static FrameControl getLastFrame() {
		return lastFrame;
	}
	
	private File selectedFile = null;
	
	/**
	 * @apiNote This method returns the currently selected file from the file chooser
	 * @return File - Returns selectedFile, if null no file is selected
	 */
	protected File getSelectedFile() {
		return selectedFile;
	}

	/**
	 * @apiNote This method sets the current output file
	 * @param selectedFile - (File) The selected file
	 */
	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	/**
	 * @apiNote This method opens the file chooser and returns the chosen file
	 * @return File - Returns the file selected or null if no file was selected
	 */
	public File openFileChooser() {
		final JFrame frame = new JFrame("File Selection");
		final JFileChooser files = new JFileChooser();
		files.setFileFilter(Singleton().getFileFilter());
		int returnVal = files.showOpenDialog(frame);
		return returnVal == JFileChooser.APPROVE_OPTION ? files.getSelectedFile() : null;
		
	}
	
	/**
	 * @apiNote This method exports all the data from a table in to a text file in readable rows
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public void exportTable() throws SQLException, NullPointerException {
		List<String> lines = new ArrayList<String>();
		String[] columns = Connection.Singleton().getAllColumns();
		for (String name : columns) {
			ResultSet rs = Connection.Singleton().getAllValuesByKey(name);
			int count = 0;
			while (rs.next()) {
				String input = name.toUpperCase() + ": " + rs.getString(1);
				if (lines.size() < count+1) lines.add(input);
				else {
					input = lines.get(count) + ", " + input;
					lines.set(count, input);
				}
				count++;
			}
		}
		int id = FileManager.addFile(getSelectedFile());
		final FileWriter write = new FileWriter(id);
		List<String> output = new ArrayList<String>();
		output.add(Connection.Singleton().tableName.toUpperCase() + " TABLE DATA:");
		lines.forEach(str -> output.add(str));
		write.writeRawLines(output.stream().toArray(String[]::new));
	}
	
	/**
	 * @apiNote This method returns to the previous frame and closes the current while ending
	 * the current connection
	 * @param previousFrame - (FrameControl Interface) The frame to switch to
	 */
	public void backButton() {
		Singleton().closeWindow();
		Singleton().getPreviousFrame().openWindow();
	}
	
}
