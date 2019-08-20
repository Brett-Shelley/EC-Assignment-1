import java.util.ArrayList;
import java.util.Collections;

// Class which implements the TwoOptOperator. 
public class TwoOptOperator implements ILocalSearchOperator
{
    // Implements the TwoOptOperator. 
    public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second)
    {
        ArrayList<Integer> twoOpt = new ArrayList<Integer>();
        for (int i = 0; i < first; i++)
        {
            twoOpt.add(permutation.get(i));
        }
        for (int j = second; j > first-1; j--)
        {
            twoOpt.add(permutation.get(j));
        }
        for (int k = second+1; k < permutation.size(); k++)
        {
            twoOpt.add(permutation.get(k));
        }
        return twoOpt;
    }
    
    public String name()
    {
        return "Two opt";
    }
}
