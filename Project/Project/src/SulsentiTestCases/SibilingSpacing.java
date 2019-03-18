package SulsentiTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import project6.Project;

public class SibilingSpacing 
{
	Project testProj = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");
	
	@Test
	public void testTwinsOneDay() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1999-01-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-01", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"};  
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		assertTrue(valid);
	}
	
	@Test
	public void testTwinsTwoDay() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1999-01-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		assertTrue(valid);
	}
	
	@Test
	public void testTwinsThreeDay() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1999-01-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-03", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected false
		assertFalse(valid);
	}
	
	@Test
	public void testSib4Month() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1999-05-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected False
		assertFalse(valid);
	}
	
	@Test
	public void testSib8Month() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1999-09-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected False
		assertFalse(valid);
	}
	
	@Test
	public void testSib9Month() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1999-10-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected False
		assertFalse(valid);
	}
	
	@Test
	public void testSib12Month() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1998-01-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected True
		assertTrue(valid);
	}
	
	@Test
	public void testSib5Year() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1994-01-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-01", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected True
		assertTrue(valid);
	}
	
	@Test
	public void testMultipleSibOnePairValid() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1994-05-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		String[] childThree = {"@I3@", "John Smith", "","1999-04-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		children.add(childThree);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2,I3'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected False
		assertFalse(valid);
	}
	
	@Test
	public void testMultipleSibAllValid() 
	{
		String[] childOne = {"@I1@", "John Smith","", "1994-05-01", "", "", "", "", ""};
		String[] childTwo = {"@I2@", "John Smith", "","1999-01-02", "", "", "", "", ""};
		String[] childThree = {"@I3@", "John Smith", "","2000-01-02", "", "", "", "", ""};
		List<String[]> children = new ArrayList<String[]>();
		children.add(childOne);
		children.add(childTwo);
		children.add(childThree);
		
		String[] famOne = {"@F1@", "","","","","","","{'I1,I2,I3'}"}; 
		List<String[]> fam = new ArrayList<String[]>();
		fam.add(famOne);
		
		//Checks valid Twins on same day
		boolean valid = testProj.checkSS(children, fam);
		
		//Expected True
		assertTrue(valid);
	}
}
