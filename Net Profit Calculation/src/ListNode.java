/**
 * ListNode represents a single-link list node
 * Each node contains an T reference to data and a reference to the nextNode in the list. 
 */
class ListNode<T>
{
	// package access members; List can access these directly
	T data;
	ListNode<T> nextNode;

	/**
	 * Constructor. It initializes data and sets next node to null 
	 * @param object a reference to node's data
	 */
	ListNode(T data)
	{
		this(data, null);
	} 
	
	/**
	 * constructor creates ListNode with passed data and next node
	 * @param object the reference to node's data
	 * @param node the next node in the list
	 */
	ListNode(T data,ListNode<T> node)
	{
		this.data = data;
		nextNode = node;
	}

	/**
	 * Returns this node's data
	 * @return the reference to node's data
	 */
	T getObject()
	{
		return data; 
	}

	/**
	 * Get reference to next node
	 * @return the next node
	 */
	ListNode<T> getNext()
	{
		return nextNode; 
	} 
} 