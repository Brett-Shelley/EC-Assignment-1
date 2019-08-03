import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main
{
    private static TSP_Problem tsp;

    public static void main(String[] args)
    {
        tsp = new TSP_Problem("./eil51.tsp");

        ArrayList<Double> jumpResultAll = new ArrayList<Double>();
        ArrayList<Double> jumpResultMin = new ArrayList<Double>();
        Double jumpMin = 0.0;

        ArrayList<Double> exchangeResultAll = new ArrayList<Double>();
        ArrayList<Double> exchangeResultMin = new ArrayList<Double>();
        Double exchangeMin = 0.0;

        ArrayList<Double> twoOptResultAll = new ArrayList<Double>();
        ArrayList<Double> twoOptResultMin = new ArrayList<Double>();
        Double twoOptMin = 0.0;

        ArrayList<Double> temp;
        for (int i = 0; i < 30; i++)
        {
            temp = jumpLS();
            jumpResultAll.addAll(temp);
            jumpResultMin.add(temp.get(temp.size()-1));
            if (i == 0)
            {
                jumpMin = temp.get(temp.size()-1);
            }
            else
            {
                if (jumpMin > temp.get(temp.size()-1))
                {
                    jumpMin = temp.get(temp.size()-1);
                }
            }

            temp = exchangeLS();
            exchangeResultAll.addAll(temp);
            exchangeResultMin.add(temp.get(temp.size()-1));
            if (i == 0)
            {
                exchangeMin = temp.get(temp.size()-1);
            }
            else
            {
                if (exchangeMin > temp.get(temp.size()-1))
                {
                    exchangeMin = temp.get(temp.size()-1);
                }
            }

            temp = twoOptLS();
            twoOptResultAll.addAll(temp);
            twoOptResultMin.add(temp.get(temp.size()-1));
            if (i == 0)
            {
                twoOptMin = temp.get(temp.size()-1);
            }
            else
            {
                if (twoOptMin > temp.get(temp.size()-1))
                {
                    twoOptMin = temp.get(temp.size()-1);
                }
            }
        }

        System.out.println("Minimum for Jump: " + jumpMin);
        System.out.println("Mean of all minimums for Jump: " + getMean(jumpResultMin));
        System.out.println("Mean of all solutions for Jump: " + getMean(jumpResultAll));
        System.out.println();

        System.out.println("Minimum for Exchange: " + exchangeMin);
        System.out.println("Mean of all minimums for Exchange: " + getMean(exchangeResultMin));
        System.out.println("Mean of all solutions for Jump: " + getMean(exchangeResultAll));
        System.out.println();

        System.out.println("Minimum for 2-Opt: " + twoOptMin);
        System.out.println("Mean of all minimums for 2-Opt: " + getMean(twoOptResultMin));
        System.out.println("Mean of all solutions for Jump: " + getMean(twoOptResultAll));
        System.out.println();

        return;
    }

    // Gets the mean of a given ArrayList of Doubles.
    public static Double getMean(ArrayList<Double> values)
    {
        Double mean = 0.0;
        for (int i = 0; i < values.size(); i++)
        {
            mean += values.get(i);
        }
        mean = mean / values.size();
        return mean;
    }

    // Generates a random permutation based on the TSP.
    public static ArrayList<Integer> initPermutation()
    {
        ArrayList<Integer> permutation = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < tsp.getDimension(); i++)
        {
            permutation.add(i);
        }
        Collections.shuffle(permutation, rand);
        return permutation;
    }

    // Jump Local Search.
    public static ArrayList<Double> jumpLS()
    {
        ArrayList<Integer> current = initPermutation();
        ArrayList<Integer> next = new ArrayList<Integer>();
        ArrayList<Integer> nextBest = new ArrayList<Integer>();

        Double currentDistance = tsp.getTotalDistance(current);
        Double nextDistance;

        ArrayList<Double> distances = new ArrayList<Double>();
        distances.add(currentDistance);

        boolean optimal = false;
        while (!optimal)
        {
            nextDistance = currentDistance;
            for (int i = 0; i < tsp.getDimension(); i++)
            {
                for (int j = i+1; j < tsp.getDimension(); j++)
                {
                    next = new ArrayList<Integer>(current);
                    next = jumpOp(next, i, j);
                    if (nextDistance > tsp.getTotalDistance(next))
                    {
                        nextBest = new ArrayList<Integer>(next);
                        nextDistance = tsp.getTotalDistance(next);
                    }
                }
            }
            if (nextDistance == currentDistance)
            {
                optimal = true;
            }
            else
            {
                current = new ArrayList<Integer>(nextBest);
                currentDistance = nextDistance;
                distances.add(currentDistance);
            }
        }
        return distances;
    }

    // Jump operation.
    public static ArrayList<Integer> jumpOp(ArrayList<Integer> permutation, int start, int end)
    {
        for (int i = start; i < end; i++)
        {
            Collections.swap(permutation, i, i+1);
        }
        return permutation;
    }

    // Exchange Local Search.
    public static ArrayList<Double> exchangeLS()
    {
        ArrayList<Integer> current = initPermutation();
        ArrayList<Integer> next = new ArrayList<Integer>();
        ArrayList<Integer> nextBest = new ArrayList<Integer>();

        Double currentDistance = tsp.getTotalDistance(current);
        Double nextDistance;

        ArrayList<Double> distances = new ArrayList<Double>();
        distances.add(currentDistance);

        boolean optimal = false;
        while (!optimal)
        {
            nextDistance = currentDistance;
            for (int i = 0; i < tsp.getDimension(); i++)
            {
                for (int j = i+1; j < tsp.getDimension(); j++)
                {
                    next = new ArrayList<Integer>(current);
                    Collections.swap(next, i, j);
                    if (nextDistance > tsp.getTotalDistance(next))
                    {
                        nextBest = new ArrayList<Integer>(next);
                        nextDistance = tsp.getTotalDistance(next);
                    }
                }
            }
            if (nextDistance == currentDistance)
            {
                optimal = true;
            }
            else
            {
                current = new ArrayList<Integer>(nextBest);
                currentDistance = nextDistance;
                distances.add(currentDistance);
            }
        }
        return distances;
    }

    // 2-Opt Local Search
    public static ArrayList<Double> twoOptLS()
    {
        ArrayList<Integer> current = initPermutation();
        ArrayList<Integer> next = new ArrayList<Integer>();
        ArrayList<Integer> nextBest = new ArrayList<Integer>();

        Double currentDistance = tsp.getTotalDistance(current);
        Double nextDistance;

        ArrayList<Double> distances = new ArrayList<Double>();
        distances.add(currentDistance);

        boolean optimal = false;
        while (!optimal)
        {
            nextDistance = currentDistance;
            for (int i = 0; i < tsp.getDimension(); i++)
            {
                for (int j = i+1; j < tsp.getDimension(); j++)
                {
                    next = new ArrayList<Integer>(current);
                    next = twoOptOp(next, i, j);
                    if (nextDistance > tsp.getTotalDistance(next))
                    {
                        nextBest = new ArrayList<Integer>(next);
                        nextDistance = tsp.getTotalDistance(next);
                    }
                }
            }
            if (nextDistance == currentDistance)
            {
                optimal = true;
            }
            else
            {
                current = new ArrayList<Integer>(nextBest);
                currentDistance = nextDistance;
                distances.add(currentDistance);
            }
        }
        return distances;
    }

    // 2-Opt operation.
    public static ArrayList<Integer> twoOptOp(ArrayList<Integer> permutation, int first, int second)
    {
        ArrayList<Integer> twoOpt = new ArrayList<Integer>();
        for (int i = 0; i < first; i++)
        {
            twoOpt.add(permutation.get(i));
        }
        for (int j = second; j > first-1; j--)
        {
            twoOpt.add(permutation.get(j));
        }
        for (int k = second+1; k < permutation.size(); k++)
        {
            twoOpt.add(permutation.get(k));
        }
        return twoOpt;
    }
}
