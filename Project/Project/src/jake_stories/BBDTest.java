import org.junit.*;
import java.util.*;
import java.text.*;

public class BBDTest {
	@Test
	public void testBasic() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","2014-12-01","",""};
		String[] two = {"2","","","1990-01-01","","","2018-10-12","",""};
		indi.add(one);
		indi.add(two);

		assert BBD.checkBBD(indi);
	}

	@Test
	public void testNADeath() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","2012-03-06","",""};
		String[] two = {"2","","","1991-01-01","","","NA","",""};
		indi.add(one);
		indi.add(two);

		assert BBD.checkBBD(indi);
	}

	@Test
	public void testNABirth() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();

		String[] one = {"1","","","NA","","","1995-07-09","",""};
		String[] two = {"2","","","1990-01-01","","","2017-01-15","",""};
		indi.add(one);
		indi.add(two);

		assert BBD.checkBBD(indi);
	}

	@Test
	public void testBirthAfter() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","1980-04-01","",""};
		String[] two = {"2","","","1990-01-01","","","1700-12-01","",""};
		indi.add(one);
		indi.add(two);

		assert !BBD.checkBBD(indi);
	}

	@Test
	public void testSameDate() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","1990-01-01","",""};
		String[] two = {"2","","","1990-01-01","","","1990-01-01","",""};
		indi.add(one);
		indi.add(two);

		assert BBD.checkBBD(indi);
	}
}