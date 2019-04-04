package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Project_8.Project;	

public class AuntsUncles 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void testAuntsUncles()
	{
		//Family one
		String[] AuntParent = {"@I07@", "Richard", "M" ,"1944-09-25", "74", "False", "1982-10-08", "N/A", "{@F3@}"};
		 // @I7@	|	    Richard /Wilson/	|	    M	|	    1944-09-25	|	    74	|	    False	|	    1982-10-08	|		N/A	|	    {@F3@}	|
		String[] AuntParentTwo = {"@I08@", "Michele", "F" ,"1944-07-01", "74", "False", "1963-12-20", "N/A", " {@F3@}"};
		//	    @I8@	|	    Michelle /Taylor/	|	    F	|	    1944-07-01	|	    74	|	    False	|	    1963-12-20	|		N/A	|	    {@F3@}
		String[] familyOne = {"@F03@", " 2020-12-23","N/A","@I07@","Richard","@I08@","Michele","{'I22', 'I18'}"};
		//|	    @F3@	|	    2020-12-23	|		N/A	|	    @I7@	|	    Richard /Wilson/	|	    @I8@	|	    Michelle /Taylor/	
		
		//FamilyTwo
		String[] Aunt = {"@I22@", "Aunt Wilson", "F", "1966-03-02", "53", "True", "N/A", "{@F3@}", "{@F9@}"};
		//  @I22@	|	    Aunt /Wilson/	|	    F	|	    1966-03-02	|	    53	|	    True	|		N/A	|	    {@F3@}	|	    {@F9@}	|
		String[] AuntSib1 = {"@I18@", "Jamie", "F" ,"1964-06-13", "54", "True", "N/A", "N/A", "{@F4@}"};	
		// @I18@	|	    Jamie /Wilson/	|	    F	|	    1964-06-13	|	    54	|	    True	|		N/A	|		N/A	|	    {@F4@}	|

		String[] AuntSibSpouse = {"@I09@", "George", "M" ,"1962-02-04", "57", "True", "N/A", "{@F3@}", "{@F4@}"};
		//    @I9@	|	    George /Wilson/	|	    M	|	    1962-02-04	|	    57	|	    True	|		N/A	|	    {@F3@}	|	    {@F4@}	  
		
		
		String[] familyTwo = {"@F04@", "N/A","N/A","@I09@","George","@I18@","Jamie","{'I19,I23'}"};
	//	    @F4@	|		N/A	|		N/A	|	    @I9@	|	    George /Wilson/	|	    @I18@	|	    Jamie /Wilson/	|	    {'I19,I23'}	
		
		String[] Nephew = {"@I23@", "Newphew", "M", "1994-04-24", "24", "True", "N/A", "N/A", "N/A"};
		
		// Nephew /Wilson/	|	    M	|	    1994-04-24	|	    24	|	    True	|		N/A	|	    {@F4@}	|	    {@F9@}
		
		String[] familyThree = {"@F09@", "2019-01-14","N/A","@I23@","Nephew","@I22@","N/A","N/A"};
		// @F9@	|	    2019-01-14	|		N/A	|	    @I23@	|	    Nephew /Wilson/	|	    @I22@	|	    Aunt /Wilson/	|		N/

		
		
		
		List<String[]> indi = new ArrayList<String[]>();
		indi.add(Aunt);
		indi.add(Nephew);
		indi.add(AuntParent);
		indi.add(AuntParentTwo);
		indi.add(AuntSib1);
		indi.add(AuntSibSpouse);
	
		
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(familyOne);
		fam.add(familyTwo);
		fam.add(familyThree);
		
		
		
		boolean isValid = testProj.auntsAndUncles(indi, fam);
		
		assertFalse(isValid);
	}
	
}
