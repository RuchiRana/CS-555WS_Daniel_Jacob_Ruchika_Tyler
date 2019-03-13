import java.util.*;
import java.text.*;

public class refac_BBM {

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

				if (date == "N/A") {
					return new Date(Long.MIN_VALUE);
				}

				return format.parse(date);
			}
		}
		return new Date(Long.MIN_VALUE);
	}

	public static boolean checkBBM(List<String[]> indi, List<String[]> fam) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < fam.size(); i++) {
			String dateM = fam.get(i)[1];

			if (dateM == "N/A" || fam.get(i)[3] == "None" || fam.get(i)[5] == "None") {
				return true;
			}

			Date dm = format.parse(dateM);
			Date husb = getIndiDate(indi, fam.get(i)[3], Dates.BDAY);
			Date wife = getIndiDate(indi, fam.get(i)[5], Dates.BDAY);

			if (dm.before(husb)) {
				System.out.println("ERROR: FAMILY: US02: " + fam.get(i)[0] + ": Husband birthdate " + husb.toString() + " is after marriage date " + dm.toString());
				return false;
			}

			if (dm.before(wife)) {
				System.out.println("ERROR: FAMILY: US02: " + fam.get(i)[0] + ": Wife birthdate " + wife.toString() + " is after marriage date " + dm.toString());
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException{
	}
}