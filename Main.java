/** 
 * Run the program from this class.
 * Make sure you have the Environment, Tube, RungeKutta, Star, 	IllegalWindSpeedException, IllegalTimeStepException, and IllegalTubeAngleException classes along with the ODESystem interface! The program won't work without them.
 * @author Michael Archer
 */

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		boolean testVariable = false;
		Environment environment = null; //the environment and tube pointers will never be null after the while and try-catch loops; I just put these to make the compiler stop complaining about calling shootStar later
		Tube tube = null;
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
		in.close();
		System.out.println ("");
		System.out.println("Flight Trajectory:");
		System.out.println ("");
		System.out.printf("\n%-10s %50s %50s", "Time (seconds)", "Horizontal Displacement (meters)", "Vertical Displacement (meters)");
		System.out.println ("");
		try
		{
		Manager.startManager(0.05, environment.clone(), tube.clone());
		}
		catch (IllegalTimeStepException e)
		{
			//do nothing if the time interval is bad; but because a valid time interval is supplied directly, the try code should never throw this exception
		}
		
	}
	
	/**
	 * The print data method takes three doubles and outputs them on one line, giving plenty of space between them.
	 * @param time is the first number to be displayed
	 * @param xDisplace is the second number
	 * @param yDisplace is the third number
	 */
		public static void printData (double time, double xDisplace, double yDisplace)
		{
			System.out.printf("%.3f %40.3f %56.3f", time, xDisplace, yDisplace);
			System.out.println("");
		}
		
		
	}
