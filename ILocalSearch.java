import java.util.ArrayList;

// Interface for local search algorithms. 
// Takes a TSP_Problem object and performs a local search,
// returning an arraylist of doubles representing the distance.
public interface ILocalSearch
{
	public ArrayList<Double> search(TSP_Problem problem);
}
