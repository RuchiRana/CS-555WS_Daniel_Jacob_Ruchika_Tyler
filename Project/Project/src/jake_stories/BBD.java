import java.util.*;
import java.text.*;

public class BBD {

	public static boolean checkBBD(List<String[]> indi) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0; i < indi.size(); i++) {

			if (indi.get(i)[3] == "N/A" || indi.get(i)[6] == "N/A") {
				continue;
			}

			Date birth = format.parse(indi.get(i)[3]);
			Date death = format.parse(indi.get(i)[6]);

			if (death.before(birth)) {
				System.out.println("ERROR: INDIVIDUAL: US03: " + indi.get(i)[0] + "Died " + death.toString() + " before born " + birth.toString());
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException{
	}
}