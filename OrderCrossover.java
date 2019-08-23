import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class OrderCrossover implements ITwoParentCrossover
{
    public ArrayList<Solution> crossover(Solution firstParent, Solution secondParent)
    {    
        int length = firstParent.size();
        Random rand = RandomNumberGenerator.getRandom();
        int start = rand.nextInt(length);
        int end = rand.nextInt(length);
        int temp;
        int current;
        
        ArrayList<Solution> children = new ArrayList<Solution>();
        // If start is before end, swap start and end. 
        if (start > end)
        {
            temp = start;
            start = end;
            end = temp;
        }
        
        children.add(crossoverhelper(firstParent, secondParent, start, end));
        children.add(crossoverhelper(secondParent, firstParent, start, end));
        
        return children;
         
    }
    
    private Solution crossoverhelper(Solution firstParent, Solution secondParent, int start, int end)
    {
        ArrayList<Integer> child = new ArrayList<Integer>();
        HashSet<Integer> points_in_child = new HashSet<Integer>();
        int size = firstParent.size();
        int child_size = 0;
        int parent_index;
        int child_index;
        
        // Child starts off filled with null points    
        for (int i = 0; i < size; i++)
        {
            child.add(null);
        }
        
        // Copy specified section from first parent to child
        for (int j = start; j < end; j++)
        {
            child.set(j, firstParent.get(j));
            // Add the points in the child to the set. 
            points_in_child.add(child.get(j));
            child_size += 1;
        }
        
        parent_index = end;
        child_index = end;
        while(child_size < size)
        {
            // If the current point in the parent is already in the child,
            // move to the next point. 
            if (points_in_child.contains(secondParent.get(parent_index)) == true)
            {
                parent_index = (parent_index + 1) % size;
                continue;
            }
            
            // Add the next point to the child. 
            child.set(child_index, secondParent.get(parent_index));
            points_in_child.add(child.get(child_index));
            
            // Update the child and parent indexes. 
            parent_index = (parent_index + 1) % size;
            child_index = (child_index + 1) % size;
            child_size += 1;
        }
        
        Solution result = new Solution(child);
        
        return result;
    } 
}
