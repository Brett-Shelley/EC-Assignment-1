    
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

        GeneticAlgorithm1 ga1=new GeneticAlgorithm1();
        GeneticAlgorithm2 ga2=new GeneticAlgorithm2();
        GeneticAlgorithm3 ga3=new GeneticAlgorithm3();
        tsp = new TSP_Problem(args[0]);
        int populationSize=Integer.parseInt(args[2]);
        pop = new Population(tsp, populationSize);

        if(Integer.parseInt(args[1])==1){
            ga1.GeneticAlgorithm(tsp,pop,populationSize);

        }
        if(Integer.parseInt(args[1])==2){
            ga2.GeneticAlgorithm(tsp,pop,populationSize);

        }
        if(Integer.parseInt(args[1])==3){
            ga3.GeneticAlgorithm(tsp,pop,populationSize);
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

    public void stats(ArrayList<Individual> population)
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
