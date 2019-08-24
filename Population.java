import java.util.ArrayList;
import java.util.Collections;


/*-----------Population Interface--------------------------

1.  creation of a population requires an array list of Coords and an integer size
    will create a population of the input size.

2.  getParents() returns an array list of Individuals. This is your population.

3.  addToParents(Individual) will add a Individual to the end of the population array list

4.  replaceParents(ArrayList<Individual>) will replace the current population with the input array list

---------------------------------------------------------- */

public class Population
{
    //arrayList of Individuals
    private ArrayList<Individual> parents;

    // Used in InverOver
    public Population(ArrayList<Individual> input){
        parents = new ArrayList<Individual>(input);
    }

    // Creates an initial population of desired size
    public Population(TSP_Problem tspCoords, int size)
    {
        Individual.set_problem(tspCoords);
        parents = new ArrayList<Individual>();
        //shuffle tspCoords and create a new Individual. Add that Individual to the population i times.
        for(int i = 0; i < size; i++)
        {
            Individual sol = new Individual();
            parents.add(sol);
        }
    }

    //get the population
    public ArrayList<Individual> getParents()
    {
        return parents;
    }

    //add a Individual to the parents list
    public void addToParents(Individual input)
    {
        parents.add(input);
    }

    //replace the parents list
    public void replaceParents(ArrayList<Individual> input)
    {
        parents = new ArrayList<Individual>(input);
    }

}
