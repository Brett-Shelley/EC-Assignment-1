import java.util.ArrayList;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        TSP_Problem tsp = new TSP_Problem("./Problems/eil51.tsp");
        Population population = new Population(20, tsp);

        Mutation mutate = new Mutation();
        Crossover crossover = new Crossover();
        Selection select = new Selection();

        for (int x = 1; x <= 20000; x++)
        {
            Population parents = select.elitism(population, 20);
            ArrayList<Individual> offspring = new ArrayList<Individual>();
            for (int i = 0; i < parents.getSize(); i++)
            {
                for (int j = i+1; j < parents.getSize(); j++)
                {
                    ArrayList<Individual> temp = crossover.order(parents.getPopulation().get(i), parents.getPopulation().get(j));
                    offspring.add(mutate.insert(temp.get(0)));
                    offspring.add(mutate.insert(temp.get(1)));
                }
            }
            offspring.addAll(parents.getPopulation());
            population.replacePopulation(offspring);
            if (x % 1 == 0)
            {
                population.print();
            }
            // if (x == 2000)
            // {
            //     population.print();
            // }
            // else if (x == 5000)
            // {
            //     population.print();
            // }
            // else if (x == 10000)
            // {
            //     population.print();
            // }
            // else if (x == 20000)
            // {
            //     population.print();
            // }
        }

        return;
    }
}
