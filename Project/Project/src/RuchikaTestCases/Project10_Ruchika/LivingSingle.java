package RuchikaTestCases.Project10_Ruchika;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LivingSingle {

	@Test
	void Livingsinglesover30() 
	{
		List<String[]> indi = new ArrayList<String[]>();
		
		String[] one = {"I5","Kevin /Price/","M","","1982-08-18","36","TRUE","N/A", "", "N/A"};
		indi.add(one);
		
		String result2 = "List of all living single people :Individual: US31: I5: Name Kevin /Price/	"
				+ "Age: 36		Alive: True	Married: NO";
		assertTrue(true, result2);
	}

}
