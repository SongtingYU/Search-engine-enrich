package au.edu.unsw.ee.stackoverflowextension;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import au.edu.unsw.soascheme.extensionservice.DAO_Factory;
import au.edu.unsw.soascheme.extensionservice.Questions_Bean;
/**A class that do series of update database operations
 * 
 *
 *
 */
public class updateDatabase {
	static String current = System.getProperty("user.dir");
	
	/**A method excute python script to get Stackoverflow questions,answers<br>
	 * Stores into database
	 */
	public void addPythonPath(){
		try {
			System.out.println("start");  
            Process pr = Runtime.getRuntime().exec("python3 "+current+"/shell_script.py");  
              
            BufferedReader in = new BufferedReader(new  
                    InputStreamReader(pr.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);  
            }  
            in.close();  
            pr.waitFor();  
            System.out.println("end");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void updateQuestions(){
		try {
			System.out.println("start");  
            Process pr = Runtime.getRuntime().exec(current+"/StackOverflow_api.py");  
              
            BufferedReader in = new BufferedReader(new  
                    InputStreamReader(pr.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);  
            }  
            in.close();  
            pr.waitFor();  
            System.out.println("end");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void test(){
		try {
			System.out.println("start");  
            Process pr = Runtime.getRuntime().exec("which python");  
              
            BufferedReader in = new BufferedReader(new  
                    InputStreamReader(pr.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                System.out.println(line);  
            }  
            in.close();  
            pr.waitFor();  
            System.out.println("end");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**A method store keywords file<br>
	 * Gets all question id from database<br>
	 * Sends the answer string and the number of phrases wants to method com_words.get_top_keywords()<br>
 	 * Calls writeFile class to write the key phrases into files
	 */
	public void updateKeyword(){
		ReadFile reader=new ReadFile();
		writeFile writer= new writeFile();
		try {
			com_words com=new com_words();
			ArrayList<Questions_Bean> Questions = DAO_Factory.GetQuestionsDAOInstance().Questions_List();
			for(int i=0;i<Questions.size(); i++){
				//store keywords to form id.key
				String filename=Questions.get(i).getAnswer();
				String answer=reader.readAnswer(filename);
				ArrayList<String> top =com.get_top_keywords(answer, 4);
				writer.writKeywords(String.valueOf(i), top);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**A method excute python script to get Youtube video descriptions<br>
	 * Based on keywords to search Youtube videos
	 * Stores descriptions into local files
	 */
	public void updateDescription(){
				try {
					System.out.println("start");  
		            Process pr = Runtime.getRuntime().exec(current+"/YouTube_discripstion.py");  
		              
		            BufferedReader in = new BufferedReader(new  
		                    InputStreamReader(pr.getInputStream()));
		            String line;  
		            while ((line = in.readLine()) != null) {  
		                System.out.println(line);  
		            }  
		            in.close();  
		            pr.waitFor();  
		            System.out.println("end");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	public void updateDescription_2(){
		ReadFile reader=new ReadFile();
		try {
			ArrayList<Questions_Bean> Questions = DAO_Factory.GetQuestionsDAOInstance().Questions_List();
			for(int i=0;i<Questions.size(); i++){
				String id=String.valueOf(Questions.get(i).getId());
				String searchTerm=reader.readKeyword(id);
				try {
					System.out.println("start");  
		            Process pr = Runtime.getRuntime().exec("python src/main/resources/python/YouTube_discripstion.py"+" "+id+" "+searchTerm);  
		              
		            BufferedReader in = new BufferedReader(new  
		                    InputStreamReader(pr.getInputStream()));
		            String line;  
		            while ((line = in.readLine()) != null) {  
		                System.out.println(line);  
		            }  
		            in.close();  
		            pr.waitFor();  
		            System.out.println("end");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		updateDatabase up=new updateDatabase();
//		up.test();
		up.addPythonPath();
		up.updateQuestions();
		up.updateKeyword();
		up.updateDescription();
		topURl top=new topURl();
		top.firstURL();
	}
}
