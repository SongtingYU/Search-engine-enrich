package au.edu.unsw.stackoverflow;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Keywords_DAOImpl implements Keywords_interface_DAO {

	private Connection DBconnect = null;
	private PreparedStatement uPreState = null;

	public Keywords_DAOImpl(Connection DBconnect) {
		this.DBconnect = DBconnect;
	}

	public Keywords_Bean GETYouTube_URL_List(String keyword) throws Exception {
		Keywords_Bean URL_List = new Keywords_Bean();
		String sql = "SELECT * FROM Keywords_table WHERE keyword = ?";
		this.uPreState = (PreparedStatement) this.DBconnect
				.prepareStatement(sql);
		this.uPreState.setString(1, keyword);
		ResultSet getR = this.uPreState.executeQuery();
		if (getR.next()) {
			URL_List.setKeyword(getR.getString(1));
			URL_List.setYouTube_URL1(getR.getString(2));
			URL_List.setYouTube_URL2(getR.getString(3));
			URL_List.setYouTube_URL3(getR.getString(4));
			URL_List.setYouTube_URL4(getR.getString(5));
			URL_List.setYouTube_URL5(getR.getString(6));
		}
		this.uPreState.close();
		return URL_List;
	}

	public Map<String, ArrayList<String>> GetKeywordsMap() throws Exception {
		Map<String, ArrayList<String>> KeywordsMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> tmpURLList;
		String sql = "SELECT * FROM Keywords_table";
		this.uPreState = (PreparedStatement) this.DBconnect
				.prepareStatement(sql);
		ResultSet getR = this.uPreState.executeQuery();
		while (getR.next()) {
			tmpURLList = new ArrayList<String>();
			for (int order = 2; order < 7; order++)
				tmpURLList.add(getR.getString(order));
			KeywordsMap.put(getR.getString(1), tmpURLList);
		}
		this.uPreState.close();
		return KeywordsMap;
	}
}
