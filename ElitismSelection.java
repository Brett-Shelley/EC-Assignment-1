import java.util.ArrayList;

public class ElitismSelection implements ISelection
{
    // Keeps the solutions with highest fitness scores
    // numElites variable is number of elites present in a population
    public Population select(TSP_Problem tsp, Population solutions, int numElites)
    {
        // Initialise population array
        ArrayList<Solution> population = solutions.getParents();
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
                    indexBest = j;
                }
            }
            // Add elites to seperate list
            elites.add(population.get(indexBest));
            population.remove(indexBest);
        }
        solutions.replaceParents(elites);

        return solutions;
    }
}
