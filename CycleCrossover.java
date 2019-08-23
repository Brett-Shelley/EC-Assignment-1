import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class CycleCrossover implements ITwoParentCrossover
{
    public ArrayList<Individual> crossover(Individual parentOne, Individual parentTwo)
    {
        int size = parentOne.size();
        Random rand = RandomNumberGenerator.getRandom();
        int start = rand.nextInt(size);
        
        ArrayList<Integer> childOne = new ArrayList<Integer>();
        ArrayList<Integer> childTwo = new ArrayList<Integer>();
        
        // Initialize child one and two to be null
        for (int i = 0; i < size; i++)
        {
            childOne.add(null);
            childTwo.add(null);
        }
        
        ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
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
            ArrayList<Integer> childA = children.get(child_index);
            ArrayList<Integer> childB = children.get((child_index + 1) % 2);
            
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
        
        ArrayList<Individual> result = new ArrayList<Individual>();
        Individual IndividualOne = new Individual(childOne);
        Individual IndividualTwo = new Individual(childTwo);
        
        result.add(IndividualOne);
        result.add(IndividualTwo);
        
        return result; 
    }
}

