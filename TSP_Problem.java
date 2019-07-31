import java.io.BufferedReader;
import java.io.FileReader;

public class TSP_Problem
{
    public class Coords
    {
        private double x;
        private double y;

        public Coords(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        public double getX()
        {
            return x;
        }

        public double getY()
        {
            return y;
        }
    }

    private String name;
    private String comment;
    private String type;
    private int dimension;
    private String edge_weight_type;
    private Coords[] points;


    public TSP_Problem(String file_path)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            String line = br.readLine();
            String[] tokens;
            boolean read_coords = false;
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
                            points = new Coords[dimension];
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
                        points[Integer.parseInt(tokens[0])-1] = new Coords(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                    }
                }
                line = br.readLine();
            }
            br.close();
            System.out.println("Name: " + name);
            System.out.println("Comment: " + comment);
            System.out.println("Type: " + type);
            System.out.println("Dimension: " + dimension);
            System.out.println("Edge Weight Type: " + edge_weight_type);
            for (int i = 0; i < dimension; i++)
            {
                System.out.println(i + " : " + points[i].getX() + ", " + points[i].getY());
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
