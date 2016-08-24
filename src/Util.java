
// (Simple) Site Generator
// @author: Sebastian Safari
// This class will be a utility to read/save Strings to/from text files.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Util {
	public static String readFile(String path) {

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "String Failed to Load.";

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

} //End Util Class