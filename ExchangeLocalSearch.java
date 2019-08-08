import java.util.ArrayList;
import java.util.Collections;

// Implements jump local search in the IlocalSearch interface. 
public class ExchangeLocalSearch implements ILocalSearch
{
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
                    Collections.swap(next, i, j);
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
		return "Exchange Local Search";
	}
}