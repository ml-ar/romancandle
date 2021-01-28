public class Environment
/**
 * The environment class. Its constants simulate shooting a ball on earth.
 * @author Michael Archer
 */

{
	private double windVelocity;
	static final double g = 9.807;
	static final double airDensity = 1.2;
	
	public Environment () throws IllegalWindSpeedException
	{
		changeWindSpeed (0);
	}
	
	public Environment (double windSpeed) throws IllegalWindSpeedException
	{
		changeWindSpeed (windSpeed);
	}
	
	public double getWindVelocity ()
	{
		return windVelocity;
	}
	
	public void changeWindSpeed (double windSpeed) throws IllegalWindSpeedException
	{
		if (windSpeed>20 || windSpeed<-20)
		{
			IllegalWindSpeedException e = new IllegalWindSpeedException ();
		 throw e;
		}
		else
			this.windVelocity = windSpeed*1000/60/60;;
	}
	
	public Environment clone () throws RuntimeException //since clone overrides Object's clone method, it wouldn't let me throw IllegalWindSpeedException. I had to settle with RuntimeException
	{
		try
		{
		Environment e = new Environment ();
		e.windVelocity = this.windVelocity;
		return e;
		}
		catch (IllegalWindSpeedException iwse)
		{
			RuntimeException rte = new RuntimeException ();
			throw rte;
			//do nothing since you can't call this method without a valid implicit Environment 
		}
				
	}
}
	