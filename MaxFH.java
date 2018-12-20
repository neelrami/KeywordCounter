
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements a Max Fibonacci Heap and all the functions such as Insert, Increase Key, Remove Max etc.
 */
public class MaxFH 
{
	//This value is used to calculate the max degree of any node in a Fibonacci Heap
	private static final double goldenRatio=1.0/Math.log((1.0+Math.sqrt(5.0))/2.0);

	//This variable is used to keep track of number of nodes in a Fibonacci Heap at any particular time.
	private long numNodes;

	//This variable is used to keep track of Max Node in a Fibonacci Heap.
	private FHNode maxFHNode=new FHNode();

	public MaxFH()
	{
		numNodes=0;
		maxFHNode=null;
	}

	//The Hash Table is used to store Key Value pairs.
	//Here Key is the string and value is the object reference of Fibonacci Node.
	private Map<String,FHNode> myMap=new HashMap<String,FHNode>();

	/**
	 * This function calls insert operation if the string is not present in the Hash Table or
	 * it calls increaseKey if the string is present in the Hash Table
	 * 
	 * @param data - Type String
	 * @param count - Type long
	 * @return void
	 */
	public void processInput(String data, long count)
	{
		boolean flag=myMap.containsKey(data);
		if(flag==true)
		{
			//System.out.println("True");
			increaseKey(data,count);
		}
		else
		{
			//System.out.println("False");
			insert(data,count);
		}
	}

	/**
	 * This function creates a node for the string and inserts this node into the Fibonacci heap.
	 * The function also inserts the string and the node reference into the Hash Table
	 * @param data - Type String
	 * @param count - Type long
	 * @return void
	 */
	public void insert(String data, long count)
	{
		FHNode newNode=new FHNode();
		newNode.wordCount=count;
		newNode.setData(data);
		if(maxFHNode!=null)
		{
			newNode.setLeftSibling(maxFHNode);
			newNode.setRightSibling(maxFHNode.getRightSibling());
			maxFHNode.getRightSibling().setLeftSibling(newNode);
			maxFHNode.setRightSibling(newNode);
			if(maxFHNode.wordCount<newNode.wordCount)
			{
				maxFHNode=newNode;
			}
		}
		else
		{
			maxFHNode=newNode;
		}

		numNodes++;

		myMap.put(data, newNode);
		//System.out.println("Inserted in HashMap");
	}

	/**
	 * This function increases key for given string and calls cascading cut if the word count of a node
	 * is greater than its parent.
	 * @param data - Type String
	 * @param count - Type long
	 * @return void
	 */
	public void increaseKey(String data,long count)
	{

		FHNode myNode=myMap.get(data);
		myNode.wordCount+=count;
		FHNode myParent=myNode.getParent();
		if(myParent!=null)
		{
			if(myNode.wordCount>myParent.wordCount)
			{
				removeChild(myNode, myParent);
				cascadingCut(myParent);
			}
		}

		if(myNode.wordCount>maxFHNode.wordCount)
		{
			maxFHNode=myNode;
		}
	}

	/**
	 * This function removes node myNode and inserts it into root level list.
	 * This function is equivalent to cut function of Fibonacci Heap.
	 * @param myNode - Type FHNode
	 * @param myParent - Type FHNode
	 * @return void
	 */
	public void removeChild(FHNode myNode, FHNode myParent)
	{
		//Remove a node from its current level list
		myNode.getLeftSibling().setRightSibling(myNode.getRightSibling());
		myNode.getRightSibling().setLeftSibling(myNode.getLeftSibling());

		myParent.setDegree(myParent.getDegree()-1);

		if(myParent.getChild()==myNode)
		{
			myParent.setChild(myNode.getRightSibling());
		}

		if(myParent.getDegree()==0)
		{
			myParent.setChild(null);
		}

		//Insert a node into root level list
		myNode.setLeftSibling(maxFHNode);
		myNode.setRightSibling(maxFHNode.getRightSibling());
		maxFHNode.getRightSibling().setLeftSibling(myNode);
		maxFHNode.setRightSibling(myNode);

		myNode.setChildCut(false);

		myNode.setParent(null);

	}

	/**
	 * This function removes Max Node from Fibonacci heap.
	 * @param None
	 * @return FHNode
	 */
	public FHNode removeMax()
	{
		FHNode myNode=maxFHNode;
		if(myNode!=null)
		{
			long numChildren=myNode.getDegree();
			FHNode myChild=myNode.getChild();

			//Insert children of Max Node into root level list
			while(numChildren>0)
			{
				FHNode tempNode=myChild.getRightSibling();

				//Remove Child Node from its current list
				myChild.getLeftSibling().setRightSibling(myChild.getRightSibling());
				myChild.getRightSibling().setLeftSibling(myChild.getLeftSibling());

				//Insert Child Node into Doubly Linked List
				myChild.setLeftSibling(maxFHNode);
				myChild.setRightSibling(maxFHNode.getRightSibling());
				myChild.getRightSibling().setLeftSibling(myChild);
				maxFHNode.setRightSibling(myChild);

				myChild.setParent(null);
				myChild=tempNode;
				numChildren--;
			}

			//Remove Max Node from root level list
			myNode.getLeftSibling().setRightSibling(myNode.getRightSibling());
			myNode.getRightSibling().setLeftSibling(myNode.getLeftSibling());

			//Remove entry of Max Node from Hash Table
			removeFromHashTable(myNode.getData());
			if(myNode==myNode.getRightSibling())
			{
				maxFHNode=null;
			}
			else
			{
				maxFHNode=myNode.getRightSibling();
				enhancedRemove();
			}

			numNodes--;
		}
		else
		{
			System.out.println("Fibonacci Heap is empty. So Remove Max Operation is failed");
		}
		return myNode;
	}

	/**
	 * This function performs pairwise combine of nodes with same degree.
	 * @param None
	 * @return void
	 */
	public void enhancedRemove()
	{
		//Max Degree of any node in a Fibonacci Heap is log numNodes to the base goldenRatio.
		int maxDegree=((int) Math.floor(Math.log(numNodes)*goldenRatio))+1;
		List<FHNode> degreeTable=new ArrayList<FHNode>(maxDegree);

		//Initializing the Degree Table 
		for(int i=0; i<maxDegree; i++) 
		{
			degreeTable.add(null);
		}

		long numRoots=0;
		FHNode myNode=maxFHNode;

		//Calculating number of nodes in root level list
		if(myNode!=null) 
		{
			numRoots++;
			myNode=myNode.getRightSibling();

			while (myNode!=maxFHNode) 
			{
				numRoots++;
				myNode=myNode.getRightSibling();
			}
		}

		
		while(numRoots>0) 
		{
			long myDegree=myNode.getDegree();
			FHNode rightNode=myNode.getRightSibling();

			//Performing pairwise merging of nodes with same degree
			for(;;) 
			{
				//Finding a node with same degree as myNode
				FHNode newNode=degreeTable.get((int) myDegree);
				if(newNode==null) 
				{
					break;
				}

				
				if(myNode.getWordCount()<newNode.getWordCount()) 
				{
					FHNode tempNode=newNode;
					newNode=myNode;
					myNode=tempNode;
				}
				link(newNode,myNode);

				degreeTable.set((int) myDegree, null);
				myDegree++;
			}

			degreeTable.set((int) myDegree,myNode);

			myNode=rightNode;
			numRoots--;
		}

		//Resetting the Max Node Value
		maxFHNode = null;

		for (int i=0;i<maxDegree;i++) 
		{
			FHNode tempNode1=degreeTable.get(i);
			if(tempNode1==null) 
			{
				continue;
			}

			if (maxFHNode!=null) 
			{
				tempNode1.getLeftSibling().setRightSibling(tempNode1.getRightSibling());
				tempNode1.getRightSibling().setLeftSibling(tempNode1.getLeftSibling());

				tempNode1.setLeftSibling(maxFHNode);
				tempNode1.setRightSibling(maxFHNode.getRightSibling());
				maxFHNode.setRightSibling(tempNode1);
				tempNode1.getRightSibling().setLeftSibling(tempNode1);

				if(tempNode1.getWordCount()>maxFHNode.getWordCount()) 
				{
					maxFHNode = tempNode1;
				}
			} 
			else 
			{
				maxFHNode = tempNode1;
			}
		}
	}

	/**
	 * This function makes FHNode m a child of FHNode n
	 * @param m Type - FHNode
	 * @param n Type - FHNode 
	 * @return void
	 */
	public void link(FHNode m,FHNode n)
	{
		m.getRightSibling().setLeftSibling(m.getLeftSibling());
		m.getLeftSibling().setRightSibling(m.getRightSibling());

		m.setParent(n);
		if(n.getChild()==null)
		{
			n.setChild(m);
			m.setLeftSibling(m);
			m.setRightSibling(m);
		}
		else
		{
			m.setLeftSibling(n.getChild());
			m.setRightSibling(n.getChild().getRightSibling());
			n.getChild().getRightSibling().setLeftSibling(m);
			n.getChild().setRightSibling(m);
		}
		m.setChildCut(false);
		n.setDegree(n.getDegree()+1);
	}

	/**
	 * This function implements Cascading cut functionality of Fibonacci Heap
	 * @param myParent - Type FHNode
	 * @return void
	 */
	public void cascadingCut(FHNode myParent)
	{
		FHNode myGParent=myParent.getParent();
		if(myGParent!=null)
		{
			if(myParent.isChildCut()==false)
			{
				myParent.setChildCut(true);
			}
			else
			{
				removeChild(myParent,myGParent);
				cascadingCut(myGParent);
			}
		}
	}

	public long getNumNodes() 
	{
		return numNodes;
	}

	public FHNode getMaxFHNode() 
	{
		return maxFHNode;
	}

	/**
	 * This function removes key value pair from Hash Table
	 * @param data - Type String
	 * @return void
	 */
	public void removeFromHashTable(String data)
	{
		myMap.remove(data);
	}

	/**
	 * This functions returns top k frequent words.
	 * @param k - Type long
	 * @return List
	 */
	public List<String> findTopK(long k)
	{
		List<String> answerString=new ArrayList<String>((int) k);
		List<FHNode> answerNode=new ArrayList<FHNode>((int) k);
		//Remove Max Node and Insert keyword into List
		for(int i=0;i<k;i++)
		{
			FHNode removedNode=removeMax();
			answerString.add(removedNode.getData());
			answerNode.add(removedNode);
		}

		//Reinsert the node back into Fibonacci Heap.
		for(FHNode fh : answerNode) 
		{
			//System.out.println(fh.getWordCount());
			processInput(fh.getData(),fh.getWordCount());
		}
		return answerString;

	}
}
