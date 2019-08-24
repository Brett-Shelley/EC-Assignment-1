import java.util.ArrayList;

//Main class
public class Main
{
    private static TSP_Problem tsp;
    private static Population pop;

    public static void main(String[] args)
    {

        String name = "./Problems/" + args[0];
        // String name = "./Problems/pr2392.tsp";
        System.out.println(name + ",Mean,Standard Deviation");
        tsp = new TSP_Problem(name);

        ElitismSelection elitism = new ElitismSelection();
        InverOver invOv = new InverOver();

        int populationSize = 50;
        for(int y = 1; y <= 30; y++){
            pop = new Population(tsp, tsp.getCoords().size());
            Population testInvOv = null;
            for(int x = 1; x <= 20000; x++){
                testInvOv = elitism.select(tsp, invOv.InverOver(tsp, pop, 10, 0.02), 10);
            }
            System.out.print("Interation: " + y + ",");
            stats(testInvOv.getParents());
        }

        // ArrayList<Double> jumpResultAll = new ArrayList<Double>();
        // ArrayList<Double> jumpResultMin = new ArrayList<Double>();
        // Double jumpMin = 0.0;

        // ArrayList<Double> exchangeResultAll = new ArrayList<Double>();
        // ArrayList<Double> exchangeResultMin = new ArrayList<Double>();
        // Double exchangeMin = 0.0;

        // ArrayList<Double> twoOptResultAll = new ArrayList<Double>();
        // ArrayList<Double> twoOptResultMin = new ArrayList<Double>();
        // Double twoOptMin = 0.0;

        // int numTests = 30;

        // JumpOperator jumpSearch = new JumpOperator();
        // jumpMin = runTests(jumpSearch, jumpResultAll, jumpResultMin,
        //     jumpMin, numTests, true);

        // ExchangeOperator exchangeSearch = new ExchangeOperator();
        // exchangeMin = runTests(exchangeSearch, exchangeResultAll, exchangeResultMin,
        //     exchangeMin, numTests, true);

        // TwoOptOperator twoOpt = new TwoOptOperator();
        // twoOptMin = runTests(twoOpt, twoOptResultAll, twoOptResultMin,
        //     twoOptMin, numTests, true);

        // System.out.println("Mean fitness before selection: " + getPopulationScore(pop));
        // Population testFit = new Population(tsp, tsp.getCoords().size());
        // Population testTourn = new Population(tsp, tsp.getCoords().size());
        // Population testElite = new Population(tsp, tsp.getCoords().size());

        // FitnessProportionate fitProp = new FitnessProportionate();
        // testFit = fitProp.select(tsp, testFit, 10);
        // System.out.println("Mean fitness after fitness selection: " + getPopulationScore(testFit));

        // TournamentSelection tournament = new TournamentSelection();
        // testTourn = tournament.select(tsp, testTourn, 10);
        // System.out.println("Mean fitness after tournament selection: " + getPopulationScore(testTourn));

        // ElitismSelection elitism = new ElitismSelection();
        // testElite = elitism.select(tsp, testElite, 10);
        // System.out.println("Mean fitness after elitism selection: " + getPopulationScore(testElite));

        // Population pop2 = new Population(tsp, tsp.getCoords().size());
        // InverOver invOv = new InverOver();
        // Population testInvOv = invOv.InverOver(tsp, pop2, 10, 0.02);
        // testInvOv = elitism.select(tsp, testInvOv, 10);
        // System.out.println("Mean fitness after elitism selection on Inver-Over: " + getPopulationScore(testInvOv));

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
            System.out.println("Mean of all Individuals for " + name + ": "  + getMean(resultAll));
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

    public static void stats(ArrayList<Individual> population)
    {
        double sum = 0;
        for (Individual solution : population) {
            sum += solution.getScore();
        }
        double mean = sum / (double)population.size();
        sum = 0;
        for (Individual solution : population) {
            sum += Math.pow(solution.getScore() - mean, 2);
        }
        double stdDev = Math.sqrt(((1.0 / (double)population.size()) * sum));
        System.out.print(mean + ",");
        System.out.print(stdDev + "\n");
    }

    public static Double getPopulationScore(Population Individuals)
    {
        Double avgScore = 0.0;
        ArrayList<Individual> popScores = Individuals.getParents();
        for (int i = 0; i < Individuals.getParents().size(); i++)
        {
            avgScore += popScores.get(i).getScore();
        }
        avgScore = avgScore / Individuals.getParents().size();
        return avgScore;
    }
}
