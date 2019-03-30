import java.util.*;
import java.text.*;

public class genderRole {

	public static String getIndiGender(List<String[]> indi, String id) throws ParseException{
		for (int i=0; i < indi.size(); i++) {
			if (indi.get(i)[0] == id) {
				return indi.get(i)[2];
			}
		}
		return "N/A";
	}

	public static boolean checkGR(List<String[]> indi, List<String[]> fam) throws ParseException{
		for (int i=0; i<fam.size(); i++) {
			String dad = fam.get(i)[3];
			String mom = fam.get(i)[5];

			if (dad != "N/A") {
				if (getIndiGender(indi, dad) == "F") {
					System.out.println("ERROR: INDIVIDUAL: US21: " + fam.get(i)[0] + ": Father is a woman.");
					return false;
				}
			}

			if (mom != "N/A") {
				if (getIndiGender(indi, dad) == "M") {
					System.out.println("ERROR: INDIVIDUAL: US21: " + fam.get(i)[0] + ": Mother is a man.");
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException {
	}
}