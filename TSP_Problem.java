import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TSP_Problem
{
    private String name;
    private String comment;
    private String type;
    private int dimension;
    private String edge_weight_type;
    private ArrayList<Coords> points;

    public TSP_Problem(String file_path)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            String line = br.readLine();
            String[] tokens;
            boolean read_coords = false;
            points = new ArrayList<Coords>();
            while (line != null)
            {
                if (!read_coords)
                {
                    tokens = line.split(" : ", 2);
                    switch(tokens[0])
                    {
                        case "NAME":
                            name = tokens[1];
                            break;
                        case "COMMENT":
                            comment = tokens[1];
                            break;
                        case "TYPE":
                            type = tokens[1];
                            break;
                        case "DIMENSION":
                            dimension = Integer.parseInt(tokens[1]);
                            break;
                        case "EDGE_WEIGHT_TYPE":
                            edge_weight_type = tokens[1];
                            break;
                        case "NODE_COORD_SECTION":
                            read_coords = true;
                            break;
                        default:
                            break;
                    }
                }
                else
                {
                    tokens = line.split(" ", 3);
                    if (tokens[0].equals("EOF")) {}
                    else
                    {
                        points.add(new Coords(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2])));
                    }
                }
                line = br.readLine();
            }
            br.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    // Gets the dimension of the TSP.
    public int getDimension()
    {
        return dimension;
    }

    // Gets the array of all Coords.
    public ArrayList<Coords> getCoords()
    {
        return points;
    }

    // Gets the distance between Coords i and j.
    public double getDistance(int i, int j)
    {
        return Math.sqrt(Math.pow((points.get(i).getX() - points.get(j).getX()), 2) + Math.pow((points.get(i).getY() - points.get(j).getY()), 2));
    }

    // Gets the total distance for a specified solution.
    public double getTotalDistance(ArrayList<Integer> solution)
    {
        double total = 0;
        for (int i = 0; i < solution.size(); i++)
        {
            if (i+1 == solution.size())
            {
                total += getDistance(solution.get(i), 0);
            }
            else
            {
                total += getDistance(solution.get(i), solution.get(i+1));
            }
        }
        return total;
    }
    
    // Generates a random permutation based on the TSP.
    public ArrayList<Integer> initPermutation()
    {
        ArrayList<Integer> permutation = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < getDimension(); i++)
        {
            permutation.add(i);
        }
        Collections.shuffle(permutation, rand);
        return permutation;
    }
}
