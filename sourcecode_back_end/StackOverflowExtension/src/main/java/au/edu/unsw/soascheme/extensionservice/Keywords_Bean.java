package au.edu.unsw.soascheme.extensionservice;

import java.io.Serializable;

public class Keywords_Bean implements Serializable {

	private static final long serialVersionUID = -6127980593421948566L;

	private String keyword;
	private String YouTube_URL1;
	private String YouTube_URL2;
	private String YouTube_URL3;
	private String YouTube_URL4;
	private String YouTube_URL5;

	public Keywords_Bean() {
		super();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getYouTube_URL1() {
		return YouTube_URL1;
	}

	public void setYouTube_URL1(String youTube_URL1) {
		YouTube_URL1 = youTube_URL1;
	}

	public String getYouTube_URL2() {
		return YouTube_URL2;
	}

	public void setYouTube_URL2(String youTube_URL2) {
		YouTube_URL2 = youTube_URL2;
	}

	public String getYouTube_URL3() {
		return YouTube_URL3;
	}

	public void setYouTube_URL3(String youTube_URL3) {
		YouTube_URL3 = youTube_URL3;
	}

	public String getYouTube_URL4() {
		return YouTube_URL4;
	}

	public void setYouTube_URL4(String youTube_URL4) {
		YouTube_URL4 = youTube_URL4;
	}

	public String getYouTube_URL5() {
		return YouTube_URL5;
	}

	public void setYouTube_URL5(String youTube_URL5) {
		YouTube_URL5 = youTube_URL5;
	}

}
