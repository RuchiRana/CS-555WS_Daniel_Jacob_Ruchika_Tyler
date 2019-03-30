package project6;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.*;

public class Project 
{	
	String mdate[] = new String[1000],  fid[] = new String[1000], hid[] = new String[1000], wid[] = new String[1000],
		   divdate[] = new String[1000], cid[] = new String[1000], bdate[] = new String[1000];
	String iid[] = new String[1000], ddate[]  = new String[1000];
	int c=0, c1=0, d=0, d1=0, e=0, e1=0 ;
	
	
	boolean boo = true;
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
	
	enum Dates {
		BDAY, DEATH;
	}
	
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
	
	private void printArr(String[] arr)
	{	
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i].contains("N/A"))
			{
				System.out.print("\t|\t\t" +arr[i]);
			}
			else
				System.out.print("\t|\t    " +arr[i]);
		}
		System.out.println("\t|");
	}
	
	private void printList(List<String[]> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			printArr(list.get(i));
		}
	}
	
	public void fillIndiList(String tag, String data)
	{
		if(tag.equals("INDI"))
		{
			indiDetails.add(saveIndi);
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
				//Save the date
				String[] dataArr = getFormattedDate(data);
				saveIndi[3] = dataArr[4];
				
				//automated Test
				checkDates(dataArr);
				
				//Handle age
				LocalDate today = LocalDate.now(); //Todays date                         
				LocalDate birthday = LocalDate.of(Integer.parseInt(dataArr[3]), Integer.parseInt(dataArr[2]), Integer.parseInt(dataArr[1]));  //Birth date
				Period p = Period.between(birthday, today);
				
				//Age will be the caluclated year
				saveIndi[4] = "" + p.getYears();
			}
		
			if(prevTag.equals("DEAT"))
			{
				//Save the date
				String[] dataArr = getFormattedDate(data);
				checkDates(dataArr);
    			
    			saveIndi[5] = "False"; //Dead
				saveIndi[6] = dataArr[4];; //Death date
			}
		}
		if(tag.equals("FAMC")) //Children
		{
			saveIndi[7] = "{" + data + "}";
		}
		
		if(tag.equals("FAMS")) //Spouses
		{
			saveIndi[8] = "{" + data + "}";
			//indiDetails.add(saveIndi);		 	
		}
		prevTag = tag;
	}
	
	
	private void fillFamList(String tag, String data)
	{
		
		if(tag.equals("FAM"))
		{
			if(boo)
			{
				indiDetails.add(saveIndi);
				boo = false;
			}
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
			String data1="", data2="";
			String[] result = data.split("@");    data1 = result[1];
			if(saveFam[7].contains("N/A"))
			{
				saveFam[7] = "{'" + data1 + "'}";
			}
			else
			{
				String[] result1 = saveFam[7].split("'");    data2 = result1[1];
				saveFam[7] = "{'" + data2 +"," + data1 + "'}";
			}
		}
		if(tag.equals("DATE"))
		{
			if(famPrevTag.equals("MARR"))
			{
				String[] dataArr = getFormattedDate(data);	
				checkDates(dataArr);
				saveFam[1] = dataArr[4];
				famDetails.add(saveFam);
			}
			else if(famPrevTag.equals("DIV"))
			{
				String[] dataArr = getFormattedDate(data);	
				checkDates(dataArr);
				saveFam[2] = dataArr[4];
			}
		}
		
		famPrevTag = tag;
	}
	
	
	private void printIndividuals()
	{
		System.out.println("\n\tIndividuals");
		System.out.print("\t");
    	for(int i=0; i<70; i++)
	  	{
	  		System.out.print("---");
	  	}
    	System.out.println();
		
    	String format = "|\t%1$-16s|\t%2$-24s|\t%3$-8s|\t%4$-16s|\t%5$-8s|\t%6$-16s|\t%7$-16s|\t%8$-16s|\t%9$-16s| \n";
		System.out.print("\t");
		System.out.format(format,"ID","Name","Gender","Birthday","Age","Alive","Death","Child","Spouse");
		System.out.print("\t");
	
    	for(int i=0; i<70; i++)
	  	{
	  		System.out.print("---");
	  	}
		
    	System.out.println();
	}
	
	private void printFamilies()
	{
		System.out.println("\tFamilies");
    	System.out.print("\t");
    	for(int i=0; i<52; i++)
	  	{
	  		System.out.print("----");
	  	}
    	System.out.println();
		System.out.print("\t");
		String format1 = "|\t%1$-16s|\t%2$-16s|   %3$-20s|    %4$-19s|\t%5$-24s|    %6$-19s|\t%7$-24s|   %8$-20s| \n";
		System.out.format(format1,"ID","Married","Divorced","Husband ID","Husband Name","Wife Id","Wife Name","Children");
		System.out.print("\t");
		for(int i=0; i<52; i++)
	  	{
	  		System.out.print("----");
	  	}
		
    	System.out.println();
	}
	
	public void resultstory5()
	{
		//Sets the value of variables
		for(int i = 0; i < famDetails.size(); i++)
		{
			for(int j  = 0; j <famDetails.get(i).length; j++)
			{
				if(famDetails.get(i)[j].contains("@F"))
				{
					String[] result0 = famDetails.get(i)[0].split("@");
		  			fid[c] = result0[1];
					mdate[c] = famDetails.get(i)[1];
					String[] result1 = famDetails.get(i)[3].split("@");
		  			hid[c] = result1[1];
		  			String[] result2 = famDetails.get(i)[5].split("@");
		  			wid[c] = result2[1];
					c++;		
				}
			}
		}
		
		for(int i = 0; i < indiDetails.size(); i++)
		{
			for(int j  = 0; j <indiDetails.get(i).length; j++)
			{
				if(indiDetails.get(i)[j].contains("@I"))
				{
					String[] result1 = indiDetails.get(i)[0].split("@");
		  			iid[c1] = result1[1];
					ddate[c1] = indiDetails.get(i)[6];
					c1++;
				}
			}
		}
		//Implements story 5 and prints
		for(int i = 0; i < famDetails.size(); i++)
		{	
			for(int j = 0; j < indiDetails.size(); j++)
			{
				boolean foo = false;
				if(  hid[i].compareTo(iid[j]) == 0 && !ddate[j].equals("N/A") && !mdate[i].equalsIgnoreCase("N/A") )
				{	
					String[] mdate1 = mdate[i].split("-");
					String myear = mdate1[0];   String mmonth = mdate1[1];   String mday = mdate1[2];
					
					String[] ddate1 = ddate[j].split("-");
					String dyear = ddate1[0];   String dmonth = ddate1[1];	String dday = ddate1[2];
					
					if(Integer.parseInt(myear) < Integer.parseInt(dyear))
					{
						foo = true;
					}
					
					else if(Integer.parseInt(myear) == Integer.parseInt(dyear))
					{
						 if(Integer.parseInt(mmonth) < Integer.parseInt(dmonth))
						 {
								foo = true;
						 }			 	
						 else if(Integer.parseInt(mmonth) == Integer.parseInt(dmonth))
						 {
							 if(Integer.parseInt(mday) <= Integer.parseInt(dday))
							 {
								foo = true;
							 }	
						 }
					}
					if (!foo)
						System.out.println("Error: FAMILY: US05: " + fid[i] + ": Married " + mdate[i] + " after husband's (" + hid[i] + ") death on " + ddate[j]);
				}	
				
				else if( wid[i].compareTo(iid[j]) == 0 && !ddate[j].equals("N/A") )
				{		
					String[] mdate1 = mdate[i].split("-");
					String myear = mdate1[0];   String mmonth = mdate1[1];   String mday = mdate1[2];
					
					String[] ddate1 = ddate[j].split("-");
					String dyear = ddate1[0];   String dmonth = ddate1[1];	String dday = ddate1[2];						
						
					if(Integer.parseInt(myear) < Integer.parseInt(dyear))
					{
						foo = true;
					}							
					else if(Integer.parseInt(myear) == Integer.parseInt(dyear))
					{
						 if(Integer.parseInt(mmonth) < Integer.parseInt(dmonth))
						 {
							foo = true;						
						 }
						 	
						 else if(Integer.parseInt(mmonth) == Integer.parseInt(dmonth))
						 {
							 if(Integer.parseInt(mday) <= Integer.parseInt(dday))
							 {
								foo = true;
							 }		
						 }
					}
					if (!foo)
					{
						System.out.println("Error: FAMILY: US05: " + fid[i] + ": Married " + mdate[i] + " after wife's (" + wid[i] + ") death on " + ddate[j]);
					}
				}
			}
		}
	}
	
	public void resultstory6()
	{
		//Sets the value of variables
		for(int i = 0; i < famDetails.size(); i++)
		{
			for(int j  = 0; j <famDetails.get(i).length; j++)
			{
				if(famDetails.get(i)[j].contains("@F"))
				{
					String[] result0 = famDetails.get(i)[0].split("@");
		  			fid[d] = result0[1]; //Family ID
					divdate[d] = famDetails.get(i)[2]; //Divorce date
					String[] result1 = famDetails.get(i)[3].split("@");
		  			hid[d] = result1[1]; //Husband's ID
		  			String[] result2 = famDetails.get(i)[5].split("@");
		  			wid[d] = result2[1]; // Wife's ID
					d++;		
				}
			}
		}
		
		for(int i = 0; i < indiDetails.size(); i++)
		{
			for(int j  = 0; j <indiDetails.get(i).length; j++)
			{
				if(indiDetails.get(i)[j].contains("@I"))
				{
					String[] result1 = indiDetails.get(i)[0].split("@");
		  			iid[d1] = result1[1]; // Individual ID
					ddate[d1] = indiDetails.get(i)[6]; // Death date
					d1++;
				}
			}
		}
		//Implements story 5 and prints
		for(int i = 0; i < famDetails.size(); i++)
		{	
			for(int j = 0; j < indiDetails.size(); j++)
			{
				boolean foo = false;
				if(  hid[i].compareTo(iid[j]) == 0 && !ddate[j].equals("N/A") && !divdate[i].equalsIgnoreCase("N/A") )
				{	
					String[] divdate1 = divdate[i].split("-");
					String divyear = divdate1[0];   String divmonth = divdate1[1];   String divday = divdate1[2];
					
					String[] ddate1 = ddate[j].split("-");
					String dyear = ddate1[0];   String dmonth = ddate1[1];	String dday = ddate1[2];
					
					if(Integer.parseInt(divyear) < Integer.parseInt(dyear))
					{
						foo = true;
					}
					
					else if(Integer.parseInt(divyear) == Integer.parseInt(dyear))
					{
						 if(Integer.parseInt(divmonth) < Integer.parseInt(dmonth))
						 {
								foo = true;
						 }			 	
						 else if(Integer.parseInt(divmonth) == Integer.parseInt(dmonth))
						 {
							 if(Integer.parseInt(divday) <= Integer.parseInt(dday))
							 {
								foo = true;
							 }	
						 }
					}
					if (!foo)
						System.out.println("Error: FAMILY: US06: " + fid[i] + ": Divorced " + divdate[i] + " after husband's (" + hid[i] + ") death on " + ddate[j]);
				}	
				
				if( wid[i].compareTo(iid[j]) == 0 && !ddate[j].equals("N/A") && !divdate[i].equalsIgnoreCase("N/A"))
				{		
					String[] divdate1 = divdate[i].split("-");
					String divyear = divdate1[0];   String divmonth = divdate1[1];   String divday = divdate1[2];
					
					String[] ddate1 = ddate[j].split("-");
					String dyear = ddate1[0];   String dmonth = ddate1[1];	String dday = ddate1[2];						
						
					if(Integer.parseInt(divyear) < Integer.parseInt(dyear))
					{
						foo = true;
					}							
					else if(Integer.parseInt(divyear) == Integer.parseInt(dyear))
					{
						 if(Integer.parseInt(divmonth) < Integer.parseInt(dmonth))
						 {
							foo = true;						
						 }
						 	
						 else if(Integer.parseInt(divmonth) == Integer.parseInt(dmonth))
						 {
							 if(Integer.parseInt(divday) <= Integer.parseInt(dday))
							 {
								foo = true;
							 }		
						 }
					}
					if (!foo)
					{
						System.out.println("Error: FAMILY: US06: " + fid[i] + ": Divorced " + divdate[i] + " after wife's (" + wid[i] + ") death on " + ddate[j]);
					}
				}
			}
		}
	}
	
	public void resultstory8()
	{
		//Sets the value of variables
		for(int i = 0; i < famDetails.size(); i++)
		{
			for(int j  = 0; j <famDetails.get(i).length; j++)
			{
				if(famDetails.get(i)[j].contains("@F"))
				{
					if(famDetails.get(i)[7].equals("N/A"))
					{
					cid[e] = "N/A";
					}
					else
					{
					String[] result0 = famDetails.get(i)[0].split("@");
		  			fid[e] = result0[1]; //Family ID
					mdate[e] = famDetails.get(i)[1]; //Divorce date
					String[] result1 = famDetails.get(i)[7].split("'");
					cid[e] = result1[1]; //Children's ID
					e++;
					}
				}
			}
		}
		
		for(int i = 0; i < indiDetails.size(); i++)
		{
			for(int j  = 0; j <indiDetails.get(i).length; j++)
			{
				if(indiDetails.get(i)[j].contains("@I"))
				{
					String[] result1 = indiDetails.get(i)[0].split("@");
		  			iid[e1] = result1[1]; // Individual ID
					bdate[e1] = indiDetails.get(i)[3]; // Birth date
					e1++;
				}
			}
		}
		//Implements story 8 and prints
		for(int i = 0; i < famDetails.size(); i++)
		{	
			for(int j = 0; j < indiDetails.size(); j++)
			{
				boolean foo = false;
				if( !cid[i].equals("N/A") && cid[i].compareTo(iid[j]) == 0 && !bdate[j].equals("N/A") && !mdate[i].equalsIgnoreCase("N/A") )
				{	
					String[] mdate1 = mdate[i].split("-");
					String myear = mdate1[0];   String mmonth = mdate1[1];   String mday = mdate1[2];
					
					String[] bdate1 = bdate[j].split("-");
					String byear = bdate1[0];   String bmonth = bdate1[1];	String bday = bdate1[2];
					
					if(Integer.parseInt(myear) < Integer.parseInt(byear))
					{
						foo = true;
					}
					
					else if(Integer.parseInt(myear) == Integer.parseInt(byear))
					{
						 if(Integer.parseInt(mmonth) < Integer.parseInt(bmonth))
						 {
								foo = true;
						 }			 	
						 else if(Integer.parseInt(mmonth) == Integer.parseInt(bmonth))
						 {
							 if(Integer.parseInt(mday) <= Integer.parseInt(bday))
							 {
								foo = true;
							 }	
						 }
					}
					if (!foo)
						System.out.println("Error: FAMILY: US08: " + cid[i] + ": born " + bdate[j] + " before marriage's on " + mdate[i]);
				}
			}
		}
	}
	
	public void No_bigamy() throws ParseException
	{
		Date marriage = new Date(), death = new Date(), samemarriage = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//Sets the value of variables
		for(int i = 0; i < famDetails.size(); i++)
		{
			String fid="",samehid="" , hid="", wid="", samewid="";
			if (!(famDetails.get(i)[1].contains("N/A")))
			{
				marriage = format.parse(famDetails.get(i)[1]);
				String[] result0 = famDetails.get(i)[0].split("@");    fid = result0[1]; //Family's ID
				String[] result1 = famDetails.get(i)[3].split("@");    hid = result1[1]; //Husband's ID
				String[] result2 = famDetails.get(i)[5].split("@");    wid = result2[1]; //Wife's ID
			}
  			for(int j = i+1; j < famDetails.size(); j++)
  			{
  				String[] result4 = famDetails.get(j)[3].split("@");    samehid = result4[1]; //Husband's ID
  				String[] result5 = famDetails.get(j)[5].split("@");    samewid = result5[1]; //Wife's ID
  				samemarriage = format.parse(famDetails.get(j)[1]);
  				if (hid.compareTo(samehid) == 0)
  				{
  					if(marriage.compareTo(samemarriage)==0)
  						System.out.println("Error: FAMILY: US11: " + fid + ": MarriageOnSameDay ocuurs of husband (" + hid + ") and wife (" + wid 
  								+ ") during marriage to another wife (" + samewid + ") on " + format.format(samemarriage));
  				}
  				if (wid.compareTo(samewid) == 0)
  				{
  					if(marriage.compareTo(samemarriage)==0)
  						System.out.println("Error: FAMILY: US11: " + fid + ": MarriageOnSameDay ocuurs of wife (" + wid + ") and husband (" + hid 
  								+ ") during marriage to another husband (" + samehid + ") on " + format.format(samemarriage));
  				}
  			}
		}
	}
	
	public void MaleLastNames() throws ParseException
	{
		//Sets the value of variables
		for(int i = 0; i < famDetails.size(); i++)
		{
			String fid="", hid="", cid="", iid="", hln="", cln="";
			if (!(famDetails.get(i)[1].contains("N/A")))
			{
				String[] result0 = famDetails.get(i)[0].split("@");    fid = result0[1]; //Family's ID
				String[] result1 = famDetails.get(i)[3].split("@");    hid = result1[1]; //Husband's ID
				if(!(famDetails.get(i)[7].contains("N/A")))
				{
					String[] result2 = famDetails.get(i)[7].split("'");    cid = result2[1]; //Children's ID
				}
			}
			for(int j = 0; j < indiDetails.size(); j++)
  			{	
				String[] result4 = indiDetails.get(j)[0].split("@");    iid = result4[1]; //Individula's ID
				if(hid.compareTo(iid) == 0)
  				{
  					String[] result00 = indiDetails.get(j)[1].split("/");    hln = result00[1]; //Husband's LastName
  				}
  			}
  			for(int j = 0; j < indiDetails.size(); j++)
  			{
  				String[] result4 = indiDetails.get(j)[0].split("@");    iid = result4[1]; //Individula's ID
  				String[] cid1 = new String[cid.length()];
  				
  				if(cid.length()>3)
  				{
  					String[] h = cid.split(",");
  					for(int kk=0; kk<h.length; kk++)
  					{
  						if(h[kk].compareTo(iid) == 0)
  						{
  							String[] result00 = indiDetails.get(j)[1].split("/");    cln = result00[1];
  							if(indiDetails.get(j)[2].matches("M") && hln.compareTo(cln) != 0)						
  								System.out.println("Error: FAMILY: US16: " + fid + ": Male members of the family does not have the same last name. Father's (" + hid + ") last name: " + hln + " and Son's (" + h[kk] + ") last name: " + cln );
  						}
  					}
  				}
  				else if(cid.compareTo(iid) == 0)
  				{
  					if(indiDetails.get(j)[2].matches("M")&& hln.compareTo(cln) != 0)
  						System.out.println("Error: FAMILY: US16: " + fid + ": Male members of the family does not have the same last name. Father's (" + hid + ") last name: " + hln + " and Son's (" + cid + ") last name: " + cln );
  				}
  			}
		}
	}
	
	
	
	public boolean compare(String computed)
	{
		return ("F1".equalsIgnoreCase(computed));
	}
	
	public String testString(String test2)
	{
		if(test2.contains("Error"))
			return "Error";
		else 
			return "NOT FOUND.";
	}
	
	public String testString2(String test2)
	{
		if(test2.contains("I4"))
			return "I4";
		else 
			return "NOT FOUND.";
	}
	
	public static void checkBBD(List<String[]> indi) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < indi.size(); i++) {

			if (indi.get(i)[3] == "N/A" || indi.get(i)[6] == "N/A") {
				continue;
			}

			Date birth = format.parse(indi.get(i)[3]);
			Date death = format.parse(indi.get(i)[6]);

			if (death.before(birth)) {
				System.out.println("ERROR: INDIVIDUAL: US03: " + indi.get(i)[0] + " Died " + format.format(death) + " before born " + format.format(birth));
				//return false;
			}
		}
		//return true;
	}
	
public void datesBeforeCurrent(List<String[]> indi, List<String[]> fam) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate curr = LocalDate.now();
		String currdate = curr.format(formatter);
		LocalDate formattedcurr = LocalDate.parse(currdate, formatter);
		LocalDate bday;
		LocalDate dday;
		LocalDate mday;
		LocalDate divday;
	
		for (int i = 0; i < indi.size(); i++) {
			if (indi.get(i)[3] == "N/A") {
				bday = LocalDate.MIN;
			} else {
				bday = LocalDate.parse(indi.get(i)[3], formatter);
			}
			if (indi.get(i)[6] == "N/A") {
				dday = LocalDate.MIN;
			} else {
				dday = LocalDate.parse(indi.get(i)[6], formatter);
			}
			if (bday.isAfter(formattedcurr)) {
				System.out.println("ERROR: INDIVIDUAL: US01: " + indi.get(i)[0] + ": Birthday " + bday.format(formatter) + " occurrs in the future.");
			}
			if (dday.isAfter(formattedcurr)) {
				System.out.println("ERROR: INDIVIDUAL: US01: " + indi.get(i)[0] + ": Death " + bday.format(formatter) + " occurrs in the future.");
			}
		}
		
		for (int j = 0; j < fam.size(); j++) {
			if (fam.get(j)[1] == "N/A") {
				mday = LocalDate.MIN;
			} else {
				mday = LocalDate.parse(fam.get(j)[1], formatter);
			}
			if (fam.get(j)[2] == "N/A") {
				divday = LocalDate.MIN;
			} else {
				divday = LocalDate.parse(fam.get(j)[2], formatter);
			}
			if (mday.isAfter(formattedcurr)) {
				System.out.println("ERROR: FAMILY: US01: " + fam.get(j)[0] + ": Marriage date " + mday.format(formatter) + " occurrs in the future.");
			}
			if (divday.isAfter(formattedcurr)) {
				System.out.println("ERROR: FAMILY: US01: " + fam.get(j)[0] + ": Divorce date " + divday.format(formatter) + " occurrs in the future.");
			}
			
		}
	}
	
	public void marriageBeforeDivorce(List<String[]> fam) {
		
		LocalDate mday;
		LocalDate divday;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		for (int i = 0; i < fam.size(); i++) {
			if (fam.get(i)[1] == "N/A" || fam.get(i)[2] == "N/A") {
				continue;
			}
			mday = LocalDate.parse(fam.get(i)[1], formatter);
			divday = LocalDate.parse(fam.get(i)[2], formatter);
			if (mday.isAfter(divday)) {
				System.out.println("ERROR: FAMILY: US04: " + fam.get(i)[0] + ": Divorced " + divday.format(formatter) + " before married " + mday.format(formatter));
			}
		}
	}
	public static void checkBBM(List<String[]> indi, List<String[]> fam) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < fam.size(); i++) {
			String dateM = fam.get(i)[1];

			if (dateM == "N/A") {
				continue;
			}

			Date dm = format.parse(dateM);
			//System.out.println(dateM);

			if (fam.get(i)[3] != "None") {
				Date husb = getIndiDate(indi, fam.get(i)[3], Dates.BDAY);
				//System.out.println(husb.toString());
				if (dm.before(husb)) {
					System.out.println("ERROR: FAMILY: US02: " + fam.get(i)[0] + ": Husband birthdate " + format.format(husb) + " is after marriage date " + format.format(dm));
					//return false;
				}
			}
			
			if (fam.get(i)[5] != "None") {
				Date wife = getIndiDate(indi, fam.get(i)[5], Dates.BDAY);			
				//System.out.println(wife.toString());
				if (dm.before(wife)) {
					System.out.println("ERROR: FAMILY: US02: " + fam.get(i)[0] + ": Wife birthdate " + format.format(wife) + " is after marriage date " + format.format(dm));
					//return false;
				}
			}
		}
		//return true;
	}

	public static Date getIndiDate(List<String[]> indi, String id, Dates d) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < indi.size(); i++) {
			if (indi.get(i)[0] == id) {

				String date;

				if (d == Dates.BDAY) {
					date = indi.get(i)[3];
				}
				else {
					date = indi.get(i)[6];
				}

				if (date == "N/A" && d == Dates.BDAY) {
					return new Date(Long.MIN_VALUE);
				}
				else if (date == "N/A") {
					return new Date(Long.MAX_VALUE);
				}

				return format.parse(date);
			}
		}
		return new Date(Long.MIN_VALUE);
	}

	public static void checkDBB(List<String[]> indi) throws ParseException
	{
		Period p;
		//Death should be < 150 years after birth
		for(int i = 0; i < indi.size(); i++)
		{
			String deathDate = indi.get(i)[6];
			String birthDate = indi.get(i)[3];	

			String byear = birthDate.substring(0,birthDate.indexOf("-"));
			String bmonth = birthDate.substring(birthDate.indexOf("-") + 1).substring(0, birthDate.substring(birthDate.indexOf("-") + 1).indexOf("-"));
			String bday = birthDate.substring(birthDate.indexOf("-") + 1).substring(birthDate.substring(birthDate.indexOf("-") + 1).indexOf("-") + 1);
			LocalDate lBDate = LocalDate.of(Integer.parseInt(byear), Integer.parseInt(bmonth), Integer.parseInt(bday));
			
			if(!deathDate.equals("N/A"))
			{
				
				String dyear = deathDate.substring(0,deathDate.indexOf("-"));
				String dmonth = deathDate.substring(deathDate.indexOf("-") + 1).substring(0, deathDate.substring(deathDate.indexOf("-") + 1).indexOf("-"));
				String dday = deathDate.substring(deathDate.indexOf("-") + 1).substring(deathDate.substring(deathDate.indexOf("-") + 1).indexOf("-") + 1);
				
				LocalDate lDDate = LocalDate.of(Integer.parseInt(dyear), Integer.parseInt(dmonth),Integer.parseInt(dday));
				
				p = Period.between(lBDate, lDDate);
								
				if(p.isNegative())
				{
					System.out.println("ERROR: INDIVIDUAL US07: " + indi.get(i)[0] + " Death Date: " + lDDate + " is before Birth Date " + lBDate);
				}
				
				if(p.isZero())
				{
					System.out.println("ERROR: INDIVIDUAL US07: " + indi.get(i)[0] + " Death Date: " + lDDate + " is the same as the Birth Date " + lBDate);
				}
				
				if(p.getYears() >= 150)
				{
					System.out.println("ERROR: INDIVIDUAL US07: " + indi.get(i)[0] + " Death Date: " + lDDate + " is not 150 years less than the Birth Date " + lBDate);
				}		
			}
			//Current date should be < 150 years from birth date
			p = Period.between(lBDate, LocalDate.now());
			
			if(p.isNegative())
			{
				System.out.println("ERROR: INDIVIDUAL US07: " + indi.get(i)[0] + " Current Date: " + LocalDate.now() + " is before the Birth Date " + lBDate);
			}
			
			if(p.isZero())
			{
				System.out.println("ERROR: INDIVIDUAL US07: " + indi.get(i)[0] + " Current Date: " + LocalDate.now() + " is the same as the Birth Date " + lBDate);
			}
			
			if(p.getYears() >= 150)
			{
				System.out.println("ERROR: INDIVIDUAL US07: " + indi.get(i)[0] + " Current Date: " + LocalDate.now() + " is not 150 years less than the Birth Date " + lBDate);
			}
			
		}
	}

	public static boolean checkBBDP(List<String[]> indi, List<String[]> fam) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i=0; i<indi.size(); i++) {
			String child = indi.get(i)[7];

			if (child == "N/A") {
				continue;
			}

			String dateB = indi.get(i)[3];

			for (int j=0; j<fam.size(); j++) {
				if (fam.get(j)[0] == child) {

					Date db = format.parse(dateB);
					Date dad = getIndiDate(indi, fam.get(i)[3], Dates.DEATH);
					Date mom = getIndiDate(indi, fam.get(i)[5], Dates.DEATH);

					if (db.before(dad)) {
						System.out.println("ERROR: INDIVIDUAL: US09: " + indi.get(i)[0] + ": Father death " + dad.toString() + " is before child birth date " + db.toString());
						return false;
					}

					if (db.after(mom)) {
						System.out.println("ERROR: INDIVIDUAL: US09: " + indi.get(i)[0] + ": Mother death " + mom.toString() + " is before birth date date " + db.toString());
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean checkMA14(List<String[]> indi, List<String[]> fam) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < fam.size(); i++) {
			String dateM = fam.get(i)[1];

			if (dateM == "N/A" || fam.get(i)[3] == "N/A" || fam.get(i)[5] == "N/A") {
				return true;
			}

			Date dm = format.parse(dateM);
			Date husb = getIndiDate(indi, fam.get(i)[3], Dates.BDAY);
			Date wife = getIndiDate(indi, fam.get(i)[5], Dates.BDAY);

			Calendar c = new GregorianCalendar();

			c.setTime(dm);
			int mYear = c.get(Calendar.YEAR);
			c.setTime(husb);
			int hYear = c.get(Calendar.YEAR);
			c.setTime(wife);
			int wYear = c.get(Calendar.YEAR);

			if (mYear - hYear < 14) {
				System.out.println("ERROR: FAMILY: US10: " + fam.get(i)[0] + ": Husband birthdate " + husb.toString() + " is less than 14 years before marriage date " + dm.toString());
				return false;
			}

			if (mYear - wYear < 14) {
				System.out.println("ERROR: FAMILY: US10: " + fam.get(i)[0] + ": Wife birthdate " + wife.toString() + " is less than 14 years before marriage date " + dm.toString());
				return false;
			}
		}
		return true;
	}
	

	public ArrayList<String> getFamChildren(List<String[]> fam, int i)
	{
		ArrayList<String> children = new ArrayList<String>();
		
		String childString = fam.get(i)[7];
		String rest = childString;
		String childTwo = "";
		
		//Find the first Spilt
		int comma = childString.indexOf(",");
		
		//Get the first Child
		String childOne = childString.substring(comma - 2, comma);
		children.add(childOne);
		
		//Set up the rest of the string minus the first child
		rest = childString.substring(comma + 1);
		
		//Find the next comma & the end
		int nextComma = rest.indexOf(",");
		int end = rest.indexOf("'}");
		
		while(rest.length() > 2)
		{		
			if(nextComma == -1)
			{
				childTwo = rest.substring(0, end);
				rest = rest.substring(rest.indexOf(childTwo) + childTwo.length());
				children.add(childTwo);
			}
			else
			{
				childTwo = rest.substring(0, nextComma);
				children.add(childTwo);
				rest = rest.substring(nextComma + 1);
			}
			
			nextComma = rest.indexOf(",");
			end = rest.indexOf("'}");
		}
		
		return children;		
	}
	public String getChildBirthDate(String id,List<String[]> indi, List<String[]> fam)
	{
		String rtnDate = "";
		for(int i = 0; i < indi.size(); i++)
		{
			//Get The string
			String searchID = indi.get(i)[0].substring(1,indi.get(i)[0].length() - 1);
			if(searchID.equals(id))
			{
				rtnDate = indi.get(i)[3];
				return rtnDate;
			}
		}
		return null;
	}
	
	public boolean checkValidSibilingBirthDates(ArrayList<String> children, List<String[]> indi, List<String[]> fam)
	{
		for(int i = 1; i < children.size(); i++)
		{
			//Get previous child
			String prevC = children.get(i - 1);
			String currC = children.get(i);
			
			String prevBirthDate = getChildBirthDate(prevC,indi, fam);
			String curBrithDate = getChildBirthDate(currC, indi, fam);
		
			
			String[] prevCom = getComponents(prevBirthDate);
			LocalDate prevBDate = LocalDate.of(Integer.parseInt(prevCom[0]), Integer.parseInt(prevCom[1]), Integer.parseInt(prevCom[2]));
			String[] curCom = getComponents(curBrithDate);
			LocalDate curBDate = LocalDate.of(Integer.parseInt(curCom[0]), Integer.parseInt(curCom[1]), Integer.parseInt(curCom[2]));
			
			Period p = Period.between(prevBDate, curBDate);
			
			//Check for Twins
			//Are the years the same?
			boolean validTwins = false;
			if(prevCom[0].equals(prevCom[0]))
			{
				//Are the months the same?
				if(prevCom[1].equals(curCom[1]))
				{
					//Are they Twins and valid?
					if(Math.abs(p.getDays()) >= 2)
					{
						//Not Valid
						System.out.println("ERROR IN US13: TWINS " + prevC + " AND " + currC + " HAVE BIRTHDATES GREATER THAN TWO DAYS");
						return false;
					}
					else
					{
						validTwins = true;
					}
				}
			}
			
			//Others
			if(!validTwins && Math.abs(p.getYears()) < 1) //The months do not matter if there is a >=2 year difference
			{
				if(Math.abs(p.getMonths()) <= 8);
				{
					System.out.println("ERROR IN US13: SIBLINGS " + prevC + " AND " + currC + " HAVE BIRTHDAYS UNDER 8 MONTHS AT " + Math.abs(p.getMonths()) + " MONTHS");
					return false;
				}
			}
			
		}	
		return true;
	}
	
	public boolean checkSS(List<String[]> indi, List<String[]> fam)
	{
		//Get the children
		//Check for Multiple Children
		ArrayList<String> children = new ArrayList<String>();
		boolean validStatus = true;
		for(int i = 0; i < fam.size(); i++)
		{
			if(fam.get(i)[7].indexOf(",") > 0) //Multiple Children
			{
				children = getFamChildren(fam, i); //Get the children
				validStatus = checkValidSibilingBirthDates(children, indi, fam); 
			}
			else //less than 0 means one child
			{
				//Nothing happens
			}
		}
		
		return validStatus;
	}
	
	public void parentsNotTooOld(List<String[]> indi, List<String[]> fam) {
		
		for (int i = 0; i < indi.size(); i++) {
			for (int j = 0; j < fam.size(); j++) {
				if (indi.get(i)[7] != "N/A" && indi.get(i)[7] == fam.get(j)[0]) {
					if (fam.get(j)[3] != "N/A" && fam.get(j)[5] != "N/A") {
						for (int k = 0; k < indi.size(); k++) {
							if (indi.get(k)[0] == fam.get(j)[3] && (Integer.parseInt(indi.get(k)[4]) - Integer.parseInt(indi.get(i)[4])) >= 80) {
								System.out.println("ERROR: INDIVIDUAL: US12: " + indi.get(i)[0] + ": Father of this individual is more than 80 years older than him/her.");
							}
							
							if (indi.get(k)[0] == fam.get(j)[5] && (Integer.parseInt(indi.get(k)[4]) - Integer.parseInt(indi.get(i)[4])) >= 60) {
								System.out.println("ERROR: INDIVIDUAL: US12: " + indi.get(i)[0] + ": Mother of this individual is more than 60 years older than him/her.");

							}
						}
					}
				}
			}
			 
		}
	}
	
	public void multipleBirths(List<String[]> indi) {
		
		for (int i = 0; i < indi.size(); i++) {
			
			int counter = 1;
			
			for (int j = i + 1; j < indi.size(); j++) {
				if (indi.get(i)[7] == indi.get(j)[7] && indi.get(i)[3] == indi.get(j)[3]) {
					counter++;
				}
			}
			
			if (counter > 5) {
				System.out.println("ERROR: FAMILY: US14: " + indi.get(i)[7] + ": Family has more than 5 children with the same birthday.");
			}
		}
	}

	//Takes in a date formatted in DD-MMM-YYYY and returns a String array
		//where 0 is the original date, 1 is the day, 2 is the month in MM, 3 is the year and 4 is the new formatted date in YYYY-MM-DD
		private String[] getFormattedDate(String data)
		{
			//Store the data
			String[] rtn = new String[5];
			rtn[0] = data;
			//Get Day
			//Find where the day number ends
			int daySplitIndex = data.indexOf(" ");
			String day = data.substring(0,daySplitIndex);
			rtn[1] = day;
					
			//Get Month
			//Find out where the day ends and take into account the space (1)
			int monthIndex = daySplitIndex + 1;
			//Get the remaining String for other units.
			String month = data.substring(monthIndex);
			String year = month;
			//Find out where month ends in the string
			int monthSplitIndex = month.indexOf(" ");
			//Get the month
			month = month.substring(0,  monthSplitIndex);
			//Get the number of the month
			month = dates.get(month);
			rtn[2] = month;
			
			//Get Year
			//Get the rest of the string and split it where the month ended. Include +1 for the space
			year = year.substring(monthSplitIndex + 1);
			rtn[3] = year;
			rtn[4] = year + "-" + month + "-" + day;
				
			return  rtn;
		}
		
		//Returns the components of a date formatted in YYYY MM DD into an array where 0 is YYYY, 1 is MM and 2 is DD
		private String[] getComponents(String date)
		{
			String[] rtn = new String[3]; 
		
			int yearIndex = date.indexOf("-");
			String year = date.substring(0,yearIndex);
			rtn[0] = year;
			
			String month = date.substring(yearIndex + 1);
			String day = month;
			int monthIndex = month.indexOf("-");
			month = month.substring(0,monthIndex);
			rtn[1] = month;
			
			day = day.substring(monthIndex + 1);
			rtn[2] = day;
			
			return rtn;
		}
		
		//This automated method will check the two refactors made in the code.
		private void checkDates(String[] data)
		{
			Boolean pass = true;
			String[] newComponents = getComponents(data[4]);
			//Dates must be in YYYY MM DD format
			for(int i = 0; i < data.length; i++)
			{
				
				
				//Check Year
				if(data[3].length() != 4)
				{
					pass = false;
					System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[3].length() + " YEAR IS GREATER THAN THE EXPECTED VALUE OF 4");
				}
				//Check Month length
				if(data[2].length() != 2)
				{
					pass = false;
					System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[2].length() + " MONTH IS GREATER THAN THE EXPECTED VALUE OF 2");
					//Check Month Validity
					if(Integer.parseInt(data[2]) < 1 || Integer.parseInt(data[2]) > 12)
					{
						pass = false;
						System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[2].length() + " MONTH IS NOT IN THE EXPECTED RANGE OF 01-12");
						
					}
				}
				//Check Day length
				if(data[1].length() != 2)
				{
					pass = false;
					System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[1].length() + " DAY IS GREATER THAN THE EXPECTED VALUE OF 2");
					//Check Day Validity
					if(Integer.parseInt(data[1]) < 1 || Integer.parseInt(data[1]) > 31)
					{
						pass = false;
						System.out.println("ERROR IN REFACTORED CODE FOR DATES: " + data[1].length() + " DAY IS NOT IN THE EXPECTED RANGE OF 01-31");
					}
				}
					
				//Check date validity
				LocalDate origDate = LocalDate.of(Integer.parseInt(data[3]), Integer.parseInt(data[2]), Integer.parseInt(data[1])); //Todays date                         
				LocalDate newDate = LocalDate.of(Integer.parseInt(newComponents[0]), Integer.parseInt(newComponents[1]), Integer.parseInt(newComponents[2]));  //Birth date
				Period p = Period.between(origDate, newDate);
				
				if(!p.isZero())
				{
					pass = false;
					System.out.println("ERROR IN REFACTORED CODE FOR DATES: THE ORIGINAL DATE: " + data[0] + " IS DIFFERENT THAN THE NEW FORMATTED DATE: " + data[4]);
				}
				
			}
			
			if(!pass)
			{
				System.out.println("ERROS IN REFACTORED CODE FOUND PLEASE CHECK ABOVE FOR ERROR DETAILS");
			}
		}
		
	public boolean checkSibCount(List<String[]> fam)
	{
		ArrayList<String> childrenOfFam = new ArrayList<String>();
		for(int i = 0; i < fam.size(); i++)
		{
			if(fam.get(i)[7].indexOf(",") > 0) //Multiple Children
			{
				childrenOfFam = getFamChildren(fam,i);
				if(childrenOfFam.size() > 15)
				{
					System.out.println("ERROR IN US15: FAMILY " + fam.get(i)[0] + " HAS " + childrenOfFam.size() + " CHILDREN WHEN THE MAXIMUM EXPECTED IS 15");
					return false;
				} 
			}
		}
		
		return true;
	}

	public String getIndiGender(List<String[]> indi, String id) throws ParseException{
		for (int i=0; i < indi.size(); i++) {
			if (indi.get(i)[0] == id) {
				return indi.get(i)[2];
			}
		}
		return "N/A";
	}

	public void checkGR(List<String[]> indi, List<String[]> fam) throws ParseException{
		for (int i=0; i<fam.size(); i++) {
			String dad = fam.get(i)[3];
			String mom = fam.get(i)[5];

			if (dad != "N/A") {
				if (getIndiGender(indi, dad) == "F") {
					System.out.println("ERROR: INDIVIDUAL: US21: " + fam.get(i)[0] + ": Father is a woman.");
					//return false;
				}
			}

			if (mom != "N/A") {
				if (getIndiGender(indi, dad) == "M") {
					System.out.println("ERROR: INDIVIDUAL: US21: " + fam.get(i)[0] + ": Mother is a man.");
					//return false;
				}
			}
		}
		//return true;
	}

	public void checkUS(List<String[]> fam) throws ParseException{
		for (int i=0; i<fam.size(); i++) {
			String dad = fam.get(i)[3];
			String mom = fam.get(i)[5];
			String mDate = fam.get(i)[1];

			for (int j=i+1; j<fam.size(); j++) {
				if (dad == fam.get(j)[3] && mom == fam.get(j)[5] && mDate == fam.get(j)[1]) {
					System.out.println("ERROR: INDIVIDUAL: US24: " + fam.get(i)[0] + ": Family has duplicate: " + fam.get(j)[0]);
					//return false;
				}
			}
		}
		//return true;
	}
	
	public void run() throws IOException, ParseException
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
	    		
	    		//Prints details
	    		print = print + "<-- ";
	    		
	    		//Print level
	    		String level = projLine.substring(0,1);
	    		print = print + level + "|";    		
	    		print = print + tag + "|";
	
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
	    	for(int i=0; i<70; i++)
		  	{
		  		System.out.print("---");
		  	}
	    	System.out.println();
	    	
	    	//Print Families
	    	printFamilies();
	    	
	    	printList(famDetails);
	    	
	    	System.out.print("\t");
	    	for(int i=0; i<52; i++)
		  	{
		  		System.out.print("----");
		  	}
			System.out.println();
			
			// Story 1
			datesBeforeCurrent(indiDetails, famDetails);
			
			// Story 2
			checkBBM(indiDetails, famDetails);

			//Story3
			checkBBD(indiDetails);
			
			// Story 4
			marriageBeforeDivorce(famDetails);
			
			//Story 5
	    	resultstory5();
			
			//Story 6
	    	resultstory6();
	    	
	    	//Story 7
			checkDBB(indiDetails);
	    	
	    	//story8
	    	resultstory8();

	    	//story9
	    	checkBBDP(indiDetails, famDetails);

	    	//story10
	    	checkMA14(indiDetails, famDetails);
			
			//Story 13
			checkSS(indiDetails,famDetails);
			
			//Story 15
			checkSibCount(famDetails);

	    	//story 11
			No_bigamy();
	    	
	    	//story 16
	    	MaleLastNames();

	    	//story 21
	    	checkGR(indiDetails, famDetails);

	    	//story 24
	    	checkUS(famDetails);

	   }
	      
	   catch(IOException e)
	   {
	   	System.out.println("Project File Not Found");
	   	e.printStackTrace();
	   }  
	}
}
