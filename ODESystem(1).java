/**
 * Any system that wishes to use my RungeKutta class to estimates systems of ODE must implement this interface.
 * The "time" argument is the independent variable of the ODE system.
 * The array of values assumes the functions keep their order consistent: e.g., if you have two functions of input x, dy/dx and dc/dx, and d^2y/dx^2=(x, dy/dx, dc/dx), then d^2c/dx^2 MUST be (x, dy/dx, dc/dx). In other words, keep the order of your arguments consistent, and always have the independent variable first! 
 * @author Michael
 *
 */

public interface ODESystem {
		int getSystemSize();
		double[] getCurrentValues();
		double[] getFunction(double time, double[] values);
		double[] getInitialConditions();
}
