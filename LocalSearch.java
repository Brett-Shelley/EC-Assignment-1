import java.util.ArrayList;

// Generic class which implements local search. 
public class LocalSearch
{
    // Function performs local search on the travelling salesman problem problem,
    // using operator to define the neighbourhood. 
    public static ArrayList<Double> search(TSP_Problem problem, ILocalSearchOperator operator)
    {
        // Generate an initial permutation.
        ArrayList<Integer> current = problem.initPermutation();
        
        // Create arrayLists to keep track of next and best solutions. 
        ArrayList<Integer> next = new ArrayList<Integer>();
        ArrayList<Integer> nextBest = new ArrayList<Integer>();

        // Create doubles to keep track of distance. 
        Double currentDistance = problem.getTotalDistance(current);
        Double nextDistance;

        // Create array to keep track of distances. 
        ArrayList<Double> distances = new ArrayList<Double>();
        distances.add(currentDistance);

        boolean optimal = false;
        
        // Loop until an optimal solution is found. 
        while (!optimal)
        {
            nextDistance = currentDistance;
            
            // For each dimension
            for (int i = 0; i < problem.getDimension(); i++)
            {
                // For each dimension
                for (int j = i+1; j < problem.getDimension(); j++)
                {
                    // The next permutation starts as a duplicate of the current. 
                    next = new ArrayList<Integer>(current);
                    
                    // Mutate the next permutation. 
                    next = operator.mutate(next, i, j);
                    
                    // If the mutated permutation gives a shorter length
                    // than the previous permutation, keep it as current best. 
                    if (nextDistance > problem.getTotalDistance(next))
                    {
                        nextBest = new ArrayList<Integer>(next);
                        nextDistance = problem.getTotalDistance(next);
                    }
                }
            }
            
            // This condition will be true if nothing in the neighbourhood 
            // has shorter distance than the current distance.
            // If so we have our optimal solution. 
            if (nextDistance == currentDistance)
            {
                optimal = true;
            }
            else
            {
                // Update the current solution to be the best solution found
                // in the neighbourhood. 
                current = new ArrayList<Integer>(nextBest);
                currentDistance = nextDistance;
                distances.add(currentDistance);
            }
        }
        return distances;
    }
}
