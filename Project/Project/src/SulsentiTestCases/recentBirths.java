package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Project10.Project;

public class recentBirths 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void testRecentBirths()
	{
		String[] sameMonthRecentBirth= {"@I01@", "Joe", "M", "2019-04-04", "0", "True", "NA", "NA", "{'F01'}"};
		String[] differentMonthRecentBirth = {"@I02@", "Camile", "F", "2019-03-29", "0", "True", "NA", "NA", "{'F01'}"};
		
		
		List<String[]> indi = new ArrayList<String[]>();
		indi.add(sameMonthRecentBirth);
		indi.add(differentMonthRecentBirth);
		
		List<String> births = testProj.recentBirths(indi);
		
		Boolean isValid = births.size() > 0;
		
		assertTrue(isValid);
	}
	
}
