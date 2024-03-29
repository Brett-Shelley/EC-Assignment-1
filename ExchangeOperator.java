import java.util.ArrayList;
import java.util.Collections;

// Class which implements the exchange operator. 
public class ExchangeOperator implements ILocalSearchOperator
{
    // Implements the exchange operator, exchanging the items
    // at location first and location second. 
    // EG:
    // If permutation = 8, 4, 6, 3, 2, 5, 1, 7
    // first = 1
    // second = 4
    // Then the returned permutation is 8, 2, 6, 3, 4. 5, 1, 7
    public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second)
    {
        ArrayList<Integer> mutated = new ArrayList<Integer>(permutation);
        Collections.swap(mutated, first, second);
        return mutated;
    }
    
    public String name()
    {
        return "Exchange";
    }
}
