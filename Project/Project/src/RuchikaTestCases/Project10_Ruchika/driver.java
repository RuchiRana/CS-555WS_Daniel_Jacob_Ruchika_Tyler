package project10_Ruchika;
import java.io.IOException;
import java.text.ParseException;

public class driver
{
	public static void main(String[] args) throws IOException, ParseException
	{
		project project08 = new project("resources/tags.txt","resources/testFile.ged");
			
		project08.run();
	}
} 