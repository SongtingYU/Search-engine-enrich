package au.edu.unsw.ee.stackoverflowextension;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**A class for read all lines from file into string
 * path is specify as current project location and the "test" folder
 * 
 * 
 * 
 */
public class ReadFile {
	static String current = System.getProperty("user.dir");
	/**read answer file from "project path"/test<br>
	 * Example file name :0.a
	 *@param id filename 
	 *@return the answer string 
	 */
	public String readAnswer(String id) throws IOException{
		String answer="";
		String filePath=current+"/test/"+id;
		BufferedReader sr;
        String sLine;
        sr = new BufferedReader(new FileReader(filePath));
         while ((sLine = sr.readLine()) != null) {
        	 answer=answer+" "+sLine;
         }
         return answer.trim();
	}
	/**read question file from "project path"/test
	 *@param id filename<br>
	 *Example file name :0.q
	 *@return the question string 
	 */
	public String readQuestion(String id) throws IOException{
		String question="";
		String filePath=current+"/test/"+id;
		BufferedReader sr;
        String sLine;
        sr = new BufferedReader(new FileReader(filePath));
         while ((sLine = sr.readLine()) != null) {
        	 question=question+" "+sLine;
         }
         return question.trim();
	}
	/**read keyword file from "project path"/test
	 *@param id filename example:0,1,2...
	 *@return the keyword string 
	 */
	public String readKeyword(String id) throws IOException{
		String keyword="";
		String filePath=current+"/test/"+id+".key";
		BufferedReader sr;
        String sLine;
        sr = new BufferedReader(new FileReader(filePath));
         while ((sLine = sr.readLine()) != null) {
        	 keyword=keyword+" "+sLine;
         }
         return keyword.trim();
	}
}
