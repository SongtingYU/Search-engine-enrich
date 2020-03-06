package au.edu.unsw.stackoverflow;

import java.util.ArrayList;

public class Questions_DAOProxy implements Questions_interface_DAO {

	private DatabaseConnection DBconnect = null;
	private Questions_interface_DAO uDAO = null;

	public Questions_DAOProxy() {
		try {
			this.DBconnect = new DatabaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.uDAO = new Questions_DAOImpl(this.DBconnect.getConnection());
	}

	public ArrayList<Questions_Bean> Questions_List() throws Exception {
		ArrayList<Questions_Bean> Questions_List = new ArrayList<Questions_Bean>();
		Questions_List = this.uDAO.Questions_List();
		this.DBconnect.close();
		return Questions_List;
	}

	public boolean UpdateURL(Questions_Bean QBean)
			throws Exception {
		boolean flag = false;
		flag = this.uDAO.UpdateURL(QBean);
		this.DBconnect.close();
		return flag;
	}

}
