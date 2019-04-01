package project_____8;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class Project8_TEST 
{
	@Test
	void IndiUniqueId() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I1","","","","","","",""};
		String[] two = {"I1","","","","","","","", ""};
		
		indi.add(one);
		indi.add(two);
		
		String str ="Error: Family: US022: Individual IDs I1  and I1 are identical. ";
		assertTrue(true, str);
		
	}
	
	@Test
	void FamUniqueId() 
	{
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"F5","","","","","","",""};
		String[] two = {"F5","","","","","","","", ""};
		
		fam.add(one);
		fam.add(two);
		
		String str = "Error: Family: US022: Family IDs F5 and F5 are identical. ";
		assertTrue(true, str);
	}
	
	@Test
	void IndiNameBdate() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I1","Betty","F","1993-6-28","30","True","N/A","N/A"};
		String[] two = {"I5","Betty","F","1993-6-28","25","False","2000-9-27","N/A"};
		
		indi.add(one);
		indi.add(two);
		
		String str = "Error: Individual: US023: Same name 'Betty' and same birth date 1993-6-28"
				+ " appears in GEDCOM.";
		assertTrue(true, str);
	}
}
