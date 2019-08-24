import java.util.ArrayList;

public class ElitismSelection implements ISelection
{
    Population populationObject;

    // Keeps the Individuals with highest fitness scores
    // numElites variable is number of elites present in a population
    public Population select(TSP_Problem tsp, Population individuals, int numElites)
    {
        populationObject = new Population(tsp, tsp.getCoords().size());
        // Initialise population array
        ArrayList<Individual> population = populationObject.getParents();
        ArrayList<Individual> elites = new ArrayList<Individual> ();

        // Find elites and seperate from population
        for (int i = 0; i < numElites; i++)
        {
            double best = population.get(0).getScore();
            int indexBest = 0;
            for (int j = 1; j < population.size(); j++) {
                if (best > population.get(j).getScore()) 
                {
                    best = population.get(j).getScore();
                    indexBest = i;
                }
            }
            // Add elites to seperate list
            elites.add(population.get(indexBest));
            population.remove(indexBest);
        }
        populationObject.replaceParents(elites);

        return populationObject;
    }
}