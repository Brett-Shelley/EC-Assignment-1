import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection implements ISelection
{
    TSP_Problem tsp;

    // Keeps solutions with higher fitness scores if selected in tournaments
    public ArrayList<ArrayList<Integer>> select(ArrayList<ArrayList<Integer>> solutions)
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
