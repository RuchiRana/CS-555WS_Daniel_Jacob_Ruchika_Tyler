import org.junit.*;
import java.util.*;
import java.text.*;

public class corrEntriesTest {
	@Test
	public void testBasic() {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","","","N/A","{2}"};
		String[] two = {"2","","","","","","","N/A","{1}"};
		String[] three = {"3","","","","","","","1","N/A"};
		String[] mar = {"1","","","1","","2","","{3}"};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert corrEntries.checkUE(indi, fam);
	}

	@Test
	public void testWrongChild() {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","","","N/A","{2}"};
		String[] two = {"2","","","","","","","N/A","{1}"};
		String[] three = {"3","","","","","","","1","N/A"};
		String[] mar = {"1","","","1","","2","","{5}"};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert !corrEntries.checkUE(indi, fam);
	}

	@Test
	public void testWrongFam() {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","","","N/A","{2}"};
		String[] two = {"2","","","","","","","N/A","{1}"};
		String[] three = {"3","","","","","","","5","N/A"};
		String[] mar = {"1","","","1","","2","","{3}"};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert corrEntries.checkUE(indi, fam);
	}

	@Test
	public void testBothAfter() {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","","","N/A","{4}"};
		String[] two = {"2","","","","","","","N/A","{1}"};
		String[] three = {"3","","","","","","","1","N/A"};
		String[] mar = {"1","","","1","","2","","{3}"};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert corrEntries.checkUE(indi, fam);
	}

	@Test
	public void testSameDate() {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","","","","","","N/A","{2}"};
		String[] two = {"2","","","","","","","N/A","{1}"};
		String[] three = {"3","","","","","","","1","N/A"};
		String[] mar = {"1","","","1","","2","","N/A"};
		indi.add(one);
		indi.add(two);
		indi.add(three);
		fam.add(mar);

		assert corrEntries.checkUE(indi, fam);
	}
}