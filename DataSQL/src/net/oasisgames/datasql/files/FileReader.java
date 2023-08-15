package net.oasisgames.datasql.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import net.oasisgames.datasql.database.Connection;

public class FileReader {

	private final File file;
	/**
	 * @apiNote This constructor passes a file argument which is permanently linked into the object.
	 * That file is then used to read from a given path
	 * @param file - (File) The file to be read
	 */
	public FileReader(File file) {
		this.file = file;
	}
	
	/**
	 * @apiNote This method gets the resulting string from a given path within the defined file in
	 * the constructor.<br>
	 * <b>Example:</b> if the file read "name: John" and the path was "name" then
	 * the return output would be "John".
	 * @param path - (String) The name of the key to retrieve a value from
	 * @return String - Returns the string located at the given path in the file
	 */
	public String getString(String path) {
		if (file.length() == 0) {
			Connection.printToConsole("File empty, cannot read!");
			return null;
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			do {
				String line = scanner.nextLine();
				Connection.printToConsole("Scanning: " + line);
				String[] pair = line.split(": ", 2);
				if (pair != null && pair.length > 1) {
					Connection.printToConsole("Key pair detected, splitting...");
					Connection.printToConsole("Key: " + pair[0] + ", Value: " + pair[1]);
				}
				if (pair[0].toLowerCase().equals(path.toLowerCase())) return pair[1];
			} while (scanner.hasNextLine());
		} catch (FileNotFoundException e) {
			Connection.printToConsole("File reader stopped because file could not be found!\nError:");
			if (Connection.isDebug()) e.printStackTrace();
		} catch (NullPointerException e) {
			Connection.printToConsole("Path could not be found!");
		} finally {
			if (scanner != null) scanner.close();
		}
		return null;
	}
	
	/**
	 * @apiNote This method checks if a key path exists within the defined file in
	 * the constructor and outputs the line number if it does.<br>
	 * <b>Example:</b> if the file read "name: John" and the path was "name" then
	 * the return output would be true.
	 * @param path - (String) The name of the key to retrieve a value from
	 * @return Integer - returns the line number of the given key if exists. If not it returns -1.
	 */
	public int keyExists(String path) {
		if (file.length() == 0) return -1;
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			int row = 0;
			do {
				String line = scanner.nextLine();
				Connection.printToConsole("Scanning: \"" + line + "\"");
				String[] pair = line.split(": ", 2);
				if (pair[0].toLowerCase().equals(path.toLowerCase())) return row;
				row++;
			} while (scanner.hasNextLine());
		} catch (FileNotFoundException e) {
			Connection.printToConsole("File reader stopped because file could not be found!\nError:");
			if (Connection.isDebug()) e.printStackTrace();
		} catch (NullPointerException e) {
			Connection.printToConsole("Path could not be found!");
		} finally {
			if (scanner != null) scanner.close();
		}
		return -1;
	}
	
	/**
	 * @apiNote This method is used to get all the lines from the file defined in the constructor
	 * and return them in an ordered list
	 * @return String List - Returns the file contents in an ordered list
	 */
	public List<String> getAllLines() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			List<String> output = new ArrayList<String>();
			while (scanner.hasNext()) {
				String add = scanner.nextLine();
				output.add(add);
			}
			return output;
		} catch (FileNotFoundException e) {
			Connection.printToConsole("File reader stopped because file could not be found!\nError:");
			if (Connection.isDebug()) e.printStackTrace();
		} catch (NullPointerException e) {
			Connection.printToConsole("File is empty!");
		} finally {
			if (scanner != null) scanner.close();
			Connection.printToConsole("File Reader Scanner closed: getAllLines()");
		}
		return new ArrayList<String>();
	}
	
}
