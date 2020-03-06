package au.edu.unsw.stackoverflow;

import java.util.ArrayList;
import java.util.Map;

public interface Keywords_interface_DAO {

	public Keywords_Bean GETYouTube_URL_List(String keyword) throws Exception;

	public Map<String, ArrayList<String>> GetKeywordsMap() throws Exception;
}
