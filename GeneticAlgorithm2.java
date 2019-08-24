import java.util.ArrayList;

public class GeneticAlgorithm2 implements IGenetic
{
    public Population GeneticAlgorithm(TSP_Problem tsp,Population pop,int populationSize){
        
        TournamentSelection tournament = new TournamentSelection(populationSize/4);
        ArrayList<Individual> res=new ArrayList<Individual>();
        InsertMutation insert = new InsertMutation();
        CycleCrossover cycle = new CycleCrossover();

        Population matingPop=new Population(tsp,populationSize);
        //Run for 20000 generations
        for(int x=1;x<=20000;x++){
            tournament.select(tsp, matingPop, populationSize);
            for(int i=0;i<populationSize;i++){
                //Add offspring to the original pool
                pop.addToParents(matingPop.getParents().get(i));
                for(int j=i+1;j<populationSize;j++){
                    //Crossover genes and then mutate the new children
                    res=cycle.crossover(matingPop.getParents().get(i), pop.getParents().get(j));
                    pop.addToParents(insert.mutate(res.get(0)));
                    pop.addToParents(insert.mutate(res.get(1)));
                }
            }
            //Select the fittest population for the next generation
           pop=tournament.select(tsp,pop,populationSize);
           //Print at each interval
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