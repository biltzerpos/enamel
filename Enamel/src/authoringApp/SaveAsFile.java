package authoringApp;

import java.io.File;

public class SaveAsFile {
	
	private File absDirectory;
	private String ext;
	
	//Initializes the class with the save file to be extension "ext".
	SaveAsFile (String ext, String directory){
		this.ext = ext;
		this.absDirectory = new File(directory);
	}
	
	//Save a string array with extension appropriate extension.
	//IMPORTANT: made changes to this class because I read up on how saving works.
	//This method should now write the array to the File this.absDirectory.
	//Remember that the file name is a substring of absDirectory.
	public void stringArrayToFile(String[] s){
		if (this.ext == "txt") {
			//TODO: save String array s as a file of extension .txt
		}
	}
	
	//Returns the file.
	public File getFile() {
		return this.absDirectory;
	}
	
}
