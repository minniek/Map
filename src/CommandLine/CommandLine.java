package CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import LoadData.MapData;
import RTree.RTreeNode_GlobalScale;
import RTree.pqDistances;
import Rectangle.RegionRectangle;
import StateNeighbors.StateNeighbors;

/* Command-line Interface
 * To test on Eclipse:
 * 		Run ---> Run Configurations ---> In the Arguments tab, enter arguments
 * 
 * Argument description:
 * 		x - latitude
 *		y - longitude
 *		k - number of nearest counties to dislay, must be between 1-10 inclusive
 * 
 */

public class CommandLine {
	/**
	 * Gets called when user provides incorrect commandline args
	 * @param errorCode: depends on the type of error from user-input
	 */
	static void errorCheck(int errorCode) {
		switch (errorCode) {
			// Incorrect number of arguments given
			case 1: System.out.println("Error:\tincorrect number of arguments entered");
					System.out.println("\tThis program requires three arguments in the following order:\n\t<latitude>   <longitude>   <number of results to display>\n\tExiting now.");
					System.exit(1);
			case 2: // out-of-bound points (ex. if user inputs 100 for the value for k)
			case 3: // incorrect input format (ex. if user inputs "abc", "17.3zre" as latitude, longitude)
		}
	}
	
	public static void main(String[] args) throws IOException {
		int argsLen = args.length;
		
        	if (argsLen != 3) {
        		errorCheck(1);
        	} else {
        		Double x = Double.parseDouble(args[1]);
        		Double y = Double.parseDouble(args[0]);
    			int k = Integer.parseInt(args[2]);

    			// If the user does not input a latitude, longtitude, or a value for k, set default values
    			try {
    				y = Double.parseDouble(args[0]);
    			} catch(NumberFormatException e) {
    				y = 42.3581;
    			}
    			
    			try {
    				x = Double.parseDouble(args[1]);
    			} catch(NumberFormatException e) {
    				x = -71.0636;
    			}
    			
    			try {
    				k = Integer.parseInt(args[2]);
    				if (k < 1 || k > 10) {
    					k = 10;
    				}
    			} catch(NumberFormatException e) {
    			   k = 10;
    			}
    			
    		
    			x = (long) (x * 10000) / 10000.0;
    			y = (long) (y * 10000) / 10000.0;
    			
    			//  ---------------------- Load Data: Counties and States and State Neighbors ---------------------- 
    			
    			// Load map data
    			HashMap mapData_States = LoadMapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
    		
    			// Load state neighbors
    			StateNeighbors stateNeighbors = LoadStateNeighborsList();
    			
    		
    			//  --------------------------- Create "RTree" Search tree data structure -------------------------- 
    			RTreeNode_GlobalScale Root = CreateRTree(mapData_States);
    			
    			//  -------------------------------- Find State that the point is in ------------------------------- 
    			ArrayList<RTreeNode_GlobalScale> nodesContainingPoint;
    			nodesContainingPoint = Root.findNodesContainingPoint(x, y);
    			String stateAbbrv = null;
    			
    			pqDistances pq = new pqDistances();
    			pqDistances pqMajorityVote = new pqDistances();
    			
    			for (int ii = 0; ii < nodesContainingPoint.size(); ii++) {
    				// Get current state node
    				RTreeNode_GlobalScale currentStateNode = nodesContainingPoint.get(ii);
    				stateAbbrv = currentStateNode.getName();											// Get name of state (abbreviation)
    				
    				if (stateAbbrv.equals("Root: United States")) continue;
    				
    				// Add distance calculations to PQ for this state
        			pq.addAdditionalDistances(mapData_States, 10000, stateAbbrv, x, y);
        			pqMajorityVote.addAdditionalDistances(mapData_States, 10000, stateAbbrv, x, y);
        			
        			//  -------------------- Find this State's State Neighbors and their distances --------------------- 
        			
        			ArrayList<String> neighbors = new ArrayList<String>();								// Array to keep track this state's neighbors
        			
        			// Iterate through the State Neighbors list to find this state's neighbors
        			Set<String> keys = StateNeighbors.stateNeighbors.keySet();
        			for (String key: keys) {
        				
        				// Skip if abbreviation we're looking for doesn't match with this entry
        				if (!key.equals(stateAbbrv)) continue;
        				
        				// Found the state in the State Neighbors list! Now place its neighbors in the neighbors array for later use
        				neighbors.addAll(StateNeighbors.stateNeighbors.get(key));
        				System.out.println("Querying state and its neighbors... " + key + ": " + StateNeighbors.stateNeighbors.get(key));
        				
        			}
        			
        			// Place this state's neighbors' counties and calculated distance into NearestCounties hashmap
        			for (int jj = 0; jj < neighbors.size(); jj++){
        				
        				String neighborAbbrv = neighbors.get(jj).toString();							// Get current state neighbor's name
        				pq.addAdditionalDistances(mapData_States, 10000, neighborAbbrv, x, y);
        				pqMajorityVote.addAdditionalDistances(mapData_States, 10000, neighborAbbrv, x, y);
        			}
    			}
    			
    			// Display resulting nodes that contain the original coordinates or nearby
    			/*for (int ii = 0; ii < nodesContainingPoint.size(); ii++) {
    				System.out.println("From recursive function. Nodes containing test point: " + nodesContainingPoint.get(ii).getName());
    			}*/

    			// Print results
    			pq.printQueue(k);
    			ArrayList topK = pqMajorityVote.getStateAndCountyName(5);
    			System.out.println(topK);
    			
    			String[] StateCountyStringArray;
    			int max = 0;
    			String StateAndCountyWithMostPoints = "";
    			for (int ii = 0; ii < topK.size(); ii ++){
    				StateCountyStringArray = ((String) topK.get(ii)).split(" ");
    				String state = StateCountyStringArray[0];
    				String county = StateCountyStringArray[1];
    				//System.out.println(MapData.getCount(state, county));//getCount
    				if (MapData.getCount(state, county) > max){
    					max = MapData.getCount(state, county);
    					StateAndCountyWithMostPoints = state + ", " + county;
    				}
    			}
    			
    			System.out.println("You are in: " + StateAndCountyWithMostPoints + " with number of points: " + max);
    		}
        }
	
	/**
	 * Loads states' neighbors
	 * @return
	 * @throws IOException
	 */
	public static StateNeighbors LoadStateNeighborsList() throws IOException{
		System.out.println("Loading State Neighbors data...");
		StateNeighbors stateNeighbors = new StateNeighbors();
		System.out.println("Loading State Neighbors completed...");
		return stateNeighbors;
	}
	
	/***
	 * Loads input map data for application
	 * @throws IOException
	 */
	public static HashMap LoadMapData(String filename) throws IOException{
		System.out.println("Loading Map Data...");
		MapData mapData = new MapData(filename);
		System.out.println("Loading Map Data completed...");
		return mapData.getStates();
	}
	
	public static RTreeNode_GlobalScale CreateRTree(HashMap<String, HashMap<String, ArrayList>> mapData){
		
		RTreeNode_GlobalScale rootNode = new RTreeNode_GlobalScale();							// Root of tree
		
		//This prints out all the states. Just for debugging purposes.
        for (Object current_state : mapData.keySet()) {											// Iterate through all states
        	
        	// Uncomment when selecting to print out a specific state (for testing purposes only)
        	//if (!current_state.toString().equals("MH")) continue;
        	
        	RTreeNode_GlobalScale stateNode = new RTreeNode_GlobalScale();						// Initialize current state's nodes
            stateNode.setName(current_state.toString());										// Add state's name to node
            
            HashMap state_value = (HashMap) mapData.get(current_state);							// Get internal state hashmap (counties and their dimensions)
            
            for (Object current_county : state_value.keySet()){									// Iterate through all counties in state
            	
                ArrayList county_dimensions = (ArrayList) state_value.get(current_county);		// Get county's list of rectangular dimensions.
                
                // Store coordinates. Note ArrayList points order: [x1, y1, x2, y2]
                Double x1, x2, y1, y2;
                
                if (county_dimensions.size() != 4) continue;	// If ArrayList does not have 4 points, skip county
                
                y2 = (Double) county_dimensions.get(0);			// Get y2 = max latitude
                x1 = (Double) county_dimensions.get(1);			// Get x1 = min longitude 
                y1 = (Double) county_dimensions.get(2);			// Get y1 = min latitude
                x2 = (Double) county_dimensions.get(3);			// Get x2 = max longitude
                
                // Create county node: name, x1, x2, y1, y2
                RTreeNode_GlobalScale countyNode = new RTreeNode_GlobalScale(current_county.toString(), x1, x2, y1, y2);
                
                // Add county to state
                stateNode.addChild(countyNode);
            }
            
            // Add state to root node
            rootNode.addChild(stateNode);
        }
        
        rootNode.setName("Root: United States");
		
		return rootNode;
	}
        	
        	
}
