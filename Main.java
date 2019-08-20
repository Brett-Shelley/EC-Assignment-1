import java.util.ArrayList;
import java.io.*;

//Main class
public class Main
{
    private static TSP_Problem tsp;
    private static Population pop;

    public static void main(String[] args)
    {
        tsp = new TSP_Problem("./Problems/eil51.tsp");
        pop = new Population(tsp.getCoords(), 200);

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

        Population matingPop;

        FitnessProportionate fit = new FitnessProportionate();
        TournamentSelection torna = new TournamentSelection();
        ElitismSelection elite = new ElitismSelection();

        InsertMutation insert = new InsertMutation();
        SwapMutation swap = new SwapMutation();
        InvertMutation invert = new InvertMutation();

        OrderCrossover order = new OrderCrossover();
        PmxCrossover pmx = new PmxCrossover();
        CycleCrossover cycle = new CycleCrossover();
        EdgeRecombinationCrossover edge = new EdgeRecombinationCrossover();

        ArrayList<Solution> crossoverResult;

        for (int x = 1; x <= 20000; x++)
        {
            matingPop = (Population)deepCopy(pop);
            torna.select(tsp, matingPop, 50);
            //System.out.println("1: " + pop.getParents().size() + " : " + matingPop.getParents().size());
            for (int i = 0; i < matingPop.getParents().size(); i++)
            {
                pop.addToParents(invert.mutate(matingPop.getParents().get(i)));
                for (int j = i+1; j < matingPop.getParents().size(); j++)
                {
                    crossoverResult = edge.crossover(swap.mutate(matingPop.getParents().get(i)), swap.mutate(matingPop.getParents().get(j)));
                    pop.addToParents(crossoverResult.get(0));
                    pop.addToParents(crossoverResult.get(1));
                }
            }
            //System.out.println("2: " + pop.getParents().size() + " : " + matingPop.getParents().size());
            torna.select(tsp, pop, 200);
            //System.out.println("3: " + pop.getParents().size() + " : " + matingPop.getParents().size());
            if (x == 2000)
            {
                System.out.println(x + ": " + pop.getBestScore());
            }
            else if (x == 5000)
            {
                System.out.println(x + ": " + pop.getBestScore());
            }
            else if (x == 10000)
            {
                System.out.println(x + ": " + pop.getBestScore());
            }
            else if (x == 20000)
            {
                System.out.println(x + ": " + pop.getBestScore());
            }
            System.out.println(x + ": " + pop.getBestScore());
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

    /**
    * Makes a deep copy of any Java object that is passed.
    */
    private static Object deepCopy(Object object) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
            outputStrm.writeObject(object);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return objInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
