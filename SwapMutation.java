import java.util.Random;

public class SwapMutation implements IMutation
{
	public Solution mutate(Solution permutation)
	{
		// Copy permutation to avoid changing the orignal. 
		Solution mutated = new Solution(permutation.getPermutation());
		
		int length = mutated.size();
		Random rand = RandomNumberGenerator.getRandom();
		int start = rand.nextInt(length);
		int end = rand.nextInt(length);
		
		// Swap the two randomly chosen items in the sequence. 
		mutated.swap(start, end);
		
		return mutated;
	}
}

