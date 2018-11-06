package com.company;
public class Queue<V> {
    // Instance Variables. Add the tail reference.
    protected NodoD<V> head;
    protected NodoD<V> tail;
    protected long size;
 
    // Empty queue constructor first
    public Queue() {
        head = null;
        tail = null;
        size = 0;
    } 
 
    /**
     * Method to add Nodes to the queue.
     * Storage space for the Node is already allocated in the calling method
     */
    public void add(NodoD<V> newNode) {
        // Set the tail only if this is the very first Node
        if (tail == null && head == null) addVeryFirst(newNode);
        else {
            newNode.setNext(head); // Make next of the new Node refer to the head
            newNode.setBefore(null);
            head.setBefore(newNode); // Make the double link
            head = newNode; // Give head a new value
        }
        // change the size
        size++;
    } 
 
    // Add very first node in the double queue
    private void addVeryFirst(NodoD<V> newNode) {
        head = newNode; // Give head a new value
        newNode.setNext(null);
        newNode.setBefore(null);
        tail = newNode; // Give tail a new value
    }
 
    /**
     * Remove the Node at the end of the queue.
     * Since the queue is double linked,
     * there is no need to traverse the queue.
     */
    public NodoD<V> remove() {
        // Declare local variables/objects
        NodoD<V> Before;
        NodoD<V> ToRemove;
 
        // Make sure we have something to remove
        if (size == 0)
            System.err.println("Error:  Attempt to remove fron an empty queue");

        // Save the node before the last Node
        Before = tail.getBefore();
        // Save the last Node
        ToRemove = tail;
        
        if ( head == tail ) { /* Is the last Node in the queue */
            head = null;
            tail = null;
        }
        else {
            // Let's do the pointer manipulation
            Before.setNext(null);
            tail = Before;
        }
        ToRemove.setBefore(null);
        ToRemove.setNext(null);
        size--;
 
        return ToRemove;
    } 
 
    // The gets to return the head and/or tail Nodes and size of the queue
    public NodoD<V> getFirst() {
        return head;
    }
 
    public NodoD<V> getLast() {
        return tail;
    }
 
    public long getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return ( size == 0 )?true:false;
    }

}
