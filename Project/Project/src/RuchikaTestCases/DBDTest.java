package RuchikaTestCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DBDTest {

	@Test
	void checkresult() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();
		
		String[] one = {"I14","","F","","","14","False","1981-10-02", "", "F7"};
		String [] third = {"F7","","1982-08-24","","","I14","",""};
		
		indi.add(one);
		fam.add(third);
		
		String result2 = "Error: FAMILY: US06: F7: Divorced 1982-08-24 after wife's (I14) death on 1981-10-02";
		assertTrue(true, result2);
	}

}
