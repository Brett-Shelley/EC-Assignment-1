import java.util.ArrayList;

public interface ILocalSearchOperator
{
	public ArrayList<Integer> mutate(ArrayList<Integer> permutation, int first, int second);
	
	public String name();
}
