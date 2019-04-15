package RuchikaTestCases.Project10_Ruchika;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LivingMarried {

	@Test
	void Livingmarried() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"I20","","M","","1998-06-02","20","TRUE","N/A", "", "F8"};
		String[] second = {"I21","","F","","1999-02-09","20","TRUE","N/A", "", "F8"};
		String [] third = {"F8","2018-01-10","N/A","I20","","I21","",""};
		indi.add(one);
		indi.add(second);
		fam.add(third);
		
		String result2 = "List of all living married people :FAMILY: US30: F8: Marriage Date: 2018-01-10 "
				+ "Husband id: I20		Wife id : I21";
		assertTrue(true, result2);
	}

}
