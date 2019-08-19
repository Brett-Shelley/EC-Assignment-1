public interface ISelection
{
    public Population select(TSP_Problem tsp, Population solutions, int numSurvivors);
}