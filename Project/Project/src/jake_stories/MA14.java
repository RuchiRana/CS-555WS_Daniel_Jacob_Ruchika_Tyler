import java.util.*;
import java.text.*;

public class MA14 {
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

	public static boolean checkMA14(List<String[]> indi, List<String[]> fam) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < fam.size(); i++) {
			String dateM = fam.get(i)[1];

			if (dateM == "N/A" || fam.get(i)[3] == "N/A" || fam.get(i)[5] == "N/A") {
				return true;
			}

			Date dm = format.parse(dateM);
			Date husb = getIndiDate(indi, fam.get(i)[3], Dates.BDAY);
			Date wife = getIndiDate(indi, fam.get(i)[5], Dates.BDAY);

			Calendar c = new GregorianCalendar();

			c.setTime(dm);
			int mYear = c.get(Calendar.YEAR);
			c.setTime(husb);
			int hYear = c.get(Calendar.YEAR);
			c.setTime(wife);
			int wYear = c.get(Calendar.YEAR);

			if (mYear - hYear < 14) {
				System.out.println("ERROR: FAMILY: US10: " + fam.get(i)[0] + ": Husband birthdate " + husb.toString() + " is less than 14 years before marriage date " + dm.toString());
				return false;
			}

			if (mYear - wYear < 14) {
				System.out.println("ERROR: FAMILY: US10: " + fam.get(i)[0] + ": Wife birthdate " + wife.toString() + " is less than 14 years before marriage date " + dm.toString());
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException{
	}
}