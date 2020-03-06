package au.edu.unsw.stackoverflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public servlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Questions_Bean> QuestionsList = new ArrayList<Questions_Bean>();
		Map<String, ArrayList<String>> KeywordsMap = new HashMap<String, ArrayList<String>>();
		String path = request.getSession().getServletContext().getRealPath("");
		QuestionsList = (ArrayList<Questions_Bean>) GetDataFile
				.findAllData(path);
		// try {
		// QuestionsList = DAO_Factory.GetQuestionsDAOInstance()
		// .Questions_List();
		// KeywordsMap = DAO_Factory.GetKeywordsDAOInstance().GetKeywordsMap();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		request.setAttribute("QuestionsList", QuestionsList);
//		request.setAttribute("KeywordsMap", KeywordsMap);

		request.getRequestDispatcher("/Welcome.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String findkey = request.getParameter("string")
				+ " in computer science field";
		String youTubeId = "";

		if (findkey != null) {
			String path = request.getSession().getServletContext()
					.getRealPath("");
			youTubeId = "https://www.youtube.com/embed/"
					+ jsSearch.getID(path, findkey);
			// System.out.println(findkey + "-->youTubeId: "+youTubeId);
		} else {
			youTubeId = "https://www.youtube.com/embed/YisbVr69r7U";
		}

		request.setAttribute("YouTubeId", youTubeId);

		request.getRequestDispatcher("/findkey.jsp").forward(request, response);

	}

}
