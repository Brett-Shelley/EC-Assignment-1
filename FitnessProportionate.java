import java.util.ArrayList;
import java.util.Random;

public class FitnessProportionate implements ISelection
{
    TSP_Problem tsp;
    
    // Keeps the solutions with higher fitness score with a higher probability 
    public ArrayList<ArrayList<Integer>> select(ArrayList<ArrayList<Integer>> solutions)
    {
        // Get all fitness scores
        ArrayList <Double> fitness = new ArrayList <Double> (solutions.size());
        for (int i = 0; i < solutions.size(); i++)
        {
            fitness.add(tsp.getTotalDistance(solutions.get(i)));
        }

        // Find sum of fitness
        double sum = 0;
        for (int i = 0; i < fitness.size(); i++)
        {
            sum += fitness.get(i);
        }

        // Select n individuals with probability assigned
        int numSurvivors = 1; // Number of next generation, equal to number of solutions returned
        double probability = sum;
        double randomProb;
        //ArrayList<ArrayList<Integer>> survivors;
        ArrayList<ArrayList<Integer>> survivors = new ArrayList<ArrayList<Integer>> ();
        for (int i = 0; i < numSurvivors; i++)
        {
            Random rand = new Random();
            randomProb = rand.nextDouble() * sum;
            for (int j = 0; j < fitness.size(); j++)
            {
                if (probability < randomProb)
                {
                    survivors.add(solutions.get(j));
                    solutions.remove(j);
                    break;
                }
                else
                {
                    probability -= fitness.get(fitness.size() - j);
                }
            }
        }
        return survivors;
    }
}