package au.edu.unsw.soascheme.extensionservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Keywords_DAOProxy implements Keywords_interface_DAO {

	private DatabaseConnection DBconnect = null;
	private Keywords_interface_DAO uDAO = null;

	public Keywords_DAOProxy() {
		try {
			this.DBconnect = new DatabaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.uDAO = new Keywords_DAOImpl(this.DBconnect.getConnection());
	}

	public Keywords_Bean GETYouTube_URL_List(String keyword) throws Exception {
		Keywords_Bean URL_List = new Keywords_Bean();
		URL_List = this.uDAO.GETYouTube_URL_List(keyword);
		this.DBconnect.close();
		return URL_List;
	}

	public Map<String, ArrayList<String>> GetKeywordsMap() throws Exception {
		Map<String, ArrayList<String>> KeywordsMap = new HashMap<String, ArrayList<String>>();
		KeywordsMap = this.uDAO.GetKeywordsMap();
		this.DBconnect.close();
		return KeywordsMap;
	}

}
