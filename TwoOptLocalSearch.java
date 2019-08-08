import java.util.ArrayList;
import java.util.Collections;

// Implements jump local search in the IlocalSearch interface. 
public class TwoOptLocalSearch implements ILocalSearch
{
	// 2-Opt operation.
    private static ArrayList<Integer> twoOptOp(ArrayList<Integer> permutation, int first, int second)
    {
        ArrayList<Integer> twoOpt = new ArrayList<Integer>();
        for (int i = 0; i < first; i++)
        {
            twoOpt.add(permutation.get(i));
        }
        for (int j = second; j > first-1; j--)
        {
            twoOpt.add(permutation.get(j));
        }
        for (int k = second+1; k < permutation.size(); k++)
        {
            twoOpt.add(permutation.get(k));
        }
        return twoOpt;
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
                    next = twoOptOp(next, i, j);
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
}
