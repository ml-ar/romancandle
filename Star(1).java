/**
 * 
 * This class describes the behavior of the 'star' object; or, a single roman candle ball.
 * The Tube class supplies a Tube object and an Environment object to shoot a ball into physical space
 * @author Michael Archer
 *
 */


public class Star implements ODESystem {
	private double mass;
	private double radius;
	private double crossSection;
	private	double xDisplace;
	private	double yDisplace;
	private	double xVelocity;
	private	double yVelocity;
	private	double xVelocityAir;
	private double gravitationalPull;
	private	double experiencedDensity;
	private double life; //how old the star is
	private	final double burnRate=0.003;
	private final int density=1900;

	 /**
	  * This is the default constructor for Star. The ball will be created but will be in a sort of metaphysical limbo.
	  */
	public Star ()
	{
		this.setNewConstants (0);
		xDisplace = 0;
		yDisplace = 0;
		xVelocity = 0;
		yVelocity = 0;
		xVelocityAir = 0;
	}
	
	/**
	 * This constructor is called by the Tube object to shoot a new ball.
	 * @param tube is a Tube object, carrying with it an angle, so that the star knows in what direction it will be shot.
	 * @param wind is an Environment object, carrying a density, a gravitational acceleration constant; and a wind velocity, so that the new Star object will be able to ascribe itself an initial velocity relative to the air.
	 */

	public Star (Tube tube, Environment wind)//takes argument in radians
	{
		this.setNewConstants (0);
		gravitationalPull = Environment.g;
		experiencedDensity = Environment.airDensity;
		xDisplace = 0;
		yDisplace = 0;
		xVelocity = 22*(Math.cos(tube.getAngle()));
		yVelocity = 22*(Math.sin(tube.getAngle()));
		xVelocityAir=xVelocity-wind.getWindVelocity();
	}

	/**
	 * 
	 * @param time is the time step the Runge-Kutta class will need to 
	 */
	public void estimateNewValues (double timeInterval, Environment e)
	{
		
		double[] array = new double [2];
		Environment newEnvironment = e.clone();
		array = RungeKutta.solver(this, life, timeInterval);
		setNewConstants (timeInterval+life);
		setXVelocityAir (newEnvironment);
		setXVelocity (array[0]);
		setYVelocity (array[1]);
		setDisplace (timeInterval);
		setXVelocityAir (newEnvironment);
	}
	
	public void estimateNewValues (double timeInterval)
	{
		
		double[] array = new double [2];
		array = RungeKutta.solver(this, life, timeInterval);
		setNewConstants (timeInterval+life);
		setXVelocity (array[0]);
		setYVelocity (array[1]);
		setDisplace (timeInterval);
		
	}
	
	public void setXVelocityAir (Environment environment)
	{
		xVelocityAir = xVelocity - environment.getWindVelocity();
	}
	

	private void setDisplace (double time)
	{
		xDisplace = xDisplace + time*xVelocity;
		yDisplace = yDisplace + time*yVelocity;
	}

	private void setNewConstants (double time) //given a moment since the star's inception, this mutator will calculate and apply the new mass, radius, and cross-section
	{
		mass=0.008-burnRate*time;
		radius = Math.cbrt(mass*3/(density*4*(Math.PI)));
		crossSection = (Math.PI)*radius*radius;
		life = time;
	}

	private void setXVelocity (double velocity)
	{
		xVelocity = velocity;
	}

	public double getXVelocity ()
	{
		return xVelocity;
	}

	public double getXDisplace ()
	{
		return xDisplace;
	}

	public double getYDisplace ()
	{
		return yDisplace;
	}

	public double getYVelocity ()
	{
		return yVelocity;
	}

	private void setYVelocity (double velocity)
	{
		yVelocity = velocity;
	}

	public double[] getCurrentValues()
	{
		double[] array = new double [2];
		array[0]=xVelocityAir;
		array[1]=yVelocity;
		return array;
	}

	public double[] getInitialConditions()
	{
		double[] conditions = new double [2];
		conditions[0] = xVelocity;
		conditions[1] = yVelocity;
		return conditions;
	}

	public int getSystemSize()
	{
		return 2;
	}

	public double[] getFunction (double time, double[] values)
	{	
		Star dummyStar;
		dummyStar = this.clone();
		dummyStar.setNewConstants (time);
		double [] array = new double [2];
		array[0] = fx(dummyStar, values[0], values[1]); //first array index of values is the x-component; second array index is the y
		array[1] = fy(dummyStar, values[0], values[1]);
		dummyStar=null;
		return array;
	}


	private static double fx (Star star, double x, double y)
	{
		double dragForce = star.experiencedDensity*(x*x+y*y)*star.crossSection*0.4/2;
		return (-dragForce*x/(star.mass*Math.sqrt(x*x+y*y)));
	}

	private static double fy (Star star, double xVelocityAir, double yVelocity)
	{
		double dragForce = star.experiencedDensity*(xVelocityAir*xVelocityAir+yVelocity*yVelocity)*star.crossSection*0.4/2;
		return (-(star.gravitationalPull)-dragForce*yVelocity/(star.mass*Math.sqrt(xVelocityAir*xVelocityAir+yVelocity*yVelocity)));

	}
	
	public double getAge ()
	{
		return life;
	}
	
	public Star clone ()
	{
		Star newStar = new Star ();
		newStar.setNewConstants(this.life);
		newStar.xDisplace=this.xDisplace;
		newStar.yDisplace=this.yDisplace;
		newStar.xVelocity=this.xVelocity;
		newStar.yVelocity=this.yVelocity;
		newStar.xVelocityAir=this.xVelocityAir;
		newStar.gravitationalPull=this.gravitationalPull;
		newStar.experiencedDensity=this.experiencedDensity;
		return newStar;
	}
	
}