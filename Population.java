import java.util.ArrayList;
import java.util.Collections;

public class Population
{
    private ArrayList<ArrayList<coords>> parents;

    //still incomplete, but should create an initial population of desired size
    //returns an arraylist of solutions
    public Population(ArrayList<coords> tspCoords, int size)
    {
        for(int i = 0; i < size; i++)
        {
            parents.add(Collections.shuffle(tspCoords));
        }
    }


    //get the population
    public ArrayList<ArrayList<coords>> getParents()
    {
        return parents;
    }

}