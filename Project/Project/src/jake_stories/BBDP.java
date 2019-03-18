package jake_stories;

import java.util.*;
import java.text.*;

public class BBDP {

	enum Dates {
		BDAY, DEATH;
	}

	public static Date getIndiDate(List<String[]> indi, String id, Dates d) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < indi.size(); i++) {
			if (indi.get(i)[0] == id) {

				String date;

				if (d == Dates.BDAY) {
					date = indi.get(i)[3];
				}
				else {
					date = indi.get(i)[6];
				}

				if (date == "N/A" && d == Dates.BDAY) {
					return new Date(Long.MIN_VALUE);
				}
				else if (date == "N/A") {
					return new Date(Long.MAX_VALUE);
				}

				return format.parse(date);
			}
		}
		return new Date(Long.MIN_VALUE);
	}

	public static boolean checkBBDP(List<String[]> indi, List<String[]> fam) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i=0; i<indi.size(); i++) {
			String child = indi.get(i)[7];

			if (child == "N/A") {
				continue;
			}

			String dateB = indi.get(i)[3];

			for (int j=0; j<fam.size(); j++) {
				if (fam.get(j)[0] == child) {

					Date db = format.parse(dateB);
					Date dad = getIndiDate(indi, fam.get(i)[3], Dates.DEATH);
					Date mom = getIndiDate(indi, fam.get(i)[5], Dates.DEATH);

					if (db.before(dad)) {
						System.out.println("ERROR: INDIVIDUAL: US09: " + indi.get(i)[0] + ": Father death " + dad.toString() + " is before child birth date " + db.toString());
						return false;
					}

					if (db.after(mom)) {
						System.out.println("ERROR: INDIVIDUAL: US09: " + indi.get(i)[0] + ": Mother death " + mom.toString() + " is before birth date date " + db.toString());
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException {
	}
}