import java.util.ArrayList;
import java.util.Random;

public class InverOver
{
	//NOTE this is just pseudo code right now
	public Population InverOver(Population pop, int while_cond, double prob){
		int loop_times = 0;
		General shifter;
		while(looptimes < while_cond){
			//each individual in the population
			for(int i = 0; i < pop.size(); i++){
				individual_1 = pop.get(i);
				city = rand(city_from_individual_1);
				while(true){
					//Calculate the next and previous
					//cities
					previous_city = individual_1.get(shifter.shift(i, pop.size(), false));
					next_city = individual_1.get(shifter.shift(i, pop.size(), true));

					//Decide what to do
					if(rand(0,1) <= prob){
						city_1 = select one of the remaining cities
						from individual_1
					}
					else{
						individual_new = pop.get(
							rand(0, pop.size()));
						city_1 = next_city;	//In other
							//words just get the next city
							//from our selection/
							//individual_1
					}
					if(next_city == city_1 ||
						previous_city == city_1){
						break;
					}
					mutate_invert(individual_1, city, city_1);
					city = city_1;
				}
				if(fitness of individual_1 is <= individual){
					pop.get(i) = individual_1;
				}
			}
			//Can't remember if we only loop "while_cond" times or if we run until we have
				//seen the same result population "while_cond" times
			loop_times++;
		}

		return pop;
	}

}
