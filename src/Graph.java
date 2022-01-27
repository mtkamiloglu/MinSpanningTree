import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
	HashMap<String, City> cities;
	City startCity;
	
	public Graph(City startCity, HashMap<String, City> cities) {
		this.startCity = startCity;
		this.cities = cities;
	}
	
	HashMap<Integer, String> shortestPath(City endCity) {
		
		HashMap<Integer, String> value = new HashMap<>();
		City source = this.startCity;
		source.minDistance=0;
		source.isKnown=true;

		ArrayList<City> citiesPQ = new ArrayList<City>();
		Set<City> visitedCities = new HashSet<>();

		for(City city: cities.values()) {
			citiesPQ.add(city);
		}

		while(visitedCities.size()!=cities.size()) {
			Collections.sort(citiesPQ, new CityComparator());
			City city1 = citiesPQ.get(0);
			visitedCities.add(city1);
			citiesPQ.remove(0);
			if(!city1.isKnown) {
				continue;
			}
			if(city1 == endCity) {
				continue;
			}
			for(City city2 : city1.adjacentCities.keySet()) {
				
				if(city1.minDistance+city1.adjacentCities.get(city2)<city2.minDistance) {
					city2.minDistance= city1.minDistance + city1.adjacentCities.get(city2);
					city2.isKnown=true;
					city2.previous = city1;
				}
			}
		}
		
		int totalTime=endCity.minDistance;
		ArrayList<String> path = new ArrayList<String>();
		City parent=endCity;
		String path1="";
		
		while(parent!=null) {
			path.add(parent.name);
			path1 = parent.name+" "+path1;
			parent = parent.previous;
		}
		
		Collections.reverse(path);
		
		if(!path.contains(source.name)) {
			totalTime = -1;
		}

		value.put(totalTime, path1);
		
		return value;

	}
	
	//Honeymoon Path
	int minimumSpanningTree() {

		for(City city :cities.values()) {
			city.minDistance=Integer.MAX_VALUE;
			city.previous=null;
		}

		ArrayList<City> visitedCities = new ArrayList<>();
		startCity.minDistance=0;
		startCity.isKnown=false;

		PriorityQueue<City> pqMST = new PriorityQueue<City>();
		pqMST.add(startCity);
		

		while(!pqMST.isEmpty()) {
			City city1 = pqMST.poll();
			if(!city1.isKnown) {
				city1.isKnown=true;
				visitedCities.add(city1);
			}
			
			for(City city2 : city1.adjacentCities.keySet()) {
				if(!city2.isKnown && city1.adjacentCities.get(city2) < city2.minDistance) {
					if(pqMST.contains(city2)) {
						pqMST.remove(city2);
						city2.minDistance=city1.adjacentCities.get(city2);
						city2.previous = city1;
						pqMST.add(city2);
					}
					else {
						city2.minDistance=city1.adjacentCities.get(city2);
						city2.previous = city1;
						pqMST.add(city2);
					}
				}
			}
		}

		int totalDistance = 0;

		for(City city :cities.values()) {
			totalDistance += city.minDistance;
		}
		
		if(cities.keySet().size()==visitedCities.size())
			return 2*totalDistance;
		else
			return -2;

	}	
}
