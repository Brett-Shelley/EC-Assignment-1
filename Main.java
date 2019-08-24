    
import java.util.ArrayList;

//Main class
public class Main
{
    private static TSP_Problem tsp;
    private static Population pop;

    public static void main(String[] args)
    {
        if(args.length==0)
        {
            System.out.println("You have provided no arguments");
            return;
        }

        tsp = new TSP_Problem(args[0]);
        int populationSize = Integer.parseInt(args[2]);
        pop = new Population(tsp, populationSize);
        int numTests = 30;

        if(Integer.parseInt(args[1]) == 1){
            ArrayList<Double> jumpResultAll = new ArrayList<Double>();
            ArrayList<Double> jumpResultMin = new ArrayList<Double>();
            Double jumpMin = 0.0;

            JumpOperator jumpSearch = new JumpOperator();
            jumpMin = runTests(jumpSearch, jumpResultAll, jumpResultMin,
                jumpMin, numTests, true);
        }
        else if(Integer.parseInt(args[1]) == 2){
            ArrayList<Double> exchangeResultAll = new ArrayList<Double>();
            ArrayList<Double> exchangeResultMin = new ArrayList<Double>();
            Double exchangeMin = 0.0;

            ExchangeOperator exchangeSearch = new ExchangeOperator();
            exchangeMin = runTests(exchangeSearch, exchangeResultAll, exchangeResultMin,
                exchangeMin, numTests, true);
        }
        else if(Integer.parseInt(args[1]) == 3){
            ArrayList<Double> twoOptResultAll = new ArrayList<Double>();
            ArrayList<Double> twoOptResultMin = new ArrayList<Double>();
            Double twoOptMin = 0.0;

            TwoOptOperator twoOpt = new TwoOptOperator();
            twoOptMin = runTests(twoOpt, twoOptResultAll, twoOptResultMin,
                twoOptMin, numTests, true);
        }
        else if(Integer.parseInt(args[1]) == 4){
            GeneticAlgorithm1 ga1 = new GeneticAlgorithm1();
            ga1.GeneticAlgorithm(tsp ,pop ,populationSize);
            stats(pop.getParents());
        }
        else if(Integer.parseInt(args[1]) == 5){
            GeneticAlgorithm2 ga2 = new GeneticAlgorithm2();
            ga2.GeneticAlgorithm(tsp ,pop ,populationSize);
            stats(pop.getParents());
        }
        else if(Integer.parseInt(args[1]) == 6){
            GeneticAlgorithm3 ga3 = new GeneticAlgorithm3();
            ga3.GeneticAlgorithm(tsp ,pop ,populationSize);
            stats(pop.getParents());
        }
        else if(Integer.parseInt(args[1]) == 7){    //InverOver
            ElitismSelection elitism = new ElitismSelection();
            InverOver invOv = new InverOver();

            for(int y = 1; y <= numTests; y++){
                pop = new Population(tsp, tsp.getCoords().size());
                Population testInvOv = null;
                for(int x = 1; x <= 20000; x++){
                    testInvOv = elitism.select(tsp, invOv.InverOver(tsp, pop, 10, 0.02), 10);
                }
                stats(testInvOv.getParents());
            }
        }
        
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
        System.out.println("Mean: " + mean);
        System.out.println("Standard Deviation: " + stdDev);
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
