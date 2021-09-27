/**
    A class of bags whose entries are stored in a chain of linked nodes.
    The bag is never full.
    INCOMPLETE DEFINITION; includes definitions for the methods add,
    toArray, isEmpty, and getCurrentSize.
    @author Frank M. Carrano, Timothy M. Henry
    @version 5.0
*/
public final class LinkedBag<T> implements BagInterface<T>
{
	private Node firstNode;       // Reference to first node
	private int numberOfEntries;

	public LinkedBag()
	{
		firstNode = null;
      	numberOfEntries = 0;
	}

	/** Adds a new entry to this bag.
	    @param newEntry  The object to be added as a new entry.
	    @return  True. */
	public boolean add(T newEntry) // OutOfMemoryError possible
	{
    	// Add to beginning of chain:
		Node newNode = new Node(newEntry);
		newNode.next = firstNode;  // Make new node reference rest of chain
                                 // (firstNode is null if chain is empty)
      	firstNode = newNode;       // New node is at beginning of chain
		numberOfEntries++;
      
		return true;
	}

	/** Retrieves all entries that are in this bag.
       @return  A newly allocated array of all the entries in this bag. */
	public T[] toArray()
	{
		// The cast is safe because the new array contains null entries.
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast
		
		int index = 0;
		Node currentNode = firstNode;
		while ((index < numberOfEntries) && (currentNode != null))
		{
			result[index] = currentNode.data;
			index++;
			currentNode = currentNode.next;
		}
		
		return result;
		// Note: The body of this method could consist of one return statement,
		// if you call Arrays.copyOf
	}
   
	/** Sees whether this bag is empty.
       @return  True if the bag is empty, or false if not. */
	public boolean isEmpty()
	{
		return numberOfEntries == 0;
	}
   
	/** Gets the number of entries currently in this bag.
       @return  The integer number of entries currently in the bag. */
	public int getCurrentSize()
	{
		return numberOfEntries;
	}

	/** Removes one unspecified entry from this bag, if possible.
       @return  Either the removed entry, if the removal
                was successful, or null. */
	public T remove()
   	{
		T result = null;
		if (firstNode != null)
		{
			result = firstNode.data;
			firstNode = firstNode.next; // Remove first node from chain
			numberOfEntries--;
		}
		return result;
    }
   
	/** Removes one occurrence of a given entry from this bag.
       @param anEntry  The entry to be removed.
       @return  True if the removal was successful, or false otherwise. */
	public boolean remove(T anEntry)
	{
		boolean result = false;
		Node nodeN  = firstNode;

		boolean found = false;
		while (!found && (nodeN  != null))
		{
			if (anEntry.equals(nodeN .data))
				found = true;
			else
			nodeN  = nodeN.next;
		}

		if (nodeN != null)
		{
				// Replace located entry with entry in first node
			nodeN.data = firstNode.data; // nodeN to nodeN.data - same to 'firstNode'
				// Remove first node
			firstNode = firstNode.next;

				numberOfEntries--;

			result = true;
		}

		return result;
    }
	
	/** Removes all entries from this bag. */
	public void clear()
   {
      while (isEmpty())
	  	remove();
   }
	
	/** Counts the number of times a given entry appears in this bag.
		 @param anEntry  The entry to be counted.
		 @return  The number of times anEntry appears in the bag. */
	public int getFrequencyOf(T anEntry)
   {
		int frequency = 0;

		int counter = 0;
		Node currentNode = firstNode;
		while ((counter < numberOfEntries) && (currentNode != null))
		{
			if (anEntry.equals(currentNode.data))
			{
				frequency++;
			}

			counter++;
			currentNode = currentNode.next;
		}

		return frequency;
   }
	
	/** Tests whether this bag contains a given entry.
		 @param anEntry  The entry to locate.
		 @return  True if the bag contains anEntry, or false otherwise. */
	public boolean contains(T anEntry)
   {
	   boolean found = false;
	   Node currentNode = firstNode;

	   while (!found && (currentNode != null))
	   {
			if (anEntry.equals(currentNode.data))
				found = true;
			else
				currentNode = currentNode.next;
	   }

	   return found;
   }

	/** Creates a new bag that combines the contents of this bag and otherBag.
		@param otherBag The bag that is to be added.
  		@return  A combined bag */
	public BagInterface<T> union(BagInterface<T> otherBag)
	{
		T[] array = toArray();
		T[] otherArray = otherBag.toArray();
		LinkedBag<T> unionBag = new LinkedBag<T>();

		int i;
		// add entries to new from this bag
		for (i = 0; i < numberOfEntries; i++)
			unionBag.add(array[i]);

		// add entries to new bag from the second bag
		for (i = 0; i < otherBag.getCurrentSize(); i++)
			unionBag.add(otherArray[i]);

		return unionBag;
	}

	/** Creates a new bag that contains overlapping entries of this bag and otherBag.
		@param otherBag The bag that is to be added.
  		@return A bag with overlapping entries */
	public BagInterface<T> intersection(BagInterface<T> otherBag)
	{
		BagInterface<T> intersectionBag = new LinkedBag<T>();
		BagInterface<T> tempBag = new LinkedBag<T>();
		Node currentNode = ((LinkedBag<T>) otherBag).firstNode;
		
		// copy entries from otherBag to tempBag
		while(currentNode != null)
		{
			tempBag.add(currentNode.data);
			currentNode = currentNode.next;
		}
		
		currentNode = firstNode;
		while(currentNode != null)
		{
			if (tempBag.contains(currentNode.data))
			{
				// add the current entry of this bag to intersectionBag
				intersectionBag.add(currentNode.data);
				
				// remove the current entry of this bag from tempBag
				tempBag.remove(currentNode.data);
			}
			
			currentNode = currentNode.next;
		}
		
		return intersectionBag;
	}
	
	/** Creates a new bag that contains only unique entries of this bag and otherBag.
		@param otherBag The bag that is to be added.
  		@return A bag with unique entries */
	public BagInterface<T> difference(BagInterface<T> otherBag)
	{
		// declare the required objects and variables
		BagInterface<T> differenceBag = new LinkedBag<T>();
		Node currentNode = firstNode;
	
		// copy all entries from this bag to differenceBag
		while (currentNode != null)
		{          
			differenceBag.add(currentNode.data);              
			currentNode = currentNode.next;
		}
	
		// repeat the loop for all entries in the anotherBag
		currentNode = ((LinkedBag<T>) otherBag).firstNode;
		while (currentNode != null)
		{
			// check if differenceBag contains the current entry of this bag
			if(differenceBag.contains(currentNode.data))
			{
				// remove the current entry from the differenceBag
				differenceBag.remove(currentNode.data);
			}
		
			currentNode = currentNode.next;
		}      
	
		return differenceBag;
	}

	private class Node
	{
	  	private T    data; // Entry in bag
	  	private Node next; // Link to next node

		private Node(T dataPortion)
		{
			this(dataPortion, null);	
		}
		
		private Node(T dataPortion, Node nextNode)
		{
			data = dataPortion;
			next = nextNode;	
		}
	}

}