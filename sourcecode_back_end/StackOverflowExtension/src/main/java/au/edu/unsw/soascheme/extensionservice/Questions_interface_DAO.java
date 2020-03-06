package au.edu.unsw.soascheme.extensionservice;

import java.util.ArrayList;

public interface Questions_interface_DAO {

	public ArrayList<Questions_Bean> Questions_List() throws Exception;

	public boolean UpdateURL(Questions_Bean QBean)
			throws Exception;

}
