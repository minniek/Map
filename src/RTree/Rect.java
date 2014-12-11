package RTree;

//Rectangle interface.
public interface Rect{
    
    
    /**
    * Child specific commands.
    */
    RNode getChild(String name);
    void removeChild(String name); //remove a child given a name
    void removeAllChildren();      //remove all children
    boolean hasChildren();         //does the node have children?
    int getNumberOfChildren();     //get total number of children
    
    /**
    * Check if a rectangle intersects or is contained inside.
    */
    boolean intersects(RNode node);       //does a rectangle intersect this rectangle?
    boolean contains(double x, double y); //is a rectangle contained in this rectangle?
    
    /**
     * Rectangle attributes.
     */
    double calculateExpansion(RNode rectangle); //calculate how much our rectangle would expand if we were to add a new point
    double getArea();
    
}
