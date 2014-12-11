package RTree;

import java.awt.Rectangle;
import java.util.ArrayList;


/**
 * Modified R-Tree node, specifically designed for the purpose of structuring regions of interest: State, Counties, etc.
 * Note: Scaling is relative to longitude and latitudes
 * @author Jeannie Trinh
 *
 */
public class RTreeNode_GlobalScale {
	
	//#region Private Variables -----------------------------------
	private String _name;
	private double _x1;
	private double _x2;
	private double _y1;
	private double _y2;
	private double _width;
	private double _height;
	private double[] _center;

	//private HashMap Children = new HashMap<Object, Object>();
	private ArrayList<RTreeNode_GlobalScale> _Children;
	//#endregion Private Variables --------------------------------
	
	//#region Properties ------------------------------------------
	// List of children nodes
	public ArrayList<RTreeNode_GlobalScale> getChildren(){
		return this._Children;
	}
	
	// Name
	public String getName(){
		return this._name;
	}
	public void setName(String name){
		this._name = name;
	}
	
	// X1
	public double getX1(){
		return this._x1;
	}
	public void setX1(double x){
		this._x1 = x;
		this.updateWidth();
		this.getCenter();
	}
	
	// X2
	public double getX2(){
		return this._x2;
	}
	public void setX2(double x){
		this._x2 = x;
		this.updateWidth();
		this.getCenter();
	}
	
	// Y1
	public double getY1(){
		return this._y1;
	}
	public void setY1(double y){
		this._y1 = y;
		this.updateHeight();
		this.getCenter();
	}
	
	// Y2
	public double getY2(){
		return this._y2;
	}
	public void setY2(double y){
		this._y2 = y;
		this.updateHeight();
		this.getCenter();
	}
	
	// Width
	public double getWidth(){
		return this._width;
	}
	
	// Height
	public double getHeight(){
		return this._height;
	}

	//Get coordinates for the center of the county
	public double[] getCenter(){
		double mid_x = (this._x2 + this._x1) / 2;
		double mid_y = (this._y2 + this._y1) / 2;
		
		double[] center = { mid_x , mid_y };
		this._center = center;
		
		return this._center; 
	}

	//Get distance
	public double calculateDistance(double longitude, double latitude){
		
		this.getCenter();
		double x = (longitude - this._center[0]) * Math.cos((latitude + this._center[1])/2);
		double y = latitude - this._center[1];
		//double x = Math.max((Math.abs(longitude - this._center[0])) - this._width / 2, 0);
		//double y = Math.max((Math.abs(latitude - this._center[1])) - this._height / 2, 0);
		
		double R = 6371;
		/*double x = this._center[0] - longitude;
		double y = this._center[1] - latitude;
		double a = Math.pow(Math.sin(y / 2), 2) + Math.cos(this._center[1]) * Math.cos(this._center[0]) * Math.pow(Math.sin(x / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;*/
		
		return Math.sqrt(x * x + y * y) * R;
		//return d;
	}
	//#endregion Properties ---------------------------------------
	
	
	//#region Methods ---------------------------------------------
	/**
	 * Constructor for the RTreeNode_GlobalScale class with no arguments
	 */
	public RTreeNode_GlobalScale(){
		this._name = "None";
		this._x1 = 0;
		this._x2 = 0;
		this._y1 = 0;
		this._y1 = 0;
		this._width = 0;
		this._height = 0;
		this._Children = new ArrayList<RTreeNode_GlobalScale>();
		this.getCenter();
	}
	
	/**
	 * Constructor for the RTreeNode_GlobalScale class. Assume lattitude and longitude inputs
	 * @param x1	The left boundary 
	 * @param x2	The right boundary
	 * @param y1	The upper boundary
	 * @param y2	The lower boundary
	 */
	public RTreeNode_GlobalScale(String name, double x1, double x2, double y1, double y2){
		
		// Assign name
		this._name = name;
		
		// Assign variables first
		this._x1 = x1;
		this._x2 = x2;
		this._y1 = y1;
		this._y2 = y2;

		// Check if x1 and x2 are both negative, flip if x1 is less negative than x2
		/*if (x1 < 0 && x2 < 0){
			if (x1 > x2){
				this._x1 = x2;
				this._x2 = x1;
			}
		}
		// Check if x1 is positive and x2 is negative
		else if (x1 > 0 && x2 < 0){
			this._x1 = x2;
			this._x2 = x1;
		}
		else if (x1 > 0 && x2 > 0){
			if (x1 > x2){
				this._x1 = x2;
				this._x2 = x1;
			}
		}
		else {
			this._y1 = y1;
			this._y2 = y2;
		}*/
		
		// Check if y1 and y2 negatives, flip if y1 is more negative than y2
		/*if (y1 < 0 && y2 < 0){
			if (y1 < y2){
				this._y1 = y2;
				this._y2 = y1;
			}
		}
		// Check if y1 is negative and y2 is positive, flip if they are
		else if (y1 < 0 && y2 > 0){
			this._y1 = y2;
			this._y2 = y1;
		}
		// Check if y1 and y2 are both positive, flip if y1 is less than y2
		else if (y1 > 0 && y2 > 0){
			if (y1 < y2){
				this._y1 = y2;
				this._y2 = y1;
			}
		}
		else {
			this._y1 = y1;
			this._y2 = y2;
		}*/
		
		// Update center
		this.getCenter();
		
		// Update width
		this.updateWidth();
		
		// Update height
		this.updateHeight();
		
		// Initialize list of children nodes
		this._Children = new ArrayList<RTreeNode_GlobalScale>();
	}
	
	/**
	 * Updates width of rectangular region of node
	 */
	private void updateWidth(){
		this._width = this._x2 - this._x1;
		if (this._width < 0) this._width = this._width * -1;
	}
	
	/**
	 * Updates height of rectangular region of node
	 */
	private void updateHeight(){
		this._height = this._y2 - this._y1;
		if (this._height < 0) this._height = this._height * -1;
	}
	
	/***
	 * Add child node to this node, and expand this node's region size
	 * @param node	Child node that is to be added
	 */
	public void addChild(RTreeNode_GlobalScale node){
		this._Children.add(node);
		this.expandRegion(node);
	}
	
	/**
	 * Returns this node's child that matches a name
	 * @param name	The name of child to be returned
	 * @return		The RTreeNode_GlobalScale of the child with matching name
	 */
	public RTreeNode_GlobalScale getChildByName(String name){
		for (int ii = 0; ii < this._Children.size(); ii++){
			if (this._Children.get(ii).getName().equals(name))
				return this._Children.get(ii);
		}
		return null;
	}
	
	/***
	 * Remove child node from this node
	 * @param node	Child node that is to be removed
	 */
	public void removeChild(RTreeNode_GlobalScale node){
		if (this._Children != null)
			if (this._Children.size() > 0)
				this._Children.remove(node);
	}
	
	/***
	 * Remove child node from this node
	 * @param node	Child node (integer index) that is to be removed
	 */
	public void removeChild(int node){
		if (this._Children != null)
			if (this._Children.size() > 0)
				this._Children.remove(node);
	}
	
	/**
	 * Remove child node from this node if name matches
	 * @param name	Name of child node to be removed
	 */
	public void removeChildByName(String name){
		if (this._Children != null){
			if (this._Children.size() > 0){
				
				for (int ii = 0; ii < this._Children.size(); ii++){
					if (this._Children.get(ii).getName().equals(name))
					this._Children.remove(ii);
				}
				
			}
		}
	}
	
	/***
	 * Clears this node's list of children nodes
	 */
	public void clearChildren(){
		if (this._Children != null)
			this._Children.clear();
		else
			this._Children = new ArrayList<RTreeNode_GlobalScale>();
	}
	
	/***
	 * Tests if a rectangle intersects with this one
	 * @param node	Another rectangle to check with this one
	 * @return		<code>true</code> if another rectangle intersects with this one
	 */
	public boolean intersects(RTreeNode_GlobalScale node){
		Rectangle thisNode = new Rectangle();
		thisNode.setRect(_x1, _y1, _width, _height);
		
		Rectangle inputNode = new Rectangle();
		inputNode.setRect(node.getX1(), node.getY1(), node.getWidth(), node.getHeight());
		
		return thisNode.intersects(inputNode);
	}
	
	/***
	 * Returns if node has any children
	 * @return	<code>true</code> if node has at least one child
	 */
	public boolean hasChildren(){
		if (this._Children == null)
			return false;
		return this._Children.size() > 0;
	}
	
	/***
	 * Returns number of children this node has
	 * @return	Number of children this node has
	 */
	public int getNumberOfChildren(){
		if (this._Children == null)
			return 0;
		return this._Children.size();
	}
	
	/**
	 * Returns if an input point is inside the <code>RTreeNode_GlobalScale</code> region
	 * @param longitude	X-coordinate of point to check (LONGITUDE)
	 * @param latitude	Y-coordinate of point to check (LATITUDE)
	 * @return	<code>true</code> if input point is contained in the <code>RTreeNode_GlobalScale</code> region
	 */
	public boolean containsPoint(double longitude, double latitude){
		if (longitude >= this._x1 && longitude <= this._x2 && latitude >= this._y1 && latitude <= this._y2)
			return true;
		return false;
	}
	
	/***
	 * Determines which nodes of an RTree contains a given point 
	 * @param x	X-coordinate of point to be checked if contained in an RTree
	 * @param y	Y-coordinate of point to be checked if contained in an RTree
	 * @return	An ArrayList of <code>RTreeNode_GlobalScale</code> that contains the input point
	 */
	public ArrayList<RTreeNode_GlobalScale> findNodesContainingPoint(double x, double y){
		
		// Initiate array to store all nodes that contain given xy coordinates
		ArrayList<RTreeNode_GlobalScale> allNodesContainingPoint = new ArrayList<RTreeNode_GlobalScale>();
		
		// If this node node contains the point, add it to list
		if (this.containsPoint(x, y))
			allNodesContainingPoint.add(this);
		
		// Return if no children
		if (this._Children == null)
			return allNodesContainingPoint;
		
		// Return if no children
		if (this._Children.size() == 0)
			return allNodesContainingPoint;
		
		// Node has children, 
		// check if point is contained in them and if they are, 
		// then check their children (recursively)
		for (int ii = 0; ii < this._Children.size(); ii++){
			if (this._Children.get(ii).containsPoint(x, y)){
				
				// Append children nodes' that contain the given point to the array
				allNodesContainingPoint.addAll(this._Children.get(ii).findNodesContainingPoint(x, y));
			}
		}
		return allNodesContainingPoint;
	}
	
	/***
	 * Expands this node's rectangular region by adjusting its bounds, 
	 * this is done in order to incorporate bounds of the second (input) node
	 * @param node	<code>RTreeNode_GlobalScale</code>Input node to incorporate
	 */
	public void expandRegion(RTreeNode_GlobalScale node){
		
		double LONGITUDE_LIMIT = 180;
		
		// Expand when necessary (one bound is farther out than the other)
		/*if (node._x1 < this._x1) this._x1 = node._x1;
		if (node._x2 > this._x2) this._x2 = node._x2;
		if (node._y1 < this._y1) this._y1 = node._y1;
		if (node._y2 > this._y2) this._y2 = node._y2;*/
		
		
		// If x1, x2, y1, y2 not set, then this is *probably* the first expansion. Expand.
		if (this._x1 == 0 && this._x2 == 0 && this._y1 == 0 && this._y2 == 0){
			this._x1 = node._x1;
			this._x2 = node._x2;
			this._y1 = node._y1;
			this._y2 = node._y2;
		}
		
		/*if (this._x1 == 0) this._x1 = node._x1;
		if (this._x2 == 0) this._x2 = node._x2;
		if (this._y1 == 0) this._y1 = node._y1;
		if (this._y2 == 0) this._y2 = node._y2;*/
		
		// Else expand when necessary normally
		
		if (node._x1 < this._x1 && this._x1 == 0) this._x1 = node._x1;
		if (node._x2 > this._x2 && this._x2 == 0) this._x2 = node._x2;
		if (node._y1 < this._y1 && this._y1 == 0) this._y1 = node._y1;
		if (node._y2 > this._y2 && this._y2 == 0) this._y2 = node._y2;
		
		/*
		if (node._x1 < this._x1 && this._x1 == 0) this._x1 = node._x1;
		if (node._x2 > this._x2 && this._x2 == 0) this._x2 = node._x2;
		if (node._y1 > this._y1 && this._y1 == 0) this._y1 = node._y1;
		if (node._y2 < this._y2 && this._y2 == 0) this._y2 = node._y2;*/
		
		if (node._x1 < this._x1) this._x1 = node._x1;
		if (node._x2 > this._x2) this._x2 = node._x2;
		if (node._y1 < this._y1) this._y1 = node._y1;
		if (node._y2 > this._y2) this._y2 = node._y2;
		
		/*
		// If new node x1 and this node x1 are both less than 0 and greater than -1*limit, compare to expand 
		if (node._x1 < 0 && node._x1 > LONGITUDE_LIMIT * -1 && this._x1 < 0 && this._x1 > LONGITUDE_LIMIT * -1){
			if (node._x1 < this._x1)
				this._x1 = node._x1;
		}
		
		if (node._x1 > 0 && node._x1 < LONGITUDE_LIMIT && this._x1 > 0 && this._x1 < LONGITUDE_LIMIT){
			if (node._x1 < this._x1)
				this._x1 = node._x1;
		}
		
		if (node._x2 < 0 && node._x2 > LONGITUDE_LIMIT * -1 && this._x2 < 0 && this._x2 > LONGITUDE_LIMIT * -1){
			if (node._x2 < this._x2)
				this._x2 = node._x2;
		}
		
		if (node._x2 > 0 && node._x2 < LONGITUDE_LIMIT && this._x2 > 0 && this._x2 < LONGITUDE_LIMIT){
			if (node._x2 > this._x2){
				this._x2 = node._x2;
			}
		}
		
		if (node._y1 > 0 && this._y1 > 0){
			if (node._y1 > this._y1)
				this._y1 = node._y1;
		}
		
		if (node._y2 > 0 && this._y2 > 0){
			if (node._y2 < this._y2)
				this._y2 = node._y2;
		}*/
		
		
		// Update center, width and height
		this.getCenter();
		this.updateWidth();
		this.updateHeight();
	}
	
	/***
	 * Prints this node's information such as name, bounding coordinates, width, height
	 */
	public void printStats(){
		System.out.println("name = " + this._name);
		System.out.println("x1 = " + this._x1);
		System.out.println("x2 = " + this._x2);
		System.out.println("y1 = " + this._y1);
		System.out.println("y2 = " + this._y2);
		System.out.println("width = " + this._width);
		System.out.println("height = " + this._height);
		System.out.println("center longitude: " + this._center[0]);
		System.out.println("center latitude: " + this._center[1]);
	}
	
	//#endregion Methods ------------------------------------------
}
