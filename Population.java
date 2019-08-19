import java.util.ArrayList;
import java.util.Collections;


/*-----------Population Interface--------------------------

1.  creation of a population requires an array list of Coords and an integer size
    will create a population of the input size.

2.  getParents() returns an array list of solutions. This is your population.

3.  addToParents(Solution) will add a solution to the end of the population array list

4.  replaceParents(ArrayList<Solution>) will replace the current population with the input array list

---------------------------------------------------------- */

public class Population
{
    //arrayList of solutions
    private ArrayList<Solution> parents;

    // Creates an initial population of desired size
    public Population(ArrayList<Coords> tspCoords, int size)
    {
        //shuffle tspCoords and create a new Solution. Add that solution to the population i times.
        for(int i = 0; i < size; i++)
        {
            Collections.shuffle(tspCoords);
            Solution sol = new Solution(tspCoords);
            parents.add(sol);
        }
    }

    //get the population
    public ArrayList<Solution> getParents()
    {
        return parents;
    }

    //add a solution to the parents list
    public void addToParents(Solution input)
    {
        parents.add(input);
    }

    //replace the parents list
    public void replaceParents(ArrayList<Solution> input)
    {
        parents = new ArrayList<Solution>(input);
    }

}
