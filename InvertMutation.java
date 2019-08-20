import java.util.Random;

public class InvertMutation implements IMutation
{
	public Solution mutate(Solution permutation)
	{
		// Copy permutation to avoid changing the orignal. 
		Solution mutated = new Solution(permutation.getPermutation());
		
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
		
		// If start and end generate to have no intermediate
		// cities, return without doing anything. 
		// This avoids an infinite loop. 
		if (start + 1 == end || start == end)
		{
			return mutated;
		}
		
		// Perform the mutation. 
		while(true)
		{
			mutated.swap(start, end);

			start = start + 1;
			end = end - 1;

			// If swap would do nothing, we are done
			// so break. 
			// This is escape condition for case
			// where start - end is an odd number,
			// and hence the number in the middle is
			// not changed. 
			if (start == end)
			{
				break;
			}
			
			// If start and end are next to each other,
			// we are done so break.
			// This is the escape condition for the case
			// when start - end is an even number, and hence
			// the swapping stops when start and end are adjacent.  
			if (start + 1 == end)
			{
				mutated.swap(start, end);
				break;
			}
		}
		
		return mutated;
	}
}

