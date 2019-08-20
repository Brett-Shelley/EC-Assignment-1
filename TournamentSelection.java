import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection implements ISelection
{
    // Keeps solutions with higher fitness scores if selected in tournaments
    // Tournaments variable is equal to number of solutions returned
    public Population select(TSP_Problem tsp, Population solutions, int tournaments)
    {
        int n = 3; // Number of individuals in tournament
        // Initialise population array
        ArrayList<Solution> population = solutions.getParents();
        ArrayList<Solution> survivors = new ArrayList<Solution> ();
        
        for (int j = 0; j < tournaments; j++)
        {
            // Select n individuals randomly from solutions pool
            Random rand = RandomNumberGenerator.getRandom();
            ArrayList<Solution> competitors = new ArrayList<Solution>();
            for (int i = 0; i < n; i++)
            {
                int randomProb = rand.nextInt(population.size());
                competitors.add(population.get(randomProb));
            }

            // Keep the individual with best fitness score
            double best = competitors.get(0).getScore();
            int indexBest = 0;
            for (int i = 1; i < competitors.size(); i++)
            {
                if (best > competitors.get(i).getScore())
                {
                    best = competitors.get(i).getScore();
                    indexBest = i;
                }
            }
            // Add individual to survivors
            survivors.add(competitors.get(indexBest));
        }
        solutions.replaceParents(survivors);
        return solutions;
    }
}