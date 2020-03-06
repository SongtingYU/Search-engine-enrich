package au.edu.unsw.ee.stackoverflowextension;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

import ee.query.files.DataSearch;
import ee.query.files.SchIndClass;
import au.edu.unsw.soascheme.extensionservice.DAO_Factory;
import au.edu.unsw.soascheme.extensionservice.Questions_Bean;
/**A class of getting top url by calculating the similarity of youtube description with the question<br>
 * 
 * Question and youtube description are file format.<br>
 * Writes the url to file and store the url to database.<br>
 * File folder path is "project folder"/test<br>
 * 
 */
public class topURl {
	static String current = System.getProperty("user.dir");
	static String filePathPattern=".*/";
	/**Gets all question id from database<br>
	 * Use id to locate question file and description folder<br>
	 * Sends the content of question and youtube description documents as arguments to similarity function<br>
	 * Gets the top similarity url and store both of them in local file and database<br>
	 */
	public void firstURL(){
		try {
			ArrayList<Questions_Bean> Questions=DAO_Factory.GetQuestionsDAOInstance().Questions_List();
			for(int i=0;i<Questions.size(); i++){
//				System.out.println("------------------");
				Integer id=Questions.get(i).getId();
//				System.out.println(Questions.get(i).getAnswer());
				
				File dscrpnFolder =new File(current+"/test/"+Questions.get(i).getId());
				// description folder path of one question
//				File answer_txt =new File(folder.getAbsolutePath()+"/"+Questions.get(i).getId()+".txt");
//				//open answer and store as a list of sentence 
//
//				System.out.println(answer_txt.getPath());
							
				String url="";
				//loop the list and get the highest score 
				//folder and answer_txt as argument for query 
				//
				ReadFile reader=new ReadFile();
				writeFile writer=new writeFile();
				String answer=reader.readQuestion(Questions.get(i).getQuestion());
				String keyword=reader.readKeyword(String.valueOf(i));
				
//				System.out.println(keyword);
		        
//		        System.out.println(dscrpnFolder.getPath());
//		        
		        senSplit preprocessor=new senSplit();
		        String text=preprocessor.preprocessSen(answer);
		        
		        SchIndClass schInd=new SchIndClass();
		        schInd.CreateIndex(dscrpnFolder.getPath(), text);
		        
		        
		        url=schInd.search();
//		        System.out.println(url);
		        //modify the return as a tuple of highest score of one sentence and url file name.
//		        for(ScoreDoc scoreDoc : s) {
//		            Document doc = DSch.getDocument(scoreDoc);		            
//		        	if(scoreDoc.score>highest_score){
//		        		highest_score=scoreDoc.score;
//		        		url=doc.get("FilePath").replaceAll(filePathPattern, "");
//		        	}
//		        }
//		        System.out.println(url+highest_score);
		        writer.writeURL(String.valueOf(id), url);
		        Questions_Bean question = new Questions_Bean();
		        question.setUrl(url);
		        question.setId(id);
		        try {
					if (DAO_Factory.GetQuestionsDAOInstance().UpdateURL(question)) {
						System.out.println("update successfully");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
//		        UPDATE URL OF QUESTION which ID is i
		        
		        
		        
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		topURl top=new topURl();
		top.firstURL();
	}
}
