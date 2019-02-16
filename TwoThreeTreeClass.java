package p8_package;

public class TwoThreeTreeClass
{
    /**
     * 2-3 node class using NodeDataClass data and three references
     * 
     * @author MichaelL
     */
    private class TwoThreeNodeClass
       {
        /**
         * internal 2-3 node data
         */
        private int leftData, centerData, rightData, numItems;
        
        /**
         * references from 2-3 node
         */
        private TwoThreeNodeClass leftChildRef, centerChildRef, rightChildRef;
        
        /**
         * references for managing 2-3 node adjustments
         */
        private TwoThreeNodeClass auxLeftRef, auxRightRef;
        
        /**
         * parent reference for 2-3 node
         */
        private TwoThreeNodeClass parentRef;
        
        /**
         * Default 2-3 node class constructor
         */
        private TwoThreeNodeClass()
           {
            leftData = centerData = rightData = numItems = 0;
            
            leftChildRef = centerChildRef = rightChildRef = null;
            
            auxLeftRef = auxRightRef = parentRef = null;
           }
        
        /**
         * Initialization 2-3 node class constructor
         * 
         * @param centerIn integer data sets first node initialization
         */
        private TwoThreeNodeClass( int centerIn )
           {
            centerData = centerIn;

            leftData = rightData = 0;
            
            numItems = 1;
            
            leftChildRef = centerChildRef = rightChildRef = null;
            
            auxLeftRef = auxRightRef = parentRef = null;
           }

        /**
         * Private constructor used to create new left or right child node
         * of given parent with the children linked from
         * a current three-node object
         *
         * @param childSelect integer selection value that informs 
         * the constructor to create a left or a right child
         * along with making all the sub-child links; 
         * uses class constants LEFT_CHILD_SELECT and RIGHT_CHILD_SELECT
         * 
         * @param localRef TwoThreeNodeClass reference
         * with three-node data and associated references
         * 
         * @param parRef TwoThreeNodeclass parent reference
         * for linking with new left or right child node that is created
         */
        private TwoThreeNodeClass( int childSelect, 
                       TwoThreeNodeClass localRef, TwoThreeNodeClass parRef )
           {
            if( childSelect == LEFT_CHILD_SELECT )
               {
                this.centerData = localRef.leftData;
                this.leftChildRef = localRef.leftChildRef;
                this.rightChildRef = localRef.auxLeftRef;
                
                if( leftChildRef != null )
                   {
                    this.leftChildRef.parentRef = this;
                    this.rightChildRef.parentRef = this;
                   }
               }
            
            else  // assume right child select
               {
                this.centerData = localRef.rightData;
                this.leftChildRef = localRef.auxRightRef;
                this.rightChildRef = localRef.rightChildRef;

                if( rightChildRef != null )
                   {
                    this.leftChildRef.parentRef = this;
                    this.rightChildRef.parentRef = this;
                   }
               }

            this.leftData = this.rightData = 0;
            this.numItems = 1;
            this.centerChildRef = null;
            this.auxLeftRef = this.auxRightRef = null;
            this.parentRef = parRef;
           }
        
        /**
         * Copy 2-3 node class constructor
         * <p>
         * Note: Only copies data; does not copy links 
         * as these would be incorrect for the new tree 
         * (i.e., they would be related to the copied tree)
         * 
         * @param copied TwoThreeNodeClass object to be copied
         */
        private TwoThreeNodeClass( TwoThreeNodeClass copied )
           {
            leftData = copied.leftData;
            centerData = copied.centerData;
            rightData = copied.rightData;
            
            numItems = copied.numItems;

            leftChildRef = centerChildRef = rightChildRef = null;
            
            auxLeftRef = auxRightRef = parentRef = null;
           }
       }

    /**
     * constant used in constructor to indicate which child to create - Left
     */
    private int LEFT_CHILD_SELECT = 101;
    
    /**
     * constant used for identifying one data item stored
     */
    private int ONE_DATA_ITEM = 1;
    
    /**
     * Used for acquiring ordered tree visitations in String form
     */
    private String outputString;
    
    /**
     * constant used in constructor to indicate which child to create - Right
     */
    private int RIGHT_CHILD_SELECT = 102;
    
    /**
     * root of tree
     */
    private TwoThreeNodeClass root;
    
    /**
     * constant used for identifying three data items stored
     */
    private int THREE_DATA_ITEMS = 3;
    
    /**
     * constant used for identifying two data items stored
     */
    private int TWO_DATA_ITEMS = 2;
    
    /**
     * Default 2-3 constructor
     */
    public TwoThreeTreeClass()
    {
        root = null;
    }
    
    /**
     * Copy 2-3 Tree Constructor
     * 
     * @param copied - TwoThreeTreeClass object to be duplicated; data is 
     * copied, but new nodes and references must be created
     */
    public TwoThreeTreeClass( TwoThreeTreeClass copied )
    {
        copyConstructorHelper( root );
    }
    
    /**
     * Implements tree duplication effort with recursive method; copies data 
     * into newly created nodes and creates all new references to child nodes
     * 
     * @param workingCopiedRef - TwoThreeNodeClass reference that is updated 
     * to lower level children with each recursive call
     * 
     * @return TwoThreeNodeClass reference to next higher level node; 
     * last return is to root node of THIS object
     */
    private TwoThreeNodeClass copyConstructorHelper( 
            TwoThreeNodeClass workingCopiedRef )
    {
        TwoThreeNodeClass localNode = null;

        if( workingCopiedRef != null )
        {
            localNode = new TwoThreeNodeClass( workingCopiedRef );

            localNode.leftChildRef 
                    = copyConstructorHelper( workingCopiedRef.leftChildRef );

            if( localNode.leftChildRef != null )
            {
                localNode.leftChildRef.parentRef = localNode;
            }

            localNode.centerChildRef 
                    = copyConstructorHelper( workingCopiedRef.centerChildRef );

            if( localNode.centerChildRef != null )
            {
                localNode.centerChildRef.parentRef = localNode; 
            }

            localNode.rightChildRef 
                    = copyConstructorHelper( workingCopiedRef.rightChildRef );

            if( localNode.rightChildRef != null )
            {
                localNode.rightChildRef.parentRef = localNode;
            }
        }

        return localNode;
    }
    
    /**
     * Method is called when addItemHelper arrives at the bottom of the 2-3 
     * search tree (i.e., all node's children are null);
     * <p>
     * Assumes one- or two- value nodes and adds one more to the appropriate 
     * one resulting in a two- or three- value node
     * 
     * @param localRef - TwoThreeNodeClass reference has original node data 
     * and contains added value when method completes; method does not manage
     * any node links
     * 
     * @param itemVal - integer value to be added to 2-3 node
     */
    private void addAndOrganizeData( TwoThreeNodeClass localRef, int itemVal )
    {
        if( localRef.numItems == ONE_DATA_ITEM )
        {
            if( itemVal > localRef.centerData )
            {
                localRef.leftData = itemVal;
                
                localRef.rightData = localRef.centerData;
                
                localRef.numItems += 1;
            }
            
            else {
                localRef.rightData = itemVal;
                
                localRef.leftData = localRef.centerData;
                
                localRef.numItems += 1;
            }
        }
        
        if( localRef.numItems == TWO_DATA_ITEMS )
        {
            if( itemVal < localRef.centerData )
            {
                localRef.centerData = localRef.leftData;
                
                localRef.leftData = itemVal;
                
                localRef.numItems += 1;
            }
            
            if( itemVal > localRef.centerData )
            {
                localRef.centerData = localRef.rightData;
                
                localRef.rightData = itemVal;
                
                localRef.numItems += 1;
            }
            
            else
            {
                localRef.centerData = itemVal;
                
                localRef.numItems += 1;
            }
        }
    }
    
    /**
     * Adds item to 2-3 tree using addItemHelper as needed
     * 
     * @param itemVal - integer value to be added to the tree
     */
    public void addItem( int itemVal )
    {
        if( root == null )
        {
            root = new TwoThreeNodeClass( itemVal );
            
            
        }
        
        addItemHelper( root.parentRef, root, itemVal );
    }
    
    /**
     * Helper method searches from top of tree to bottom using divide and 
     * conquer strategy to find correct location (node) for new added value;
     * once location is found, item is added to node using addAndOrganizeData
     * and then fixUpInsert is called in case the updated node has become a 
     * three-value node
     * 
     * @param parRef - TwoThreeNodeClass reference to the parent of the current
     * reference at a given point in the recursion process
     * 
     * @param localRef - TwoThreeNodeClass reference to the current item at 
     * the same given point in the recursion process
     * 
     * @param itemVal - integer value to be added to the tree
     */   
    private void addItemHelper( 
            TwoThreeNodeClass parRef, TwoThreeNodeClass localRef, int itemVal )
    {
        // TWO item node always has 3 children, ONE item node always have 2 
        // children, and 3 always has 4 children, etc.
        
        if( localRef.numItems == ONE_DATA_ITEM && localRef.leftChildRef != null )
        {
            if( localRef.centerData > itemVal )
            {
                addItemHelper( localRef, localRef.leftChildRef, itemVal );
            }
            else
            {
                addItemHelper( localRef, localRef.rightChildRef, itemVal );
            }
        }
        
        else if( localRef.numItems == TWO_DATA_ITEMS && localRef.centerChildRef != null )
        {
            if( localRef.leftData > itemVal )
            {
                addItemHelper( localRef, localRef.leftChildRef, itemVal );
            }
            
            else if( localRef.rightData < itemVal )
            {
                addItemHelper( localRef, localRef.rightChildRef, itemVal );
            }
            
            else
            {
                addItemHelper( localRef, localRef.leftChildRef, itemVal );
            }
        }
        
        else 
        {
            addAndOrganizeData(localRef, itemVal);
            
            fixUpInsert( localRef );
        }
    }
    /**
     * method clears tree so that new items can be added again
     */
    public void clear()
    {
        root = null;
    }
    
    /**
     * Method used to fix tree any time a three-value node has been added to 
     * the bottom of the tree; it is always called but decides to act only if 
     * it finds a three-value node
     * <p>
     * Resolves current three-value node which may add a value to the node 
     * above; if the node above becomes a three-value node, then this is 
     * resolved with the next recursive call
     * <p>
     * Note: Method does not use any branching operations such as if/else/etc.

     * @param localRef - TwoThreeNodeClas reference initially given the 
     * currently updated node, then is given parent node recursively each 
     * time a three-value node was resolved
     */
    private void fixUpInsert( TwoThreeNodeClass localRef )
    {
        //check for is local ref a three node
        if( localRef.numItems == THREE_DATA_ITEMS )
        {
            // check for no parent
            if( localRef.parentRef == null )
            {
                // set parent ref to center data - constructor
                localRef.parentRef = new TwoThreeNodeClass(
                                        localRef.centerData );
                
                // set root to parent
                root = localRef.parentRef;
                
                localRef.parentRef.leftChildRef = new TwoThreeNodeClass(
                        LEFT_CHILD_SELECT, localRef, localRef.parentRef );
                
                localRef.parentRef.rightChildRef = new TwoThreeNodeClass( 
                        RIGHT_CHILD_SELECT, localRef, localRef.parentRef );
            }
            //check for one item parent
            if( localRef.parentRef.numItems == ONE_DATA_ITEM )
            {
                //set num items to 2
                localRef.parentRef.numItems = TWO_DATA_ITEMS;
                
                //check if local ref is the left child of the parent
                if( localRef == localRef.parentRef.leftChildRef )
                {
                    //set parent center data to right data
                    localRef.parentRef.centerData = localRef.parentRef.rightData;
                    
                    //set local center data to parent left data
                    localRef.centerData = localRef.parentRef.leftData;
                    
                    //set parent left child ref to a new node - set LEFT
                    localRef.parentRef.leftChildRef = new TwoThreeNodeClass( 
                            LEFT_CHILD_SELECT, localRef, localRef.parentRef );
                    //set parent center child ref to a new node - set to RIGHT
                    localRef.parentRef.centerChildRef = new TwoThreeNodeClass( 
                            RIGHT_CHILD_SELECT, localRef, localRef.parentRef );
                }
                
                //assume local ref is right child
                //do same as above for right child
                
            }
            
            //otherwise assume 2 item parent
            //set parent's num items to 3
            localRef.parentRef.numItems = THREE_DATA_ITEMS;
            
            if( localRef == localRef.parentRef.leftChildRef )
            {
                //set parent's left data to its center data
                localRef.parentRef.leftData = localRef.parentRef.centerData;
                
                // set hcild's center data to parent's left data
                localRef.centerData = localRef.parentRef.leftData;
                
                // Set parent's left child to new node sing const - LEFT
                localRef.parentRef.leftChildRef = new TwoThreeNodeClass( 
                        LEFT_CHILD_SELECT, localRef, localRef.parentRef );
                
                // set parent's aux left to new node using const - RIGHT
                localRef.parentRef.auxLeftRef = new TwoThreeNodeClass( 
                        RIGHT_CHILD_SELECT, localRef, localRef.parentRef );
                
                // set parent's center link to parent's aux right link
                localRef.parentRef.centerChildRef = localRef.parentRef.auxRightRef;
            }
            
            //check for local ref is parent's center child
            if( localRef == localRef.parentRef.centerChildRef )
            {
                
            }
        }
    }
    
    /**
     * Tests center value if single node, tests left and right values if 
     * two-value node; returns true if specified data is found in any given node
     * <p>
     * Note: Method does not use any branching operations such as if/else/etc.
     * 
     * @param localRef - TwoThreeNodeClass reference to node with one or 
     * two data items in it
     * 
     * @param searchVal - integer value to be found in given node
     * 
     * @return boolean result of test
     */
    private boolean foundInNode( TwoThreeNodeClass localRef, int searchVal )
    {
        boolean oneItemFound = localRef.numItems == ONE_DATA_ITEM && 
                localRef.centerData == searchVal;
        
        boolean twoItemFoundLeft = localRef.numItems == TWO_DATA_ITEMS &&
                localRef.leftData == searchVal;
        
        boolean twoItemFoundRight = localRef.numItems == TWO_DATA_ITEMS &&
                localRef.rightData == searchVal;
                
        return oneItemFound || twoItemFoundLeft || twoItemFoundRight;
    }
    
    /**
     * Public method called by user to display data in order
     * 
     * @return String result to be displayed and/or analyzed
     */
    public String inOrderTraversal()
    {
        outputString = "";
        
        inOrderTraversalHelper( root );
        
        return outputString;
    }
    
    /**
     * Helper method conducts in order traversal with 2-3 tree
     * <p>
     * Shows location of each value in a node: "C" at center of single node 
     * "L" or "R" at left or right of two-value node
     * 
     * @param localRef - TwoThreeNodeClass reference to current location at 
     * any given point in the recursion process
     */
    private void inOrderTraversalHelper( TwoThreeNodeClass localRef )
    {
        if( localRef == null )
        {
            return;
        }
        
        if( localRef.numItems == ONE_DATA_ITEM )
        {
            outputString += localRef.toString();
        }
        
        else if( localRef.numItems == TWO_DATA_ITEMS )
        {
            inOrderTraversalHelper( localRef.leftChildRef );
            
            outputString += localRef.toString();
            
            inOrderTraversalHelper( localRef.rightChildRef );
        }
    }
    
    /**
     * Search method used by programmer to find specified item in 2-3 tree
     * 
     * @param searchVal - integer value to be found
     * 
     * @return boolean result of search effort
     */
    public boolean search( int searchVal )
    {
        return searchHelper( root, searchVal );
    }
    
    /**
     * Search helper method that traverses through tree in a recursive divide 
     * and conquer search fashion to find given integer in 2-3 tree
     * 
     * @param localRef - localRef - TwoThreeNodeClass reference to given node 
     * at any point during the recursive process
     * 
     * @param searchVal - integer value to be found in tree
     * 
     * @return boolean result of search effort
     */
    private boolean searchHelper( TwoThreeNodeClass localRef, int searchVal )
    {
        if( localRef == null )
        {
            return false;
        }
        
        if( foundInNode( localRef, searchVal ) )
        {
            return true;
        }
        
        else if( localRef.numItems == TWO_DATA_ITEMS )
        {
            if( searchVal < localRef.leftData )
            {
                return searchHelper( localRef.leftChildRef, searchVal );
            }
            
            else if( searchVal > localRef.rightData )
            {
                return searchHelper( localRef.rightChildRef, searchVal );
            }
            
            else
            {
                return searchHelper( localRef.centerChildRef, searchVal );
            }
        }
        
        else
        {
            if( searchVal < localRef.leftData )
            {
                return searchHelper( localRef.leftChildRef, searchVal );
            }
            else if( searchVal > localRef.leftData)
            {
                return searchHelper(localRef.rightChildRef, searchVal);
            }
            else 
            {
                return searchHelper(localRef.centerChildRef, searchVal);
            }
        }

    }
}
