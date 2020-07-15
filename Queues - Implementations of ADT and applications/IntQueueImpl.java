import java.io.PrintStream;
import java.util.NoSuchElementException; 

/**
 * Implements the methods for a Queue that handles any data type
 */
public class IntQueueImpl<T> implements IntQueue<T>
{ 
	private ListNode<T> firstNode;	//Queue top.
	private ListNode<T> lastNode;	//Queue bottom.
    private int n; //Size of the queue.
 
    //Initializes an empty queue.
    public IntQueueImpl() 
    {
    	firstNode = lastNode = null;
        n = 0;
    }//QueueImpl
    
    //Returns true if queue is empty.
    public boolean isEmpty() 
    {
        return firstNode == null;
    }//isEmpty

    //Inserts an item to the queue.
	public void put(T item)
	{
		ListNode<T> node = new ListNode<T>(item);
		if (isEmpty()) 
			firstNode = lastNode = node;
		else{
			node.nextNode = firstNode;
			firstNode = node;
		}
        n++; //Increasing stack size by one.
	}//put

	//Returns and removes the oldest item of the queue.
	public T get() 
	{
		if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T removedObject = lastNode.data;
        ListNode<T> current = firstNode;
        
        if (firstNode==lastNode)
        	firstNode = lastNode = null;
        else {
	        //Loop while current node does not refer to lastNode
	        while (current.nextNode!=lastNode)
	        	current=current.nextNode;
	        lastNode = current;
	        current.nextNode = null;
        }
        n--; //Decreasing size by one.
        return removedObject;           
    }//get

	//Returns the oldest item of the queue.
	public T peek() 
	{
		if (isEmpty()) throw new NoSuchElementException("Queue is empty");
		return firstNode.data;
	}//peek

	//Prints all the elements of the queue.
	public void printQueue(PrintStream stream)
	{
		if (isEmpty())
		{
			System.out.printf("Empty Queue");
		}else{
			System.out.printf("The queue is:");
			ListNode<T> current = firstNode;
			while (current!=null)
			{
				System.out.printf("%s",current.data);
				current = current.nextNode;
			}
			System.out.println("\n");
		}			
	}//printQueue
    
	//Returns the size of the queue.
	public int size()
	{
		return n;
	}//size
}
