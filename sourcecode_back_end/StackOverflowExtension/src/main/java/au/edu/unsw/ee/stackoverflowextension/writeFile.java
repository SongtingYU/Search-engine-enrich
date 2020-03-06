package au.edu.unsw.ee.stackoverflowextension;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import au.edu.unsw.soascheme.extensionservice.DAO_Factory;
/**A class for write a string to file
 * Static path used is "project path"/test
 *
 *
 */
public class writeFile {
	static String current = System.getProperty("user.dir");
	/**write the url of youtube into file
	 * Example: 0.u,1.u
	 * @param id name of the file
	 * @param URL content would be written
	 */
	public void writeURL(String id,String URL){ 
		try {
			File file =new File(current+"/test/"+id+".u");
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				writer.println(URL);
				writer.close();
				//store url to form id.url
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**write the key phrase into file
	 * Example: 0.key
	 * @param id name of the file
	 * @param keywords keywords array list contains keywords would be written
	 */
	public void writKeywords(String id, ArrayList<String> keywords){ 
		try {
			File file =new File(current+"/test/"+id+".key");
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				for(String keyword:keywords){
					writer.println(keyword);
				}
				
				writer.close();
				//store keyword to form id.key
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
