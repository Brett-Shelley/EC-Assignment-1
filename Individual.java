import java.util.ArrayList;

public class Individual
{
    private ArrayList<Integer> trip;
    private TSP_Problem tsp;
    private double distance;
    private double fitness;

    public Individual(ArrayList<Integer> trip, TSP_Problem tsp)
    {
        this.trip = trip;
        this.tsp = tsp;
        distance = tsp.getTotalDistance(trip);
        fitness = 1.0 / distance;
    }

    public ArrayList<Integer> getTrip()
    {
        return trip;
    }

    public TSP_Problem getTsp()
    {
        return tsp;
    }

    public double getDistance()
    {
        return distance;
    }

    public double getFitness()
    {
        return fitness;
    }
}
