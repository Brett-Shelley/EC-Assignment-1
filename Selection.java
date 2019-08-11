import java.util.ArrayList;
import java.util.Random;

public class Selection
{
    TSP_Problem tsp;
    
    // Keeps the solutions with higher fitness score with a higher probability 
    public ArrayList<ArrayList<Integer>> fitnessProportionate(ArrayList<ArrayList<Integer>> solutions)
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
        int numSurvivors = 1; // Used to keep certain number of next generation
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

    // Keeps solutions with higher fitness scores if selected in tournaments
    public ArrayList<ArrayList<Integer>> tournamentSelection(ArrayList<ArrayList<Integer>> solutions)
    {
        int tournaments = 10; // Number of tournaments run, equal to number of solutions returned
        int n = 3; // Number of individuals in tournament
        ArrayList<ArrayList<Integer>> survivors = new ArrayList<ArrayList<Integer>> ();
        for (int j = 0; j < tournaments; j++)
        {
            // Select n individuals randomly from solutions pool
            Random rand = new Random();
            ArrayList<ArrayList<Integer>> competitors = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < n; i++)
            {
                int randomProb = rand.nextInt(solutions.size());
                competitors.add(solutions.get(randomProb));
            }

            // Get all fitness scores
            ArrayList<Double> fitness = new ArrayList<Double>(competitors.size());
            for (int i = 0; i < competitors.size(); i++)
            {
                fitness.add(tsp.getTotalDistance(competitors.get(i)));
            }

            // Keep the individual with best fitness score
            double best = fitness.get(0);
            int indexBest = 0;
            for (int i = 1; i < competitors.size(); i++)
            {
                if (best > fitness.get(i))
                {
                    best = fitness.get(i);
                    indexBest = i;
                }
            }
            // Add individual to survivors
            survivors.add(competitors.get(indexBest));
        }
        return survivors;
    }
    
    public ArrayList<ArrayList<Integer>> elitismSelection(ArrayList<ArrayList<Integer>> solutions)
    {
        // code here
        ArrayList<ArrayList<Integer>> survivors = new ArrayList<ArrayList<Integer>> ();
        return survivors;
    }
}