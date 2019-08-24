import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection implements ISelection
{
    Population populationObject;

    // Keeps individuals with higher fitness scores if selected in tournaments
    // Tournaments variable is equal to number of individuals returned
    public Population select(TSP_Problem tsp, Population individuals, int tournaments)
    {
        int n = 3; // Number of individuals in tournament
        // Initialise population array
        ArrayList<Individual> population = individuals.getParents();
        ArrayList<Individual> survivors = new ArrayList<Individual> ();
        
        for (int j = 0; j < tournaments; j++)
        {
            // Select n individuals randomly from individuals pool
            Random rand = RandomNumberGenerator.getRandom();
            ArrayList<Individual> competitors = new ArrayList<Individual>();
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
        individuals.replaceParents(survivors);
        return individuals;
    }
}
