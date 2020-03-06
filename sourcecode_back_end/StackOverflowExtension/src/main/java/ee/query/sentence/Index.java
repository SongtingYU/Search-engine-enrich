/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.query.sentence;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Alireza
 */
public class Index {
    private IndexWriter writer;
    private StandardAnalyzer Analyzer=new StandardAnalyzer(Version.LUCENE_41);
    public Index(String indexDirectory) throws IOException
    {
      FSDirectory indexDir=FSDirectory.open(new File(indexDirectory));
         IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_41,Analyzer);
	 writer=new IndexWriter(indexDir,config);
    }
    public void Close() throws CorruptIndexException, IOException
    {
	  writer.close();
    }
    
    private Document ListDoc(String  inputSentence) throws IOException
    {
        Document doc=new Document();
//        Field fileContent=new Field("Content",new FileReader("FileName Must Be Provided"));
//        doc.add(fileContent);
    doc.add(new TextField("Content",inputSentence, Field.Store.YES));
    //doc.add(new TextField("FilePath",file.getCanonicalPath(),Field.Store.YES));
    return doc;
    }
      private void IndexDocuments(String inputSentenceFilePath) throws IOException{
      System.out.println("Indexing Data  ");
      List<String> lstProcessedData=ReadData.ReadPreProcessedData(inputSentenceFilePath);
      for(String inputSentence:lstProcessedData)
          {
              try
              {
              Document document = ListDoc(inputSentence);
              writer.addDocument(document);
              }
              catch(Exception ex)
              {         
                  System.out.print(ex.getMessage());
              }
          }
      }
      public boolean IndexData(String inputSentenceFilePath) 
      throws IOException{
      //File[] files = new File(dataDir).listFiles();
      File f=new File(inputSentenceFilePath);
      //for (File file : files) {
         if(!f.isDirectory()&& f.exists())
         {
            IndexDocuments(f.getPath());
         }
         else
         {
             return false;
         }
     // }
      //return writer.numDocs();
      return true;
   }
     
}
