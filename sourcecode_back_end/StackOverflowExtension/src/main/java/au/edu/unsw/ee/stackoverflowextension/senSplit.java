package au.edu.unsw.ee.stackoverflowextension;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.*;

import au.edu.unsw.soascheme.extensionservice.DAO_Factory;
import au.edu.unsw.soascheme.extensionservice.Questions_Bean;
/**A class for preprocessing text string
 * Removes http patterns and symbol patterns
 * Example how this used:<br>
 * <p><blockquote><pre>
 * String text="One of the famous search engine website is http://www.google.com. [#! hello world]";
 * senSplit tool=new senSplit();
 * String newtext =tool.preprocessSen(text);
 * </pre></blockquote><p>
 * new string would be "One of the famous search engine website is hello world "
 */
public class senSplit {
	static String HttpPattern="(http:|https:|ftp)[:-_?\\a-zA-Z\\d.*//]+";
	static String symbolPattern="[^a-zA-Z0-9\\s\\.]";
	static String numberPattern=".*[0-9].*";
	static String spacePattern="[\\s]+";
	
	/**
	 * 
	 * @param text string want to process
	 * @return the processed string
	 */
	public String preprocessSen(String text){	
			String text_processed="";
			for(String word:text.split("\\s+") ){
				word=word.replaceAll(HttpPattern, "");
				word=word.replaceAll(symbolPattern, " ");
				text_processed=text_processed+word+" ";
			}
			text_processed=text_processed.replaceAll(spacePattern, " ");
//			System.out.println(text_processed.trim());
			
		return text_processed;
		
	}
}
