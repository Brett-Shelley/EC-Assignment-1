import java.util.ArrayList;

public class ElitismSelection implements ISelection
{
    Population populationObject;

    // Keeps the solutions with highest fitness scores
    // numElites variable is number of elites present in a population
    public Population select(Population solutions, int numElites)
    {
        // Initialise population array
        ArrayList<Solution> population = populationObject.getParents();
        ArrayList<Solution> elites = new ArrayList<Solution> ();

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

        // Elites now seperated, what selection method to use?
        

        // Add elites to the rest of population
        population.addAll(elites);
        populationObject.replaceParents(population);
        return populationObject;
    }
}
