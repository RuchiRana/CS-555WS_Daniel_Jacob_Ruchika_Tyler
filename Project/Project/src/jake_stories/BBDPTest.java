import org.junit.*;
import java.util.*;
import java.text.*;

public class BBDPTest {
	@Test
	public void testBasic() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","2018-01-01","",""};
		String[] two = {"2","","","","","2019-01-01","",""};
		String[] three = {"3","","","2010-01-01","","","1",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert BBDP.checkBBDP(indi, fam);
	}

	@Test
	public void testNA() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","N/A","",""};
		String[] two = {"2","","","","","N/A","",""};
		String[] three = {"3","","","2010-01-01","","","1",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert BBDP.checkBBDP(indi, fam);
	}

	@Test
	public void testHusbBefore() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","2008-01-01","",""};
		String[] two = {"2","","","","","2019-01-01","",""};
		String[] three = {"3","","","2010-01-01","","","1",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert !BBDP.checkBBDP(indi, fam);
	}

	@Test
	public void testBothBefore() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","2008-01-01","",""};
		String[] two = {"2","","","","","2009-01-01","",""};
		String[] three = {"3","","","2010-01-01","","","1",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert !BBDP.checkBBDP(indi, fam);
	}

	@Test
	public void testSameDate() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","2010-01-01","",""};
		String[] two = {"2","","","","","2010-01-01","",""};
		String[] three = {"3","","","2010-01-01","","","1",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert BBDP.checkBBDP(indi, fam);
	}
}