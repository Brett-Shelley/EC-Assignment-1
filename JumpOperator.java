import java.util.ArrayList;
import java.util.Collections;

// Class which implements the jump operator. 
public class JumpOperator implements ILocalSearchOperator
{
    // Performs the jump operator, shifting the item at first to be 
    // before the item at second with the items previously between them
    // behind first. 
    // EG:
    // If permutation = 8, 4, 6, 3, 2, 5, 1, 7
    // first = 1
    // second = 4
    // Then in first loop i = 1 and permutation becomes 8, 6, 4, 3, 2, 5, 1, 7
    // In second loop i = 2 and permutation becomes 8, 6, 3, 4, 2, 5, 1, 7
    // In third loop i = 3 and permutation becomes 8, 6, 3, 2, 4, 5, 1, 7
    // Then i = 4 and the sequence is returned. 
    public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second)
    {
        ArrayList<Integer> mutated = new ArrayList<Integer>(permutation);
        mutated.add(second+1, mutated.get(first));
        mutated.remove(first);
        return mutated;
    }
    
    public String name()
    {
        return "Jump";
    }
}
