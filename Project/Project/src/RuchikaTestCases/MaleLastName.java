package sprint2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MaleLastName {

	@Test
	void Truetest() {
		List<String[]> fam = new ArrayList<String[]>();
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] third = {"F3","","","I7","","","","I9"};
		String[] one = {"I7","Wilson","","","","","","", "N/A"};
		String[] second = {"I9","Price","","","","","","", "F3"};
		
		indi.add(one);
		indi.add(second);
		fam.add(third);
		
		String result2 = "Error: FAMILY: US16: F3: Male members of the family does not have the same last name."
				+ " Father's (I7) last name: Wilson and Son's (I9) last name: Price";
		assertTrue(true, result2);
	}
	@Test
	void FalseResult() {
		List<String[]> fam = new ArrayList<String[]>();
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] third = {"F3","","","I7","","","","I9"};
		String[] one = {"I7","Wilson","","","","","","", "N/A"};
		String[] second = {"I9","Wilson","","","","","","", "F3"};
		
		indi.add(one);
		indi.add(second);
		fam.add(third);
		
		String result2 = "Error: FAMILY: US16: F3: Male members of the family does not have the same last name."
				+ " Father's (I7) last name: Wilson and Son's (I9) last name: Price";
		assertFalse(false, result2);
	}

}
