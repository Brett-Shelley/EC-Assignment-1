import java.util.ArrayList;
import java.util.Collections;

// Implements jump local search in the IlocalSearch interface. 
public class JumpLocalSearch implements ILocalSearch
{
	// Default constructor.
	public JumpLocalSearch()
	{
	}
	
	// Jump operation.
	// If the original permutation is 2, 4, 1, 5, 3
	// And start = 2 and end = 4
	// In first loop i = 2 and permutation would change to 2, 4, 5, 1, 3
	// In second loop i = 3 and permutation would change to 2, 4, 5, 3, 1
	// NOTE: May want to factor this into its own class to aide usability. 
	// NOTE: May want to encapsulate the ArrayList representing the sequence as an object
	// which only has a swap operator to prevent misuse of it. 
    private static ArrayList<Integer> jumpOp(ArrayList<Integer> permutation, int start, int end)
    {
        for (int i = start; i < end; i++)
        {
            Collections.swap(permutation, i, i+1);
        }
        return permutation;
    }
    
    // Performs a local search on the provided travelling salesman problem
    // using the jump operation to define the neighbourhood. 
	public ArrayList<Double> search(TSP_Problem problem)
	{
		ArrayList<Integer> current = problem.initPermutation();
        ArrayList<Integer> next = new ArrayList<Integer>();
        ArrayList<Integer> nextBest = new ArrayList<Integer>();

        Double currentDistance = problem.getTotalDistance(current);
        Double nextDistance;

        ArrayList<Double> distances = new ArrayList<Double>();
        distances.add(currentDistance);

        boolean optimal = false;
        while (!optimal)
        {
            nextDistance = currentDistance;
            for (int i = 0; i < problem.getDimension(); i++)
            {
                for (int j = i+1; j < problem.getDimension(); j++)
                {
                    next = new ArrayList<Integer>(current);
                    next = jumpOp(next, i, j);
                    if (nextDistance > problem.getTotalDistance(next))
                    {
                        nextBest = new ArrayList<Integer>(next);
                        nextDistance = problem.getTotalDistance(next);
                    }
                }
            }
            if (nextDistance == currentDistance)
            {
                optimal = true;
            }
            else
            {
                current = new ArrayList<Integer>(nextBest);
                currentDistance = nextDistance;
                distances.add(currentDistance);
            }
        }
        return distances;
	}
	
	public String name()
	{
		return "Jump Local Search";
	}
}
