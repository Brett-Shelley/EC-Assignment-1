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

    private ArrayList<Integer> getCityEdges(HashMap<Integer,Integer> table){
        ArrayList<Integer> edges = new ArrayList<Integer>();
        edges.addAll(table.keySet());
        return edges;
    }

    //Generates a sub-list of all the paths that match this requirement from the pool
        //The pool should be the HashMap of edges for current head
    private ArrayList<Integer> shortestLists(ArrayList<HashMap<Integer,Integer>> table, ArrayList<Integer> pool){
        ArrayList<Integer> subPool = new ArrayList<Integer>();
        System.out.println("shortestLists: " + table.size() + ", " + pool.size());
        int shortestEdgesElement = -1;
        for(int i = 0; i < pool.size(); i++){
            if(shortestEdgesElement < 0 || table.get(pool.get(i)).size() < shortestEdgesElement){
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
        System.out.println("commonEdges: " + table.size() + ", " + pool.size() + ", " + pool.get(pool.size() - 1));

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
        ArrayList<Integer> city_ids = new ArrayList<Integer>();
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
            city_ids.add(i);
        }

        //NOTE THE "city_ids" ARRAY KEEPS TRACK OF THE ORIGINAL ORDER
            //"city" refers to the index of remaining cities available
            //So if we have {2, 4 and 8} city can range between 0 and 2

            //If i == 0 we choose a random city from the whole lot

        ArrayList<Integer> remainingCities = new ArrayList<Integer>(city_ids);
        Random rand;
        int city;   //The city we will add and choose to delete
                    //Need a seperate var to keep track of the original values
        HashMap<Integer, Integer> previousCityPaths = null;
        for(int i = 0; i < size; i++){
            //Select a city to chose

            //Priority list
                // First time, choose random
                // Common edge (Both parents have it as a neighbour)
                // Shortest list
                // Failsafe (Final element, no clear leader, etc)
            if(i == 0){
                rand = RandomNumberGenerator.getRandom();
                city = rand.nextInt(city_ids.size());
                System.out.println("Initial city is: " + city); //Note this is zero indexed
            }
            // else if(i == size - 1){ //Last element
            //     ;
            // }
            else{   //Note, the first time we come in here, we have 1 less than the initial number of cities to choose from
                    //So for 51 cities, we will always start with 50 cities to choose from

                //We'll initially want a list of available cities
                //Then reduce it to a list of most common edges
                    // We can have up to 2 common edges, so we should favour those with more common edges
                //Then reduce that same list to only contain those with the shortest path
                //If the list is still not one, choose randomly from the list (The list shouldn't be able to be zero here)

                ArrayList<Integer> edges = getCityEdges(previousCityPaths);
                // System.out.println(edges);
                // return null;

                if(edges.size() <= 0){  //No edges left
                    rand = RandomNumberGenerator.getRandom();
                    city = rand.nextInt(city_ids.size());
                }
                else{
                    System.out.println("A: " + edges.size());
                    edges = commonEdges(table, edges);  //Getting a list of those with the most common edges
                    System.out.println("B: " + edges.size());
                    if(edges.size() == 1){
                        System.out.println("found most common edges");
                        city = edges.get(0);
                    }
                    else{
                        edges = shortestLists(table, edges);
                        System.out.println("C: " + edges.size() + " i: " + i);
                        if(edges.size() == 1){
                            System.out.println("found Shortest list");
                            city = edges.get(0);
                        }
                        else{   //Still no clear winner
                            System.out.println("no clear winner");
                            rand = RandomNumberGenerator.getRandom();
                            city = edges.get(rand.nextInt(edges.size()));
                        }
                    }
                    // System.out.println("Re-setting city: " + city + ", " + edges.size());
                    // city = edges.get(city);
                }
            }
            //Get the edges for the city we just chose
            previousCityPaths = table.get(city);

            //Add that city to our list and delete its stuff from the table
            child.add(parentOne.get(city_ids.get(city)));
            removeCityFromTable(table, city);
            city_ids.remove(city);

        }

        // System.out.println(city_ids);

        Solution result = new Solution(child);
        
        return result;

    }
}