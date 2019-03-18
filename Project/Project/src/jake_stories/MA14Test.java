package jake_stories;

import org.junit.*;
import java.util.*;
import java.text.*;

public class MA14Test {
	@Test
	public void testBasic() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","2018-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert MA14.checkMA14(indi, fam);
	}

	@Test
	public void testWifeYoung() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1995-01-01","","","",""};
		String[] mar = {"","2007-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !MA14.checkMA14(indi, fam);
	}

	@Test
	public void testHusbAfter() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","2019-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","2018-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !MA14.checkMA14(indi, fam);
	}

	@Test
	public void testBothYoung() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","1995-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !MA14.checkMA14(indi, fam);
	}

	@Test
	public void test14Y() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","2004-01-01","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert MA14.checkMA14(indi, fam);
	}
}