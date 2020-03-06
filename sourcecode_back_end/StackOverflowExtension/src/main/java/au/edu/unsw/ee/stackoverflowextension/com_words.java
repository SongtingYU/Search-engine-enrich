package au.edu.unsw.ee.stackoverflowextension;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.*;

/**A class for extracting phrases from text.<br>
 * Using stopwords and regular expressions to split text into sentences and phrases as candidate phrases.<br>
 * Based on single words frequencies to score the phrases.<br>
 * 
 */

public class com_words {
	
	private static Path stop_words_file=Paths.get("stopwords.txt");
	private String text= "";
	
	/**
	* Gets the text string for this class
	*/
	public String getText() {
		return text;
	}
	/**
	* Sets the text string for this class
	*/
	public void setText(String text) {
		this.text = text;
	}
	/**Reads stop words file to a java list
	 * @return the stop words list
	 */
	public List<String> load_stop_word_file() throws IOException{
		Charset cs = Charset.forName("UTF-8");
		List<String> stop_words=Files.readAllLines(stop_words_file, cs);
		return stop_words;
	}
	/**Gets stop words regular expression pattern
	 * @return the string pattern of all stop words
	 */
	public String create_stop_words_pattern() throws IOException{
		List<String> stop_words=load_stop_word_file();
		ArrayList<String> stop_words_regexs=new ArrayList<String>();
		for(int i=0;i<stop_words.size();i++){
			String stop_words_regex="\\b"+stop_words.get(i)+"(?![\\w])"+"|^"+stop_words.get(i)+"(?![\\w])";
			stop_words_regexs.add(stop_words_regex);
		}
		String pattern=Arrays.asList(stop_words_regexs).toString().replaceAll("(^(\\[\\[)|(\\]\\])$)", "").replace(",", "|");
//		System.out.println(pattern);
		return pattern;
	}
	/**Splits a phrase into words
	 * @param phrase String of a phrase
	 * @param minimun_character minimum length of a words
	 * @return the words Arraylist
	 */
	public ArrayList<String> seperate_to_words(String phrase, int minimun_character){
		Pattern splitter=Pattern.compile("[^a-zA-Z0-9_\\+\\-/]");
		ArrayList<String> words=new ArrayList<String>();
		for(String word:splitter.split(phrase)){
			String current_word=word.trim().toLowerCase();
			if(current_word.length()>minimun_character && current_word!="" && !current_word.matches("^[0-9]+$")){
				words.add(current_word);
			}
		}
//		for(String word:words){
//			System.out.println(word);
//		}
		return words;
	}
	/**Splits this class's text string into sentences string.
	*@return the string array of sentences 
	*/
	public String[] split_sentences(){
		Pattern sentence_delimiters=Pattern.compile("[.!?,;:\t\\\"\\(\\)\\\'\u2019\u2013]|\\s\\-\\s");
		String[] sentences=sentence_delimiters.split(text);
//		for(int i=0;i<sentences.length;i++){
//			System.out.println(sentences[i]);
//		}
		return sentences;
	}
	
	/**Removes stop words from sentences
	 * @param sentences string array
	 * @param pattern stop words regular expression pattern
	*@return the array list of candidate phrases
	*/
	public ArrayList<String> generate_candidate_keywords(String[] sentences,String pattern){
		ArrayList<String> phrase_list=new ArrayList<String>();
		for(String s:sentences){
//			System.out.println(s);
			s=s.replaceAll("^\\s+|\\s+$", "");
//			s=s.toLowerCase();
			s=s.replaceAll("(?i)"+pattern, "|");
//			System.out.println(s);
			String[] phrases=s.split("\\|");
			for(String phrase:phrases){
//				System.out.println(phrase);
				phrase=phrase.replaceAll("^(\\s)+|(\\s)+$", "");
				if(!phrase.equals("") && phrase.length()<30 && phrase.matches("^[a-z].*")){
//					System.out.println(phrase);
					phrase_list.add(phrase);
				}
			}
		}
		return phrase_list;
	}
	/**Calculates frequency scores for each word in phrase list
	 * @param phrase_list String list of phrases
	*@return the map of words in candidate phrases and their scores
	*/
	public Map<String,Float> compute_word_scores(ArrayList<String> phrase_list){
		Map<String,Float> word_scores=new LinkedHashMap<String,Float>();
		Map<String,Integer> word_frequency=new LinkedHashMap<String,Integer>();
		Map<String,Integer> word_degree=new LinkedHashMap<String,Integer>();
		for(String phrase :phrase_list){
			ArrayList<String> word_list=seperate_to_words(phrase,0);
			Integer word_list_length = word_list.size();
			Integer word_list_degree = word_list_length - 1;
			for(String word:word_list){
				if(word_frequency.get(word)!=null){
					word_frequency.put(word, word_frequency.get(word)+1);
//					System.out.println(word+" "+word_frequency.get(word));
				}
				else{
					word_frequency.put(word, 1);
				}
				if(word_degree.get(word)!=null){
					word_degree.put(word, word_degree.get(word)+word_list_degree);
				}
				else{
					word_degree.put(word, word_list_degree);
				}
			}
		}
		for(String key:word_frequency.keySet()){
			word_degree.put(key, word_degree.get(key)+word_frequency.get(key));
		}
		for(String key:word_frequency.keySet()){
			word_scores.put(key, (float) (word_degree.get(key)/(word_frequency.get(key)*1.0)));
		}
		
//		for(String key:word_scores.keySet()){
//			System.out.println(key+" ====frequency is ==== "+word_scores.get(key));
//		}
		
		return word_scores;
		
	}
	
	/**Calculates scores for each phrase
	 * @param phrase_list String list of phrases
	 * @param word_scores map of words
	*@return the map of phrases and their scores
	*/
	public Map<String,Float> generate_candidate_scores(ArrayList<String> phrase_list,Map<String,Float> word_scores){
		Map<String,Float> candidate_scores=new LinkedHashMap<String,Float>();
		for(String phrase :phrase_list){
			ArrayList<String> words_list=seperate_to_words(phrase,0);
			Float candidate_score=(float) 0;
			for(String word:words_list){
				candidate_score+=word_scores.get(word);
			}
			candidate_scores.put(phrase, candidate_score);
		}
//		for(String item:candidate_scores.keySet()){
//			System.out.println(candidate_scores.get(item));
//		}
		
		
		return candidate_scores;
	}
	/**Sorts scores for each phrase<br>
	 * Gets ordered phrase list
	 * @param candidate_scores phrase score map
	*@return the array list of scored phrases in descending order
	*/
	public ArrayList<String> sort_by_score(Map<String,Float> candidate_scores){
		ArrayList<String> keywords=new ArrayList<String>();
		List<Map.Entry<String, Float>> entries =
				  new ArrayList<Map.Entry<String, Float>>(candidate_scores.entrySet());
				Collections.sort(entries, new Comparator<Map.Entry<String, Float>>() {
				  public int compare(Map.Entry<String, Float> a, Map.Entry<String, Float> b){
				    return b.getValue().compareTo(a.getValue());
				  }
				});
				Map<String, Float> sortedMap = new LinkedHashMap<String, Float>();
				for (Map.Entry<String, Float> entry : entries) {
				  sortedMap.put(entry.getKey(), entry.getValue());
				}
			
//				for (Entry<String, Float> entry : sortedMap.entrySet()) {  
//					 
//				    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
//				  
//				}
				for (Entry<String, Float> entry : sortedMap.entrySet()) {  
					keywords.add(entry.getKey());
				      	  
				}
		return keywords;
		
	}
	/**Main method of this object<br>
	 * Gets specific top number of phrase list from text
	 * @param text the string of text
	 * @param num the number of phrase wants
	*@return the array list of top scored phrases
	*/
	public ArrayList<String> get_top_keywords(String text, Integer num){
		ArrayList<String> keywords=new ArrayList<String>();
		setText(text);
		String[] sentences =split_sentences();
		ArrayList<String> candadate_phrases = null;
		try {
			candadate_phrases = generate_candidate_keywords(sentences,create_stop_words_pattern());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Float> word_score=compute_word_scores(candadate_phrases);
		Map<String,Float> candidate_score=generate_candidate_scores(candadate_phrases,word_score);
		ArrayList<String>All_keywords=sort_by_score(candidate_score);
		
		if(All_keywords.size()<num){
			keywords=All_keywords;
		}
		else{
			for(int i=0;i<num;i++){
				keywords.add(All_keywords.get(i));
			}
		}
		return keywords;
		
	}
	public static void main(String[] args){
		String test="To describe the main objective of the project, and demo it’s main features;Provide a detailed but concise description of your final implemented tool,showcasing its main implemented features preferably via use of an example; Expect to answer questions related to features and it’s implementation; Additionally, we look at clarity of code (e.g. comments, readability, lightweight code docs, such as JavaDoc or Swagger provided);and Quality of deployment instructions";
		com_words extractor=new com_words();
		for(String s:extractor.get_top_keywords(test, 4)){
			System.out.println(s);
		}
	}
	
}
