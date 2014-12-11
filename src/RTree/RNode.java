package RTree;

import java.util.HashMap;
import java.util.Set;
import java.awt.Rectangle;

public class RNode implements Rect{
    
    //This HashMap will contain all of the child nodes
    protected HashMap children;
    
    //The x- and y-coordinates for the minimum bounding rectangle
    protected double max_x, min_x, max_y, min_y;
    
    //x- and y-coordinates for the center
    protected double[] center = {0,0};
    
    //Name of the state & county
    protected String State;
    protected String County;
    
    //Pointer to parent
    //private RNode parent;
    
    //Unique ID for the node
    protected int ID;
    
    //Level of the node on the tree
    protected int tree_level;
    
    //Number of children
    protected int nChildren = 0;
    
    
    RNode(String state, String county, double x1, double y1, double x2, double y2, int level, int id){
        
        //Set Name
        this.State = state;
        this.County = county;
        
        //Initialize HashMap
        this.children = new HashMap<String, RNode>();
        
        //Set rectangle attributes
        this.max_x = x1;
        this.max_y = y1;
        this.min_x = x2;
        this.min_y = y2;
        findCenter();
        
        //Set parent
        //this.parent = Parent;
        
        //Set level
        this.tree_level = level;
        
        //Set ID
        this.ID = id;
    }
    
    //Alternative /Default constructor 
    RNode(int id, int tree_level){
        this.State = "[no state]";
        this.County = "[no county]";
        this.children = new HashMap<String, RNode>();
        
        //Rectangle attributes
        this.max_x = Double.POSITIVE_INFINITY;
        this.min_x = Double.NEGATIVE_INFINITY;
        this.max_y = Double.POSITIVE_INFINITY;
        this.min_y = Double.NEGATIVE_INFINITY;
        
        //Set parent
        //this.parent = null;
        
        //Set level
        this.tree_level = tree_level;
        
        //Set ID
        this.ID = id;
    }

    public void addChild(RNode child) {
        
        
        String state_county = child.County + ", " +child.State;
        if (child.County == "[no county]"){
            this.children.put(state_county,child);
            this.nChildren++;
            System.out.println("Splitting, then adding navigation node....");
        }
        else if (children.containsKey(state_county)){
            System.out.println(state_county + " has already been added!");
        }
        else{
            this.children.put(state_county,child);
            this.nChildren++;
            System.out.println("Added " + state_county);
        }
    }

    public RNode getChild(String name) {
        return (RNode)children.get(name);
    }

    public void removeChild(String name) {
        try{
            children.remove(name);
            this.nChildren--;
            System.out.println(name + " removed.");
        }
        catch (Exception e){
            System.out.println("Child does not exist.");
        }
    }

    public void removeAllChildren() {
        children.clear();
        this.nChildren = 0;
    }

    public boolean hasChildren() {
        return children.isEmpty();
    }

    public int getNumberOfChildren() {
        Set keys = children.keySet();
        return keys.size();
    }

    public boolean intersects(RNode rectangle) {
        double width, height;
        
        //The rectangle for this node
        width = this.max_x - this.min_x;
        height = this.max_y - this.max_y;
        Rectangle thisNode = new Rectangle();
        thisNode.setRect(this.min_x, this.min_y, width, height);
		
        
        //Input rectangle
        width = rectangle.max_x - rectangle.min_x;
        height = rectangle.max_y - rectangle.min_y;
        Rectangle inputNode = new Rectangle();
        inputNode.setRect(rectangle.min_x, rectangle.min_y, width, height);
		
        return thisNode.intersects(inputNode);
    }

    public boolean contains(double x, double y) {
        if (x >= this.min_x && x <= this.max_x && y >= this.min_y && y <= this.max_y)
            return true;
        return false;
    }


    public double calculateExpansion(RNode rectangle){
        double expanded_x, expanded_y, expansion;
    
        expanded_x = Math.max(this.max_x, rectangle.max_x) - Math.min(this.min_x, rectangle.min_x);
        expanded_y = Math.max(this.max_y, rectangle.max_y) - Math.min(this.min_y, rectangle.min_y);
        expansion = expanded_x * expanded_y;

        return expansion - getArea();
    }
    
    
    public double getArea() {
        return (this.max_x - this.min_x) * (this.max_y - this.min_y);
    }
    
    public void findCenter(){
        this.center[0] = (this.max_x + this.min_x)/2;
        this.center[1] = (this.min_x + this.min_y)/2;
    }
    
    public void updateMBR(double x1, double y1, double x2, double y2){
        this.min_x = x1;
        this.min_y = y1;
        this.max_x = x2;
        this.max_y = y2;
    }
    
    public void updateCounty(String new_name){
        this.County = new_name;
    }
    
    public void updateState(String new_name){
        this.State = new_name;
    }
    
}
