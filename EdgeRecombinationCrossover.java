import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class EdgeRecombinationCrossover implements ITwoParentCrossover
{
    public ArrayList<Solution> crossover(Solution parentOne, Solution parentTwo)
    {
        int length = parentOne.size();
        
        ArrayList<Solution> children = new ArrayList<Solution>();
        children.add(crossoverHelper(parentOne, parentTwo));
        children.add(crossoverHelper(parentTwo, parentOne));

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

    private ArrayList<Integer> getCityEdges(ArrayList<HashMap<Integer,Integer>> table, int index){
        System.out.println(index);
        ArrayList<Integer> edges = new ArrayList<Integer>();
        edges.addAll(table.get(index).keySet());
        return edges;
    }

    //Generates a sub-list of all the paths that match this requirement from the pool
        //The pool should be the HashMap of edges for current head
    private ArrayList<Integer> shortestLists(ArrayList<HashMap<Integer,Integer>> table, ArrayList<Integer> pool){
        ArrayList<Integer> subPool = new ArrayList<Integer>();
        int shortestEdgesElement = 0;
        for(int i = 0; i < pool.size(); i++){
            if(table.get(pool.get(i)).size() < shortestEdgesElement){
                shortestEdgesElement = table.get(pool.get(i)).size();
            }
        }

        for(int i = 0; i < pool.size(); i++){
            if(table.get(pool.get(i)).size() == shortestEdgesElement){
                subPool.add(pool.get(i));
            }
        }
        return subPool;
    }

    //Generates a sub-list of all the paths that match this requirement from the pool
        //The pool should be the HashMap of edges for current head
    private ArrayList<Integer> commonEdges(ArrayList<HashMap<Integer,Integer>> table, ArrayList<Integer> pool){
        ArrayList<Integer> subPool = new ArrayList<Integer>();

        //Find the higest amount of common edges across all available city's edges
        int most_common_edges = 0;
        for(int i = 0; i < pool.size(); i++){
            for(int j = 0; j <= 2; j++){
                if(table.get(pool.get(i)).containsValue(j) && most_common_edges < j){
                    most_common_edges = j;
                }
            }
        }

        //Construct the list
        for(int i = 0; i < pool.size(); i++){
            if(table.get(pool.get(i)).containsValue(most_common_edges)){
                subPool.add(pool.get(i));
            }
        }

        return subPool;
    }

    private Solution crossoverHelper(Solution parentOne, Solution parentTwo)
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
        int previous_city = 0;  //Java is so dumb, it thinks its possible this might not be initialised...
        HashMap<Integer, Integer> currentCityPaths = null;
        for(int i = 0; i < size; i++){
            //Select a city to chose

            //Priority list
                // First time, choose random
                // Common edge (Both parents have it as a neighbour)
                // Shortest list
                // Failsafe (Final element, no clear leader, etc)
            if(i == 0){
                rand = RandomNumberGenerator.getRandom();
                city = rand.nextInt(cities_left);
            }
            else{
                //We'll initially want a list of available cities
                //Then reduce it to a list of most common edges
                    // We can have up to 2 common edges, so we should favour those with more common edges
                //Then reduce that same list to only contain those with the shortest path
                //If the list is still not one, choose randomly from the list (The list shouldn't be able to be zero here)

                System.out.println(previous_city);
                ArrayList<Integer> edges = getCityEdges(table, previous_city);

                if(edges.size() <= 0){  //No edges left
                    rand = RandomNumberGenerator.getRandom();
                    city = rand.nextInt(cities_left);
                }
                else{
                    edges = commonEdges(table, edges);  //Getting a list of those with the most common edges
                    if(edges.size() == 1){
                        city = edges.get(0);
                    }
                    else{
                        edges = shortestLists(table, edges);
                        if(edges.size() == 1){
                            city = edges.get(0);
                        }
                        else{   //Still no clear winner
                            rand = RandomNumberGenerator.getRandom();
                            city = edges.get(rand.nextInt(edges.size()));
                        }
                    }
                }
            }
            //Get the edges for the city we just chose
            currentCityPaths = table.get(city);
            previous_city = city;

            //Add that city to our list and delete its stuff from the table
            child.add(parentOne.get(city));
            removeCityFromTable(table, city);
            cities_left--;

        }

        Solution result = new Solution(child);
        
        return result;

    }
}