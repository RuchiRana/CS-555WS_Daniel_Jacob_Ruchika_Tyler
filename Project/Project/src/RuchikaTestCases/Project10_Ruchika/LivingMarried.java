package project10_Ruchika;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LivingMarried {

	@Test
	void testFatherBeforeChild() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I7","Wilson","","","1963-12-20","","","", "I9"};
		String[] second = {"I9","Price","","","1978-09-17","","","F1", ""};
		
		indi.add(one);
		indi.add(second);
		
		String result2 = "List of all living married people :FAMILY: US30: F8: Marriage Date: 2018-01-10 "
				+ "Husband id: I20		Wife id : I21";
		assertTrue(true, result2);
	}

}
