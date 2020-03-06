/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.query.test;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import ee.query.files.SchIndClass;
import ee.query.sentence.SchIndData;

/**
 *
 * @author Alireza
 */
public class run {
    
    public static void main(String [] args) throws IOException, ParseException
    {
    	//for computing the similarity of a sentence with a collection of documents
    	
        SchIndClass schInd=new SchIndClass();
        schInd.CreateIndex("/Users/snow/Desktop/cs9323/workspace/StackOverflowExtension/test/0", "call me maybe");
        schInd.search();
    	
    	//For computing the similarity of a sentence with a collection of sentences
    	
//        SchIndData sc=new SchIndData();
//        sc.CreateIndex("Users/snow/Desktop/cs9323/workspace/questions/6.txt");
//        System.out.println(sc.similarSentence);
//        List<SchIndData> lstSen=sc.search("python module ");
//        for(SchIndData s:lstSen)
//        System.out.println(s.inputSentence+": "+s.similarSentence+": "+s.score);
    }
}
