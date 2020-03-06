package au.edu.unsw.stackoverflow;

import java.io.Serializable;

public class Questions_Bean implements Serializable {

	private static final long serialVersionUID = -4567038697530789848L;

	private int id;
	private String title;
	private String question;
	private String org_answer;
	private String answer;
	private String url;

	public Questions_Bean() {
		super();
	}

	public String getOrg_answer() {
		return org_answer;
	}

	public void setOrg_answer(String org_answer) {
		this.org_answer = org_answer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
