/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.query.files;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
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
public class DataIndex {
    private IndexWriter writer;
    private StandardAnalyzer Analyzer=new StandardAnalyzer(Version.LUCENE_41);
    public DataIndex(String indexDirectory) throws IOException
    {
      FSDirectory indexDir=FSDirectory.open(new File(indexDirectory));
         IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_41,Analyzer);
	 writer=new IndexWriter(indexDir,config);
    }
    public void Close() throws CorruptIndexException, IOException
    {
	  writer.close();
    }
    
    private Document ListDoc(File file) throws IOException{
        Document doc=new Document();
    Field fileContent=new Field("FileContent",new FileReader(file));
    doc.add(fileContent);
    doc.add(new TextField("FileName", file.getName(), Field.Store.YES));
    doc.add(new TextField("FilePath",file.getCanonicalPath(),Field.Store.YES));
    return doc;
    }
      private void IndexDocuments(File file) throws IOException{
      System.out.println("Indexing Document  "+file.getName());
      Document document = ListDoc(file);
      writer.addDocument(document);
      }

      public int createIndex(String dataDir, FileFilter filt) 
      throws IOException{
      File[] files = new File(dataDir).listFiles();

      for (File file : files) {
         if(!file.isDirectory()
            && file.exists()
         ){
            IndexDocuments(file);
         }
      }
      return writer.numDocs();
   }
     
}
