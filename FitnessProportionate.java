import java.util.ArrayList;
import java.util.Random;

public class FitnessProportionate implements ISelection
{
    Population populationObject;
    
    // Keeps the Individuals with higher fitness score with a higher probability
    // numSurvivors variable is number of Individuals in population
    public Population select(TSP_Problem tsp, Population Individuals, int numSurvivors)
    {
        populationObject = new Population(tsp, tsp.getCoords().size());

        // Initialise population array
        ArrayList<Individual> population = populationObject.getParents();

        // Find sum of fitness
        double sum = 0;
        for (int i = 0; i < population.size(); i++)
        {
            sum += population.get(i).getScore();
        }

        // Select n individuals with probability assigned
        double probability = sum;
        double randomProb;
        ArrayList<Individual> survivors = new ArrayList<Individual> ();
        for (int i = 0; i < numSurvivors; i++)
        {
            Random rand = RandomNumberGenerator.getRandom();
            randomProb = rand.nextDouble() * sum;
            for (int j = 0; j < population.size(); j++)
            {
                if (probability < randomProb)
                {
                    survivors.add(population.get(j));
                    population.remove(j);
                    break;
                }
                else
                {
                    probability -= population.get(population.size() - j - 1).getScore();
                }
            }
        }
        populationObject.replaceParents(survivors);
        return populationObject;
    }
}
