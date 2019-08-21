import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Crossover
{
    public Crossover()
    {

    }

    public ArrayList<Individual> order(Individual parentOne, Individual parentTwo)
    {
        ArrayList<Integer> permutationOne = new ArrayList<Integer>(parentOne.getTrip());
        ArrayList<Integer> permutationTwo = new ArrayList<Integer>(parentTwo.getTrip());
        
        ArrayList<Integer> finalPermutationOne = new ArrayList<Integer>();
        ArrayList<Integer> finalPermutationTwo = new ArrayList<Integer>();
        for (int k = 0; k < permutationOne.size(); k++)
        {
            finalPermutationOne.add(null);
            finalPermutationTwo.add(null);
        }
        Random rand = RandomNumberGenerator.getRandom();
        int i = rand.nextInt(permutationOne.size());
        int j = i;
        while (j == i)
        {
            j = rand.nextInt(permutationOne.size());
        }
        if (i > j)
        {
            int temp = i;
            i = j;
            j = temp;
        }
        for (int k = i; k <= j; k++)
        {
            finalPermutationOne.set(k, permutationOne.get(k));
            finalPermutationTwo.set(k, permutationTwo.get(k));
        }
        int k = j+1;
        int l = j+1;
        while (k != i)
        {
            if (k == finalPermutationOne.size())
            {
                k = 0;
            }
            while (true)
            {
                if (l == finalPermutationOne.size())
                {
                    l = 0;
                }
                if (!finalPermutationOne.contains(permutationTwo.get(l)))
                {
                    finalPermutationOne.set(k, permutationTwo.get(l));
                    l++;
                    break;
                }
                else
                {
                    l++;
                }
            }
            k++;
        }
        k = j+1;
        l = j+1;
        while (k != i)
        {
            if (k == finalPermutationTwo.size())
            {
                k = 0;
            }
            while (true)
            {
                if (l == finalPermutationOne.size())
                {
                    l = 0;
                }
                if (!finalPermutationTwo.contains(permutationOne.get(l)))
                {
                    finalPermutationTwo.set(k, permutationOne.get(l));
                    l++;
                    break;
                }
                else
                {
                    l++;
                }
            }
            k++;
        }

        ArrayList<Individual> children = new ArrayList<Individual>();
        children.add(new Individual(finalPermutationOne, parentOne.getTsp()));
        children.add(new Individual(finalPermutationTwo, parentTwo.getTsp()));
        return children;
    }

    public ArrayList<Individual> pmx(Individual parentOne, Individual parentTwo)
    {
        ArrayList<Integer> permutationOne = new ArrayList<Integer>(parentOne.getTrip());
        ArrayList<Integer> permutationTwo = new ArrayList<Integer>(parentTwo.getTrip());
        
        ArrayList<Integer> finalPermutationOne = new ArrayList<Integer>();
        ArrayList<Integer> finalPermutationTwo = new ArrayList<Integer>();
        for (int k = 0; k < permutationOne.size(); k++)
        {
            finalPermutationOne.add(null);
            finalPermutationTwo.add(null);
        }

        Random rand = RandomNumberGenerator.getRandom();
        int i = rand.nextInt(permutationOne.size());
        int j = i;
        while (j == i)
        {
            j = rand.nextInt(permutationOne.size());
        }
        if (i > j)
        {
            int temp = i;
            i = j;
            j = temp;
        }

        for (int k = i; k <= j; k++)
        {
            finalPermutationOne.set(k, permutationOne.get(k));
            finalPermutationTwo.set(k, permutationTwo.get(k));
        }

        for (int k = i; k <= j; k++)
        {
            if (!finalPermutationOne.contains(permutationTwo.get(k)))
            {
                int index = permutationTwo.indexOf(finalPermutationOne.get(k));
                while (finalPermutationOne.get(index) != null)
                {
                    index = permutationTwo.indexOf(finalPermutationOne.get(index));
                }
                finalPermutationOne.set(index, permutationTwo.get(k));
            }

            if (!finalPermutationTwo.contains(permutationOne.get(k)))
            {
                int index = permutationOne.indexOf(finalPermutationTwo.get(k));
                while (finalPermutationTwo.get(index) != null)
                {
                    index = permutationOne.indexOf(finalPermutationTwo.get(index));
                }
                finalPermutationTwo.set(index, permutationOne.get(k));
            }
        }

        for (int k = 0; k < finalPermutationOne.size(); k++)
        {
            if (finalPermutationOne.get(k) == null)
            {
                finalPermutationOne.set(k, permutationTwo.get(k));
            }

            if (finalPermutationTwo.get(k) == null)
            {
                finalPermutationTwo.set(k, permutationOne.get(k));
            }
        }

        ArrayList<Individual> children = new ArrayList<Individual>();
        children.add(new Individual(finalPermutationOne, parentOne.getTsp()));
        children.add(new Individual(finalPermutationTwo, parentTwo.getTsp()));
        return children;
    }

    public ArrayList<Individual> cycle(Individual parentOne, Individual parentTwo)
    {
        ArrayList<Integer> permutationOne = new ArrayList<Integer>(parentOne.getTrip());
        ArrayList<Integer> permutationTwo = new ArrayList<Integer>(parentTwo.getTrip());
        
        ArrayList<Integer> finalPermutationOne = new ArrayList<Integer>();
        ArrayList<Integer> finalPermutationTwo = new ArrayList<Integer>();
        for (int k = 0; k < permutationOne.size(); k++)
        {
            finalPermutationOne.add(null);
            finalPermutationTwo.add(null);
        }

        boolean flip = false;
        int index;
        for (int k = 0; k < finalPermutationOne.size(); k++)
        {
            if (finalPermutationOne.get(k) == null && !flip)
            {
                finalPermutationOne.set(k, permutationOne.get(k));
                finalPermutationTwo.set(k, permutationTwo.get(k));
                index = permutationOne.indexOf(permutationTwo.get(k));
                if (index != k)
                {
                    while (index != k)
                    {
                        finalPermutationOne.set(index, permutationOne.get(index));
                        finalPermutationTwo.set(index, permutationTwo.get(index));
                        index = permutationOne.indexOf(permutationTwo.get(index));
                    }
                }
                flip = true;
            }
            else if (finalPermutationOne.get(k) == null && flip)
            {
                finalPermutationOne.set(k, permutationTwo.get(k));
                finalPermutationTwo.set(k, permutationOne.get(k));
                index = permutationOne.indexOf(permutationTwo.get(k));
                if (index != k)
                {
                    while (index != k)
                    {
                        finalPermutationOne.set(index, permutationTwo.get(index));
                        finalPermutationTwo.set(index, permutationOne.get(index));
                        index = permutationOne.indexOf(permutationTwo.get(index));
                    }
                }
                flip = false;
            }
        }

        ArrayList<Individual> children = new ArrayList<Individual>();
        children.add(new Individual(finalPermutationOne, parentOne.getTsp()));
        children.add(new Individual(finalPermutationTwo, parentTwo.getTsp()));
        return children;
    }
}
