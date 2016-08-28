import java.util.ArrayList;

/**(Simple) Site Generator
* @author Sebastian Safari (ssebs)
* Idea for the program: generate website from txt files
* 	TODO:-Bootstrap Styling
* 	TODO:-Different layouts of pages: Home, Text Only, "Portfolio", Gallery, Contact
*	TODO:-Read Number of files, make them all link to eachother in a toolbar 
*/

public class Main
{

	private static final String _HEAD_PATH = "genFiles/head.html";
	private static final String _BODY_CONTENT_PATH = "genFiles/bodyContents.html";
	private static final String _BODY_END_PATH = "genFiles/bodyEnd.html";
	private static final String _HOME_PAGE_PATH = "__site/index.html";
	private static final String _VAR_PATH = "genFiles/var.txt";

	private static String _author, _description;

	private static void init()
	{
		ArrayList<String> vars = new ArrayList<String>();
		vars = Util.readFileToArrayList(_VAR_PATH);
		for (String s : vars)
		{
			if (s.startsWith("author:"))
			{
				_author = s.substring("author:".length());
			} else if (s.startsWith("description:"))
			{
				_description = s.substring("description:".length());
			}
		}

		//Clear contents of genFile: bodyContents.html
		Util.writeFile("", _BODY_CONTENT_PATH);

	}

	private static void replaceHome()
	{
		// Syntax of these files are subject to change.
		ArrayList<String> homeLines = new ArrayList<String>();

		homeLines = Util.readFileToArrayList("_textSite/Home.txt");

		// Below generates the metadata in the head.
		for (String s : homeLines)
		{
			if (s.startsWith("MetaTitle:"))
			{
				Util.replaceStringInFIle(_HEAD_PATH, "#TITLE#", s.substring("MetaTitle:".length()));
			}
		}
		Util.replaceStringInFIle(_HEAD_PATH, "#AUTHOR#", _author);
		Util.replaceStringInFIle(_HEAD_PATH, "#DESCRIPTION#", _description);

		/// Below generates the body contents
		for (String s : homeLines)
		{
			if (s.startsWith("<Home>") || (s.startsWith("MetaTitle:")))
			{
				/*Having the code like this makes it easier to read. Above is selecting 
				  	the stuff that's not the body contents.*/
			} else if (s.startsWith("BodyTitle:"))
			{
				/*This is to generate the main header (h1)*/
				String str = "<h1>" + s.substring("BodyTitle:".length()).trim() + "</h1>";
				Util.appendFile(str + System.lineSeparator(), _BODY_CONTENT_PATH); // Will add every line, needs rules
			} else
			{
				//TODO: Add rules to this, below should be a last resort.
				String str = "<p>" + s + "</p>";
				Util.appendFile(str + System.lineSeparator(), _BODY_CONTENT_PATH); // Will add every line, needs rules
			} // Final else: this should just be a <p>
		}

		Util.appendFile("<!-- This content is generated -->", _BODY_CONTENT_PATH);

	}

	private static void generateHome()
	{
		String head = Util.readFileToString(_HEAD_PATH);
		String body = Util.readFileToString(_BODY_CONTENT_PATH);
		String bodyEnd = Util.readFileToString(_BODY_END_PATH);

		Util.writeFile(head + body + bodyEnd, _HOME_PAGE_PATH);
	}

	public static void main(String[] args)
	{
		System.out.println("Welcome to the Site Generator.");
		init();
		replaceHome();
		generateHome();
		System.out.println("index page has been generated at: " + _HOME_PAGE_PATH);

		System.out.println("Completed. Please see \"__site\" to view generated site files.");
	}

} // End Main class 