package net.oasisgames.datasql.frames.controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import net.oasisgames.datasql.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import net.oasisgames.datasql.files.UserPreference.Details;
import net.oasisgames.datasql.frames.FrameControl;
import net.oasisgames.datasql.frames.MainMenu;
import net.oasisgames.datasql.database.Connection;

public class MainMenuControl {
	
	/**
	 * @apiNote This is the JFrame linked to this controller class
	 */
	private static MainMenu menu;
	
	/**
	 * @apiNote This method is used to get the single created frame of the MainMenu
	 * @return MainMenu - (JFrame) Returns the main menu JFrame
	 */
	public static MainMenu Singleton() {
		if (menu == null) {
			menu = new MainMenu();
		}
		return menu;
	}
	
	/**
	 * @param text - (JTextField) Text field to be erased
	 */
	protected void textDisappear(JTextField text) {
		text.setText("");
	}
	
	/**
	 * @param msg - (String) The message the error field should show
	 * @apiNote this message will display in red!
	 */
	public static void setErrorTextMessage(String msg) {
		Singleton().getErrorText().setForeground(Color.RED);
		Singleton().getErrorText().setText(msg);
	}
	
	/**
	 * @param msg - (String) The message the loading field should show
	 * @apiNote This message will display in blue
	 */
	public void setLoadingTextMessage(String msg) {
		Singleton().getErrorText().setForeground(Color.BLUE);
		Singleton().getErrorText().setText(msg);
	}
	
	/**
	 * @apiNote This method is used to submit the form on the opening frame and open the next phase
	 * of the SQL data access process.
	 * @param textFields - (JTextField Array) An array of text fields containing the login credentials
	 * @param nextForm - (FrameControl Interface) The next frame to open
	 */
	public void submitForm(JTextField[] textFields, FrameControl nextForm) {
		for (int i = 0; i < 4; i++) {
			if (textFields[i].getText().isBlank() ||
					textFields[i].getText().isEmpty()) {
				setErrorTextMessage("You must fill out all"
						+ "\nfields before continuing!"
						+ "\n(Password can be blank)");
				return;
			}
		}
		if (!Connection.setLoginCredentials(textFields[0].getText(), textFields[1].getText(), 
				textFields[2].getText(), textFields[3].getText(), textFields[4].getText())) {
			setErrorTextMessage("Error loading credentials...");
			return;
		}
		storePreferences(Arrays.asList(textFields)
				.stream()
				.map(jtext -> jtext.getText())
				.toArray(String[] :: new));
		
		this.setLoadingTextMessage("Loading next module...");
		Connection.printToConsole("Login Credentials Updated Via Menu!");
		try {
			TaskAPI.runTaskThread(new Runnable() {
				@Override
				public void run() {
					Singleton().closeWindow();
					nextForm.run();
					nextForm.setPreviousFrame(Singleton());
				}
			}, 2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Connection.printToConsole("ERROR: Failed to run loading thread, running exit code with"
					+ " error status!");
		}
	}
	
	/**
	 * @apiNote This method is used to store the login preferences for the user
	 * @param prefs - (String Array) The credentials to be stored
	 */
	protected void storePreferences(String[] prefs) {
		prefs = Arrays.asList(prefs)
				.stream()
				.filter(s -> s != null)
				.toArray(String[] :: new);
		Details.HOST.setLastUsed(prefs[0]);
		Details.PORT.setLastUsed(prefs[1]);
		Details.DATABASE.setLastUsed(prefs[2]);
		Details.USERNAME.setLastUsed(prefs[3]);
		Details.PASSWORD.setLastUsed(prefs[4]);
		Connection.printToConsole("Preferences saved!");
	}
	
	/**
	 * @apiNote This method returns a list of the 
	 * @return String List - Returns the credential preferences in an ordered list
	 */
	protected String[] pullPreference() {
		String[] output = new String[5];
		output[0] = Details.HOST.getLastUsed();
		output[1] = Details.PORT.getLastUsed();
		output[2] = Details.DATABASE.getLastUsed();
		output[3] = Details.USERNAME.getLastUsed();
		output[4] = Details.PASSWORD.getLastUsed();
		return Arrays.asList(output)
				.stream()
				.filter(s -> s != null)
				.toArray(String[]::new);
	}
	
	/**
	 * @apiNote This method restores the login credentials based on last use stored in the 
	 * User Preference file.
	 * @param field - (JTextField Array) The array of JTextFields to restore
	 */
	public void restorePreferences(JTextField[] field) {
		final String[] prefs = pullPreference();
		for (int i = 0; i < 5; i++) {
			field[i].setText(prefs[i]);
		}
	}
	
	/**
	 * @apiNote Helpful static tool for centering new frames into the center screen
	 * @param frame - (JFrame) The frame to be centered
	 */
	public static void setFrameToCenterScreen(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
	
}
