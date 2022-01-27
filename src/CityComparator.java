import java.util.Comparator;

public class CityComparator implements Comparator<City>{

	@Override
	public int compare(City o1, City o2) {
		if(o1.minDistance <  o2.minDistance)
			return -1;
		else if(o1.minDistance==o2.minDistance)	
			return 0;
		else
			return 1;
	}
}
