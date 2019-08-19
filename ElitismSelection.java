import java.util.ArrayList;

public class ElitismSelection implements ISelection
{
    TSP_Problem tsp;

    public ArrayList<ArrayList<Integer>> select(ArrayList<ArrayList<Integer>> solutions)
    {
        
        ArrayList<ArrayList<Integer>> population = solutions;
        ArrayList<ArrayList<Integer>> elites = new ArrayList<ArrayList<Integer>>();
        int numElites = 3; // The number of elites present in a population

        // Get all fitness scores
        ArrayList<Double> fitness = new ArrayList<Double>(population.size());
        for (int i = 0; i < population.size(); i++)
        {
            fitness.add(tsp.getTotalDistance(population.get(i)));
        }

        // Find elites and seperate from population
        for (int i = 0; i < numElites; i++)
        {
            double best = fitness.get(0);
            int indexBest = 0;
            for (int j = 1; j < population.size(); j++) {
                if (best > fitness.get(j)) 
                {
                    best = fitness.get(j);
                    indexBest = i;
                }
            }
            // Add elites to seperate list
            elites.add(population.get(indexBest));
            population.remove(indexBest);
            fitness.remove(indexBest);
        }

        // Elites now seperated, what selection method to use?
        

        // Add elites to the rest of population
        population.addAll(elites);

        return population;
    }
}
