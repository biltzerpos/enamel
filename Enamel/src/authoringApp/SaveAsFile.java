package authoringApp;

import java.io.File;

public class SaveAsFile {
	
	private File file;
	private String ext;
	
	//Initializes the class with the save file to be extension "ext".
	SaveAsFile (String ext){
		this.ext = ext;
	}
	
	//Save a string array with extension appropriate extension.
	public void stringArrayToFile(String[] s){
		if (this.ext == ".txt") {
			//TODO: save String array s as a file of extension .txt;
		}
	}
	
	//Returns the file.
	public File getFile() {
		return this.file;
	}
	
}
