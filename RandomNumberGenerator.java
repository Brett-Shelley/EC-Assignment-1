import java.util.Random;

// Class designed to allow the entire program to share a single instance
// of Random.
// This should help avoid overhead caused by multiple instances of Random.
public class RandomNumberGenerator
{
	static Random generator;
	
	public static Random getRandom()
	{
		if (generator == null)
		{
			generator = new Random();
		}
		
		return generator;
	}
}
