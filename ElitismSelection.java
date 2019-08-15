import java.util.ArrayList;

public class ElitismSelection
{
    Population populationObject;

    // Keeps the solutions with highest fitness scores
    // numElites variable is number of elites present in a population
    // Method determines what selection algorithm to use
    // input "fitness" or "tournament" for corresponding selection
    public Population select(Population solutions, int numElites, String method)
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
        populationObject.replaceParents(population);

        // Elites now seperated, use input selection method
        if (method == "fitness")
        {
            FitnessProportionate fit = new FitnessProportionate();
            populationObject = fit.select(populationObject, 10 - numElites);
        }
        else if (method == "tournament")
        {
            TournamentSelection trnmt = new TournamentSelection();
            populationObject = trnmt.select(populationObject, 10 - numElites);
        }

        // Add elites to the rest of population
        for (int i = 0; i < elites.size(); i++)
        {
            populationObject.addToParents(elites.get(i));
        }

        return populationObject;
    }
}
