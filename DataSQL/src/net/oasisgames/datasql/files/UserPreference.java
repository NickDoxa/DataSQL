package net.oasisgames.datasql.files;

import java.io.File;

import net.oasisgames.datasql.database.Connection;

/**
 * @apiNote This class is used to access and control the UserPreferences file
 * @see
 * {@link net.oasisgames.datasql.files.FileManager FileManager}<br>
 * {@link net.oasisgames.datasql.files.FileReader FileReader}
 */
public class UserPreference {
	
	private final String fileName = "pref.text";
	private static int fileID;
	public UserPreference() {
		fileID = FileManager.addFile(new File(fileName));
		Connection.printToConsole("File Added with ID: " + fileID);
	}
	
	/**
	 * <b>DO NOT USE WITHOUT INSTANTIATING THE {@link UserPreference UserPreference Class} FIRST</b>
	 */
	public enum Details {
		
		HOST, PORT, DATABASE, USERNAME, PASSWORD;
		
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
		
		/**
		 * @apiNote This method returns the value of the given enumerator if it exists within
		 * the user preference file
		 * @return String - The string found if there is a user preference set
		 */
		public String getLastUsed() {
			FileReader reader = new FileReader(FileManager.getFileByID(fileID));
			return reader.getString(toString());
		}
		
		/**
		 * @apiNote This method is used to set a user preference in the local file for the
		 * corresponding enumerator
		 * @param last - (String) The string to set in the preference file
		 */
		public void setLastUsed(String last) {
			FileWriter writer = new FileWriter(fileID);
			writer.setString(toString(), last);
		}
		
	}
	
}
