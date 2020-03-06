/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.query.files;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

/**
 *
 * @author Alireza
 */
public class SchIndClass {
    DataIndex dInd;
    DataSearch DSch;
    //String indexDir="D:\\Data\\Index\\";
    String DataDir="";
    String current = System.getProperty("user.dir");
    String SchTxt="";
    public static void delete(File file)
    	throws IOException{
 
    	if(file.isDirectory()){
    		if(file.list().length==0){
    		   file.delete();
    		  // System.out.println("Directory is deleted : "  + file.getAbsolutePath());
    		}else{
        	   String files[] = file.list();
     
        	   for (String temp : files) {
        	      File fileDelete = new File(file, temp);
        	     delete(fileDelete);
        	   }
        	   if(file.list().length==0){
           	     file.delete();
        	   //  System.out.println("Directory is deleted : " + file.getAbsolutePath());
        	   }
    		}	
    	}else{
    		file.delete();
    		//System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
    }

    
    
     public void CreateIndex(String DataDirPath,String searchText) throws IOException, ParseException
     {
         //String current = System.getProperty("user.dir");
         SchTxt=searchText;
         File fileCheck=new File(current+"/src/Index");
         DataDir=DataDirPath;
         if(!fileCheck.exists())
         {
             fileCheck.mkdir();
         }
         else
        	 System.out.println(fileCheck);
             if(fileCheck.exists()&& fileCheck.listFiles().length>0)
             {
            	 String answer="Y";
//                 Scanner sc=new Scanner(System.in);
//                 System.out.println("Index directory is exist; Do you want to index you data again? (Y/N)");
//                 String answer=sc.next();
                 if(answer.equalsIgnoreCase("y"))
                 {
                     delete(fileCheck);
                     System.out.println("All Index Files are deleted.");
                     fileCheck.mkdir();
                 }
                 else
                     if(answer.equalsIgnoreCase("n"))
                     {
//                         Scanner input=new Scanner(System.in);
//                         System.out.println("Please Write a sentence to extract the most similar documents?");
//                         String searchText=input.next();
                         search();
                         System.exit(0);
                     }
             }
	dInd = new DataIndex(current+"/src/Index");
      int numIndexed;	
      numIndexed = dInd.createIndex(DataDir, new TxtFilter());
      long endTime = System.currentTimeMillis();
      dInd.Close();
      
     }
     public String search() throws IOException, ParseException{
         
//         Scanner input=new Scanner(System.in);
//         System.out.println("Please Write a sentence to extract the most similar documents?");
//         String searchText=input.next();
      DSch = new DataSearch(current+"/src/Index");
      long startTime = System.currentTimeMillis();
      TopDocs hits = DSch.search(SchTxt);
      long endTime = System.currentTimeMillis();
   
      System.out.println(hits.totalHits +
         " documents found");
      String highest_score_doc="";
      String filePathPattern=".*/";
      if(hits.scoreDocs.length>0){
//    	  System.out.println("+++++++++++++"+hits.scoreDocs[0]);
    	  Document doc = DSch.getDocument(hits.scoreDocs[0]);  
    	  highest_score_doc=doc.get("FilePath").replaceAll(filePathPattern, "");
      }
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = DSch.getDocument(scoreDoc);
         //String s=String.format("$1 and Score is $2: ",doc.get("FilePath"), scoreDoc.toString());
            System.out.println("File: "
            + doc.get("FilePath")+" [Similarity Score is] "+scoreDoc.score);
      }
      return highest_score_doc;
   }
}
