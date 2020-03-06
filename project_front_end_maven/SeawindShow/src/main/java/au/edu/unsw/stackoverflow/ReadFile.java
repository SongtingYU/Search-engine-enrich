package au.edu.unsw.stackoverflow;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	static String current = System.getProperty("user.dir");
	
	public String readAnswer(String id) throws IOException{
		String question="";
		String filePath=current+"/src/resources/"+id+".a";
		BufferedReader sr;
        String sLine;
        sr = new BufferedReader(new FileReader(filePath));
         while ((sLine = sr.readLine()) != null) {
        	 question=question+" "+sLine;
         }
         return question;
	}
}
