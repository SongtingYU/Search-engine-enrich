package au.edu.unsw.soascheme.extensionservice;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Questions_DAOImpl implements Questions_interface_DAO {

	private Connection DBconnect = null;
	private PreparedStatement uPreState = null;

	public Questions_DAOImpl(Connection DBconnect) {
		this.DBconnect = DBconnect;
	}

	public ArrayList<Questions_Bean> Questions_List() throws Exception {

		ArrayList<Questions_Bean> Questions_List = new ArrayList<Questions_Bean>();
		String sql = "SELECT * FROM Questions_table";
		this.uPreState = (PreparedStatement) this.DBconnect
				.prepareStatement(sql);
		ResultSet getR = this.uPreState.executeQuery();
		Questions_Bean tempBean;
		while (getR.next()) {
			tempBean = new Questions_Bean();
			tempBean.setId(getR.getInt(1));
			tempBean.setTitle(getR.getString(2));
			tempBean.setQuestion(getR.getString(3));
			tempBean.setOrg_answer(getR.getString(4));
			tempBean.setAnswer(getR.getString(5));
			tempBean.setUrl(getR.getString(6));
			Questions_List.add(tempBean);
		}
		this.uPreState.close();
		return Questions_List;
	}

	public boolean UpdateURL(Questions_Bean QBean) throws Exception {
		boolean flag = false;

		String sql = "UPDATE questions_table SET url=? WHERE id=?";
		this.uPreState = (PreparedStatement) this.DBconnect
				.prepareStatement(sql);
		this.uPreState.setString(1, QBean.getUrl());
		this.uPreState.setInt(2, QBean.getId());
		if (this.uPreState.executeUpdate() > 0) {
			flag = true;
		}
		this.uPreState.close();

		return flag;
	}

}
