import java.util.ArrayList;
import java.util.Collections;


/*--------------Solution Interface-------------------

1.  creation of a solution requires an array list of Coords.

2. getScore() returns a double reperesenting the solutions fitness level

3. getPermutation() returns an ArrayList<Coords>. This is the individual solution path.

4. swap(int a, int b) swaps the two points in the permutation/solution path. 

----------------------------------------------------*/

public class Solution
{
    private double score;
    private ArrayList<Coords> permutation;

    // Initializes a solution from an ArrayList<Coords> and calculates score upon creation.
    public Solution(ArrayList<Coords> input)
    {
        permutation = new ArrayList<Coords>(input);
        score = getTotalDistance();
    }
    
    public int size()
    {
    	return permutation.size();
    }
    
    public Coords get(int i)
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
    public int index_of(Coords point)
    {
    	return permutation.indexOf(point);
    }
    
    //get the score
    public double getScore()
    {
        return score;
    }

    //get the permutation
    public ArrayList<Coords> getPermutation()
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
        score = getTotalDistance();
    }

	// Prints a line container each of the coordinates, followed by the trip length. 
	public void print()
	{
		for (Coords point : permutation)
		{
			System.out.print("(" + point.getX() + ", " + point.getY() + ") -> "); 
		}
		System.out.print("Total: " + score + "\n");
		System.out.println("-------------------------------------");
	}

    // Helper function to getTotalDistance, calculates distance between two points
    private double getDistance(int i, int j)
    {
        //get the x and y coordinates of the nodes at the ith and jth elements
        double startX = permutation.get(i).getX();
        double endX = permutation.get(j).getX();
        double startY = permutation.get(i).getY();
        double endY = permutation.get(j).getY();

        //get the difference between the x and y coordinates at the ith and jth elements
        double diffX = Math.abs(startX - endX);
        double diffY = Math.abs(startY - endY);

        //calculate the euclidean distance between the two points sqrt(diffX^2 + diffY^2)
        return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }

    // Gets the total distance for a specified solution.
    private double getTotalDistance()
    {
        double score = 0;

        //calculate the euclidean distance between each node in the permutation and add to the total euclidean distance traveled
        int length = permutation.size() - 1;
        for (int i = 0; i < length; i++)
        {
            score += getDistance(i, i + 1);
        }

        return score;
    }
}
