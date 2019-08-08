import java.util.ArrayList;
import java.util.Collections;

public class ExchangeOperator implements ILocalSearchOperator
{
	public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second)
	{
		Collections.swap(permutation, first, second);
		return permutation;
	}
	
	public String name()
	{
		return "Exchange";
	}
}
