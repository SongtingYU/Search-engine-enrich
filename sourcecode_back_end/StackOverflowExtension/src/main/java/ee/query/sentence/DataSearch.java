/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.query.sentence;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Alireza
 */

public class DataSearch {
  IndexReader reader;
  IndexSearcher indSearch;
  Query query;
  public DataSearch(String IndexDir) throws IOException
  {
      reader=DirectoryReader.open(FSDirectory.open(new File(IndexDir)));
      indSearch=new IndexSearcher(reader);
  }
  public TopDocs search(String searchText) throws IOException, ParseException
  {
      Query q=new QueryParser(Version.LUCENE_41, "Content", 
             new StandardAnalyzer(Version.LUCENE_41)).parse(searchText);
      return indSearch.search(q, 20); 
  }
  public Document getDocument(ScoreDoc score) throws CorruptIndexException, IOException
  {
	  return indSearch.doc(score.doc);
  }
}
