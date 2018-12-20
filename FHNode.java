/**
 This java file contains structure of a Fibonacci Heap Node.
 */

public class FHNode 
{
	// Field to store the string.
	String data;
	
	// Field to store count of the string.
	long wordCount;
	
	// Field to store number of children i.e degree of a node. 
	long degree;
	
	//Field that points to a child.
	FHNode child;
	
	/*
	 Left Sibling and Right Sibling fields are used to implement Doubly Circular Linked List 
	 */
	
	//Field that points to its left sibling.
	FHNode leftSibling;
	
	//Field that points to its right sibling.
	FHNode rightSibling;
	
	//Field that points to its parent.
	FHNode parent;
	
	/*
	 * In Fibonacci Heap, the childCut value  of root is undefined but 
	 * here we set the childCut Filed of root as false.
	 */
	
	//childCut Field is either true or false.
	boolean childCut;

	
	/**
	 Construction of the class.
	 Here all the fields of Fibonacci Heap are initialized to their default values.
	 *
	 @param 
	 @return void
	 */
	public FHNode()
	{
		childCut=false;
		leftSibling=this;
		rightSibling=this;
		wordCount=0;
		degree=0;
		parent=null;
		child=null;
	}
	
	/**
	 * A getter method to get parent of a particular node.
	 * @param None
	 * @return parent - Type FHNode
	 */
	public FHNode getParent() 
	{
		return parent;
	}

	/**
	 * A setter method used to set parent of a particular node.
	 * @param parent - Type FHNode
	 * @return void
	 */
	public void setParent(FHNode parent) 
	{
		this.parent = parent;
	}
	
	/**
	 * A getter method used to get data of a particular node.
	 * @param None
	 * @return data - Type String
	 */
	public String getData() 
	{
		return data;
	}
	
	/**
	 * A setter method used to set data of a particular node.
	 * @param data - Type String
	 * @return void
	 */
	public void setData(String data) 
	{
		this.data = data;
	}

	/**
	 * A getter method used to get wordCount of a particular node.
	 * @param None
	 * @return wordCount - Type long
	 */
	public long getWordCount() 
	{
		return wordCount;
	}

	/**
	 * A setter method used to set wordCount of a particular node.
	 * @param wordCount - Type long
	 * @return void
	 */
	public void setWordCount(long wordCount) 
	{
		this.wordCount = wordCount;
	}

	/**
	 * A getter method used to get degree of a particular node.
	 * @param None
	 * @return degree - Type long
	 */
	public long getDegree() 
	{
		return degree;
	}

	/**
	 * A setter method used to set degree of a particular node.
	 * @param degree - Type long
	 * @return void
	 */
	public void setDegree(long degree) 
	{
		this.degree = degree;
	}

	/**
	 * A getter method used to get child of a particular node.
	 * @param None
	 * @return child - Type FHNode
	 */
	public FHNode getChild() 
	{
		return child;
	}

	/**
	 * A setter method used to set child of a particular node.
	 * @param child - Type FHNode
	 * @return void
	 */
	public void setChild(FHNode child) 
	{
		this.child = child;
	}

	/**
	 * A getter method used to get leftSibling of a particular node.
	 * @param None
	 * @return leftSibling - Type FHNode
	 */
	public FHNode getLeftSibling() 
	{
		return leftSibling;
	}

	/**
	 * A setter method used to set leftSibling of a particular node.
	 * @param leftSibling - Type FHNode
	 * @return void
	 */
	public void setLeftSibling(FHNode leftSibling) 
	{
		this.leftSibling = leftSibling;
	}

	/**
	 * A getter method used to get rightSibling of a particular node.
	 * @param None
	 * @return rightSibling - Type FHNode
	 */
	public FHNode getRightSibling() 
	{
		return rightSibling;
	}

	/**
	 * A setter method used to set rightSibling of a particular node.
	 * @param rightSibling - Type FHNode
	 * @return void
	 */
	public void setRightSibling(FHNode rightSibling) 
	{
		this.rightSibling = rightSibling;
	}

	/**
	 * A getter method used to get childCut value of a particular node.
	 * @param None
	 * @return childCut - Type boolean
	 */
	public boolean isChildCut() 
	{
		return childCut;
	}

	/**
	 * A setter method used to set childCut of a particular node.
	 * @param childCut - Type boolean
	 * @return void
	 */
	public void setChildCut(boolean childCut) 
	{
		this.childCut = childCut;
	}
}