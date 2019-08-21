import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mutation
{
    public Mutation()
    {

    }

    public Individual insert(Individual solution)
    {
        ArrayList<Integer> permutation = new ArrayList<Integer>(solution.getTrip());
        Random rand = RandomNumberGenerator.getRandom();
        int i = rand.nextInt(permutation.size());
        int j = i;
        while (j == i)
        {
            j = rand.nextInt(permutation.size());
        }
        if (i > j)
        {
            int temp = i;
            i = j;
            j = temp;
        }
        for (int k = i; k < j; k++)
        {
            Collections.swap(permutation, k, k+1);
        }
        Individual mutated = new Individual(permutation, solution.getTsp());
        return mutated;
    }

    public Individual swap(Individual solution)
    {
        ArrayList<Integer> permutation = new ArrayList<Integer>(solution.getTrip());
        Random rand = RandomNumberGenerator.getRandom();
        int i = rand.nextInt(permutation.size());
        int j = i;
        while (j == i)
        {
            j = rand.nextInt(permutation.size());
        }
        Collections.swap(permutation, i, j);
        Individual mutated = new Individual(permutation, solution.getTsp());
        return mutated;
    }

    public Individual inversion(Individual solution)
    {
        ArrayList<Integer> permutation = new ArrayList<Integer>(solution.getTrip());
        Random rand = RandomNumberGenerator.getRandom();
        int i = rand.nextInt(permutation.size());
        int j = i;
        while (j == i)
        {
            j = rand.nextInt(permutation.size());
        }
        if (i > j)
        {
            int temp = i;
            i = j;
            j = temp;
        }
        while (true)
        {
            Collections.swap(permutation, i, j);
            i++;
            j--;
            if (i == j)
            {
                break;
            }
            else if ((i + 1) == j)
            {
                Collections.swap(permutation, i, j);
                break;
            }
        }
        Individual mutated = new Individual(permutation, solution.getTsp());
        return mutated;
    }
}
