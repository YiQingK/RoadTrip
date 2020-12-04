import java.util.ArrayList;
import java.util.Hashtable;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
public class Map
{
	public class Road
	{
		int origin;
		int destination;
		int distance;
		int time;

		public Road(int from, int to, int miles, int mins)
		{
			origin = from;
			destination = to;
			distance = miles;
			time = mins;
		}

		public int getdest()
		{
			return origin;
		}

		public int getDist()
		{
			return distance;
		}

		public int getTime()
		{
			return time;
		}
	}

	public class City
	{
		String name;
		List<String> attractions;

		public City(String name)
		{
			this.name = name;
			attractions = new ArrayList<String> ();
		}

		public void addAttraction(String name)
		{
			attractions.add(name);
		}

		public String getName()
		{
			return name;
		}

		public boolean containsAttraction(String attraction_name)
		{
			if(attractions.contains(attraction_name))
			{
				return true;
			}
			return false;
		}
	}

	//Data members for Map
	String acsv = "/Users/yiqingkhoo/CS245/RoadTrip/attractions.csv"; //Please change to whre you have saved the csv file
	String rcsv = "/Users/yiqingkhoo/CS245/RoadTrip/roads.csv"; //Please change to whre you have saved the csv file
	BufferedReader br;	
	List<City> cities;
	List<ArrayList<Road>> roads;
	int numCities;
	int total_distance;
	int total_time;

	//Dijkstra Table members
	Hashtable<String, Boolean> visited = new Hashtable<>(); //"Known"
	Hashtable<String, Integer> cost = new Hashtable<>(); //"Cost"
	Hashtable<String, String> previous = new Hashtable<>(); //"Path"

	public Map()
	{
		cities = new ArrayList<City> ();
		roads = new ArrayList<ArrayList<Road>> ();
		readRoad();
		readAttractions();		
	}

	//Reads roads.csv file
	private void readRoad()
	{
		try
		{
		br = new BufferedReader(new FileReader(rcsv));
		String line;
		while((line = br.readLine()) != null) 
		{
			String[] info = line.split(",");
			addRoad(info[0],info[1],Integer.parseInt(info[2]),Integer.parseInt(info[3]));
		}
		br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Reads attractions.csv file
	private void readAttractions()
	{
		try
		{
		br = new BufferedReader(new FileReader(acsv));
		String line;
		while((line = br.readLine()) != null)
		{
			String [] info = line.split(",");
			add_Attraction(info[0], info[1]);
		}	
		br.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Adds city if it does not exist already
	private void addCity(String name)
	{
		if(numCities == 0)
		{
			City city = new City(name);
			cities.add(city);
			numCities++;	
		}
		else
		{
			for(int i =0; i<numCities; i++)
			{
				if(cities.indexOf(name) < 0) //If name does not exist
				{
					City city = new City(name);
					cities.add(city);
					numCities++;
					break;
				}
			}
		}
	}
	private int pos(String city)
	{
		for (int i =0; i< cities.size(); i++)
		{
			if(cities.get(i).getName().equalsIgnoreCase(city))
			{
				return i;
			}
		}
		return -1;
	}

	private void addRoad(String from, String to, int distance, int time)
	{
		//Add 
		addCity(from);
		roads.add(new ArrayList<Road>());
		addCity(to);
		roads.add(new ArrayList<Road>());

		//gets index of cities
		int index_from = pos(from);
		int index_to = pos(to);

		ArrayList<Road> road1 = new ArrayList<Road>();
		road1 = roads.get(index_from);
		Road road_from = new Road(index_from,index_to, distance, time);
		road1.add(road_from);

		
		ArrayList<Road> road2 = new ArrayList<Road>();
		road2 = roads.get(index_to);
		Road road_to = new Road(index_to, index_from, distance, time);
		road2.add(road_to);
	}

	public void add_Attraction(String city, String attraction_name)
	{
		for(int i =0; i<cities.size(); i++)
		{
			if(cities.get(i).getName().equalsIgnoreCase(city))
			{
				cities.get(i).addAttraction(attraction_name);
			}
		}
	}

	public List<String> route(String starting_city, String ending_city, List<String> attractions)
	{
		List<String> plan = new ArrayList<String> (); //Results to go here
		List<String> cities_list = new ArrayList<String> (); //Cities that contain the attraction, must pass through here
		for(int j=0; j<attractions.size(); j++)
		{
			for(int k =0; k<cities.size(); k++)
			{
				if(cities.get(k).containsAttraction(attractions.get(j)))
				{
					cities_list.add(cities.get(k).getName());
				}
			}
		}

		for (int i =0; i<numCities; i++)
		{
			if(cities.get(i).equals(starting_city)) //Intialises as first vertex 
			{
				cost.put(cities.get(i).getName(), 0);
				previous.put(cities.get(i).getName(), cities.get(i).getName());
			}
			else
			{
			cost.put(cities.get(i).getName(), -1);
			previous.put(cities.get(i).getName(), "");
			}
			visited.put(cities.get(i).getName(), false);
		}



		// Initial Dijkstra
		for(City city : cities)
		{
			while(!visited.get(city.getName()))
			{
				String vertex = least_cost_unknown_vertex();
				known(vertex);
				for(City n : cities)
				{
					if(cost.get(n.getName()) > cost.get(vertex) + edge_dist(n.getName(),vertex) && !n.equals(vertex))
					{
						cost.put(n.getName(),cost.get(vertex)+edge_dist(n.getName(),vertex));
						previous.put(vertex,n.getName());
					}
				}
			}
		}

		Stack<String> path = new Stack<String>();
		path = printpath(previous, ending_city, starting_city);
		for (int l =0; l<path.size(); l++)
		{
			plan.add(path.pop());
		}
	return plan;

	}

	private Stack<String> printpath(Hashtable<String, String> previous, String ending_city, String starting_city)
	{	//recursive
		//takes from previous and calls again
		//adds to stack
		Stack<String> path = new Stack<String>();
		if(previous.get(ending_city).equals(starting_city))
		{
			path.push(previous.get(ending_city));
			return path;
		}
		path = printpath(previous, previous.get(ending_city), starting_city);
		return path;

	}

	private String least_cost_unknown_vertex()
	{
		String vertex = "";
		int temp = Integer.MAX_VALUE;

		for (City city : cities)
		{
			if(!visited.get(city.getName()) && cost.get(city.getName()) <= temp)
			{
				temp = cost.get(city.getName());
				vertex = city.getName();
			}
		}
		return vertex;
	}

	private void known(String vertex)
	{
		if(vertex != null)
		{
			visited.put(vertex, true);
		}
	}

	private int edge_dist(String n, String vertex)
	{
		int distance;
		int index_1 = pos(vertex);
		int index_2 = pos(v2);
		int index_3 = road_pos(index_1,index_2);

		distance = roads.get(index_1).get(index_3).getDist();

		return distance;
	}

	private int road_pos(int index1, int index2)
	{
		for (int i=0; i<roads.get(index1).size();i++)
		{
			if(roads.get(index1).get(i).getdest() == index2)
			{
				return i;
			}
		}
		return -1;
	}

	public int getDist()
	{
		return total_distance;
	}

	public int getTime()
	{
		return total_time;
	}

}