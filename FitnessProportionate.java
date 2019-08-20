import java.util.ArrayList;
import java.util.Random;

public class FitnessProportionate implements ISelection
{
    // Keeps the solutions with higher fitness score with a higher probability
    // numSurvivors variable is number of solutions in population
    public Population select(TSP_Problem tsp, Population solutions, int numSurvivors)
    {
        // Initialise population array
        ArrayList<Solution> population = solutions.getParents();

        // Find sum of fitness
        double sum = 0;
        for (int i = 0; i < population.size(); i++)
        {
            sum += 1/population.get(i).getScore();
        }

        // Select n individuals with probability assigned
        double probability;
        double randomProb;
        ArrayList<Solution> survivors = new ArrayList<Solution> ();
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
        solutions.replaceParents(survivors);
        return solutions;
    }
}
