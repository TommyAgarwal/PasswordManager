package fileHandling;

import java.io.File;
import java.io.IOException;


public class parseJson {
//	private String currentPath;

	public parseJson() {
//		String currentDirectory = System.getProperty("user.dir");
//		currentPath = currentDirectory;
	}
	public boolean createFile(String fileName) {
		File myFile = new File("src/data/" + fileName);
		try {
			if (myFile.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	public File openFile(String fileName) {
		File myFile = new File("src/data/" + fileName);
		return myFile;	
	}

}
