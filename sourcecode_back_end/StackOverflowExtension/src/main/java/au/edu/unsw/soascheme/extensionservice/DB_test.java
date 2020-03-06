package au.edu.unsw.soascheme.extensionservice;

import java.util.ArrayList;

public class DB_test {

	public static void main(String[] args) {
		ArrayList<Questions_Bean> Questions_List = new ArrayList<Questions_Bean>();
		try {
			Questions_List = DAO_Factory.GetQuestionsDAOInstance()
					.Questions_List();
			for (Questions_Bean tempBean : Questions_List) {
				System.out.println(tempBean.getId() + ":");
				System.out.println(" Title:");
				System.out.println("  " + tempBean.getTitle());
				System.out.println();
				System.out.println(" Question:");
				System.out.println("  " + tempBean.getQuestion());
				System.out.println();
				System.out.println(" ORG_Answer:");
				System.out.println("  " + tempBean.getOrg_answer());
				System.out.println();
				System.out.println(" Answer:");
				System.out.println("  " + tempBean.getAnswer());
				System.out.println();
				System.out.println(" URL:");
				System.out.println("  " + tempBean.getUrl());
				System.out.println();
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Keywords_Bean keyword_URL = new Keywords_Bean();

		try {
			keyword_URL = DAO_Factory.GetKeywordsDAOInstance()
					.GETYouTube_URL_List("java");
			System.out.println(keyword_URL.getKeyword() + ": ");
			System.out.println(" " + keyword_URL.getYouTube_URL1());
			System.out.println(" " + keyword_URL.getYouTube_URL2());
			System.out.println(" " + keyword_URL.getYouTube_URL3());
			System.out.println(" " + keyword_URL.getYouTube_URL4());
			System.out.println(" " + keyword_URL.getYouTube_URL5());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Questions_Bean qBean = new Questions_Bean();

		qBean.setId(6);
		qBean.setUrl("test5");
		try {
			System.out.println();
			System.out
					.println("Database Questions Table No."
							+ qBean.getId()
							+ " update "
							+ (DAO_Factory.GetQuestionsDAOInstance().UpdateURL(
									qBean) ? "successfully." : "error."));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
