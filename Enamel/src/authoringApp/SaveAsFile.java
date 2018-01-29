package authoringApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SaveAsFile {
	private String[] str;
	private File file;
	private String ext;
	//public String[] savefile;
	//Initializes the class with the save file to be extension "ext".
	SaveAsFile (String ext, String[] str, File f){
		this.ext = ext;
		this.str = str;
		this.file = f;
		
	}
	
	//Save a string array with extension appropriate extension.
	public void stringArrayToFile(String[] s) throws IOException{
		FileWriter fw = new FileWriter(this.file);
		
		if (this.ext == "txt") {

		    for (int i = 0; i < str.length; i++) {
		      fw.write(str[i] + "\n");
		    }
		    fw.close();
		  }
			
		}
		

	
	//Returns the file.
	public File getFile() {
		return this.file;
	}
	
}
