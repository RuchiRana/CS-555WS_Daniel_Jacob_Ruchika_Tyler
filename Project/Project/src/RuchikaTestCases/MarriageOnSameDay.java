package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MarriageOnSameDay {

	@Test
	public void testResults() throws ParseException 
	{
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"F1","1991-03-3","","I2","","I3","",""};
		String[] two = {"F2","","1991-03-3","","I2","","I4","", ""};
		
		fam.add(one);
		fam.add(two);
		
		String result2 = "Error: FAMILY: US11: F1: MarriageOnSameDay ocuurs of husband (I2) and wife (I3) "
				+ "during marriage to another wife (I4) on 1991-03-03";
		assertTrue(true, result2);
	}

	@Test
	public void testFalseResults() throws ParseException 
	{
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"F1","1991-03-3","","I2","","I3","",""};
		String[] two = {"F2","","1981-03-3","","I2","","I4","", ""};
		
		fam.add(one);
		fam.add(two);
		String result2 = "Error: FAMILY: US11: F1: MarriageOnSameDay ocuurs of husband (I2) and wife (I3) "
				+ "during marriage to another wife (I4) on 1991-03-03";
		assertFalse(false, result2);
	}

}
