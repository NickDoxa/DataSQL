package net.oasisgames.datasql.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;

import net.oasisgames.datasql.database.Connection;

public class FileManager {

	public FileManager(String filePath) {
		File file = new File(filePath);
		boolean exists = createFileIfNotExists(file);
		if (exists) files.put(file, filePath);
		
	}
	
	static {
		files = new HashMap<File, String>();
		Connection.printToConsole("File Manager Enabled");
	}
	
	private static Map<File, String> files;
	private static File[] allFiles;
	
	/**
	 * @apiNote This method returns the file matched to the ID. If no file matches
	 * the ID then the method returns null
	 * @param id - (Integer) The number identifier for the file
	 * @return
	 */
	public static File getFileByID(int id) {
		return allFiles[id];
	}
	
	/**
	 * @apiNote This method returns the string path used to create the file
	 * @param file - (File) The file to get the path of
	 * @return String - The path to the file
	 */
	public static String getFilePath(File file) {
		return files.get(file);
	}
	
	/**
	 * @apiNote This method replaces an existing file within the 
	 * @param id - (Integer) The integer identifier of the file to replace
	 * @param file - (File) The file to replace the previous
	 */
	public static void replaceFileByID(int id, File file) {
		try {
			allFiles[id] = file;
		} catch (IndexOutOfBoundsException e) {
			Connection.printToConsole("ID not recognized by FileManager, Error: out of index!");
			createFileIfNotExists(file);
		}
	}
	
	/**
	 * @apiNote This method adds a file to the array list
	 * @param file - (File) The file to be added
	 */
	private static void addFileToArray(File file) {
		if (allFiles == null) {
			Connection.printToConsole("File array created with file index 0");
			allFiles = new File[]{file}; 
			return;
		}
		File[] newArray = new File[allFiles.length+1];
		for (int i = 0; i < allFiles.length; i++) {
			if (allFiles[i] == null) continue;
			newArray[i] = allFiles[i];
		}
		newArray[allFiles.length] = file;
		allFiles = newArray;
	}
	
	/**
	 * @apiNote This method returns a boolean determining whether the files is already
	 * added to the File Manager data
	 * @param file - (File) The file to be checked
	 * @return Boolean - Returns true if the file is already in the File Manager, false if it is not
	 */
	protected static boolean fileAlreadyAdded(File file) {
		if (allFiles == null) return false;
		boolean output = !Arrays.stream(allFiles).filter(f -> f == file).findAny().isEmpty();
		if (output) Connection.printToConsole("File already added to manager!");
		return output;
	}
	
	/**
	 * @apiNote This method removes the given file from the array list
	 * @param file - (File) The file to remove
	 */
	private static void removeFileFromArray(final File file) {
		int count = 0;
		for (File f : allFiles) {
			if (f == file) break;
			count++;
		}
		if (count >= allFiles.length) return;
		allFiles[count] = null;
	}
	
	/**
	 * @apiNote This method removes a file from the array list using its given id
	 * @param id - (Integer) The id assigned to the file
	 */
	private static void removeFileFromArray(final int id) {
		allFiles[id] = null;
	}
	
	/**
	 * @apiNote This method adds a file to the active files in the manager
	 * @param file - (String) The path of the file to be added
	 * @return Integer - Returns the ID/Position of the file added to the manager<br>
	 * <b>NOTE:</b> The ID will return as -1 if the file creation failed
	 */
	public static int addFile(String filePath) {
		File file = new File(filePath);
		if (fileAlreadyAdded(file)) return -1;
		boolean created = createFileIfNotExists(file);
		String output = created ? "File exists or was created successfully!" : 
			"File not found and could not be created!";
		Connection.printToConsole(output);
		return created ? files.size()-1 : -1;
	}
	
	/**
	 * @apiNote This method adds a file to the active files in the manager
	 * @param file - (File) The file to be added
	 * @return Integer - Returns the ID/Position of the file added to the manager<br>
	 * <b>NOTE:</b> The ID will return as -1 if the file creation failed
	 */
	public static int addFile(File file) {
		if (fileAlreadyAdded(file)) return -1;
		boolean created = createFileIfNotExists(file);
		String output = created ? "File exists or was created successfully!" : 
			"File not found and could not be created!";
		Connection.printToConsole(output);
		return created ? files.size()-1 : -1;
	}
	
	/**
	 * @apiNote This method deletes and removes a file from the manager
	 * @param file - (File) The file that needs to be deleted
	 */
	public static void removeFile(File file) {
		removeFileFromArray(file);
		String output = file.delete() ? "Deleted Successfully!" : "Failed to Delete!";
		if (files.containsKey(file)) files.remove(file);
		Connection.printToConsole("File " + output);
	}
	
	/**
	 * @apiNote This method removes a file from the manager using its int identifier
	 * @param id - (Integer) The identifier assigned to the file
	 */
	public static void removeFile(int id) {
		File file = allFiles[id];
		removeFileFromArray(id);
		if (file.delete()) {
			Connection.printToConsole("Successfully deleted file!");
			return;
		}
		Connection.printToConsole("Failed to delete file!");
	}
	
	/**
	 * @apiNote This method deletes and removes a file from the manager
	 * @deprecated
	 * @param filePath - (String) The path to the file to be deleted
	 * @throws NoSuchElementException If the file doesn't exist or the path is wrong an
	 * error may be thrown
	 */
	public static void removeFile(final String filePath) throws NoSuchElementException {
		BiFunction<Set<File>, String, File> getFile = (set, path) -> {
			File file = set.stream().filter(f -> files.get(f) == path).findFirst().get();
			if (files.containsKey(file)) files.remove(file);
			return file;
		};
		File file = getFile.apply(files.keySet(), filePath);
		String output = file.delete() ? "Deleted Successfully!" : "Failed to Delete!";
		Connection.printToConsole("File " + output);
	}
	
	/**
	 * @apiNote This method streams the iteration of files and returns the active ones
	 * @return File List - Returns a list of active files
	 */
	public static List<File> getActiveFiles() {
		final List<File> fileList = new ArrayList<File>();
		files.keySet().stream()
		.filter(key -> key.exists())
		.forEach(key -> fileList.add(key));
		return fileList;
	}
	
	/**
	 * @apiNote This method is used to get a file using the file name, it is recommended to
	 * get a file, however, using the
	 * {@link net.oasisgames.datasql.files.FileManager#getFileByID(int) getFileByID(int) Method}
	 * @param name - (String) The name of the file you are searching for
	 * <br><b>NOTE: </b>Must include the file extension in the name<br>
	 * <i>Example: "file.txt"</i>
	 * @return File - Returns the first found file with the given name
	 * @throws NoSuchElementException If the file name does not exist within the manager
	 * an error may be thrown
	 */
	public static File getFile(final String name) throws NoSuchElementException {
		File file = files.keySet().stream().filter(f -> f.getName() == name).findFirst().get();
		if (file.exists()) return file;
		return null;
	}
	
	/**
	 * @apiNote This method is a boolean used to check if a file exists and if it does not
	 * then it attempts to create a new one
	 * @param file - (File) The file to check and or create
	 * @return Boolean - Returns true if the file exists or was created successfully, and false if it
	 * did not exist and could not be created
	 */
	private static boolean createFileIfNotExists(File file) {
		if (file.exists()) {
			files.put(file, file.getPath());
			addFileToArray(file);
			return true;
		}
		boolean output = false;
		try {
			file.setReadable(true);
			file.setWritable(true);
			output = file.createNewFile();
			if (!output) return false; 
			files.put(file, file.getPath()); 
			addFileToArray(file);
		} catch (FileNotFoundException e) {
			Connection.printToConsole("File Not Found Error: ");
			if (Connection.isDebug()) e.printStackTrace();
		} catch (IOException e) {
			Connection.printToConsole("Internal File Error: ");
			if (Connection.isDebug()) e.printStackTrace();
		} finally {
			String result = output ? "Created Successfully!" : "Failed To Create File...";
			Connection.printToConsole("File Creation Outcome: " + result);
		}
		return output;
	}
	
}

@FunctionalInterface
interface FileSetup {
	void setupFile(File f);
}
