import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class CycleCrossover implements ITwoParentCrossover
{
    public ArrayList<Solution> crossover(Solution parentOne, Solution parentTwo)
    {
        int size = parentOne.size();
        Random rand = RandomNumberGenerator.getRandom();
        int start = rand.nextInt(size);
        
        ArrayList<Coords> childOne = new ArrayList<Coords>();
        ArrayList<Coords> childTwo = new ArrayList<Coords>();
        
        // Initialize child one and two to be null
        for (int i = 0; i < size; i++)
        {
            childOne.add(null);
            childTwo.add(null);
        }
        
        ArrayList<ArrayList<Coords>> children = new ArrayList<ArrayList<Coords>>();
        children.add(childOne);
        children.add(childTwo);
        
        int current_index = 0;
        int child_index = 0;
        int child_size = 0;
        
        while (child_size < size)
        {
            // If the children are not null at current index
            if (children.get(child_index).get(current_index) != null)
            {
                // Move to next index. 
                current_index = (current_index + 1) % size;
                continue;
            }
            
            // Grab the children.
            // Note that childA could be childOne or childTwo
            ArrayList<Coords> childA = children.get(child_index);
            ArrayList<Coords> childB = children.get((child_index + 1) % 2);
            
            // Copy current_index to the children.
            childA.set(current_index, parentOne.get(current_index));
            childB.set(current_index, parentTwo.get(current_index));
            
            child_size++;
            
            // Find the next index in the loop.
            current_index = parentOne.index_of(childB.get(current_index));
            
            if (childA.get(current_index) != null)
            {
                // Flip the children.
                child_index = (child_index + 1) % 2;
                // If we reach the end of a loop, don't invert
                // the parents. 
                continue;
            }
        }
        
        ArrayList<Solution> result = new ArrayList<Solution>();
        Solution solutionOne = new Solution(childOne);
        Solution solutionTwo = new Solution(childTwo);
        
        result.add(solutionOne);
        result.add(solutionTwo);
        
        return result; 
    }
}

