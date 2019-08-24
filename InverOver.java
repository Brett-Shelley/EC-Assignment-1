import java.util.ArrayList;
import java.util.Random;

public class InverOver
{
	private int takeCity(Individual individual, RandomNumberGenerator rand){
		int new_city_index = rand.nextInt(individual.size());
		int city = individual.get(new_city_index);	//Get a new random city from our individual
		individual.remove(new_city_index);	//And I think we delete this city from the list?
		return city;
	}

	//NOTE this is just pseudo code right now
		//Its recommended that `while_cond` is 10 and prob is `0.02`
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
		// int new_city_index;

		int loop_times = 0;	//When best individual is unchanged for "while_cond" loops
		while(looptimes < while_cond){
			//each individual in the population
			for(int i = 0; i < num_parents; i++){
				individual_1 = parents.get(i);

				city = takeCity(individual_1, rand);

				// new_city_index = rand.nextInt(individual_1.size());
				// city = individual_1.get(new_city_index);	//Get a new random city from our individual
				// individual_1.remove(new_city_index);	//And I think we delete this city from the list?

				while(true){

					//Decide what to do (Note nextFloat returns a new float between 0 and 1)
					if(rand.nextFloat() <= prob){
						//Select the city c' from the remaining cities in I'

						//Which city? Any? If not what if c' isn't in I'?
						city_1 = individual_1.get(rand.nextInt(individual_1.size()));	//Get one of the remaining cities
					}
					else{
						//Select (randomly) an individual from P
							//Assign to c' the "next" city to the city c in the selected individual
						individual_new = parents.get(rand.nextInt(num_parents));
						next_city = individual_1.get(shifter.shift(individual_1.indexOf(city), individual_1.size(), true));
						city_1 = next_city;	//In other words just get the next city from our individual_1
					}

					//Get the next and previous cities
					previous_city = individual_1.get(shifter.shift(individual_1.indexOf(city), individual_1.size(), false));
					next_city = individual_1.get(shifter.shift(individual_1.indexOf(city), individual_1.size(), true));
					if(next_city == city_1 || previous_city == city_1){
						//if the next or previous cities of city c in I' is C'
						break;
					}
					individual_1 = invertor.mutateHelper(individual_1, individual_1.indexOf(city), individual_1.indexOf(city_1));
					city = city_1;
				}

				//Fitness evaluation
				if(fitness of individual_1 is <= individual){
					parents.get(i) = individual_1;
					loop_times++;
				}
				else{
					loop_times = 0;
				}

				//Leaving the loops
				if(looptimes >= while_cond){
					break;
				}
			}
		}

		return pop;
	}

}
