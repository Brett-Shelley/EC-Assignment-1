import java.util.ArrayList;
import java.util.HashSet;
// import java.util.HashMap;
import java.util.Random;


public class EdgeRecombinationCrossover implements ITwoParentCrossover
{
    public ArrayList<Solution> crossover(Solution parentOne, Solution parentTwo)
    {
        int length = parentOne.size();
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
        
        children.add(crossoverhelper(parentOne, parentTwo, start, end));
        children.add(crossoverhelper(parentTwo, parentOne, start, end));
        
        return children;
    }

    private Solution crossoverhelper(Solution parentOne, Solution parentTwo, int start, int end)
    {
        ArrayList<Coords> child = new ArrayList<Coords>();
        // HashSet<Coords> points_in_child = new HashSet<Coords>();
        int size = parentOne.size();
        int child_size = 0;
        int parent_index;
        int child_index;

        //Child starts off filled with null points 
        for (int i = 0; i < size; i++){
            child.add(null);
        }

        //Create the neighbours table
        ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();

        //Populate the table
        

        //THE STUFF BELOW HERE WILL PROBABLY BE REMOVED

        

        // // Copy specified section from first parent to child
        // for (int j = start; j < end; j++)
        // {
        //     child.set(j, parentOne.get(j));
        //     // Add the points in the child to the set. 
        //     points_in_child.add(child.get(j));
        //     child_size += 1;
        // }

        // parent_index = end;
        // child_index = end;
        // while(child_size < size){
        //     if(blah){
        //         //Do stuff
        //         continue;
        //     }

        //     child.set(child_index, parentTwo.get(parent_index));
        //     points_in_child.add(child.get(child_index));
            
        //     // Update the child and parent indexes. 
        //     parent_index = (parent_index + 1) % size;
        //     child_index = (child_index + 1) % size;
        //     child_size += 1;
        // }

        Solution result = new Solution(child);
        
        return result;

    }
}