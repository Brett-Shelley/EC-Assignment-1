import java.util.ArrayList;
import java.util.Random;

public class InverOver
{
	//NOTE this is just pseudo code right now
	public Population InverOver(Population pop, int while_cond, double prob){
		General shifter;
		RandomNumberGenerator rand = RandomNumberGenerator.getRandom();
		ArrayList<Individual> parents = pop.getParents();
		int num_parents = parents.size();

		//Our inverter mutator
		InvertMutation invertor;

		//Initialising these vars
		Individual individual_1;
		Individual individual;
		int city_1 = 0;
		int city = 0;
		int previous_city = 0;
		int next_city = 0;
		int new_city_index;

		int loop_times = 0;
		while(looptimes < while_cond){
			//each individual in the population
			for(int i = 0; i < num_parents; i++){
				individual_1 = parents.get(i);

				new_city_index = rand.nextInt(num_parents);
				city = individual_1.get(new_city_index);	//Get a new random city from our individual
				individual_1.remove(new_city_index);	//And I think we delete this city from the list?

				while(true){
					//Gets the next and previous cities
					previous_city = individual_1.get(shifter.shift(i, num_parents, false));
					next_city = individual_1.get(shifter.shift(i, num_parents, true));

					//Decide what to do
					if(rand.nextFloat() <= prob){
						city_1 = select one of the remaining cities
						from individual_1
					}
					else{
						individual_new = parents.get(rand.nextInt(num_parents));
						city_1 = next_city;	//In other
							//words just get the next city
							//from our selection/
							//individual_1
					}
					if(next_city == city_1 || previous_city == city_1){
						break;
					}
					// individual_1 = invertor.mutate(individual_1, city, city_1);	//Not sure how i'd select the inversion range
					city = city_1;
				}

				//Fitness evaluation
				if(fitness of individual_1 is <= individual){
					parents.get(i) = individual_1;
				}
			}
			//Can't remember if we only loop "while_cond" times or if we run until we have
				//seen the same result population "while_cond" times
			loop_times++;
		}

		return pop;
	}

}
