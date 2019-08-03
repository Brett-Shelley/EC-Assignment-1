import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main
{
    private static TSP_Problem tsp;

    public static void main(String[] args)
    {
        tsp = new TSP_Problem("./eil51.tsp");
        exchangeLS();
        return;
    }

    // Generates a random permutation based on the TSP.
    public static ArrayList<Integer> initPermutation()
    {
        ArrayList<Integer> permutations = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < tsp.getDimension(); i++)
        {
            permutations.add(i);
        }
        Collections.shuffle(permutations, rand);
        return permutations;
    }

    public static void jumpLS()
    {
        ArrayList<Integer> permutation = initPermutation();
        ArrayList<Double> distances = new ArrayList<Double>();
        Double minDistance = tsp.getTotalDistance(permutation);
        Double distance;
        int count = 0;
        int i;
        int j;
        while (count < 30)
        {
            Random rand = new Random();
            i = rand.nextInt(tsp.getDimension());
            j = rand.nextInt(tsp.getDimension());
            while (j == i)
            {
                j = rand.nextInt(tsp.getDimension());
            }
        }
    }

    public static void exchangeLS()
    {
        ArrayList<Integer> permutation = initPermutation();
        ArrayList<Double> distances = new ArrayList<Double>();
        Double minDistance = tsp.getTotalDistance(permutation);
        System.out.println(minDistance);
        Double distance;
        int count = 0;
        int i;
        int j;
        while (count < 30)
        {
            Random rand = new Random();
            i = rand.nextInt(tsp.getDimension());
            j = rand.nextInt(tsp.getDimension());
            while (j == i)
            {
                j = rand.nextInt(tsp.getDimension());
            }
            Collections.swap(permutation, i, j);
            distance = tsp.getTotalDistance(permutation);
            distances.add(distance);
            if (distance < minDistance)
            {
                minDistance = distance;
                count = 0;
            }
            else
            {
                count++;
            }
        }
        System.out.println(minDistance);
        Double mean = 0.0;
        for (Double d : distances)
        {
            mean += d;
        }
        mean = mean / distances.size();
        System.out.println(mean);
    }

    public static void twoOptLS()
    {

    }
}
