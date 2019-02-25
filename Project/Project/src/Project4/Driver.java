package Project4;

import java.io.IOException;
import java.text.ParseException;

public class Driver
{
	public static void main(String[] args) throws IOException, ParseException
	{
		Project project04 = new Project("Project/Project/resources/tags.txt","Project/Project/resources/Project04.ged");
		
		project04.run(); 
	}
}
