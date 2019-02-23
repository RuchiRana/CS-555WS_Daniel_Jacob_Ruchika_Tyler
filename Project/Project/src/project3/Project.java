package project3;

import java.io.*;
import java.time.*;
import java.util.*;


public class Project 
{	
	HashMap<Integer,List<String>> validTags;
	HashSet<String> ids;
	HashSet<String> famIds;
	Hashtable<String, String> dates;
	Hashtable<String, String> names;
	public List<String[]> indiDetails;
	public List<String[]> famDetails;
	String tagsFilePath;
	String projFilePath;
	String[] saveIndi;
	String[] saveFam;	
	int counter;
	String prevTag;
	String famPrevTag;
	
	
	public Project(String tagsFilePath, String projFilePath)
	{
		this.tagsFilePath = tagsFilePath;
		this.projFilePath = projFilePath;
		this.indiDetails = new ArrayList<String[]>();
		this.famDetails = new ArrayList<String[]>();
		this.saveIndi = new String[9];
		this.saveFam = new String[8];
		this.counter = 0;
		this.prevTag = "";
		this.famPrevTag = "";
		this.dates = new Hashtable<String,String>();
		this.ids = new HashSet<String>();
		this.famIds = new HashSet<String>();
		this.names = new Hashtable<String, String>();
		initDates();
	}
	
	private void initDates()
	{
		dates.put("JAN", "01");
		dates.put("FEB", "02");
		dates.put("MAR", "03");
		dates.put("APR", "04");
		dates.put("MAY", "05");
		dates.put("JUN", "06");
		dates.put("JUL", "07");
		dates.put("AUG", "08");
		dates.put("SEP", "09");
		dates.put("OCT", "10");
		dates.put("NOV", "11");
		dates.put("DEC", "12");	
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
	
	private void printArr()
	{
		for(int i = 0; i < saveIndi.length; i++)
		{
			System.out.println(saveIndi[i]);
		}
	}
	
	private void printArr(String[] arr)
	{	
		for(int i = 0; i < arr.length; i++)
		{
			
			System.out.print("\t|\t" +arr[i]);
			//System.out.format(format, arr[i]);
		}
		System.out.println("\t|");
		//System.out.println("----------------------------");
	}
	
	private void printList(List<String[]> list)
	{
		//System.out.println(details.size());
		for(int i = 0; i < list.size(); i++)
		{
			printArr(list.get(i));
		}
	}
	
	public void fillIndiList(String tag, String data)
	{
		if(tag.equals("INDI"))
		{
			if(!ids.contains(data))
			{
				saveIndi = new String[9];
			}
			else
			{
				ids.add(data);
			}
			
			saveIndi[0] =  data;
			
			//set defaults
			saveIndi[5] = "True"; //Alive
			saveIndi[6] = "N/A"; //Death
			saveIndi[7] = "N/A"; //Child
			saveIndi[8] = "N/A"; //Spouse
		}

		if(tag.equals("NAME"))
		{
			saveIndi[1] = data;
			if(saveIndi[0] != null)
				names.put(saveIndi[0],saveIndi[1]);
		}
		if(tag.equals("SEX"))
		{
			saveIndi[2] = data;
		}
		if(tag.equals("DATE"))
		{
			//HANDLE AGE
			if(prevTag.equals("BIRT"))
			{
    			String month = dates.get(data.substring(data.indexOf(" ") + 1).substring(0,data.substring(data.indexOf(" ") + 1).indexOf(" ")));
    			String day = data.substring(0,data.indexOf(" "));
    			String year = data.substring(data.indexOf(" ") + 1).substring(data.substring(data.indexOf(" ") + 1).indexOf(" ") + 1);
    			
				saveIndi[3] = year + "-" + month + "-" + day;
				
				//Handle age
				LocalDate today = LocalDate.now(); //Todays date                         
				LocalDate birthday = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));  //Birth date
				Period p = Period.between(birthday, today);
				
				//Age will be the caluclated year
				saveIndi[4] = "" + p.getYears();
			}
		
			if(prevTag.equals("DEAT"))
			{
				String month = dates.get(data.substring(data.indexOf(" ") + 1).substring(0,data.substring(data.indexOf(" ") + 1).indexOf(" ")));
    			String day = data.substring(0,data.indexOf(" "));
    			String year = data.substring(data.indexOf(" ") + 1).substring(data.substring(data.indexOf(" ") + 1).indexOf(" ") + 1);
    			
    			saveIndi[5] = "False"; //Dead
				saveIndi[6] = year + "-" + month + "-" + day; //Death date
			}
		}
		if(tag.equals("FAMS")) //Spouses
		{
			saveIndi[7] = "{" + data + "}";
		}
		
		if(tag.equals("FAMC")) //Children
		{
			saveIndi[8] = "{" + data + "}";
			indiDetails.add(saveIndi);
		}
		
		prevTag = tag;
	}
	
	private void fillFamList(String tag, String data)
	{
		if(tag.equals("FAM"))
		{
			if(!famIds.contains(data))
			{
				saveFam = new String[8];
			}
			else
			{
				famIds.add(data);
			}

			saveFam[0] =  data;
			
			//set defaults
			saveFam[1] = "N/A"; //Married
			saveFam[2] = "N/A"; //Divorced
			saveFam[3] = "N/A"; //HusbandID
			saveFam[4] = "N/A"; //HusbandName
			saveFam[5] = "N/A"; //WifeID
			saveFam[6] = "N/A"; //WifeName
			saveFam[7] = "N/A"; //Children

		}
		
		if(tag.equals("HUSB"))
		{
			//Husband ID
			saveFam[3] = data;
			//Get Husband Name
			saveFam[4] = names.get(data);
		}
		
		if(tag.equals("WIFE"))
		{
			//Wife ID
			saveFam[5] = data;
			//Get Wife Name
			saveFam[6] = names.get(data);
		}
		
		if(tag.equals("CHIL"))
		{
			saveFam[7] = "{" + data + "}";
		}
		
		if(tag.equals("DATE"))
		{
			if(famPrevTag.equals("MARR"))
			{
				String month = dates.get(data.substring(data.indexOf(" ") + 1).substring(0,data.substring(data.indexOf(" ") + 1).indexOf(" ")));
    			String day = data.substring(0,data.indexOf(" "));
    			String year = data.substring(data.indexOf(" ") + 1).substring(data.substring(data.indexOf(" ") + 1).indexOf(" ") + 1);
    			
				saveFam[1] = year + "-" + month + "-" + day;
				famDetails.add(saveFam);
			}
		}
		
		famPrevTag = tag;
	}
	
	
	private void printIndividuals()
	{
		System.out.println("\n\tIndividuals");
    	System.out.print("\t");
    	for(int i=0; i<80; i++)
	  	{
	  		System.out.print("--");
	  	}
    	System.out.println();
		
    	String format = "|\t%1$-8s|\t%2$-16s|\t%3$-8s|\t%4$-16s|\t%5$-8s|\t%6$-8s|\t%7$-8s|\t%8$-8s|\t%9$-8s| \n";
		System.out.print("\t");
		System.out.format(format,"ID","Name","Gender","Birthday","Age","Alive","Death","Child","Spouse");
		System.out.print("\t");
		
		for(int i=0; i<80; i++)
	  	{
	  		System.out.print("--");
	  	}
		
    	System.out.println();
	}
	
	private void printFamilies()
	{
		System.out.println("\tFamilies");
    	System.out.print("\t");
    	for(int i=0; i<51; i++)
	  	{
	  		System.out.print("---");
	  	}
    	
    	System.out.println();
		System.out.print("\t");
		String format1 = "|\t%1$-8s|\t%2$-16s|   %3$-12s|    %4$-11s|\t%5$-16s|    %6$-11s|\t%7$-16s|   %8$-12s| \n";
		System.out.format(format1,"ID","Married","Divorced","Husband ID","Husband Name","Wife Id","Wife Name","Children");
		System.out.print("\t");
		for(int i=0; i<51; i++)
	  	{
	  		System.out.print("---");
	  	}
		
    	System.out.println();
	}
	
	public void run() throws IOException
	{
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
	    		int id = Integer.parseInt(projLine.substring(0,1));
	    		//Check Validity
	    		tagIsValid = checkValidTag(id, tag);
	    		
	    		//Print line
	    		//System.out.println("--> "  + projLine);
	    		
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
	    	
	    		if(tag.equals("INDI") || tag.equals("FAM"))
	    		{
	    			String temp = projLine.substring(projLine.indexOf(" ") + 1);
	    			arguments = temp.substring(0,temp.indexOf(" "));
	    		}
	    		print = print + arguments + "\n";
	    		
	    		//System.out.print(print);
	    		print = "";
	    		
	    		fillIndiList(tag, arguments);
	    		fillFamList(tag, arguments);
	    		
	    		projLine = proj.readLine();
	    	}

	    	//Adjust
	    	if(indiDetails.get(0)[0] == null)
	    		indiDetails.remove(0);
	    	if(famDetails.get(0)[0] == null)
	    		famDetails.remove(0);
	    	
	    	//Print Individuals 
	    	
	    	printIndividuals();
	    	
	    	printList(indiDetails);
	    	
	    	System.out.print("\t");
	    	for(int i=0; i<80; i++)
		  	{
		  		System.out.print("--");
		  	}
	    	System.out.println();
	    	
	    	//Print Families
	    	printFamilies();
	    	
	    	printList(famDetails);
	    	
	    	System.out.print("\t");
			for(int i=0; i<51; i++)
		  	{
		  		System.out.print("---");
		  	}
			
	    }
	    catch(IOException e)
	    {
	    	System.out.println("Project File Not Found");
	    	e.printStackTrace();
	    }   
	    
	}
}
