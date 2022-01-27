import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class project3main {

	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));

		int timeLimit = in.nextInt();
		int numOfCities = in.nextInt();
		HashMap<String, City> leftSideCities = new HashMap<String, City>();
		HashMap<String, City> rightSideCities = new HashMap<String, City>();
		String mecnunCityName = in.next();
		String leylaCityName = in.next();
		String emptyLine = in.nextLine();
		int numOfLeftCities=Integer.parseInt(leylaCityName.substring(1, leylaCityName.length()));
		int numOfRightCities=numOfCities-numOfLeftCities;
		int leftCityCounter=1;
		int rightCityCounter=1;

		//Creating left cities
		for(int i=0; i<numOfLeftCities;i++) {
			City city= new City("c"+leftCityCounter);
			leftSideCities.put(city.name, city);
			leftCityCounter++;
		}
		//Creating right cities
		for(int i=0; i<numOfRightCities;i++) {
			City city= new City("d"+rightCityCounter);
			rightSideCities.put(city.name, city);
			rightCityCounter++;
		}

		City mecnunCity = leftSideCities.get(mecnunCityName);
		City leylaCity = leftSideCities.get(leylaCityName);
		
		//Specializing Left Cities
		for(int i=0; i<numOfLeftCities; i++) {
			String data = in.nextLine();
			String[] datas = data.split(" ");

			String cityName = datas[0];
			City city = leftSideCities.get(cityName);
			
			for(int m=1; m<datas.length;m+=2) {
				String neighborCity = datas[m];
				
				if(cityName.equals(leylaCityName)) {
					City nCity = rightSideCities.get(neighborCity);
					int distance = Integer.parseInt(datas[m+1]);
					city.adjacentCities.put(nCity, distance);
					nCity.adjacentCities.put(city, distance);
					continue;
				}
			
				City nCity = leftSideCities.get(neighborCity);
				int distance = Integer.parseInt(datas[m+1]);
				city.adjacentCities.put(nCity, distance);
			}
		}	

		Graph leftSide = new Graph(mecnunCity, leftSideCities);
		HashMap<Integer, String>  value = leftSide.shortestPath(leylaCity);
		int arriveTime = -1; String path="";
		
		for(Integer totTime : value.keySet()) {
			arriveTime=totTime;
			path=value.get(totTime);
		}
		
		
		rightSideCities.put(leylaCityName, leylaCity);
	
		//Specializing Right Cities
		for(int i=0; i<numOfRightCities; i++) {
			String data = in.nextLine();
			String[] datas = data.split(" ");

			String cityName = datas[0];
			City city = rightSideCities.get(cityName);

			//Creating two-way roads
			for(int m=1; m<datas.length;m+=2) {
				String neighborCity = datas[m];
				City nCity = rightSideCities.get(neighborCity);
				int distance = Integer.parseInt(datas[m+1]);
				if(city.adjacentCities.containsKey(nCity) && distance<city.adjacentCities.get(nCity)) {
					city.adjacentCities.replace(nCity, distance);
					nCity.adjacentCities.replace(city, distance);
				}
				else if(city.adjacentCities.containsKey(nCity) && distance>city.adjacentCities.get(nCity)) {
					continue;
				}
				else if(!city.adjacentCities.containsKey(nCity)) {
					city.adjacentCities.put(nCity, distance);
					nCity.adjacentCities.put(city, distance);
				}
			}
		}

		if(arriveTime==-1) {
			out.println(-1);
			out.println(-1);
		}
		else if(arriveTime>timeLimit) {
			out.println(path);
			out.println(-1);
		}
		else if(arriveTime<=timeLimit) {
			out.println(path);
			Graph rightSide = new Graph(leylaCity, rightSideCities);
			out.print(rightSide.minimumSpanningTree());
		}
	}
}
