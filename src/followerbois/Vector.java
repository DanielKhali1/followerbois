package followerbois;

public class Vector 
{
	public double x, y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector v)
	{
		x += v.x;
		y += v.y;
	}

	
	public String toString()
	{
		return "X: " + x + " Y: " + y;
	}
	
    public Vector clone () {
        return new Vector(x, y);
    }
    
    public Vector Avg(Vector v)
    {
    	return new Vector( (x + v.x)/2, (y + v.y)/2 );
    }
}
