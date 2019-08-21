import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Selection
{
    public Selection()
    {

    }

    public Population fitness_proportionate(Population population, int numSurvivors)
    {
        if (numSurvivors > population.getSize())
        {
            numSurvivors = population.getSize();
        }
        
        population.sortPopulationByFitness();
        ArrayList<Individual> individuals = new ArrayList<Individual>(population.getPopulation());
        ArrayList<Individual> survivors = new ArrayList<Individual>();

        double sum_of_fitness;
        double random_probability;
        double previous_probability;
        while (survivors.size() < numSurvivors)
        {
            sum_of_fitness = 0.0;
            for (int i = 0; i < individuals.size(); i++)
            {
                sum_of_fitness += individuals.get(i).getFitness();
            }

            Random rand = RandomNumberGenerator.getRandom();
            random_probability = rand.nextDouble();
            previous_probability = 0.0;
            for (int i = 0; i < individuals.size(); i++)
            {
                previous_probability += individuals.get(i).getFitness() / sum_of_fitness;
                if (random_probability < previous_probability)
                {
                    survivors.add(individuals.get(i));
                    individuals.remove(i);
                    break;
                }
            }
        }

        Population newPopulation = new Population(survivors);
        return newPopulation;
    }

    public Population tournament_selection(Population population, int numSurvivors, int tournamentSize, double probability)
    {
        if (numSurvivors > population.getSize())
        {
            numSurvivors = population.getSize();
        }
        if (population.getSize() - numSurvivors < tournamentSize)
        {
            numSurvivors = population.getSize() - tournamentSize;
        }
        
        ArrayList<Individual> individuals = new ArrayList<Individual>(population.getPopulation());
        ArrayList<Individual> survivors = new ArrayList<Individual>();

        while (survivors.size() < numSurvivors)
        {
            Random rand = RandomNumberGenerator.getRandom();
            ArrayList<Individual> tournament = new ArrayList<Individual>();
            int random_individual;
            while (tournament.size() < tournamentSize)
            {
                random_individual = rand.nextInt(individuals.size());
                if (!tournament.contains(individuals.get(random_individual)))
                {
                    tournament.add(individuals.get(random_individual));
                }
            }

            Collections.sort(tournament, new IndividualComparator());

            double random_probability = rand.nextDouble();
            double previous_probability = probability;
            for (int i = tournament.size()-1; i >= 0; i--)
            {
                if (random_probability < previous_probability)
                {
                    survivors.add(tournament.get(i));
                    individuals.remove(tournament.get(i));
                    break;
                }
                previous_probability += previous_probability * (1 - probability);
            }
        }

        Population newPopulation = new Population(survivors);
        return newPopulation;
    }

    public Population elitism(Population population, int numSurvivors)
    {
        if (numSurvivors > population.getSize())
        {
            numSurvivors = population.getSize();
        }
        
        population.sortPopulationByFitness();
        ArrayList<Individual> individuals = new ArrayList<Individual>(population.getPopulation());
        ArrayList<Individual> survivors = new ArrayList<Individual>();

        while (survivors.size() < numSurvivors)
        {
            survivors.add(individuals.get(individuals.size()-1));
            individuals.remove(individuals.get(individuals.size()-1));
        }

        Population newPopulation = new Population(survivors);
        return newPopulation;
    }
}
