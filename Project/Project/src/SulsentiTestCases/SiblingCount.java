package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import project6.Project;

public class SiblingCount 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void validSibCountU15()
	{
		List<String[]> children = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		
		for(int i = 0; i < 10; i++)
		{
			String[] child = {"@I" + (i+1) + "@", "John Smith","", "1999-01-01", "", "", "", "", ""};
			children.add(child);
		}
		
		String[] famDetail = {"@F1@", "","","","","","","{'I1,I2,I3,I4,I5,I6,I7,I8,I9,I10'}"}; 
		fam.add(famDetail);
		
		boolean valid = testProj.checkSibCount(fam);
		
		//Expected True;
		assertTrue(valid);
	}
	
	@Test 
	public void sibCount15()
	{
		List<String[]> children = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		
		for(int i = 0; i < 15; i++)
		{
			String[] child = {"@I" + (i+1) + "@", "John Smith","", "1999-01-01", "", "", "", "", ""};
			children.add(child);
		}
		
		String[] famDetail = {"@F1@", "","","","","","","{'I1,I2,I3,I4,I5,I6,I7,I8,I9,I10,I11,I12,I13,I14,I15'}"}; 
		fam.add(famDetail);
		
		boolean valid = testProj.checkSibCount(fam);
		
		//Expected True;
		assertTrue(valid);
	}
	
	@Test 
	public void invalidSibCountO15()
	{
		List<String[]> children = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		
		for(int i = 0; i < 16; i++)
		{
			String[] child = {"@I" + (i+1) + "@", "John Smith","", "1999-01-01", "", "", "", "", ""};
			children.add(child);
		}
		
		String[] famDetail = {"@F1@", "","","","","","","{'I1,I2,I3,I4,I5,I6,I7,I8,I9,I10,I11,I12,I13,I14,I15,I16'}"}; 
		fam.add(famDetail);
		
		boolean valid = testProj.checkSibCount(fam);
		
		//Expected False;
		assertFalse(valid);
	}
	
	public void zeroSibCount()
	{
		List<String[]> children = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
			
		String[] famDetail = {"@F1@", "","","","","","","N/A"}; 
		fam.add(famDetail);
		
		boolean valid = testProj.checkSibCount(fam);
		
		//Expected True;
		assertTrue(valid); 
	}
	
}
