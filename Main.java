public class Main
{
    public static void main(String[] args)
    {
        TSP_Problem tsp = new TSP_Problem("./eil51.tsp");
        System.out.println(tsp.getDistance(4, 8));
        return;
    }
}
