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

    //Add a new edge to a given city
    private void addTableEntry(HashMap<Integer,Integer> map, int key){
        int count = map.containsKey(key) ? map.get(key) : 0;
        map.put(key, count + 1);  //If wasn't present, this is set to 1, else its incremented

        return;
    }

    //Note we don't remove the city's edges from the table, only the occurances of that city from other cities
    private void removeCityFromTableEdges(ArrayList<HashMap<Integer,Integer>> table, int index){
        for(int i = 0; i < table.size(); i++){
            if(table.get(i).containsKey(index)){
                table.get(i).remove(index);
            }
        }

        return;
    }

    //Get a list of edges for a given city. Note if there was a shared neighbour, that info is lost here
    private ArrayList<Integer> getCityEdges(HashMap<Integer,Integer> table){
        ArrayList<Integer> edges = new ArrayList<Integer>();
        edges.addAll(table.keySet());
        return edges;
    }

    //The pool should be the list of edges we can choose from and we return the sublist which has the least edges
        //Eg. pool = [2, 3, 7]
        //table.get(2) = [9 1]
        //table.get(3) = [0 2 1]
        //table.get(7) = [1 5]
        //subPool = [2 7]
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

        //This should never trigger, but just incase its here
        if(subPool.size() <= 0){
            return pool;
        }

        return subPool;
    }

    //The pool should be the list of edges we can choose from and we return the sublist which has the most common edges
        //Eg. pool = [2, 3, 7]
        //table.get(2) = [9 1]
        //table.get(3) = [1 2 1]    //1 is a common edge
        //table.get(7) = [1 5 3 5]  //5 is a common edge
        //subPool = [3 7]
    //Do note if a city has 2 common edges (eg. table.get(n) = [1 5 5 1]) then that city is given higher priority over
        //single common edge cities
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

        //If all options have no edges left, return the original list
        if(subPool.size() <= 0){
            return pool;
        }

        return subPool;
    }

    private Solution crossoverHelper(Solution parentOne, Solution parentTwo){
        ArrayList<Coords> child = new ArrayList<Coords>();
        int size = parentOne.size();
        int parent1_index;
        int parent2_index = 0;  //Zero because otherwise Java complains it may not be set

        //Create the neighbours table and populate it
        ArrayList<HashMap<Integer,Integer>> table = new ArrayList<HashMap<Integer,Integer>>();
        ArrayList<Integer> remainingCities = new ArrayList<Integer>();
        for(int i = 0; i < size; i++){
            HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();

            //Find the indexes of this element for both parents
                //Use parent 1 as source
            parent1_index = i;

            //Add the neighbours from parent1
            addTableEntry(temp, shift(parent1_index, size, false));
            addTableEntry(temp, shift(parent1_index, size, true));

            for(int j = 0; j < size; j++){
                if(parentOne.get(parent1_index) == parentTwo.get(j)){
                    parent2_index = j;
                    //Convert parent2_index to the parent1 list
                        //get solution N from parentTwo and find the same solution from parentOne and get the index
                    int left = shift(parent2_index, size, false);
                    int right = shift(parent2_index, size, true);
                    for(int k = 0; k < size; k++){
                        //Add the neighbours from parent2
                        if(parentTwo.get(left) == parentOne.get(k) || parentTwo.get(right) == parentOne.get(k)){
                            addTableEntry(temp, k);
                        }
                    }
                    break;
                }
            }

            table.add(temp);
            remainingCities.add(i);

            System.out.println("Parent indexes: " + parent1_index + ", " + parent2_index);
        }

        System.out.println("\nContent of table");
        for(int i = 0; i < size; i++){
            System.out.println(i + ": " + table.get(i).keySet());
        }
        System.out.println("");

        Random rand;
        int city_id;    //This is the "city_id" th element in the parentOne list
        HashMap<Integer, Integer> previousCityPaths = null;
        for(int i = 0; i < size; i++){
            System.out.println("i: " + i);
            //Select a city to chose

            //Priority list
                // First time, choose random
                // Common edge (Both parents have city as a neighbour)
                // Shortest list
                // Failsafe (no clear leader, etc)
            if(i == 0){    //For first element
                rand = RandomNumberGenerator.getRandom();
                city_id = remainingCities.get(rand.nextInt(remainingCities.size()));    //Choose a random city we haven't chosen yet
                System.out.println("Initial or last city is: " + city_id); //Note this is zero indexed
            }
            else{   //Note, the first time we come in here, we have 1 less than the initial number of cities to choose from
                    //So for 51 cities, we will always start this block with 50 cities to choose from

                ArrayList<Integer> edges = getCityEdges(previousCityPaths);

                if(edges.size() <= 0){  //No edges left
                    System.out.println("No edges left, choosing random from remaining elements");
                    rand = RandomNumberGenerator.getRandom();
                    city_id = remainingCities.get(rand.nextInt(remainingCities.size()));
                }
                else if(edges.size() == 1){ //One option so we choose it
                    System.out.println("Only 1 edge available");
                    city_id = edges.get(0);
                }
                else{
                    edges = commonEdges(table, edges);  //Getting a list of those with the most common edges
                    if(edges.size() == 1){
                        System.out.println("found most common edges");
                        city_id = edges.get(0);
                    }
                    else{
                        edges = shortestLists(table, edges);
                        if(edges.size() == 1){
                            System.out.println("found Shortest list");
                            city_id = edges.get(0);
                        }
                        else{   //Still no clear winner
                            System.out.println("no clear winner");
                            rand = RandomNumberGenerator.getRandom();
                            city_id = edges.get(rand.nextInt(edges.size()));
                        }
                    }
                }
            }
            System.out.println("City chosen was: " + city_id);

            //Get the edges for the city we just chose
            previousCityPaths = table.get(city_id);
            child.add(parentOne.get(city_id));
            removeCityFromTableEdges(table, city_id);
            remainingCities.remove(remainingCities.indexOf(city_id));   //Since this is always from 0 to n-1 this should work fine

        }

        System.out.println("\n------------------------\n");

        Solution result = new Solution(child);
        
        return result;

    }
}