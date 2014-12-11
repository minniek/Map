package RTree;

import java.util.Comparator;

public class pqComparator implements Comparator<Double>
{
	public int compare(Double arg0, Double arg1) {
		if (arg1 > arg0) {
            return -1;
        }
        
		if ( arg1  < arg0 ) {
            return 1;
        }
        
		return 0;
	}
}
