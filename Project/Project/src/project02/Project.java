package project02;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Project 
{	
	HashMap<Integer,List<String>> validTags;
	List<String[]> details;
	String tagsFilePath;
	String projFilePath;
	
	
	public Project(String tagsFilePath, String projFilePath)
	{
		this.tagsFilePath = tagsFilePath;
		this.projFilePath = projFilePath;
		this.details = new ArrayList<String[]>();
	}
	
	public boolean checkValidTag(int id, String tag)
	{
		//Unsupported id
		if(validTags.get(id) == null)
		{
			return false;
		}
		
		return validTags.get(id).contains(tag);
	}
	
	public String getTag(String projLine)
	{
		String tag = "";
		
		//Handle INDI
		if(projLine.contains("INDI") || (projLine.contains("FAM") && (!projLine.contains("FAMC") && !projLine.contains("FAMS"))))
		{
			if(projLine.contains("INDI"))
			{
				//index of where INDI starts
				int index = projLine.indexOf("INDI");
				//Get INDI
				tag = projLine.substring(index, index + "INDI".length() );		
			}
					
			//Handle FAM and determine if tag is FAM or FAMC/S
			if(projLine.contains("FAM") && (!projLine.contains("FAMC") && !projLine.contains("FAMS")))
			{
				//index of where FAM starts
				int index = projLine.indexOf("FAM");
				//Get FAM
				tag = projLine.substring(index, index + "FAM".length());
			}
		}
		else
		{
	    	try 
	    	{
	    		 tag = projLine.substring(2).substring(0,projLine.substring(2).indexOf(" "));
	    	}
	    	catch(StringIndexOutOfBoundsException e)
	    	{
	    		 tag = projLine.substring(2).substring(0);
	    	}
		}
		
		return tag;
	}
	
	public void saveInfo(String line)
	{
		
	}
	
	public void run() throws IOException
	{
		String save = "";
		BufferedReader proj = null;
		BufferedReader tagsFile = null;
		validTags = new HashMap<Integer,List<String>>();
		List<String> list0 = new ArrayList<String>();
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		boolean tagIsValid = false;
		
		String print = "";
	    
	    try 
	    {
	    	tagsFile = new BufferedReader(new FileReader(tagsFilePath));
	    	String txtLine = tagsFile.readLine();
	    	while(txtLine != null)
	    	{
	    		int id = Integer.parseInt(txtLine.substring(0,1));
	    		String tag = txtLine.substring(2);
		    
	    		switch(id)
		    	{
		    		case 0:
		    			list0.add(tag);
		    			break;
		    		
		    		case 1:
		    			list1.add(tag);
		    			break;
		    			
		    		case 2:
		    			list2.add(tag);
		    			break;
		    		
		    		default:
		    			System.out.println("Unsupported ID: " + id);
		    			break;
		    	}
	    	
	    		
	    		txtLine = tagsFile.readLine();
	    	}
	    	validTags.put(0,list0);
	    	validTags.put(1,list1);
	    	validTags.put(2,list2);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Tags File Not Found");
	    	e.printStackTrace();
	    }
	    
	    	
	   try 
	   {
	    	proj = new BufferedReader(new FileReader(projFilePath));
	    	String projLine = proj.readLine();
	    
	    	
	    	while(projLine != null)
	    	{	    		
	    		//get tag
	    		String tag = getTag(projLine);
	    		    		
	    		//Check Validity
	    		tagIsValid = checkValidTag(Integer.parseInt(projLine.substring(0,1)), tag);
	    		
	    		//Print line
	    		System.out.println("--> "  + projLine);
	    		
	    		//Prints details
	    		print = print + "<-- ";
	    		
	    		//Print level
	    		String level = projLine.substring(0,1);
	    		print = print + level + "|";
	    		
	    		print = print + tag + "|";
	    		 //******************************************
	    		//Handle INDI and FAM  		
	    		if(tag.equals("INDI"))
	    		{
	    			if(projLine.indexOf("INDI") < 3)
	    			{
	    				tagIsValid = false;
	    			}
	    			else
	    			{
	    				tagIsValid = true;
	    			}
	    		}
	    		
	    		if(tag.equals("FAM"))
	    		{
	    			if(projLine.indexOf("FAM") < 3)
	    			{
	    				tagIsValid = false;
	    			}
	    			else
	    			{
	    				tagIsValid = true;
	    			}
	    		}
	    	
	    		
	    		//Check if tag is valid
	    		if(tagIsValid)
	    		{
	    			print = print + "Y|";
	    		}
	    		else
	    		{
	    			print = print + "N|";
	    		}
	    		
	    		//Print arguments
	    		String arguments = projLine.substring(2).substring(projLine.substring(2).indexOf(" ") + 1);
	    		if(arguments.equals(tag))
	    		{
	    			arguments = "";
	    		}
	    		print = print + arguments + "\n";
	    		
	    		System.out.print(print);
	    		print = "";
	    		
	    		
	    		projLine = proj.readLine();
	    	}
	    }
	    catch(IOException e)
	    {
	    	System.out.println("Project File Not Found");
	    	e.printStackTrace();
	    }   
	    
	}
}
