import java.util.ArrayList;

public class GeneticAlgorithm1 implements IGenetic
{
    public Population GeneticAlgorithm(TSP_Problem tsp,Population pop,int populationSize){
        
        FitnessProportionate fit = new FitnessProportionate();
        ArrayList<Individual> res=new ArrayList<Individual>();
        InvertMutation invert = new InvertMutation();
        OrderCrossover order = new OrderCrossover();
        Population matingPop=new Population(tsp,populationSize);
        
        for(int x=1;x<=20000;x++){
            fit.select(tsp, matingPop, populationSize);
            for(int i=0;i<populationSize;i++){
                pop.addToParents(matingPop.getParents().get(i));
                for(int j=i+1;j<populationSize;j++){
                    res=order.crossover(matingPop.getParents().get(i), pop.getParents().get(j));
                    pop.addToParents(invert.mutate(res.get(0)));
                    pop.addToParents(invert.mutate(res.get(1)));
                }
            }
           pop=fit.select(tsp,pop,populationSize);
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