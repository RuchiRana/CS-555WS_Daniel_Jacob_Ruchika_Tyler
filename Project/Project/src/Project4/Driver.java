

import java.io.IOException;

public class Driver
{
	public static void main(String[] args) throws IOException
	{
		Project project04 = new Project("../../resources/tags.txt","../../resources/Project04.ged");
		project04.run();
	}
}
