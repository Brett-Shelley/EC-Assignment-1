import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class EdgeRecombinationCrossover implements ITwoParentCrossover
{
    public ArrayList<Solution> crossover(Solution parentOne, Solution parentTwo)
    {
        int length = parentOne.size();
        
        ArrayList<Solution> children = new ArrayList<Solution>();
        children.add(crossoverhelper(parentOne, parentTwo));
        children.add(crossoverhelper(parentTwo, parentOne));

        return children;
    }

    //Direction is False for left and True for right
    private int shift(int index, int size, Boolean direction){
        if(direction == false){
            if(index == 0){
                return size - 1;
            }
            else{
                return index - 1;
            }
        }
        else{
            if(index == size - 1){
                return 0;
            }
            else{
                return index + 1;
            }
        }
    }

    private void addTableEntry(HashMap<Integer,Integer> map, int index, int key){
        int count = map.containsKey(key) ? map.get(key) : 0;
        map.put(key, count + 1);  //If wasn't present, this is set to 1, else its incremented

        return;
    }

    private void removeCityFromTable(ArrayList<HashMap<Integer,Integer>> table, int index){
        table.remove(index);
        for(int i = 0; i < table.size(); i++){
            if(table.get(i).containsKey(index)){
                table.get(i).remove(index);
            }
        }

        return;
    }

    private Solution crossoverhelper(Solution parentOne, Solution parentTwo)
    {
        ArrayList<Coords> child = new ArrayList<Coords>();
        int size = parentOne.size();
        int parent1_index = 0;  //Otherwise Java complains it may not be there
        int parent2_index = 0;

        //Create the neighbours table and populate it
        ArrayList<HashMap<Integer,Integer>> table = new ArrayList<HashMap<Integer,Integer>>();
        for(int i = 0; i < size; i++){
            HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();

            //Find the indexes of this element for both parents
                //Use parent 1 as source
            parent1_index = i;
            for(int j = 0; j < size; j++){
                if(parentOne.get(parent1_index) == parentTwo.get(j)){
                    parent2_index = j;
                    break;
                }
            }

            //Add the 4 neighbours (If we get a repeated number, that means both parents share a neighbour)
            addTableEntry(temp, i, shift(parent1_index, size, false));
            addTableEntry(temp, i, shift(parent1_index, size, true));
            addTableEntry(temp, i, shift(parent2_index, size, false));
            addTableEntry(temp, i, shift(parent2_index, size, true));

            table.add(temp);
        }

        Random rand;
        int cities_left = size;
        int city;
        for(int i = 0; i < size; i++){
            //Select a city to chose

            //Priority list
                // First time, choose random
                // Shortest list
                // Common edge (Both parents have it as a neighbour)
                // Only one option (Only 1 edge to choose from)
                // Failsafe (Final element, no clear leader, etc)
            if(i == 0){
                rand = RandomNumberGenerator.getRandom();
                city = rand.nextInt(cities_left);
            }
            // else if(){
            //     ;
            // }
            // else if(){
            //     ;
            // }
            // else if(){
            //     ;
            // }
            else{   //Failsafe
                rand = RandomNumberGenerator.getRandom();
                city = rand.nextInt(cities_left);
            }

            //The add that city to our list and delete its stuff from the table
            // child.set(i, city);
            child.add(parentOne.get(city));
            removeCityFromTable(table, city);
            cities_left--;

        }

        Solution result = new Solution(child);
        
        return result;

    }
}