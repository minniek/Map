package RTree;


/**
Skeleton interface for the R-tree
*/

public interface Skeleton{
    
    /**
    * Adds a node to the tree.
    *    @param node       - the node to be added
    *    @param node_id    - the node's unique ID
    *    @param tree_level - level of the tree to add the node to
    */
    public void add(RNode node, int node_id, int tree_level);
    
    
    /**
     * Deletes a node from the tree given an ID
     *    @param id is the id of the node to be deleted
     */
    public void delete(String name);
    
    
    
    /**
     * For splitting a node
     *     @param node is the current node to split
     *     @param new_min_x is the new min_x value
     *     @param new_min_y ----''---- min_y --''--
     *     @param new_max_x ----''---- max_x --''--
     *     @param new_max_y ----''---- max_y --''--
     */
    public RNode split(RNode node, RNode extra, int ID);
    
    
    
    //For picking seeds (called in "split")
    public void pickSeeds(RNode node,  RNode new_node, double new_min_x, double new_min_y, double new_max_x, double new_max_y);
    
    
    
    //Called during "split"
    public int getNextNode();
    
    
    //Chooses the leaf to add a node to
    public RNode chooseLeaf(RNode node, int tree_level);
    
    //Get node by id
    public RNode getNode(int ID);
    
}
