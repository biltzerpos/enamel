package authoringApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Saves string into a text file.
 * @author Xiahan Chen, Huy Hoang Minh Cu, Qasim Mahir
 *
 */
public class SaveAsFile {
	private String ext;
	private File file;

	/**
	 * Initializes the SaveAsFile object.
	 * @param ext The extension to save as.
	 * @param f The directory to save in and file name to save as.
	 */
	SaveAsFile (String ext, File f){
		this.ext = ext;
		this.file = f;
	}
	
	/**
	 * Writes the elements of the LinkedList this.file.
	 * @param s The LinkedList
	 * @throws IOException
	 */
	public void stringArrayToFile(LinkedList<String> s) throws IOException{
		FileWriter fw = new FileWriter(this.file);
		
		if (this.ext == "txt") {

		    for (int i = 0; i < s.size(); i++) {
		      fw.write(s.get(i) + "\n");
		    }
		    fw.close();
		  }
			
		}
	
}
