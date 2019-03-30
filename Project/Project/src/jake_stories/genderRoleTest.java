import org.junit.*;
import java.util.*;
import java.text.*;

public class genderRoleTest {
	@Test
	public void testBasic() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","M","","","","",""};
		String[] two = {"2","","F","","","","",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert genderRole.checkGR(indi, fam);
	}

	@Test
	public void testNA() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","N/A","","","","",""};
		String[] two = {"2","","F","","","","",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert genderRole.checkGR(indi, fam);
	}

	@Test
	public void testHusbWoman() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","W","","","","",""};
		String[] two = {"2","","W","","","","",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !genderRole.checkGR(indi, fam);
	}

	@Test
	public void testWifeMan() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","M","","","","",""};
		String[] two = {"2","","M","","","","",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert !genderRole.checkGR(indi, fam);
	}

	@Test
	public void testHusbWifeFail() throws ParseException {
		List<String[]> indi = new ArrayList<String[]>();
		List<String[]> fam = new ArrayList<String[]>();

		String[] one = {"1","","W","","","","",""};
		String[] two = {"2","","M","","","","",""};
		String[] mar = {"1","","","1","","2","",""};
		indi.add(one);
		indi.add(two);
		fam.add(mar);

		assert genderRole.checkGR(indi, fam);
	}
}