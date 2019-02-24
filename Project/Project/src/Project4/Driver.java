package Project4;

import java.io.IOException;
import java.text.ParseException;

public class Driver
{
	public static void main(String[] args) throws IOException, ParseException
	{
		Project project04 = new Project("resources/tags.txt","resources/Project04.ged");
		
		project04.run();
	}
}
