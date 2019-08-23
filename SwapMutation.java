import java.util.Random;

public class SwapMutation implements IMutation
{
	public Individual mutate(Individual permutation)
	{
		// Copy permutation to avoid changing the orignal. 
		Individual mutated = new Individual(permutation.getPermutation());
		
		int length = mutated.size();
		Random rand = RandomNumberGenerator.getRandom();
		int start = rand.nextInt(length);
		int end = rand.nextInt(length);
		
		// Swap the two randomly chosen items in the sequence. 
		mutated.swap(start, end);
		
		return mutated;
	}
}

