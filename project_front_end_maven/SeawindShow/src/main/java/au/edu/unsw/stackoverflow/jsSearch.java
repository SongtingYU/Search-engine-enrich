package au.edu.unsw.stackoverflow;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class jsSearch {

	public static String getID(String path, String searchKey) {
		String cmd1 = "chmod 755 "+path + "findkey.py";
		Runtime run1 = Runtime.getRuntime();
		try {
			Process p1=run1.exec(cmd1);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String cmd = path + "findkey.py "+ "\""+searchKey+"\"";
		
		System.out.println(cmd);
//		String cmd = "cmd.exe /c echo "+ searchKey + "|" + path + "\\findkey.py";
		Runtime run = Runtime.getRuntime();
		String result = "";
		try {
			Process p = run.exec(cmd);
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
			}
			br.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
