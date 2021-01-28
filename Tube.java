/**
 * The launch tube class needed for my roman candle program.
 * As of right now, the only thing it needs is the angle from the vertical (always stored in radians).
 * The user sees positive angles as measured from the right of the vertical; negative, from the left.
 * @author Michael Archer
 *
 */


public class Tube {
	private double angle;
	
	/**
	 * This is the default constructor. If no argument is supplied to the constructor, the tube will be set sticking straight up.
	 * Note that the angle is not set to zero. The reason is that though the user will be taking the vertical as the principal angle, I take the horizontal as the principal angle.
	 * Thus, all user-supplied angles need to be converted to reflect their relation to the horizontal.
	 */
	
	public Tube () throws IllegalTubeAngleException
	{
	changeAngle (0);
	}
	
	/**
	 * Overloaded constructor which accepts as an argument the initial angle of the tube (referenced from the vertical).
	 * @param the angle the tube will be initialized to (take the vertical as the principal angle).
	 */
	
	public Tube (double angle) throws IllegalTubeAngleException
	{
		changeAngle (angle);
	}
	
	/**
	 * Acessor for the Tube class. Returns the only parameter an instance of Tube has i.e., the angle from the vertical. Returns in radians.
	 * @return the angle of the tube 
	 */
	
	public double getAngle ()
	{
		return this.angle;
	}
	
	/**
	 * This is the mutator for the only parameter an instance of Tube has, i.e., the angle. The argument takes the vertcal as its reference angle.
	 * @param the new angle of the tube
	 * @throws Exception if the supplied argument has an illegal value, i.e., if it's not between -15 and 15 inclusive.
	 */
	
	public void changeAngle (double angle) throws IllegalTubeAngleException
	{
		if (angle<-15 || angle>15)
		{
			IllegalTubeAngleException e = new IllegalTubeAngleException ();
			throw e;
		}
		else
	this.angle=Math.toRadians(90-angle); //angle from the ground; needs to be in radians
	}
	
	/**
	 * 
	 * @param a wind object, which carries the wind speed the instance of star needs to know
	 * @return
	 */
	
	public Star shootStar (Environment wind)
	{
		Star star = new Star (this.clone(), wind);
		return star;
	}
	
	public Tube clone () throws RuntimeException //since clone overrides Object's clone method, it wouldn't let me throw IllegalTubeAngleException. I had to settle with RuntimeException
	{
		try{
		Tube t = new Tube ();
		t.angle = this.angle;
		return t;
		}
		catch (IllegalTubeAngleException itae)
		{
			RuntimeException rte = new RuntimeException();
			throw rte;
		}
	}
	
}
