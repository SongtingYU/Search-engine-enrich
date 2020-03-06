package au.edu.unsw.stackoverflow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetDataFile {

	@SuppressWarnings("resource")
	public static ArrayList<Questions_Bean> findAllData(String path)
			throws IOException {
		ArrayList<Questions_Bean> QuestionsList = new ArrayList<Questions_Bean>();

		path += "/data/";
		String tmpPath;
		for (int i = 0; i < 30; i++) {
			Questions_Bean tmpQuestion = new Questions_Bean();
			String sLine = "";
			String title = "";
			String question = "";
			String oanswer = "";
			String answer = "";
			String url = "";
			BufferedReader sr;

			tmpPath = path + i + ".t";
			sLine = "";
			sr = new BufferedReader(new FileReader(tmpPath));
			while ((sLine = sr.readLine()) != null) {
				title = title + " " + sLine;
			}

			tmpPath = path + i + ".q";
			sLine = "";
			sr = new BufferedReader(new FileReader(tmpPath));
			while ((sLine = sr.readLine()) != null) {
				question = question + " " + sLine;
			}

			tmpPath = path + i + ".o";
			sLine = "";
			sr = new BufferedReader(new FileReader(tmpPath));
			while ((sLine = sr.readLine()) != null) {
				oanswer = oanswer + " " + sLine;
			}

			tmpPath = path + i + ".a";
			sLine = "";
			sr = new BufferedReader(new FileReader(tmpPath));
			while ((sLine = sr.readLine()) != null) {
				answer = answer + " " + sLine;
			}

			tmpPath = path + i + ".u";
			sLine = "";
			sr = new BufferedReader(new FileReader(tmpPath));
			while ((sLine = sr.readLine()) != null) {
				url = sLine;
			}

			tmpQuestion.setId(i);
			tmpQuestion.setQuestion(question);
			tmpQuestion.setOrg_answer(oanswer);
			tmpQuestion.setAnswer(answer);
			tmpQuestion.setTitle(title);
			tmpQuestion.setUrl(url);
			QuestionsList.add(tmpQuestion);
		}

		return QuestionsList;
	}
}
