/**
 * This class contains methods to perform a Runge-Kutta estimation given a system size, current inputs, and function outputs, and initial conditions.
 * Runge-Kutta works only for ordinary differential equations (ODE); ergo, there is only one independent variable, arbitrarily called time.
 * Works both for systems of equations and just one system.
 * Note that Runge-Kutta does not actually solve ODE: given a set of values, it provides only an estimation at a short time later given inputs.
 * Calling the static method from another class will run the system a total of once and then return the estimate 0.05 later from the starting time provided; if you want more, you must call this iteratively
 * The solver takes two arguments: the system is the first (with its values), and the second is the independent variable (called time) associated with those values
 * You will not get the correct estimate if your system values are not the ones that correspond with your time values!
 * @author Michael Archer
 *
 */


public class RungeKutta {
	
	public static double[] solver (ODESystem system, double time, double step)
	{
		int systemSize=system.getSystemSize();
		double[] k1= new double [systemSize];
		double[] k2= new double [systemSize];
		double[] k3= new double [systemSize];
		double[] k4= new double [systemSize];
		double[] values = new double [systemSize];
		double[] initialConditions = new double [systemSize];
		initialConditions = system.getInitialConditions();
		k1=k1(system, time);
		k2=k2andk3(system, time, step, k1);
		k3=k2andk3(system, time, step, k2);
		k4=k4(system, time, step, k3);
		for (int i=0; i<systemSize; i++)
		{
			values[i]=initialConditions[i]+step*(k1[i]+2*k2[i]+2*k3[i]+k4[i])/6;
		}
	return values;
	}
	
	private static double[] k1 (ODESystem system, double time) //returns the outputs of the system given specified times; list will be sorted so that the first item will be the function's SECOND argument; the second, the THIRD argument, etc.
	{
		return (system.getFunction(time, system.getCurrentValues()));
	}

	
	private static double[] k2andk3 (ODESystem system, double time, double step, double[] k) //this method will work for both the k2 and k3 functions provided array k passed is full of k-1 values; i.e., if you're using this to find values for k2, you must supply an array of k1 values; and if you want k3 values, pass k2 values
	{
		double[] currentValues = new double [system.getSystemSize()];
		currentValues = system.getCurrentValues();
		for (int i = 0; i<system.getSystemSize(); i++)
		{
			currentValues[i] = currentValues[i]+step*k[i]/2; //replacing current values with their new Runge-Kutta values
		}
		return (system.getFunction(time+step/2, currentValues));
	}
	
	private static double[] k4 (ODESystem system, double time, double step, double[] k3)//this works only for k4; the passed array MUST contain your k3 values!
	{
		double[] currentValues = new double [system.getSystemSize()];
		currentValues = system.getCurrentValues();
		for (int i = 0; i<system.getSystemSize(); i++)
		{
			currentValues[i] = currentValues[i]+step*k3[i]; //replacing current values with their new values
		}
		return (system.getFunction(time+step, currentValues));
	}

}
