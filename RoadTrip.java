public class RoadTrip
{
	public static void main(String[] args)
	{
		Map map = new Map();
		List<String> dest = new ArrayList() <String>;
		dest.add("The Liberty Bell");
		dest.add("Funland");
		dest.add("Cloud Gate");

		List<String> plan = map.route("San Francisco CA", "Orlando FL", dest);
		System.out.println("Route: " + plan);
		System.out.println("Time taken: " + map.getTime() + " mins.")
		System.out.println("Distance travelled: " + map.getDist() + " miles.");
	}

}