
// (Simple) Site Generator
// @author: Sebastian Safari
// This class will be a utility to read/save Strings to/from text files.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Util {
	public static String readFileToString(String path) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\r\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "String Failed to Load.";
		}
	}

	public static ArrayList<String> readFileToArrayList(String path) {
		try {
			ArrayList<String> ret = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while (line != null) {
				ret.add(line);
				line = br.readLine();
			}
			br.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean writeFile(String content, String path) {
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();

			FileWriter wr = new FileWriter(file);

			wr.write(content);
			wr.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean appendFile(String content, String path) {
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();

			FileWriter wr = new FileWriter(file, true);

			wr.write(content);
			wr.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean replaceStringInFIle(String path, String from, String to) {
		String out = "";
		String ret = "";
		out = readFileToString(path);
		ret = out.replaceAll(from, to);

		return writeFile(ret, path);
	}

} // End Util Class