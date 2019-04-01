package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import project8.Project;	

public class AuntsUncles 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void testAuntsUncles()
	{
		//Family one
		String[] AuntParent = {"I01", "", "" ,"", "", "", "", "", ""};
		String[] familyOne = {"F01", "","","I02","","","","{'I02', 'I03'}"};
		
		//FamilyTwo
		String[] Aunt = {"I02", "Joe", "M", "1960-07-15", "53", "True", "NA", "NA", ""};
		String[] AuntSib1 = {"I03", "", "" ,"", "", "", "", "", ""};	
		String[] familyTwo = {"F01", "","","I03","","","","{'I05'}"};
		
		
		String[] Nephew = {"I05", "Camile", "F", "1961-07-09", "52", "True", "NA", "NA", ""};
		
		String[] familyThree = {"F01", "","","I05","","I02","","{'I05'}"};
		

		
		List<String[]> indi = new ArrayList<String[]>();
		indi.add(Aunt);
		indi.add(Nephew);
		indi.add(AuntParent);
		indi.add(AuntSib1);
	
		
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(familyOne);
		fam.add(familyTwo);
		fam.add(familyThree);
		
		
		boolean isValid = testProj.auntsAndUncles(indi, fam);
		
		assertTrue(isValid);
	}
	
}
