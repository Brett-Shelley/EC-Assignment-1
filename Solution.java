import java.util.ArrayList;
import java.util.Collections;


/*--------------Solution Interface-------------------
0. Use set_problem to set which TSP problem you are solving. 

1.  creation of a solution requires an array list of ints representing cities.

2. getScore() returns a double reperesenting the solutions fitness level

3. getPermutation() returns an ArrayList<int>. This is the individual solution path.

4. swap(int a, int b) swaps the two points in the permutation/solution path. 

----------------------------------------------------*/

public class Solution
{
    private static TSP_Problem problem;
    private double score;
    private ArrayList<Integer> permutation;
    
    // Sets the TSP problem which solutions are following. 
    // Once this is called, all existing Solutions are stale and should
    // not be used. 
    // This MUST be called before the constructor for Solution.
    public static void set_problem(TSP_Problem new_problem)
    {
        problem = new_problem;
    }

    // Initializes a solution from an ArrayList<Coords> and calculates score upon creation.
    public Solution()
    {
        if (problem == null)
        {
            score = 0;
            permutation = new ArrayList<Integer>();
        }
        
        permutation = problem.initPermutation();
        problem.getTotalDistance(permutation);
    }
    
    public int size()
    {
    	return permutation.size();
    }
    
    public int get(int i)
    {
    	if (i >= permutation.size())
    	{
    		i = permutation.size();
    	}
    	if (i < 0)
    	{
    		i = 0;
    	}
    	
    	return permutation.get(i);
    }
    
    // Returns the index of point, or -1 if it is not
    // in the sequence. 
    public int index_of(int point)
    {
    	return permutation.indexOf(point);
    }
    
    //get the score
    public double getScore()
    {
        return score;
    }

    //get the permutation
    public ArrayList<Integer> getPermutation()
    {
        return permutation;
    }

    //swaps any two points in the permutation
    public void swap(int a, int b)
    {
        //keep a/b in bounds. if < 0 set to 0, if > than the permutation length, set a/b to the last element
        if(a < 0) {a = 0;}
        else if(a >= permutation.size()) {a = permutation.size() - 1;}

        if(b < 0) {b = 0;}
        else if(b >= permutation.size()) {b = permutation.size() - 1;}

        //swap ath and bth element
        Collections.swap(permutation, a, b);

        //recalculate score
        problem.getTotalDistance(permutation);
    }

	// Prints a line container each of the coordinates, followed by the trip length. 
	public void print()
	{
		for (int point : permutation)
		{
		    System.out.println(point + " -> ");
			//System.out.print("(" + point.getX() + ", " + point.getY() + ") -> "); 
		}
		System.out.print("Total: " + score + "\n");
		System.out.println("-------------------------------------");
	}
}
