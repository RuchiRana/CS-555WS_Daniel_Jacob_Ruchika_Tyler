package project02;

import java.io.IOException;

public class Driver 
{
	public static void main(String[] args) throws IOException
	{
		Project project02 = new Project("resources/tags.txt","resources/Project02.ged");
		
		project02.run();
	}
}
