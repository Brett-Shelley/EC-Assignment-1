import java.util.ArrayList;

public class GeneticAlgorithm3 implements IGenetic
{
    public Population GeneticAlgorithm(TSP_Problem tsp,Population pop,int populationSize){
        
        ElitismSelection elite = new ElitismSelection();
        ArrayList<Individual> res=new ArrayList<Individual>();
        SwapMutation swap = new SwapMutation();
        PmxCrossover pmx = new PmxCrossover();
        Population matingPop=new Population(tsp,populationSize);
        //Run for 20000 generations
        for(int x=1;x<=20000;x++){
            elite.select(tsp, matingPop, populationSize);
            for(int i=0;i<populationSize;i++){
                //Add offspring to the original pool
                pop.addToParents(matingPop.getParents().get(i));
                for(int j=i+1;j<populationSize;j++){
                    //Crossover genes and then mutate the new children
                    res=pmx.crossover(matingPop.getParents().get(i), pop.getParents().get(j));
                    pop.addToParents(swap.mutate(res.get(0)));
                    pop.addToParents(swap.mutate(res.get(1)));
                }
            }
            //Select the fittest population for the next generation
           pop=elite.select(tsp,pop,populationSize);
           //Print each generation
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
        return pop;
    }
}