import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * (Simple) Site Generator
 * 
 * @author Sebastian Safari (ssebs) Idea for the program: generate website from
 *         txt files TODO:-Bootstrap Styling TODO:-Different layouts of pages:
 *         Home, Text Only, "Portfolio", Gallery, Contact TODO:-Read Number of
 *         files, make them all link to eachother in a toolbar TODO:-Make
 *         Snippets (things like image galleries, columns, background things)
 */

public class Main {

	private static final String _HEAD_PATH = "genFiles/head.html";
	private static final String _BODY_CONTENT_PATH = "genFiles/bodyContents.html";
	private static final String _BODY_END_PATH = "genFiles/bodyEnd.html";

	private static final String _ORIG_HEAD_PATH = "genFiles/orig/head.html";
	private static final String _ORIG_BODY_CONTENT_PATH = "genFiles/orig/bodyContents.html";
	private static final String _ORIG_BODY_END_PATH = "genFiles/orig/bodyEnd.html";

	private static final String _HOME_PAGE_PATH = "__site/index.html";
	private static final String _VAR_PATH = "genFiles/var.txt";
	private static final String _HOME_GEN_FILE_PATH = "_textSite/Home.txt";

	private static String _author, _description;

	public static void main(String[] args) {
		System.out.println("Welcome to the Site Generator.");
		initVars();
		replaceHome();
		compileHome();
		System.out.println("index.html page has been generated at: " + _HOME_PAGE_PATH);
		System.out.println("Completed. Please see \"__site\" to view generated site files.");
	}

	private static void initVars() {
		ArrayList<String> vars = new ArrayList<String>();
		vars = Util.readFileToArrayList(_VAR_PATH);

		for (String s : vars) {
			if (s.startsWith("author:")) {
				_author = s.substring("author:".length()).trim();
			} else if (s.startsWith("description:")) {
				_description = s.substring("description:".length()).trim();
			}
		}

		// copy master files to working dir
		try {
			Files.copy(new File(_ORIG_HEAD_PATH).toPath(), new File(_HEAD_PATH).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(new File(_ORIG_BODY_CONTENT_PATH).toPath(), new File(_BODY_CONTENT_PATH).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(new File(_ORIG_BODY_END_PATH).toPath(), new File(_BODY_END_PATH).toPath(),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void replaceHome() {
		// Syntax of these files are subject to change.
		ArrayList<String> homeLines = new ArrayList<String>();
		homeLines = Util.readFileToArrayList(_HOME_GEN_FILE_PATH);

		String homeStr = Util.readFileToString(_HOME_GEN_FILE_PATH);

		// Below generates the metadata in the head.
		for (String s : homeLines) {
			if (s.startsWith("MetaTitle:")) {
				Util.replaceStringInFIle(_HEAD_PATH, "#TITLE#", s.substring("MetaTitle:".length()).trim());
			}
		}
		Util.replaceStringInFIle(_HEAD_PATH, "#AUTHOR#", _author);
		Util.replaceStringInFIle(_HEAD_PATH, "#DESCRIPTION#", _description);

		/// Below generates the body contents
		for (String s : homeLines) {
			if (s.startsWith("#")) {
				// ignore comments
			} else if (s.startsWith("MetaTitle:")) {
				// ignore this line
			} else if (s.startsWith("BodyTitle:")) {
				/* This is to generate the main header (h1) */
				String str = "<h1>" + s.substring("BodyTitle:".length()).trim() + "</h1>";
				Util.appendFile(str + System.lineSeparator(), _BODY_CONTENT_PATH);

			} else if (s.startsWith(":2col:") && !s.endsWith("END")) {
				makeTwoCol(homeStr);

				// TODO: Fix this

			} else if (s.startsWith(":2col:")) {
				// seems to be needed
			} else {
				Util.appendFile(s + System.lineSeparator(), _BODY_CONTENT_PATH);
				// TODO: Add rules to this, below should be a last resort.

			}
		}

		Util.appendFile(System.lineSeparator() + "<!-- This content is generated -->", _BODY_CONTENT_PATH);

	}

	private static void makeTwoCol(String homeStr) {
		String[] twoCol = homeStr.split(":2col:");
		String[] colContents = twoCol[1].split("\\[");

		colContents[1] = colContents[1].replaceAll("\\]", "");
		colContents[2] = colContents[2].replaceAll("\\]", "");

		// System.out.println(colContents[1]); // contents of 2col
		// System.out.println(colContents[2]); // contents of 2col

		String s1 = "<div class='col2'>" + colContents[1] + "</div>";
		String s2 = "<div class='col2'>" + colContents[2] + "</div>";

		Util.appendFile("<div class='clear-float'></div>", _BODY_CONTENT_PATH);
		Util.appendFile(s1 + System.lineSeparator(), _BODY_CONTENT_PATH);
		Util.appendFile(s2 + System.lineSeparator(), _BODY_CONTENT_PATH);
		Util.appendFile("<div class='clear-float'></div>", _BODY_CONTENT_PATH);
	}

	private static void compileHome() {
		String head = Util.readFileToString(_HEAD_PATH);
		String body = Util.readFileToString(_BODY_CONTENT_PATH);
		String bodyEnd = Util.readFileToString(_BODY_END_PATH);

		Util.writeFile(head + body + bodyEnd, _HOME_PAGE_PATH);
	}

} // End Main class