package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		this.size = 0;
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		// TODO: Implement this method
		add(size, element);
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of 
	 * bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (size == 0 || index < 0 || index >= size) {
			indexOutOfBounds();
		}
		
		LLNode<E> tmp = new LLNode<E>();
		if (index < size / 2) {
			int i = 0;
			tmp = head.next;
			while(i != index) {
				tmp = tmp.next;
				i++;
			} 
		}
		else {
			int i = size-1;
			tmp = tail.prev;
			while(i != index) {
				tmp = tmp.prev;
				i--;
			} 
		}
		return tmp.data;
	}
	
	
	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			nullPointer();
		}
		
		if (index < 0 || index > size) {
			indexOutOfBounds();
		}
		
		LLNode<E> elementToAdd = new LLNode<E>(element);
		
		if (size == 0 && index == 0) {
			head.next = elementToAdd;
			tail.prev = elementToAdd;
			elementToAdd.prev = head;
			elementToAdd.next = tail; 
		} else {
			if (index != size) { 
				LLNode<E> elementToShift = getNode(index);
				elementToAdd.prev = elementToShift.prev;
				elementToAdd.next = elementToShift;
				elementToAdd.prev.next = elementToAdd;
				elementToShift.prev = elementToAdd;	
			}
			else {
				elementToAdd.next = tail;
				elementToAdd.prev = tail.prev;
				tail.prev = elementToAdd;
				elementToAdd.prev.next = elementToAdd;
			}
		} 
		size++;
	}

	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index < 0 || index >= size || size == 0) {
			indexOutOfBounds();
		}
		
		LLNode<E> elementToRemove = getNode(index);
		elementToRemove.prev.next = elementToRemove.next;
		elementToRemove.next.prev = elementToRemove.prev;
		elementToRemove.prev = null;
		elementToRemove.next = null;
		size--;
		return elementToRemove.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) {
			nullPointer();
		}
		
		if (index < 0 || index >= size || size == 0) {
			indexOutOfBounds();
		}
		
		E elementToReplace = get(index);
		getNode(index).data = element;
		return elementToReplace;
	}   
	
	// Helper method that returns the node at the specified index
	LLNode<E> getNode(int index) 
	{
		// TODO: Implement this method.
		if (index < 0 || index > size || size == 0) {
			indexOutOfBounds();
		}
			
		LLNode<E> tmp = new LLNode<E>();
		if (index < size / 2) {
			int i = 0;
			tmp = head.next;
			while(i != index) {
				tmp = tmp.next;
				i++;
			} 
		}
		else {
			int i = size-1;
			tmp = tail.prev;
			while(i != index) {
				tmp = tmp.prev;
				i--;
			} 
		}
		return tmp;
	}
	
	// Helper method that throw index of bounds exception
	public void indexOutOfBounds() {
		throw new IndexOutOfBoundsException("Error! The index is "
				+ "out of bounds.");
	}
	
	// Helper method that throw null pointer exception
	public void nullPointer() {
		throw new NullPointerException("Error! Null pointer "
				+ "exception");
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode() 
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}	
}
