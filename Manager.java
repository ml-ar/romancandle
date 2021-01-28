public class Manager {
	public static void startManager (double timeInterval, Environment environment, Tube tube) throws IllegalTimeStepException
	{
		Environment newEnvironment = environment.clone();
		Star star = tube.shootStar(environment.clone()); //Away!
		if (timeInterval<=0 || timeInterval>0.05)
			throw (new IllegalTimeStepException ());
		for (int x = 0; x<((int)(8/(3*timeInterval))+1); x++) //the star lasts for 8/3 seconds; divide by the time interval, cast as an int, then add one to figure out how many increments you need
		{
			Main.printData (star.getAge(), star.getXDisplace(), star.getYDisplace());
			star.estimateNewValues (timeInterval, newEnvironment);
		}
	}
}