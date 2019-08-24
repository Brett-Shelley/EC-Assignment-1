import java.util.ArrayList;
import java.util.Random;

public class FitnessProportionate implements ISelection
{
    // Keeps the individuals with higher fitness score with a higher probability
    // numSurvivors variable is number of individuals in population
    public Population select(TSP_Problem tsp, Population individuals, int numSurvivors)
    {
        // Initialise population array
        ArrayList<Individual> population = individuals.getParents();

        // Find sum of fitness
        double sum = 0;
        for (int i = 0; i < population.size(); i++)
        {
            sum += 1/population.get(i).getScore();
        }

        // Select n individuals with probability assigned
        double probability;
        double randomProb;
        ArrayList<Individual> survivors = new ArrayList<Individual> ();
        for (int i = 0; i < numSurvivors; i++)
        {
            probability = 0;
            Random rand = RandomNumberGenerator.getRandom();
            randomProb = rand.nextDouble() * sum;
            for (int j = 0; j < population.size(); j++)
            {
                if (probability > randomProb)
                {
                    survivors.add(population.get(j));
                    population.remove(j);
                    break;
                }
                else
                {
                    probability += 1/population.get(population.size() - j - 1).getScore();
                }
            }
        }
        individuals.replaceParents(survivors);
        return individuals;
    }
}
