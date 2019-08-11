import java.util.ArrayList;

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
    
    // Getter Functions
    public double getScore()
    {
        return score;
    }

    public ArrayList<Coords> getPermutation()
    {
        return permutation;
    }

    // Helper function to getTotalDistance, calculates distance between two points
    private double getDistance(int i, int j)
    {
        double startX = permutation.get(i).getX();
        double endX = permutation.get(j).getX();
        double startY = permutation.get(i).getY();
        double endY = permutation.get(j).getY();

        double diffX = Math.abs(startX - endX);
        double diffY = Math.abs(startY - endY);

        return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }

    // Gets the total distance for a specified solution.
    private double getTotalDistance()
    {
        double score = 0;
        int length = permutation.size() - 1;
        for (int i = 0; i < length; i++)
        {
            score += getDistance(i, i + 1);
        }
        return score;
    }
}