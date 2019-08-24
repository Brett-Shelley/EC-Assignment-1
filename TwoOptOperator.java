import java.util.ArrayList;
import java.util.Collections;

// Class which implements the TwoOptOperator. 
public class TwoOptOperator implements ILocalSearchOperator
{
    // Implements the TwoOptOperator. 
    public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second)
    {
        ArrayList<Integer> mutated = new ArrayList<Integer>(permutation);
        int i = first;
        int j = second;
        while (true)
        {
            if (i == j)
            {
                break;
            }
            else if (i+1 == j)
            {
                Collections.swap(mutated, i, j);
                break;
            }
            else
            {
                Collections.swap(mutated, i, j);
                i++;
                j--;
            }
        }
        return mutated;
    }
    
    public String name()
    {
        return "Two opt";
    }
}
