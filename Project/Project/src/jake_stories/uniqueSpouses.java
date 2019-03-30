import java.util.*;
import java.text.*;

public class uniqueSpouses {

	public static boolean checkUS(List<String[]> fam) throws ParseException{
		for (int i=0; i<fam.size(); i++) {
			String dad = fam.get(i)[3];
			String mom = fam.get(i)[5];
			String mDate = fam.get(i)[1];

			for (int j=i+1; j<fam.size(); j++) {
				if (dad == fam.get(j)[3] && mom == fam.get(j)[5] && mDate == fam.get(j)[1]) {
					System.out.println("ERROR: INDIVIDUAL: US24: " + fam.get(i)[0] + ": Family has duplicate: " + fam.get(j)[0]);
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException {
	}
}