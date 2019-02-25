package Project4;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.*;

public class Project 
{	
	String mdate[] = new String[1000],  fid[] = new String[1000], hid[] = new String[1000], wid[] = new String[1000],
		   divdate[] = new String[1000];
	String iid[] = new String[1000], ddate[]  = new String[1000];
	int c=0, c1=0, d=0, d1=0 ;
	
	
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
			else if(famPrevTag.equals("DIV"))
			{
				String month = dates.get(data.substring(data.indexOf(" ") + 1).substring(0,data.substring(data.indexOf(" ") + 1).indexOf(" ")));
    			String day = data.substring(0,data.indexOf(" "));
    			String year = data.substring(data.indexOf(" ") + 1).substring(data.substring(data.indexOf(" ") + 1).indexOf(" ") + 1);
				saveFam[2] = year + "-" + month + "-" + day;
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
						System.out.println("Error: FAMILY: US05: " + fid[i] + ": Married " + mdate[i] + " after husband's (" + hid[i] + ") death on " + ddate[j]+ "\n");
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
						System.out.println("Error: FAMILY: US05: " + fid[i] + ": Married " + mdate[i] + " after wife's (" + wid[i] + ") death on " + ddate[j]+ "\n");
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
						System.out.println("Error: FAMILY: US06: " + fid[i] + ": Divorced " + divdate[i] + " after husband's (" + hid[i] + ") death on " + ddate[j]+ "\n");
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
						System.out.println("Error: FAMILY: US06: " + fid[i] + ": Divorced " + divdate[i] + " after wife's (" + wid[i] + ") death on " + ddate[j]+ "\n");
					}
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
				Date husb = getIndiBirthDate(indi, fam.get(i)[3]);
				//System.out.println(husb.toString());
				if (dm.before(husb)) {
					System.out.println("ERROR: FAMILY: US02: " + fam.get(i)[0] + ": Husband birthdate " + format.format(husb) + " is after marriage date " + format.format(dm));
					//return false;
				}
			}
			
			if (fam.get(i)[5] != "None") {
				Date wife = getIndiBirthDate(indi, fam.get(i)[5]);			
				//System.out.println(wife.toString());
				if (dm.before(wife)) {
					System.out.println("ERROR: FAMILY: US02: " + fam.get(i)[0] + ": Wife birthdate " + format.format(wife) + " is after marriage date " + format.format(dm));
					//return false;
				}
			}
		}
		//return true;
	}
	
	//Story 7
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

	public static Date getIndiBirthDate(List<String[]> indi, String id) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		for (int i=0; i < indi.size(); i++) {

			if (indi.get(i)[0].equals(id)) {
				String dateB = indi.get(i)[3];

				if (dateB == "N/A") {
					return format.parse("0000-01-01");
				} 

				return format.parse(dateB);
			}
		}
		return format.parse("0000-01-01");
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
			
			//Story 5
	    	resultstory5();
			
			//Story 6
	    	resultstory6();
	    	
	    	// Story 1
			datesBeforeCurrent(indiDetails, famDetails);
			
			// Story 4
			marriageBeforeDivorce(famDetails);

			// Story 2
			checkBBM(indiDetails, famDetails);

			//Story3
			checkBBD(indiDetails);
			
			//Story 7
			checkDBB(indiDetails);
			
	   }
	   
							   
	   catch(IOException e)
	   {
	   	System.out.println("Project File Not Found");
	   	e.printStackTrace();
	   }  
	   
	}
	
}
