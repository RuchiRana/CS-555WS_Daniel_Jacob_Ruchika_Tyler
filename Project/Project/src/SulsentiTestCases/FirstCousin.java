package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Project_8.Project;

public class FirstCousin 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void testFirstCousin()
	{
		String[] cousinOne = {"@I01@", "Joe", "M", "1960-07-15", "53", "True", "NA", "NA", "{'F01'}"};
		String[] cousinTwo = {"@I02@", "Camile", "F", "1961-07-09", "52", "True", "NA", "NA", "{'F01'}"};
		
		String[] cousinOneParentOne = {"@I032", "", "" ,"", "", "", "", "@I01@", ""};
		String[] cousinOneParentTwo = {"@I04@", "", "" ,"", "", "", "", "@I01@", ""};
		
		String[] cousinTwoParentOne = {"@I05@", "", "" ,"", "", "", "", "@I02@", ""};
		String[] cousinTwoParentTwo = {"@I06@", "", "" ,"", "", "", "", "@I02@", ""};
		
		String[] cousinOneGP1 = {"@I06@", "", "" ,"", "", "", "", "@I03@", ""};
		String[] cousinOneGP2 = {"@I07@", "", "" ,"", "", "", "", "", ""};
		
		String[] cousinTwoGP1 = {"@I08@", "", "" ,"", "", "", "", "@I05@", ""};
		String[] cousinTwoGP2 = {"@I09@", "", "" ,"", "", "", "", "", ""};
		
		String[] family = {"", "","","@I01@","","@I02@","",""};
		
		List<String[]> indi = new ArrayList<String[]>();
		indi.add(cousinOne);
		indi.add(cousinTwo);
		indi.add(cousinOneParentOne);
		indi.add(cousinOneParentTwo);
		indi.add(cousinTwoParentOne);
		indi.add(cousinTwoParentTwo);
		indi.add(cousinOneGP1);
		indi.add(cousinOneGP2);
		indi.add(cousinTwoGP1);
		indi.add(cousinTwoGP2);
		
		List<String[]> fams = new ArrayList<String[]>();
		fams.add(family);
		
		boolean isValid = testProj.firstCousin(indi, fams);
		
		assertFalse(isValid);
	}
	
}
