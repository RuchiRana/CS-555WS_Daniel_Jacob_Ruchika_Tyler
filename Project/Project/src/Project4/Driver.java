package project4;

import java.io.IOException;

public class Driver 
{
	public static void main(String[] args) throws IOException
	{
		Project project4 = new Project("src/resources/tags.txt","src/resources/Project04.ged");
		
		project4.run();
	}
}
