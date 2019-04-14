import org.junit.*;
import java.util.*;
import java.text.*;

public class uniqueChildrenTest {
	@Test
	public void testNoChildren() {
		List<String[]> fam = new ArrayList<String[]>();

		String[] mar = {"","","","","","","","N/A"};
		fam.add(mar);

		assert uniqueChildren.checkUC(fam);
	}

	@Test
	public void testOneChild() {
		List<String[]> fam = new ArrayList<String[]>();

		String[] mar = {"","","","","","","","{1}"};
		fam.add(mar);

		assert uniqueChildren.checkUC(fam);
	}

	@Test
	public void testMultipleChildren() {
		List<String[]> fam = new ArrayList<String[]>();

		String[] mar = {"","","","","","","","{1,2,3,4}"};
		fam.add(mar);

		assert uniqueChildren.checkUC(fam);
	}

	@Test
	public void testDuplicate() {
		List<String[]> fam = new ArrayList<String[]>();

		String[] mar = {"","1","","","","","","{1,1}"};
		fam.add(mar);

		assert !uniqueChildren.checkUC(fam);
	}

	@Test
	public void testMultipleDuplicates() {
		List<String[]> fam = new ArrayList<String[]>();

		String[] mar = {"","","","","","","","{1,1,1,1}"};
		fam.add(mar);

		assert uniqueChildren.checkUC(fam);
	}
}