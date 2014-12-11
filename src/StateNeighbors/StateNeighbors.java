package StateNeighbors;

/*
 * References:
 * http://www.native-languages.org/state_map.jpg
 * http://www.skilledmonster.com/2013/10/21/hashmap-single-key-multiple-values-example/
 */

import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class StateNeighbors {
	
	public static Multimap<String, String> stateNeighbors;
	
	public StateNeighbors(){
		stateNeighbors = ArrayListMultimap.create();
		loadStateNeighbors();
	}
	
	public static void main(String[] args) {
		loadStateNeighbors();
	}
	
	private static void loadStateNeighbors(){
		
		stateNeighbors = ArrayListMultimap.create();
		
		// Add all states and their neighbors to multimap "stateNeighbors"
		// AK Alaska...no neighbors :*(
		stateNeighbors.put("AK", null);
		
		// AL Alabama
		stateNeighbors.put("AL", "FL");
		stateNeighbors.put("AL", "GA");
		stateNeighbors.put("AL", "MS");
		stateNeighbors.put("AL", "TN");
		
		// AR Arkansas
		stateNeighbors.put("AR", "LA");
		stateNeighbors.put("AR", "MO");
		stateNeighbors.put("AR", "MS");
		stateNeighbors.put("AR", "OK");
		stateNeighbors.put("AR", "TN");
		stateNeighbors.put("AR", "TX");
	
		// AZ Arizona
		stateNeighbors.put("AZ", "CA");
		stateNeighbors.put("AZ", "CO");
		stateNeighbors.put("AZ", "NM");
		stateNeighbors.put("AZ", "NV");
		stateNeighbors.put("AZ", "UT");
		
		// CA California
		stateNeighbors.put("CA", "AZ");
		stateNeighbors.put("CA", "NV");
		stateNeighbors.put("CA", "OR");
		
		// CO Colorado
		stateNeighbors.put("CO", "AZ");
		stateNeighbors.put("CO", "KS");
		stateNeighbors.put("CO", "NE");
		stateNeighbors.put("CO", "NM");
		stateNeighbors.put("CO", "OK");
		//stateNeighbors.put("CO", "TX"); // Add?
		stateNeighbors.put("CO", "UT");
		stateNeighbors.put("CO", "WY");
		
		// CT Connecticut
		stateNeighbors.put("CT", "MA");
		stateNeighbors.put("CT", "NY");
		stateNeighbors.put("CT", "RI");
		
		// DE Delaware
		stateNeighbors.put("DE", "MD");
		stateNeighbors.put("DE", "NJ");
		stateNeighbors.put("DE", "PA");
		
		// FL Florida
		stateNeighbors.put("FL", "AL");
		stateNeighbors.put("FL", "GA");
		
		// GA Georgia
		stateNeighbors.put("GA", "AL");
		stateNeighbors.put("GA", "FL");
		stateNeighbors.put("GA", "NC");
		stateNeighbors.put("GA", "SC");
		stateNeighbors.put("GA", "TN");
		
		// HI Hawaii...no neighbors :*(
		stateNeighbors.put("HI", null);
		
		// IA Iowa
		stateNeighbors.put("IA", "IL");
		stateNeighbors.put("IA", "MN");
		stateNeighbors.put("IA", "MO");
		stateNeighbors.put("IA", "NE");
		stateNeighbors.put("IA", "SD");
		stateNeighbors.put("IA", "WI");
		
		// ID Idaho
		stateNeighbors.put("ID", "MT");
		stateNeighbors.put("ID", "NV");
		stateNeighbors.put("ID", "OR");
		stateNeighbors.put("ID", "UT");
		stateNeighbors.put("ID", "WA");
		stateNeighbors.put("ID", "WY");
		
		// IL Illinois
		stateNeighbors.put("IL", "IA");
		stateNeighbors.put("IL", "IN");
		stateNeighbors.put("IL", "KY");
		stateNeighbors.put("IL", "MO");
		stateNeighbors.put("IL", "WI");
		
		// IN Indiana
		stateNeighbors.put("IN", "IL");
		stateNeighbors.put("IN", "KY");
		stateNeighbors.put("IN", "MI");
		stateNeighbors.put("IN", "OH");
		
		// KS Kansas
		//stateNeighbors.put("KS", "AR"); // Add?
		stateNeighbors.put("KS", "CO");
		//stateNeighbors.put("KS", "IA"); // Add?
		stateNeighbors.put("KS", "OK");
		stateNeighbors.put("KS", "MO");
		stateNeighbors.put("KS", "NE");
		
		// KY Kentucky
		stateNeighbors.put("KY", "IL");
		stateNeighbors.put("KY", "IN");
		stateNeighbors.put("KY", "MO");
		stateNeighbors.put("KY", "OH");
		stateNeighbors.put("KY", "TN");
		stateNeighbors.put("KY", "VA");
		stateNeighbors.put("KY", "WV");
		
		// LA Louisiana
		stateNeighbors.put("LA", "AR");
		stateNeighbors.put("LA", "MS");
		//stateNeighbors.put("LA", "OK"); // Add?
		stateNeighbors.put("LA", "TX");
		
		// MA Massachusetts
		stateNeighbors.put("MA", "CT");
		stateNeighbors.put("MA", "NH");
		stateNeighbors.put("MA", "NY");
		stateNeighbors.put("MA", "RI");
		stateNeighbors.put("MA", "VT");
		
		// MD Maryland
		stateNeighbors.put("MD", "DE");
		stateNeighbors.put("MD", "PA");
		stateNeighbors.put("MD", "VA");
		stateNeighbors.put("MD", "WV");
		
		// ME Maine
		stateNeighbors.put("ME", "NH");
				
		// MI Michigan
		stateNeighbors.put("MI", "IN");
		stateNeighbors.put("MI", "OH");
		stateNeighbors.put("MI", "WI");
		
		// MN Minnesota
		stateNeighbors.put("MN", "IA");
		stateNeighbors.put("MN", "ND");
		stateNeighbors.put("MN", "SD");
		stateNeighbors.put("MN", "WI");
		
		// MO Missouri
		stateNeighbors.put("MO", "AR");
		stateNeighbors.put("MO", "IA");
		stateNeighbors.put("MO", "IL");
		stateNeighbors.put("MO", "KS");
		stateNeighbors.put("MO", "KY");
		stateNeighbors.put("MO", "NE");
		stateNeighbors.put("MO", "OK");
		stateNeighbors.put("MO", "TN");
		
		// MS Mississippi
		stateNeighbors.put("MS", "AL");
		stateNeighbors.put("MS", "AR");
		stateNeighbors.put("MS", "LA");
		stateNeighbors.put("MS", "TN");
		
		// MT Montana
		stateNeighbors.put("MT", "ID");
		stateNeighbors.put("MT", "ND");
		stateNeighbors.put("MT", "SD");
		stateNeighbors.put("MT", "WA");
		stateNeighbors.put("MT", "WY");
		
		// NC North Carolina
		stateNeighbors.put("NC", "GA");
		stateNeighbors.put("NC", "SC");
		stateNeighbors.put("NC", "TN");
		stateNeighbors.put("NC", "VA");
		
		// ND North Dakota
		stateNeighbors.put("ND", "MN");
		stateNeighbors.put("ND", "MT");
		stateNeighbors.put("ND", "SD");
		
		// NE Nebraska
		stateNeighbors.put("NE", "CO");
		stateNeighbors.put("NE", "IA");
		stateNeighbors.put("NE", "KS");
		stateNeighbors.put("NE", "MO");
		stateNeighbors.put("NE", "SD");
		stateNeighbors.put("NE", "WY");
		
		// NH New Hampshire
		stateNeighbors.put("NH", "MA");
		stateNeighbors.put("NH", "ME");
		stateNeighbors.put("NH", "VT");
		
		// NJ New Jersey
		stateNeighbors.put("NJ", "DE");
		stateNeighbors.put("NJ", "NY");
		stateNeighbors.put("NJ", "PA");
		
		// NM New Mexico
		stateNeighbors.put("NM", "AZ");
		stateNeighbors.put("NM", "CO");
		stateNeighbors.put("NM", "OK");
		stateNeighbors.put("NM", "TX");
		stateNeighbors.put("NM", "UT");
	
		// NV Nevada
		stateNeighbors.put("NV", "AZ");
		stateNeighbors.put("NV", "CA");
		stateNeighbors.put("NV", "ID");
		stateNeighbors.put("NV", "OR");
		stateNeighbors.put("NV", "UT");
		
		// New York
		stateNeighbors.put("NY", "CT");
		stateNeighbors.put("NY", "MA");
		stateNeighbors.put("NY", "NJ");
		stateNeighbors.put("NY", "PA");
		stateNeighbors.put("NY", "VT");
		
		// OH Ohio
		stateNeighbors.put("OH", "IN");
		stateNeighbors.put("OH", "KY");
		stateNeighbors.put("OH", "MI");
		stateNeighbors.put("OH", "PA");
		stateNeighbors.put("OH", "WV");
		
		// OK Oklahoma
		stateNeighbors.put("OK", "AR");
		stateNeighbors.put("OK", "CO");
		stateNeighbors.put("OK", "KS");
		stateNeighbors.put("OK", "MO");
		stateNeighbors.put("OK", "NM");
		stateNeighbors.put("OK", "TX");
		
		// OR Oregon
		stateNeighbors.put("OR", "CA");
		stateNeighbors.put("OR", "ID");
		stateNeighbors.put("OR", "NV");
		stateNeighbors.put("OR", "WA");
		
		// PA Pennsylvania
		stateNeighbors.put("PA", "DE");
		stateNeighbors.put("PA", "MD");
		stateNeighbors.put("PA", "NJ");
		stateNeighbors.put("PA", "NY");
		stateNeighbors.put("PA", "OH");
		stateNeighbors.put("PA", "WV");
		
		// RI Rhode Island
		stateNeighbors.put("RI", "CT");
		stateNeighbors.put("RI", "MA");
		
		// SC South Carolina
		stateNeighbors.put("SC", "GA");
		stateNeighbors.put("SC", "NC");
		//stateNeighbors.put("SC", "TN"); // Add?
		
		// South Dakota
		stateNeighbors.put("SD", "IA");
		stateNeighbors.put("SD", "MN");
		stateNeighbors.put("SD", "MT");
		stateNeighbors.put("SD", "ND");
		stateNeighbors.put("SD", "NE");
		stateNeighbors.put("SD", "WY");
		
		// TN Tennessee
		stateNeighbors.put("TN", "AL");
		stateNeighbors.put("TN", "AR");
		stateNeighbors.put("TN", "GA");
		stateNeighbors.put("TN", "KY");
		stateNeighbors.put("TN", "MO");
		stateNeighbors.put("TN", "MS");
		stateNeighbors.put("TN", "NC");
		stateNeighbors.put("TN", "VA");

		// TX Texas
		stateNeighbors.put("TX", "AR");
		stateNeighbors.put("TX", "LA");
		stateNeighbors.put("TX", "NM");
		stateNeighbors.put("TX", "OK");
		
		// UT Utah
		stateNeighbors.put("UT", "AZ");
		stateNeighbors.put("UT", "CO");
		stateNeighbors.put("UT", "ID");
		stateNeighbors.put("UT", "NM");
		stateNeighbors.put("UT", "NV");
		stateNeighbors.put("UT", "WY");
		
		// VA Virginia
		stateNeighbors.put("VA", "KY");
		stateNeighbors.put("VA", "MD");
		stateNeighbors.put("VA", "NC");
		stateNeighbors.put("VA", "TN");
		stateNeighbors.put("VA", "WV");
		
		// VT Vermont
		stateNeighbors.put("VT", "MA");
		stateNeighbors.put("VT", "NH");
		stateNeighbors.put("VT", "NY");
		
		// WA Washington
		stateNeighbors.put("WA", "ID");
		stateNeighbors.put("WA", "OR");
		
		// WI Wisconsin
		stateNeighbors.put("WI", "IA");
		stateNeighbors.put("WI", "IL");
		stateNeighbors.put("WI", "MI");
		stateNeighbors.put("WI", "MN");
		
		// WV West Virginia
		stateNeighbors.put("WV", "KY");
		stateNeighbors.put("WV", "MD");
		stateNeighbors.put("WV", "OH");
		stateNeighbors.put("WV", "PA");
		stateNeighbors.put("WV", "VA");
		
		// WY Wyoming
		stateNeighbors.put("WY", "CO");
		stateNeighbors.put("WY", "ID");
		stateNeighbors.put("WY", "MT");
		stateNeighbors.put("WY", "NE");
		stateNeighbors.put("WY", "SD");
		stateNeighbors.put("WY", "UT");
		
		// Print entries
		//System.out.println("Displaying states and their neighbors...");
		Set<String> keys = stateNeighbors.keySet();
		//for (String key: keys) {
		//	System.out.println(key + ": " + stateNeighbors.get(key));
			// Testing...
			/*if (key.contains("MA")) {
				System.out.println(countkey + ": " + stateNeighbors.get(key));
			}*/
		//}
		System.out.println("State Neighbors list created.");
	}
}
