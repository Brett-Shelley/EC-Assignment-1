import java.util.Random;

public class InsertMutation implements IMutation
{
	public Individual mutate(Individual permutation)
	{
		// Copy permutation to avoid changing the orignal. 
		Individual mutated = new Individual(permutation.getPermutation());
		
		int length = mutated.size();
		Random rand = RandomNumberGenerator.getRandom();
		int start = rand.nextInt(length);
		int end = rand.nextInt(length);
		int temp;
		int current;
		
		// If start is before end, swap start and end. 
		if (start > end)
		{
			temp = start;
			start = end;
			end = temp;
		}
		
		// Start at the item before end
		current = end - 1;
		
		// Until we reach start
		while (current > start)
		{
			// Move the value which started at end
			// one space back in the sequence. 
			mutated.swap(current, current + 1);
			current -= 1;
		}
		
		return mutated;
	}
}

