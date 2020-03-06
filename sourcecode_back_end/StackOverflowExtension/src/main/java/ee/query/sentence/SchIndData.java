/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.query.sentence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;


/**
 *
 * @author Alireza
 */
public class SchIndData {
    
    public String inputSentence;
    public String similarSentence;
    public String score;
    
    static String Pattern="(http:|https:|ftp)[:-_?\\a-zA-Z\\d.*//]+";
    public SchIndData(){}
    public SchIndData(String inputSentence,String similarSentence,String score)
    {
        this.inputSentence=inputSentence;
        this.similarSentence=similarSentence;
        this.score=score;
    }
    
    Index dInd;
    DataSearch DSch;
    String twitterData="";
    String current = System.getProperty("user.dir");
    String SchTxt="";
    public static void delete(File file)
    	throws IOException{
 
    	if(file.isDirectory())
        {
    		if(file.list().length==0){
    		   file.delete();
    		}else{
        	   String files[] = file.list();
     
        	   for (String temp : files) {
        	      File fileDelete = new File(file, temp);
        	     delete(fileDelete);
        	   }
        	   if(file.list().length==0){
           	     file.delete();
        	   }
    		}	
    	}
        else{
    		file.delete();
    	}
    }
     public void CreateIndex(String twitterFile) throws IOException, ParseException
     {
         System.out.println(current);
         File fileCheck=new File(current+"\\File_Index");
         twitterData=twitterFile;
         if(!fileCheck.exists())
         {
        	 System.out.println("--------------");
              fileCheck.mkdir();
              dInd = new Index(current+"\\File_Index\\");
              boolean numIndexed;
              long startTime = System.currentTimeMillis();	
              numIndexed = dInd.IndexData(twitterData);
              long endTime = System.currentTimeMillis();
              dInd.Close();
              if(numIndexed)
              System.out.println(" File indexed, time taken: "
                 +(endTime-startTime)+" ms");
              else
                     System.out.println("Can not index data. Check the file name or make sure file exists...");
         }
         else
        	
             if(fileCheck.exists()&& fileCheck.listFiles().length>0)
             {
            	 System.out.println("--------------");
                 Scanner sc=new Scanner(System.in);
                 System.out.println("Index directory is exist; Do you want to index data again? (Y/N)");
                 String answer=sc.next();
                 if(answer.equalsIgnoreCase("y"))
                 {
                     delete(fileCheck);
                     System.out.println("All Index Files are deleted.");
                     fileCheck.mkdir();
                      dInd = new Index(current+"\\File_Index\\");
                      boolean numIndexed;
                      long startTime = System.currentTimeMillis();	
                      numIndexed = dInd.IndexData(twitterData);
                      long endTime = System.currentTimeMillis();
                      dInd.Close();
                      if(numIndexed)
                      System.out.println(" File indexed, time taken: "
                         +(endTime-startTime)+" ms");
                      else
                             System.out.println("Can not index data. "
                                     + "Check the file name or make sure file exists...");
                 }
                 else
                     if(answer.equalsIgnoreCase("n"))
                     {
                         System.out.println("Searching "
                                     + "Based on the previous Indexed file...");
                     }
             }
     }
   public List<SchIndData> search(String searchSentence) throws IOException, ParseException
   {
      String processedSentence=PreProcessSentence(searchSentence);
      List<SchIndData> lstSearch=new ArrayList<>();
      DSch = new DataSearch(current+"\\File_Index\\");
      try
      {
          TopDocs hits = DSch.search(processedSentence);
          for(ScoreDoc scoreDoc : hits.scoreDocs)
          {
             Document doc = DSch.getDocument(scoreDoc);
             lstSearch.add(new SchIndData(searchSentence,doc.get("Content"), 
                     String.valueOf(scoreDoc.score)));
          }
      }
      catch(Exception ex){}
      return lstSearch;
   }
   public List<SchIndData> searchSentenceFile(String twitterFilePath) throws IOException, ParseException
   {
      List<SchIndData> lstSearchResult=new ArrayList<>();
      List<String> lstData=ReadData.ReadPreProcessedData(twitterFilePath);
      DSch = new DataSearch(current+"\\File_Index\\");
      //long startTime = System.currentTimeMillis();
      for(String dds : lstData)
      {
          try
          {
              TopDocs hits = DSch.search(dds);
          //long endTime = System.currentTimeMillis();
          // System.out.println(hits.totalHits +
          //   " documents found. Time :" + (endTime - startTime));
              for(ScoreDoc scoreDoc : hits.scoreDocs)
              {
                 Document doc = DSch.getDocument(scoreDoc);
                 lstSearchResult.add(new SchIndData(dds,doc.get("Content"), 
                         String.valueOf(scoreDoc.score)));      
              }
          }
          catch(Exception ex){}
      }
      return lstSearchResult;
   }
   private String PreProcessSentence(String inputSentence)
   {
       inputSentence=inputSentence.replaceAll(Pattern, "");
             String [] arrSLine=inputSentence.split(" ");
             String Line="";
             for(String str:arrSLine)
             {
                     str=str.replace("'","");
                     str=str.replace("(","");
                     str=str.replace(")","");
                     str=str.replace("!","");
                     str=str.replace("[","");
                     str=str.replace("]","");
                     str=str.replace("{","");
                     str=str.replace("}","");
                     str=str.replace("\"","");
                     str=str.replace("?","");
                     str=str.replace(".","");
                     Line+=str+" ";
             }
             return Line;
   }
}
