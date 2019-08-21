import java.util.Comparator;

public class IndividualComparator implements Comparator<Individual>
{
    @Override
    public int compare(Individual i1, Individual i2)
    {
        //System.out.println(i1.getFitness() + " : " + i2.getFitness());
        if (i1.getFitness() > i2.getFitness())
        {
            return 1;
        }
        else if (i1.getFitness() < i2.getFitness())
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
