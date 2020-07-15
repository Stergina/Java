import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Implements the methods for a Stack that handles any data type
 */
public class StringStackImpl < T > implements StringStack < T > {
    private ListNode < T > firstNode; //Stack top.
    private int n; //Size of the stack.

    //Initializes an empty stack.
    public StringStackImpl() {
        firstNode = null;
        n = 0;
    } //StackImpl

    //Returns true if stack is empty.
    public boolean isEmpty() {
        return firstNode == null;
    } //isEmpty

    //Inserts an item to the stack.
    public void push(T item) {
        ListNode < T > node = new ListNode < T > (item);
        if (isEmpty())
            firstNode = node;
        else {
            node.nextNode = firstNode;
            firstNode = node;
        }
        n++; //Increasing stack size by one.
    } //push

    //Returns and removes the item on the top of the stack.
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        T removedObject = firstNode.data;
        firstNode = firstNode.nextNode;
        n--; //Decreasing size by one.
        return removedObject;
    } //pop

    //Returns the item on the top of the stack.
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return firstNode.data;
    } //peek

    //Prints all the elements of the stack.
    public void printStack(PrintStream stream) {
        if (isEmpty()) {
            System.out.printf("Empty Stack");
        } else {
            //System.out.printf("The stack is:");
            ListNode < T > current = firstNode;
            while (current != null) {
                System.out.printf("%s", current.data);
                current = current.nextNode;
            }
            System.out.println("\n");
        }
    } //printStack

    //Returns the size of the stack.
    public int size() {
        return n;
    } //size
}
