public class MyStack<T> implements MyStackInterface<T>{
    // the code from the Weiss textbook for MyArrayList was sampled here.
    private static final int DEFAULT_CAPACITY = 10;
    private T [ ] theItems;
    private int theSize;
  
    //creates a stack with capacity 10 and size 0
    public MyStack() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    /* pushes x onto the top of the stack (back of the array)
     * if there is no more space in the stack the capacity is enlarged
    */
    public void push (T x) {
        if( theItems.length == size( ) )
            ensureCapacity( size( ) * 2 + 1 );
        theItems[size()] = x;
        theSize++;
    }

    // gets the item at the top of the stack (end of the array) and returns it
    public T pop() {
        T removedItem = theItems[size()-1];
        theSize--;
        return removedItem;
    }

    // returns the item at the top of the stack (end of the array)
    public T peek() {
      return theItems[size()-1];
    }

    public boolean isEmpty() {
      return (size() == 0);
    }

    public int size() {
      return theSize;
    }
    
    // creates a new array with double + 1 space of the previous array and fills the elements into it
    @SuppressWarnings("unchecked")
    private void ensureCapacity( int newCapacity )
    {
        if( newCapacity < theSize )
            return;

        T [ ] old = theItems;
        theItems = (T []) new Object[ newCapacity ];
        for( int i = 0; i < size( ); i++ )
            theItems[ i ] = old[ i ];
    }
	
}
