package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Project10.Project;

public class multipleBirths 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void testMultipleBirths()
	{
		String[] one = {"@I01@", "Joe", "M", "2019-04-04", "0", "True", "NA", "NA", "{'F01'}"};
		String[] two = {"@I02@", "Camile", "F", "22019-04-04", "0", "True", "NA", "NA", "{'F01'}"};
		String[] three = {"@I03@", "Joe", "M", "2019-04-04", "0", "True", "NA", "NA", "{'F01'}"};
		String[] four = {"@I04@", "Camile", "F", "2019-04-04", "0", "True", "NA", "NA", "{'F01'}"};
		String[] five = {"@I05@", "Joe", "M", "2019-04-04", "0", "True", "NA", "NA", "{'F01'}"};
		String[] six = {"@I06@", "Camile", "F", "2019-04-049", "0", "True", "NA", "NA", "{'F01'}"};
		
		
		
		List<String[]> indi = new ArrayList<String[]>();
		indi.add(one);
		indi.add(two);
		indi.add(three);
		indi.add(four);
		indi.add(five);
		indi.add(six);
		
		String[] fam = {"","","","","","","","{'I01, I02, I03, I04, I05, I06'}"};
		List<String[]> fams = new ArrayList<String[]>();
		fams.add(fam);
		
		List<String> births = testProj.multipleBirths(fams,indi);
		
		Boolean isValid = births.size() > 0;
		
		assertTrue(isValid);
	}
	
}
