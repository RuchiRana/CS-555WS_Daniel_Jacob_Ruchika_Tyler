package Project_8;



import java.io.IOException;
import java.text.ParseException;

public class Driver
{
	public static void main(String[] args) throws IOException, ParseException
	{

		Project project08 = new Project("Project/project/resources/tags.txt","Project/project/resources/testFile.ged");

		Project project06 = new Project("../../resources/tags.txt","../../resources/testFile.ged");

		project08.run();
	}
} 
