package project6;



import java.io.IOException;
import java.text.ParseException;

public class Driver
{
	public static void main(String[] args) throws IOException, ParseException
	{
		Project project06 = new Project("Project/Project/resources/tags.txt","Project/Project/resources/testFile.ged");
		
		project06.run();
	}
} 
