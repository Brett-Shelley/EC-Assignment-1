import java.util.ArrayList;
import java.util.Collections;

public class Population
{
    //arrayList of solutions
    private ArrayList<Solution> parents;

    // Creates an initial population of desired size
    public Population(ArrayList<Coords> tspCoords, int size)
    {
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