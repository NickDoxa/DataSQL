package net.oasisgames.datasql.files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.oasisgames.datasql.database.Connection;

public class FileWriter {

	private final File file;
	private final int fileID;
	private final String filePath;
	public FileWriter(int id) {
		this.fileID = id;
		file = FileManager.getFileByID(fileID);
		filePath = FileManager.getFilePath(file);
	}
	
	/**
	 * @apiNote This method sets a value to a specified key within the file. If the key doesn't
	 * exist the file writer creates one and then adds the value.
	 * <br><b>Example:</b> If the key was 'age' and the value was '20' the FileWriter would either
	 * find or write in the key as 'age: ' and the final output into the file would be 'age: 20'
	 * @param key - (String) The key to add or replaces a value for
	 * @param value - (String) The value to be written in
	 */
	public void setString(String key, String value) {
		FileReader read = new FileReader(file);
		int row = read.keyExists(key);
		boolean exists = row != -1;
		String input = key + ": " + value;
		java.io.FileWriter write = null;
		try {
			List<String> contents = read.getAllLines();
			//Delete old file to truncate old text
			if (!file.delete()) {
				Connection.printToConsole("File write failed!");
				return;
			}
			File newFile = new File(filePath);
			//Create new file
			if (newFile.createNewFile()) FileManager.replaceFileByID(fileID, newFile);
			else {
				Connection.printToConsole("File creation failed, old file was deleted!"); 
				return;
			}
			//Old file deleted, new file created
			write = new java.io.FileWriter(newFile);
			if (contents == null || contents.isEmpty()) {
				write.write(input);
				return;
			}
			if (exists) contents.set(row, input);
			else contents.add(input);
			for (int i = 0; i < contents.size(); i++) {
				writeLine(write, contents.get(i));
			}
		} catch (IOException e) {
			if (Connection.isDebug()) e.printStackTrace();
		} finally {
			if (write != null)
				try {
					write.close();
				} catch (IOException e) {
					Connection.printToConsole("Could not close java.io.FileWriter!\nError:");
					if (Connection.isDebug()) e.printStackTrace();
				}
		}
		
	}
	
	/**
	 * @apiNote This method writes in a line with a line break
	 * @param write - (JDK FileWriter) The Java File Writer to use
	 * @param str - (String) The value to write
	 * @throws IOException
	 */
	private void writeLine(java.io.FileWriter write, String str) throws IOException {
		write.write(str + "\n");
		write.flush();
	}
	
	/**
	 * @apiNote This method writes a raw line at a time using the Java JDK File Writer
	 * @param line - (String) The line to write into the file
	 */
	public void writeRawLine(String line) {
		java.io.FileWriter writer = null;
		try {
			writer = new java.io.FileWriter(file);
			writeLine(writer, line);
			writer.flush();
		} catch (IOException e) {
			Connection.printToConsole("File writing failed. Error:");
			if (Connection.isDebug()) e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					if (Connection.isDebug()) e.printStackTrace();
				}
		}
	}
	
	public void writeRawLines(String[] lines) {
		java.io.FileWriter writer = null;
		try {
			writer = new java.io.FileWriter(file);
			for (String line : lines) {
				writeLine(writer, line);
			}
			writer.flush();
		} catch (IOException e) {
			Connection.printToConsole("File writing failed. Error:");
			if (Connection.isDebug()) e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					if (Connection.isDebug()) e.printStackTrace();
				}
		}
	}
	
}
