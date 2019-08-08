import java.util.ArrayList;
import java.util.Collections;

public class JumpOperator implements ILocalSearchOperator
{
	public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second)
	{
		for (int i = first; i < second; i++)
        {
            Collections.swap(permutation, i, i+1);
        }
        return permutation;
	}
	
	public String name()
	{
		return "Jump";
	}
}
