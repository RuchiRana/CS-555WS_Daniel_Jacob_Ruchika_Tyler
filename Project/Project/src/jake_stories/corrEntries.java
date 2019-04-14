import java.util.*;
import java.text.*;

public class corrEntries {

	public static String[] listifySet(String values) {
		String x = values.replace("{", "").replace("}", "");
		return x.split(",");
	}

	public static boolean checkUE(List<String[]> indi, List<String[]> fam) {

		boolean temp = false;
		for (int i=0; i < fam.size(); i++) {
			if (fam.get(i)[7].compareTo("N/A") != 0) {
				String[] children = listifySet(fam.get(i)[7]);

				for (int j=0; j < children.length; j++) {
					temp = false;

					for (int k=0; k < indi.size(); k++) {
						if (indi.get(k)[0].compareTo(children[j]) == 0) {
							temp = true;
							if (indi.get(k)[7].compareTo(fam.get(i)[0]) != 0) {
								System.out.println("ERROR: INDIVIDUAL: US26: " + indi.get(k)[0] + ": Family id " + indi.get(k)[7] + " doesn't match " + fam.get(i)[0]);
								return false;
							}
						}
					}

					if (!temp) {
						System.out.println("ERROR: FAMILY: US26: " + fam.get(i)[0] + ": Child " + children[j] + " doesn't exist");
						return false;
					}
				}
			}
		}

		boolean inside = false;

		for (int i=0; i < indi.size(); i++) {
			if (indi.get(i)[7].compareTo("N/A") !=0) {
				for (int j=0; j < fam.size(); j++) {
					temp = false;
					inside = false;

					if (fam.get(j)[0].compareTo(indi.get(i)[7]) == 0) {
						temp = true;

						if (fam.get(j)[7].compareTo("N/A") == 0) {
							System.out.println("ERROR: FAMILY: US26: " + fam.get(j)[0] + ": Child " + indi.get(i)[0] + " doesn't have existing parents");
							return false;
							//continue;
						}

						String[] children = listifySet(fam.get(j)[7]);

						for (int k=0; k < children.length; k++) {
							if (children[k].compareTo(indi.get(i)[0]) == 0) {
								inside = true;
							}
						}
					}

					if (!temp || !inside) {
						System.out.println("ERROR: FAMILY: US26: " + fam.get(j)[0] + ": Child " + indi.get(i)[0] + " doesn't have matching parents");
						return false;
					}
				}
			}
		}

		for (int i=0; i < indi.size(); i++) {
			if (indi.get(i)[8].compareTo("N/A") != 0) {
				String[] spouses = listifySet(indi.get(i)[8]);

				for (int j=0; j < spouses.length; j++) {
					temp = false;

					for (int k=0; k < fam.size(); k++) {
						if (spouses[j].compareTo(fam.get(k)[3]) == 0 || spouses[j].compareTo(fam.get(k)[5]) == 0) {
							temp = true;
						}
					}

					if (!temp) {
						System.out.println("ERROR: FAMILY: US26: " + fam.get(i)[0] + ": Spouse " + spouses[j] + " doesn't exist");
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