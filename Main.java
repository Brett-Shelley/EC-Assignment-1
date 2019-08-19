import java.util.ArrayList;

//Main class
public class Main
{
    private static TSP_Problem tsp;
    private static Population pop;

    public static void main(String[] args)
    {
        tsp = new TSP_Problem("./Problems/eil51.tsp");
        pop = new Population(tsp.getCoords(), tsp.getCoords().size());

        ArrayList<Double> jumpResultAll = new ArrayList<Double>();
        ArrayList<Double> jumpResultMin = new ArrayList<Double>();
        Double jumpMin = 0.0;

        ArrayList<Double> exchangeResultAll = new ArrayList<Double>();
        ArrayList<Double> exchangeResultMin = new ArrayList<Double>();
        Double exchangeMin = 0.0;

        ArrayList<Double> twoOptResultAll = new ArrayList<Double>();
        ArrayList<Double> twoOptResultMin = new ArrayList<Double>();
        Double twoOptMin = 0.0;

        int numTests = 30;

        JumpOperator jumpSearch = new JumpOperator();
        jumpMin = runTests(jumpSearch, jumpResultAll, jumpResultMin,
            jumpMin, numTests, true);

        ExchangeOperator exchangeSearch = new ExchangeOperator();
        exchangeMin = runTests(exchangeSearch, exchangeResultAll, exchangeResultMin,
            exchangeMin, numTests, true);

        TwoOptOperator twoOpt = new TwoOptOperator();
        twoOptMin = runTests(twoOpt, twoOptResultAll, twoOptResultMin,
            twoOptMin, numTests, true);

        System.out.println("Mean fitness before selection: " + getPopulationScore(pop));
        Population testPop = pop;

        FitnessProportionate fitProp = new FitnessProportionate();
        testPop = fitProp.select(tsp, pop, 10);
        System.out.println("Mean fitness after fitness selection: " + getPopulationScore(testPop));

        TournamentSelection tournament = new TournamentSelection();
        testPop = tournament.select(tsp, pop, 10);
        System.out.println("Mean fitness after tournament selection: " + getPopulationScore(testPop));

        ElitismSelection elitism = new ElitismSelection();
        testPop = elitism.select(tsp, pop, 10);
        System.out.println("Mean fitness after elitism selection: " + getPopulationScore(testPop));

        return;
    }

    //I'd pass the function by reference instead, but Java 11 doesn't let you do that
    public static double runTests(ILocalSearchOperator operator, ArrayList<Double> resultAll,
        ArrayList<Double> resultMin, double min, int numTests, boolean printOutput)
    {
        String name = operator.name();
        ArrayList<Double> temp = LocalSearch.search(tsp, operator);
        for(int i = 0; i < numTests; i++)
        {
            resultAll.addAll(temp);
            resultMin.add(temp.get(temp.size()-1));
            if(i == 0){
                min = temp.get(temp.size()-1);
            }
            else{
                if(min > temp.get(temp.size()-1)){
                    min = temp.get(temp.size()-1);
                }
            }
        }

        if(printOutput){
            System.out.println("Minimum for " + name + ": " + min);
            System.out.println("Mean of all minimums for " + name + ": "  + getMean(resultMin));
            System.out.println("Mean of all solutions for " + name + ": "  + getMean(resultAll));
            System.out.println();
        }

        return min; //Since we can't pass primitives by reference in Java, lets just return the only primitive
                    //Although we don't really need it after here
    }

    // Gets the mean of a given ArrayList of Doubles.
    public static Double getMean(ArrayList<Double> values)
    {
        Double mean = 0.0;
        for (int i = 0; i < values.size(); i++)
        {
            mean += values.get(i);
        }
        mean = mean / values.size();
        return mean;
    }

    public static Double getPopulationScore(Population solutions)
    {
        Double avgScore = 0.0;
        ArrayList<Solution> popScores = solutions.getParents();
        for (int i = 0; i < solutions.getParents().size(); i++)
        {
            avgScore += popScores.get(i).getScore();
        }
        avgScore = avgScore / solutions.getParents().size();
        return avgScore;
    }
}
