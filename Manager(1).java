/** 
 * Run the program from this class.
 * Make sure you have the Environment, Tube, RungeKutta, Star, 	IllegalWindSpeedException, IllegalTimeStepException, and IllegalTubeAngleException classes along with the ODESystem interface! The program won't work without them.
 * @author Michael Archer
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {

	public static void main(String[] args) {
		boolean testVariable = false;
		Environment environment = null; //the environment and tube pointers will never be null after the while loops; I just put these to make the compiler stop complaining about calling shootStar later
		Tube tube = null;
		final double delta = 0.05;
		Scanner in  =  new Scanner (System.in);
		System.out.println ("What is the crosswind velocity in km/hr? (Do not exceed 20 km/hr in magnitude.)");
		while (testVariable == false)
		{
			testVariable = true; //the exit condition will hold as long as it passes both tests
			try
			{
				environment = new Environment (in.nextDouble());
			}
			catch (InputMismatchException e)
			{
				System.out.println ("Don't be silly! That's not a number. Try again.");
				testVariable = false; //to make sure the loop starts again
			}
			catch  (IllegalWindSpeedException e)
			{	
				System.out.println ("Wind is way too strong, man. Pick a weaker windspeed.");
				testVariable = false;
			}
			in.nextLine(); //to clear and try again
		}
		testVariable = false; //so that I can use it in the next while loop
		System.out.println ("What is the cannon's angle in degrees? (Do not exceed fifteen degrees in either direction.)");
		while (testVariable == false)
		{
			testVariable = true; //the exit condition will hold as long as it passes both tests
			try
			{
				tube = new Tube (in.nextDouble());
			}
			catch (InputMismatchException e)
			{
				System.out.println ("Ahem. I said DEGREES.");
				testVariable = false; //to make sure the loop starts again
			}
			catch  (IllegalTubeAngleException e)
			{	
				System.out.println ("Anarchy! No, seriously, pick an angle that won't kill people when the ball start shooting.");
				testVariable = false;
			}
			in.nextLine(); //to clear and try again
		}
		Star star = tube.shootStar(environment); //Away!
		System.out.println ("");
		System.out.println("Flight Trajectory:");
		System.out.println ("");
		System.out.printf("\n%-10s %50s %50s", "Time (seconds)", "Horizontal Displacement (meters)", "Vertical Displacement (meters)");
		System.out.println ("");

		for (int x = 0; x<54; x++) //A ball with a mass of 0.008 kg with a decay rate of 0.003 kg/s will live 8/3 seconds or 54 iterations of 0.05 seconds
		{
			print (star.getAge(), star.getXDisplace(), star.getYDisplace());
			try
			{
			if (delta<=0 || delta>0.05)
			{
				IllegalTimeStepException e = new IllegalTimeStepException();
				throw e;
			}
			star.estimateNewValues (delta);
			}
			catch (IllegalTimeStepException e)
			{
				break;
				//if there's an exception, stop the program
			}

		}
		in.close();
	}

	private static void print (double time, double xDisplace, double yDisplace)
	{
		System.out.printf("%.3f %40.3f %56.3f", time, xDisplace, yDisplace);
		System.out.println("");
	}
}