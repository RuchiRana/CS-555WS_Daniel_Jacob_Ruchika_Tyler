import org.junit.*;
import java.util.*;
import java.text.*;

public class refac_BBMTest {
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

		assert refac_BBM.checkBBM(indi, fam);
	}

	@Test
	public void testWifeAfter() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1991-01-01","","","",""};
		String[] mar = {"","1990-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !refac_BBM.checkBBM(indi, fam);
	}

	@Test
	public void testHusbAfter() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1991-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","1990-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !refac_BBM.checkBBM(indi, fam);
	}

	@Test
	public void testBothAfter() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","1987-04-06","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !refac_BBM.checkBBM(indi, fam);
	}

	@Test
	public void testSameDate() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","1990-01-01","","","",""};
		String[] two = {"2","","","1990-01-01","","","",""};
		String[] mar = {"","1990-01-01","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert refac_BBM.checkBBM(indi, fam);
	}
}