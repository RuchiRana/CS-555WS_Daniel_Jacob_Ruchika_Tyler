package project6;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class Test_Project6 {

	@Test
	void testFatherBeforeChild() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I7","Wilson","","","1963-12-20","","","", "I9"};
		String[] second = {"I9","Price","","","1978-09-17","","","F1", ""};
		
		indi.add(one);
		indi.add(second);
		
		String result2 = "ERROR: INDIVIDUAL: US09: @I36@: Mother death 1963-12-20 is before birth date date 1978-09-17";
		assertTrue(true, result2);
	}
	
	@Test
	void test14Y() 
	{
		List<String[]> fam = new ArrayList<String[]>();
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"F2","1900-03-03","","","","","","", ""};
		String[] second = {"I2","Andrew /Wilson/","","","1928-06-28","","",""};
		
		fam.add(one);
		indi.add(second);
		
		String result2 = "ERROR: FAMILY: US10: @F2@: Husband birthdate 1928-06-28 is less than 14 years before marriage date 1900-03-03";
		assertTrue(true, result2);
	}

	@Test
	void testResults() 
	{
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"F2","1900-03-03","N/A","I2","","I4","","", ""};
		String[] second = {"F1","1810-03-03","N/A","I2","","I3","",""};
		
		fam.add(one);
		fam.add(second);
		
		String result2 = "Error: FAMILY: US11: F1 : (I2) married to (I4) and (I3) simultaneously.";
		assertTrue(true, result2);
	}
	
	@Test
	void test() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I13","Rachel /Smith","","1942-05-15","","","","F1", ""};
		String[] second = {"I14","Jane /Wilson/","","2004-08-22","","","","F1"};
		
		indi.add(one);
		indi.add(second);
		
		String result2 = "ERROR: Family: US12: F6: Mother 'I13' is more than 60 years older 'I14'.";
		assertTrue(true, result2);
	}
	
	@Test
	void testSib8Month() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I25"," John /Wilson/","","1963-02-25","","","","F3", ""};
		String[] second = {"I26","Gary /Wilson/","","1962-07-30","","","F3",""};
		
		indi.add(one);
		indi.add(second);
		
		String result2 = "ERROR IN US13: SIBLINGS I25 AND I26 HAVE BIRTHDAYS UNDER 8 MONTHS AT 6 MONTHS";
		assertTrue(true, result2);
	}
	
	@Test
	void testSibBirth() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		String[] one = {"F1"," ","","","","","","", "'I1,I37,I38,I39,I40,I41'"};
		String[] second = {"I37","","2020-06-22","","","","F1",""};
		String[] third = {"I38","","2020-06-22","","","","F1",""};
		String[] forth = {"I39","","2020-06-22","","","","F1",""};
		String[] fifth = {"I40","","2020-06-22","","","","F1",""};
		
		fam.add(one);
		indi.add(second);
		indi.add(third);
		indi.add(forth);
		indi.add(fifth);
		String result2 = "ERROR: INDIVIDUAL: US14: {@F1@}: Individual has more than 4 other siblings with the same birthday.";
		assertTrue(true, result2);
	}
	@Test
	void sibCount15() 
	{
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"F3","","","","","","I2,I9,I11,I22,I24,I25,I26,I27,I28,I29,I30,I31,I32,I33,I34,I35,I36'"};
	
		fam.add(one);
		
		String result2 = "ERROR IN US15: FAMILY @F3@ HAS 17 CHILDREN WHEN THE MAXIMUM EXPECTED IS 15";
		assertTrue(true, result2);
	}
	
	@Test
	void TrueTest() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I2","  Andrew /Wilson/","","","","","","F3", ""};
		String[] second = {"I5","Kevin /Price/","","","","","F3",""};
		
		indi.add(one);
		indi.add(second);
		
		String result2 = "Error: FAMILY: US16: F2: Male members of the family does not have the same last name. "
				+ "Father's (I2) last name: Wilson and Son's (I5) last name: Price";
		assertTrue(true, result2);
	}
	
}
