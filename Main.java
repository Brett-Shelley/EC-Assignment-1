    
import java.util.ArrayList;

//Main class
public class Main
{
    private static TSP_Problem tsp;
    private static Population pop;

    public static void main(String[] args)
    {
        tsp = new TSP_Problem("./Problems/kroA100.tsp");
        int populationSize=20;
        pop = new Population(tsp, populationSize);
        OrderCrossover order = new OrderCrossover();
        PmxCrossover pmx = new PmxCrossover();
        CycleCrossover cycle = new CycleCrossover();

        SwapMutation swap = new SwapMutation();
        InsertMutation insert = new InsertMutation();
        InvertMutation invert = new InvertMutation();

        FitnessProportionate fit = new FitnessProportionate();
        TournamentSelection tournament = new TournamentSelection();
        ElitismSelection elite = new ElitismSelection();

        ArrayList<Individual> res;
        //20000 for 20000 generations
        Population matingPop=new Population(tsp,populationSize);
        for(int x=1;x<=20000;x++){
            tournament.select(tsp, matingPop, 50);
            for(int i=0;i<populationSize;i++){
                pop.addToParents(matingPop.getParents().get(i));
                for(int j=i+1;j<populationSize;j++){
                    res=order.crossover(matingPop.getParents().get(i), pop.getParents().get(j));
                    pop.addToParents(invert.mutate(res.get(0)));
                    pop.addToParents(invert.mutate(res.get(1)));
                }
            }
           pop=tournament.select(tsp,pop,populationSize);
            if(x==2000){
                System.out.println(x+": "+pop.getBestScore());
            }
            else if(x==5000){
                System.out.println(x+": "+pop.getBestScore());
            }
            else if(x==10000){
                System.out.println(x+": "+pop.getBestScore());

            }
            else if(x==20000){
                System.out.println(x+": "+pop.getBestScore());
            }
        }

        System.out.println("Number of cities: " + tsp.getCoords().size());
        
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
