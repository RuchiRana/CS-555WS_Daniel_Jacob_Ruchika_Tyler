import java.util.*;
import java.text.*;

public class uniqueChildren {

	public static String[] listifySet(String values) {
		String x = values.replace("{", "").replace("}", "");
		return x.split(",");
	}

	public static boolean checkUC(List<String[]> fam) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < fam.size(); i++) {
			if (fam.get(i)[7].compareTo("N/A") == 0) {
				continue;
			}

			String[] children = listifySet(fam.get(i)[7]);

			for (int j=0; j < children.length; j++) {
				for (int k=j+1; k < children.length; k++) {
					if (children[j].compareTo(children[k]) == 0) {
						System.out.println("ERROR: FAMILY: US25: " + fam.get(i)[0] + ": Child " + children[j] + " has a duplicate sibling");
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
	}
}