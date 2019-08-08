import java.util.ArrayList;
import java.util.Collections;

// Implements jump local search in the IlocalSearch interface. 
public class JumpLocalSearch implements ILocalSearch
{
	// Default constructor
	public JumpLocalSearch()
	{
	}
	
	// Jump operation.
    private static ArrayList<Integer> jumpOp(ArrayList<Integer> permutation, int start, int end)
    {
        for (int i = start; i < end; i++)
        {
            Collections.swap(permutation, i, i+1);
        }
        return permutation;
    }
    
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
