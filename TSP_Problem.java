import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

    // Gets the rounded euclidean distance between Coords i and j.
    public double getDistance(int i, int j)
    {
        double x_diff = points.get(i).getX() - points.get(j).getX();
        double y_diff = points.get(i).getY() - points.get(j).getY();
        double x_pow = Math.pow(Math.abs(x_diff), 2);
        double y_pow = Math.pow(Math.abs(y_diff), 2);
        double root = Math.sqrt(x_pow + y_pow);
        return root;
    }

    // Gets the total euclidean distance for a specified solution.
    public double getTotalDistance(ArrayList<Integer> solution)
    {
        double total = 0;

        total += getDistance(0, solution.get(0));
        for (int i = 0; i < solution.size() - 1; i++)
        {
            total += getDistance(solution.get(i), solution.get(i+1));
        }
        total += getDistance(solution.get(solution.size() - 1), 0);
        return total;
    }
}
