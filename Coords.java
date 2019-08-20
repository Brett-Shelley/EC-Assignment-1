import java.io.Serializable;

public class Coords implements Serializable
{
    private static final long serialVersionUID = 1L;
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
