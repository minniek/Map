import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import LoadData.MapData;
import RTree.RTreeNode_GlobalScale;
import StateNeighbors.StateNeighbors;


public class TesterClass {
	
	public static void main(String[] args) throws IOException {
		
		// Load map data
		HashMap mapData_States = LoadMapData("src\\NationalFile_StateProvinceDecimalLatLong.txt");
		
		
		// Load state neighbors
		StateNeighbors stateNeighbors = LoadStateNeighborsList();
		
		
		// Create RTree
		RTreeNode_GlobalScale Root = CreateRTree(mapData_States);
		
		
		// Find the state(s) the user-input point is in or nearby
		double longitude = 0;
		double latitude = 0;
		latitude = 42.4900;
		longitude = -71.3900;
		
		ArrayList<RTreeNode_GlobalScale> nodesContainingPoint;
		// Some testing points
		//nodesContainingPointTest = Root.findNodesContainingPoint(-118.809997, 46.694205); // WA
		//nodesContainingPointTest = Root.findNodesContainingPoint(-170.7583333, -13);		// AS
		//nodesContainingPointTest = Root.findNodesContainingPoint(-104.98, 39.7516667);	// CO
		//nodesContainingPointTest = Root.findNodesContainingPoint(-99.948225, 46.939944);	// ND
		//nodesContainingPointTest = Root.findNodesContainingPoint(-83.600569, 37.706635);	// KY
		//nodesContainingPointTest = Root.findNodesContainingPoint(-121.657211, 40.569105);	// CA
		//nodesContainingPointTest = Root.findNodesContainingPoint(-71.433562, 42.551768);	// MA
		//nodesContainingPoint = Root.findNodesContainingPoint(-85.498725, 36.272302);		// TN
		nodesContainingPoint = Root.findNodesContainingPoint(longitude, latitude);
		
		
		// Display resulting nodes that point is in or nearby
		for (int ii = 0; ii < nodesContainingPoint.size(); ii++){
			System.out.println("From recursive function. Nodes containing test point: " + nodesContainingPoint.get(ii).getName());
		}
		
		
		// Initialize structures containing nearest counties
		HashMap<String, Double> NearestCounties = new HashMap<String, Double>();
		
		
		// Go through arraylist of state nodes that point is or may be in
		for (int ii = 0; ii < nodesContainingPoint.size(); ii++){
			
			RTreeNode_GlobalScale currentStateNode = nodesContainingPoint.get(ii);				// Get current state node
			String stateAbbrv = currentStateNode.getName();										// Get name of state (abbreviation)
			
			if (stateAbbrv.equals("Root: United States")) continue;								// Check that it is not the root node
			System.out.println("Processing counties in state... " + stateAbbrv);
			
			// We are at a non-root (state) node... Let's process it!
			for (RTreeNode_GlobalScale current_county : currentStateNode.getChildren()) {		// Iterate through this state's counties
				
				double distance = current_county.calculateDistance(longitude, latitude);		// Calculate distance from this county
				String stateAndCounty = stateAbbrv + " " + current_county.getName();			// Include state and county name for keeping track
				NearestCounties.put(stateAndCounty, distance);									// Add county to hashmap<string, double> [key: state and county name, value: distance]
			}
			
			// Now find this state's state neighbors
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
				RTreeNode_GlobalScale currentNeighborNode = Root.getChildByName(neighborAbbrv);	// Get current state node
				
				System.out.println("Processing counties in state... " + neighborAbbrv);
				
				// Iterate through this state's counties 
				for (RTreeNode_GlobalScale current_county : currentNeighborNode.getChildren()) {
					
					double distance = current_county.calculateDistance(longitude, latitude);	// Calculate distance from this county
					String stateAndCounty = neighborAbbrv + " " + current_county.getName();		// Include state and county name for keeping track
					NearestCounties.put(stateAndCounty, distance);								// Add county to hashmap<string, double> [key: state and county name, value: distance]
				}
			}
		}
		
		
		// Iterates through NearestCounties list
		int k = 10;
		ArrayList<String> TopKCountyNames = new ArrayList<String>();
		ArrayList<Double> TopKCountyDistances = new ArrayList<Double>();
		HashMap<String, Double> TopK = new HashMap<String, Double>();
		double minDistance = 0;												
		String minDistanceKey = "";											// Represents the county's name (w/ state info)
		for (int ii = 0; ii < k; ii++){
			
	        for (Object key: NearestCounties.keySet()) {
	        	
	        	if (minDistance == 0){
	        		minDistance = NearestCounties.get(key);					// Initialize minimum distance tracker if not set
	        		minDistanceKey = key.toString();						// Initialize minimum distance county name if not set
	        	}
	        	
	        	if (NearestCounties.get(key) < minDistance){
	        		minDistance = NearestCounties.get(key);
	        		minDistanceKey = key.toString();
	        	}
	        }
	        
	        // Place in top K results
	        TopKCountyNames.add(minDistanceKey);
	        TopKCountyDistances.add(minDistance);
	        //TopK.put(minDistanceKey, minDistance);	// Not in use
	        
	        // Remove from main list (so we don't query it again)
	        NearestCounties.remove(minDistanceKey);
	        
	        // Reset minimums
	        minDistance = 0;
			minDistanceKey = "";
		}
		
		for (Object key: TopK.keySet()) System.out.println(key.toString() + ": " + TopK.get(key));
		
		System.out.println("\nSorted order: ");
		for (int ii = 0; ii < k; ii++){
			System.out.println(TopKCountyNames.get(ii).toString() + ": " + TopKCountyDistances.get(ii).toString());
		}
        	
		/*
		// Keep for reference on how to iterate through some levels of modified RTreeNode
		for (Object stateChild: Root.getChildren()){
			stateChild = (RTreeNode_GlobalScale) stateChild;
			if (!((RTreeNode_GlobalScale) stateChild).getName().equals("NY")) continue;
			((RTreeNode_GlobalScale) stateChild).printStats();
			for (Object countyChild: ((RTreeNode_GlobalScale) stateChild).getChildren()){
				countyChild = (RTreeNode_GlobalScale) countyChild;
				((RTreeNode_GlobalScale) countyChild).printStats();
			}
		}*/
		
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
	
	/**
	 * Creates a specific Rectangle Tree: 
	 * Level 1 (Root): The United States
	 * Level 2: States
	 * Level 3: Counties
	 * @param mapData	Type: HashMap
	 * @return
	 */
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
	
	public static void TestRTreeNode_GlobalScaleClass(){
		// Test RTreeNode_GlobalScales class
		System.out.println("----------------------------------");
		RTreeNode_GlobalScale UnitedStates = new RTreeNode_GlobalScale("United States", 1, 25, 1, 25);
		
		RTreeNode_GlobalScale MA = new RTreeNode_GlobalScale("MA", 1, 10, 1, 10);
		RTreeNode_GlobalScale CA = new RTreeNode_GlobalScale("CA", 1, 3, 1, 3);
		RTreeNode_GlobalScale TX = new RTreeNode_GlobalScale("TX", 2, 20, 2, 20);
		RTreeNode_GlobalScale blah = new RTreeNode_GlobalScale();
		
		UnitedStates.addChild(MA);
		UnitedStates.addChild(CA);
		UnitedStates.addChild(TX);
		UnitedStates.addChild(blah);
		
		System.out.println(UnitedStates.getName());
		System.out.println(UnitedStates.getChildByName("MA").getName());
		System.out.println(UnitedStates.getChildByName("TX").getName());
		System.out.println(UnitedStates.getChildByName("CA").getName());
		System.out.println(UnitedStates.getChildByName("None").getName());
		
		// Test the expand region method
		System.out.println("----------------------------------");
		
		RTreeNode_GlobalScale Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);
		Basic.printStats();
		
		RTreeNode_GlobalScale Larger = new RTreeNode_GlobalScale("Larger", -5, 20, -5, 20);
		Larger.printStats();
		
		RTreeNode_GlobalScale Smaller = new RTreeNode_GlobalScale("Smaller", 2, 4, 2, 4);
		Smaller.printStats();
		
		RTreeNode_GlobalScale Both = new RTreeNode_GlobalScale("Both", -5, 9, 1, 20);
		Both.printStats();
		
		System.out.println("----------------- Now expand Basic-----------------");
		System.out.println("----------------- Basic + Larger -----------------");
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Larger);
		Basic.printStats();
		System.out.println("----------------- Basic + Smaller -----------------");
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Smaller);
		Basic.printStats();
		System.out.println("----------------- Basic + Both -----------------");
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		Basic.expandRegion(Both);
		Basic.printStats();
		
		// Test recursive find
		Basic = new RTreeNode_GlobalScale("Basic", 0, 10, 0, 10);	// Reset
		
		RTreeNode_GlobalScale hello1 = new RTreeNode_GlobalScale("hello1", 0, 7, 0, 7);
		RTreeNode_GlobalScale hello2 = new RTreeNode_GlobalScale("hello2", 0, 11, 0, 11);
		RTreeNode_GlobalScale hello2baby = new RTreeNode_GlobalScale("hello2baby", 0, 11, 0, 11);
		RTreeNode_GlobalScale hellothere1 = new RTreeNode_GlobalScale("hellothere1", -5, 7, 0, 7);
		hello1.addChild(hellothere1);
		hello2.addChild(hello2baby);
		
		Basic.addChild(hello1);
		Basic.addChild(hello2);
		
		double x = 1;
		double y = 10;
		
		System.out.println("Basic contains point (" + x + ", " + y + ")?: " + Basic.containsPoint(x, y));
		System.out.println("hello1 contains point (" + x + ", " + y + ")?: " + hello1.containsPoint(x, y));
		System.out.println("hello2 contains point (" + x + ", " + y + ")?: " + hello2.containsPoint(x, y));
		System.out.println("hellothere1 contains point (" + x + ", " + y + ")?: " + hellothere1.containsPoint(x, y));
		
		ArrayList<RTreeNode_GlobalScale> nodesContainingPointTest = Basic.findNodesContainingPoint(x, y);
		for (int ii = 0; ii < nodesContainingPointTest.size(); ii++){
			System.out.println(nodesContainingPointTest.get(ii).getName());
		}
	}
}
