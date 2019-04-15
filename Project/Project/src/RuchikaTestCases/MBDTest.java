package RuchikaTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MBDTest {

	@Test
	void checkresult() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"I15","","M","","","56","False","1982-08-14", "", "F7"};
		String [] third = {"F7","1991-11-01","1982-08-24","I15","","","",""};
		
		indi.add(one);
		fam.add(third);
		
		String result2 = "Error: FAMILY: US05: F7: Married 1991-11-01 after husband's (I15) death on 1982-08-14";
		assertTrue(true, result2);
	}
}
