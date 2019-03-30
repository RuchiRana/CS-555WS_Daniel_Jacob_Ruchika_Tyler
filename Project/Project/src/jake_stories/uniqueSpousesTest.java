import org.junit.*;
import java.util.*;
import java.text.*;

public class uniqueSpousesTest {
	@Test
	public void testBasic() throws ParseException {
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","2010-01-01","","1","","2","",""};
		String[] two = {"2","2009-01-01","","3","","4","",""};
		fam.add(one);
		fam.add(two);

		assert uniqueSpouses.checkUS(fam);
	}

	@Test
	public void testSameHusb() throws ParseException {
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","2010-01-01","","1","","2","",""};
		String[] two = {"2","2009-01-01","","1","","3","",""};
		fam.add(one);
		fam.add(two);

		assert uniqueSpouses.checkUS(fam);
	}

	@Test
	public void testSameWife() throws ParseException {
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","2010-01-01","","1","","2","",""};
		String[] two = {"2","2009-01-01","","3","","2","",""};
		fam.add(one);
		fam.add(two);

		assert uniqueSpouses.checkUS(fam);
	}

	@Test
	public void testSameDate() throws ParseException {
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","2010-01-01","","1","","2","",""};
		String[] two = {"2","2010-01-01","","3","","4","",""};
		fam.add(one);
		fam.add(two);

		assert uniqueSpouses.checkUS(fam);
	}

	@Test
	public void testAllSame() throws ParseException {
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","2009-01-01","","1","","2","",""};
		String[] two = {"2","2009-01-01","","1","","2","",""};
		fam.add(one);
		fam.add(two);

		assert !uniqueSpouses.checkUS(fam);
	}
}