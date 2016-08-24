// (Simple) Site Generator
// @author: Sebastian Safari
// This class will be a utility to read/save Strings to/from text files.

import java.io.*;

public class Util 
{
    public static String readFile(String path)
    {
        
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
        
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
    
            while (line != null) 
            {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (Exception e)
        {
            e.printStackTrace();  
            return "String Failed to Load.";
          
        }
        
    }
    
    public static boolean saveFile(String content, String path)
    {
        return true;
    }
    
    
    
} //End Util Class