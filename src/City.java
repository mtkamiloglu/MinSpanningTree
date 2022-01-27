import java.util.HashMap;

public class City implements Comparable<City>{

	String name;
	int minDistance = Integer.MAX_VALUE;
	City previous;
	HashMap<City, Integer> adjacentCities;
	boolean isKnown;
	

	public City(String name) {
		this.name = name;
		adjacentCities = new HashMap<City, Integer>();
		minDistance = Integer.MAX_VALUE;
		this.previous = null;
		this.isKnown=false;
	}
	
	public String toString() { 
		return name; 
	}

	public int compareTo(City other) {
		return this.minDistance-other.minDistance;
	}
	
}
