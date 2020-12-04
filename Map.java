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

		public boolean containsAttraction(Strng attraction_name)
		{
			if(attractions.contains(attraction_name))
			{
				return true;
			}
			return false;
		}
	}

	//Data members for Map
	String acsv = "/Users/yiqingkhoo/CS245/RoadTrip/attractions.csv";
	String rcsv = "/Users/yiqingkhoo/CS245/RoadTrip/roads.csv";
	BufferedReader br;	
	List<City> cities;
	List<ArrayList<Road>> roads;
	int numCities;
	int total_distance;
	int total_time;

	//Dijkstra Table members
	Hashtable<String, Boolean> visited; //"Known"
	Hashtable<String, Integer> cost; //"Cost"
	Hashtable<String, String> previous; //"Path"

	public Map()
	{
		cities = new ArrayList<City> ();
		roads = new ArrayList<Road> ();
		readRoad();
		readAttractions();		
	}

	//Reads roads.csv file
	private void readRoad()
	{
		br = new BufferedReader(new FileReader(rcsv));
		while((line = br.readLine()) != null) 
		{
			String[] info = line.split(",");
			addRoad(info[0],info[1],Integer.parseInt(info[2]),Integer.parseInt(info[3]));
		}
		br.close();
	}

	//Reads attractions.csv file
	private void readAttractions()
	{
		br = new BufferedReader(new FileReader(acsv));
		while((line = br.readLine()) != null)
		{
			String [] info = line.split(",");
			add_Attraction(info[0], info[1]);
		}	
		br.close();	
	}

	//Adds city if it does not exist already
	private void addCity(String name)
	{
		for(int i =0; i<cities.size(); i++)
		{
			if(cities.indexOf(name) < 0) //If name does not exist
			{
				City city = new City(name);
				cities.add(city);
				numCities++;
			}
		}
	}

	private void addRoad(String from, String to, int distance, int time)
	{
		//Add 
		addCity(from);
		roads.add(new ArrayList<Road>);

		addCity(to);
		roads.add(new ArrayList<Road>);

		//gets index of cities
		int from = cities.indexOf(from);
		int to = cities.indexOf(to);

		ArrayList<Road> road = edges.get(from);
		Edge edge_from = new Edge(from,to, distance, time);
		road.add(edge_from);

		road = edges.get(to);
		Edge edge_to = new Edge(to, from, distance, time);
		road.add(edge_to);
	}

	public void add_Attraction(String city, String attraction_name)
	{
		for(int i =0; i<cities.size(); i++)
		{
			if(cities.get(i).getName.equalsIgnoreCase(city))
			{
				cities.get(i).addAttraction(attraction_name);
			}
		}
	}

	public List<String> route(String starting_city, String ending_city, List<String> attractions)
	{
		List<String> plan = new ArrayList<String> ();
		List<String> cities_list = new ArrayList<String> ();
		for(int j=0; j<attractions.size(); j++)
		{
			for(int k =0; k<cities.size(); k++)
			{
				if(cities.get(k).containsAttraction(attractions.get(j)))
				{
					cities_list.add(cities.getName());
				}
			}
		}

		addRoad(starting_city,starting_city,0,0);

		for (int i =0; i<numCities; i++)
		{
			cost[i] = -1;
			path[i] = -1;
		}

		// Initial Dijkstra
		for(String city : cities)
		{
			while(!visited.get(city))
			{
				String vertex = least_cost_unknown_vertex();
				known(vertex) = true;
				for(String n : cities)
				{
					if(cost.get(n) > cost.get(vertex) + edge_dist(n,vertex) && !n.equals(vertex))
					{
						cost.put(n,cost.get(vertex)+edge_dist(n,vertex))
						previous.put(n,vertex);
					}
				}
			}
		}
		for(int i =0; i<roads.size();i++)
		{
			for (int j =0; j<roads.size();; j++)
			{
				if (roads.get(i).get(j) != null)
				{
					
				}
			}
		}

	}

	private String least_cost_unknown_vertex()
	{
		String vertex - "";
		int temp = Integer.MAX_VALUE;

		for (String city : cities)
		{
			if(!visited.get(city) && cost.get(city) <= temp)
			{
				temp = cost.get(city);
				vertex = city;
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

	private int edge_dist(String vertex_1, String vertex_2)
	{
		int distance;
		int index_1 = cities.get(vertex_1);
		int index_2 = cities.get(vertex_2);

		distance = roads.get(index_1).get(index_2).getDist();

		return distance;
	}

	private int edge_time(String from, String to)
	{
		int time;
		int index_1 = cities.get(vertex_1);
		int index_2 = cities.get(vertex_2);

		time = roads.get(index_1).get(index_2).getTime();

		return time;
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