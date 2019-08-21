import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population
{
    private int size;
    private int individualSize;
    private ArrayList<Individual> population;

    public Population(int size, TSP_Problem tsp)
    {
        this.size = size;
        individualSize = tsp.getDimension();
        population = new ArrayList<Individual>();
        ArrayList<Integer> permutation;
        for (int i = 0; i < size; i++)
        {
            permutation = initPermutation();
            population.add(new Individual(permutation, tsp));
        }
    }

    public Population(ArrayList<Individual> population)
    {
        size = population.size();
        individualSize = population.get(0).getTrip().size();
        this.population = population;
    }

    // Generates a random permutation based on the TSP.
    public ArrayList<Integer> initPermutation()
    {
        ArrayList<Integer> permutation = new ArrayList<Integer>();
        Random rand = RandomNumberGenerator.getRandom();
        for (int i = 1; i < individualSize; i++)
        {
            permutation.add(i);
        }
        Collections.shuffle(permutation, rand);
        return permutation;
    }

    public void replacePopulation(ArrayList<Individual> population)
    {
        size = population.size();
        this.population = population;
    }

    public void addPopulation(ArrayList<Individual> population)
    {
        size += population.size();
        this.population.addAll(population);
    }

    public void sortPopulationByFitness()
    {
        try {
            Collections.sort(population, new IndividualComparator());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public int getSize()
    {
        return size;
    }

    public int getIndividualSize()
    {
        return individualSize;
    }

    public ArrayList<Individual> getPopulation()
    {
        return population;
    }

    public void print()
    {
        sortPopulationByFitness();
        System.out.println("Minimum Distance: " + population.get(size-1).getDistance());
        if (population.get(size-1).getDistance() < 428.0)
        {
            try {
                System.out.println(population.get(size-1).getTrip());
                throw new Exception();
            }
            catch (Exception e)
            {
                System.out.println("lol");
            }
        } 
        int sum = 0;
        for (int i = 0; i < size; i++)
        {
            sum += population.get(i).getDistance();
        }
        System.out.println("Mean Distance: " + ((double)sum / (double)size));
    }
}
