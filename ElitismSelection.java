import java.util.ArrayList;

public class ElitismSelection implements ISelection
{
    Population populationObject;

    // Keeps the individuals with highest fitness scores
    // numElites variable is number of elites present in a population
    public Population select(TSP_Problem tsp, Population individuals, int numElites)
    {
        // Initialise population array
        ArrayList<Individual> population = individuals.getParents();
        ArrayList<Individual> elites = new ArrayList<Individual> ();

        // Find elites and seperate from population
        for (int i = 0; i < numElites; i++)
        {
            double best = population.get(0).getScore();
            int indexBest = 0;
            for (int j = 1; j < population.size(); j++)
            {
                if (population.get(j).getScore() < best)
                {
                    best = population.get(j).getScore();
                    indexBest = j;
                }
            }
            // Add elites to seperate list
            elites.add(population.get(indexBest));
            population.remove(indexBest);
        }
        individuals.replaceParents(elites);
        return individuals;
    }
}
