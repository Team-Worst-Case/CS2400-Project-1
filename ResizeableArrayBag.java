public final class ResizeableArrayBag<T> implements BagInterface<T>
{
	private final T[] bag;
	private int numberOfEntries;
   private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
   
	/** Creates an empty bag whose initial capacity is 25. */
	public ResizeableArrayBag()
	{
		this(DEFAULT_CAPACITY);
	}
   
	/** Creates an empty bag having a given capacity.
       @param desiredCapacity  The integer capacity desired. */
	public ResizeableArrayBag(int desiredCapacity)
	{
      if (desiredCapacity <= MAX_CAPACITY)
      {
         // The cast is safe because the new array contains null entries
         @SuppressWarnings("unchecked")
         T[] tempBag = (T[])new Object[desiredCapacity]; // Unchecked cast
         bag = tempBag;
         numberOfEntries = 0;
         integrityOK = true;
      }
      else
         throw new IllegalStateException("Attempt to create a bag whose" +
                                         "capacity exceeds allowed maximum.");
	}
   
	/** Adds a new entry to this bag.
       @param newEntry  The object to be added as a new entry.
       @return  True if the addition is successful, or false if not. */
	public boolean add(T newEntry)
	{
		checkIntegrity();
      boolean result = true;
      if (isArrayFull())
      {
         result = false;
      }
      else
      {  // Assertion: result is true here
         bag[numberOfEntries] = newEntry;
         numberOfEntries++;
      }
      
      return result;
	}
   
	/** Retrieves all entries that are in this bag.
       @return  A newly allocated array of all the entries in this bag. */
	public T[] toArray()
	{
		checkIntegrity();
      
      // The cast is safe because the new array contains null entries.
      @SuppressWarnings("unchecked")
      T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast
      
      for (int index = 0; index < numberOfEntries; index++)
      {
         result[index] = bag[index];
      }
      return result;
      // Note: The body of this method could consist of one return statement,
      // if you call Arrays.copyOf
	}
   
	/** Sees whether this bag is empty.
       @return  True if this bag is empty, or false if not. */
	public boolean isEmpty()
	{
      return numberOfEntries == 0;
	}
   
	/** Gets the current number of entries in this bag.
       @return  The integer number of entries currently in this bag. */
	public int getCurrentSize()
	{
      return numberOfEntries;
	}

	/** Counts the number of times a given entry appears in this bag.
       @param anEntry  The entry to be counted.
       @return  The number of times anEntry appears in this ba. */
	public int getFrequencyOf(T anEntry)
	{
		checkIntegrity();
      int counter = 0;
      
      for (int index = 0; index < numberOfEntries; index++)
      {
         if (anEntry.equals(bag[index]))
         {
            counter++;
         }
      }
      
      return counter;
	}
   
	/** Tests whether this bag contains a given entry.
       @param anEntry  The entry to locate.
       @return  True if this bag contains anEntry, or false otherwise. */
   public boolean contains(T anEntry)
	{
		checkIntegrity();
      boolean found = false;
		int index = 0;      
      while (!found && (index < numberOfEntries))
		{
			if (anEntry.equals(bag[index]))
			{
				found = true;
			}
         index++;
		}
      return found;
   }
   
	/** Removes one unspecified entry from this bag, if possible.
       @return  Either the removed entry, if the removal
                was successful, or null. */
	public T remove()
   {
      checkIntegrity();

      T result = null;
      if (numberOfEntries > 0)
      {
         result = bag[numberOfEntries - 1];
         bag[numberOfEntries - 1] = null;
         numberOfEntries -= 1;
      }
      return result;
   }
   
	/** Removes one occurrence of a given entry from this bag.
       @param anEntry  The entry to be removed.
       @return  True if the removal was successful, or false if not. */

   /** While loop goes through entries in 'bag', and if entry matches 
       item in bag, returns null. */
    
   public boolean remove(T anEntry)
   {
      int i = 0;
      while (i < numberOfEntries)
      {
         if (bag[i].equals(anEntry))
         {
            if (i < numberOfEntries)
            {
               bag[i] = bag[numberOfEntries - 1];
               bag[numberOfEntries - 1] = null;
               numberOfEntries--;
               return true;
            }
         }
         i++;
      }
      return false; 
   }
	
	/** Removes all entries from this bag. */
	public void clear()
   {
      for (int index = 0; index < numberOfEntries; index++)
         bag[index] = null;
      numberOfEntries = 0;
   }
   
   // Returns true if the array bag is full, or false if not.
	private boolean isArrayFull()
	{
		return numberOfEntries >= bag.length;
	}
   
   // Throws an exception if this object is not initialized.
   private void checkIntegrity()
   {
      if (!integrityOK)
         throw new SecurityException("ArrayBag object is corrupt.");
   }

   /** Creates a new bag that combines the contents of this bag and otherBag.
        @param otherBag The bag that is to be added.
        @return  A combined bag */
   public BagInterface <T> union(BagInterface<T> otherBag)
   {
      BagInterface <T> result = new ResizeableArrayBag <>();
      
      T[] others = otherBag.toArray();
      for (T elem: others)
         result.add(elem);

      T[] mine = this.toArray();

      for (T elem : mine)
         result.add(elem);

      return result; 
   }

   /** Creates a new bag that contains overlapping entries of this bag and otherBag.
        @param otherBag The bag that is to be added.
        @return A bag with overlapping entries */
   public BagInterface <T> intersection(BagInterface<T> otherBag)
   {
      BagInterface <T> result = new ResizeableArrayBag <>();
      BagInterface <T> finalResult = new ResizeableArrayBag<>();
      T[] mine = this.toArray();

      for (T elem : mine)
         result.add(elem);

      T[] others = otherBag.toArray();

      for (T elem : others)
         if (result.contains(elem))
            finalResult.add(elem);

      return finalResult; 
   }

   /** Creates a new bag that contains only unique entries of this bag and otherBag.
        @param otherBag The bag that is to be added.
        @return A bag with unique entries */
   public BagInterface <T> difference(BagInterface<T> otherBag)
   {
      BagInterface <T> result = new ResizeableArrayBag <>();
      T[] mine = this.toArray();

      for (T element : mine)
         result.add(element);
      
      T[] others = otherBag.toArray();
      for (T elem : others)
         if (result.contains(elem))
            result.remove(elem);
            
      return result; 
   }
   
}