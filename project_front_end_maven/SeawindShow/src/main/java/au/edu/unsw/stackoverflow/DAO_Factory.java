package au.edu.unsw.stackoverflow;

public class DAO_Factory {

	public static Questions_interface_DAO GetQuestionsDAOInstance() {
		return new Questions_DAOProxy();
	}

	public static Keywords_interface_DAO GetKeywordsDAOInstance() {
		return new Keywords_DAOProxy();
	}
}
