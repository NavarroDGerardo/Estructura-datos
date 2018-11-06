package com.company;
public class Stack<V> {
    // Instance Variables. Add the tail reference.
    protected Nodo<V> head;
    protected Nodo<V> tail;
    protected long size;
 
    // Empty list constructor first
    public Stack() {
        head = null;
        tail = null;
        size = 0;
    } 
 
    /**
     * Method to add Nodes to the list.
     * Storage space for the Node is already allocated in the calling method
     */
    public void push(Nodo<V> newNode) {
        // Set the tail only if this is the very first Node
        if (tail == null && head == null) addVeryFirst(newNode);
        else {
            newNode.setNext(head); // Make next of the new Node refer to the head
            head = newNode; // Give head a new value
        }
        // change the size
        size++;
    } 
 
    /**
     * Methods to remove Nodes from the list.
     */
    public Nodo<V> pop() {
        if (head == null)
            System.err.println("Error:  Attempt to remove from an empty list");
 
        // save the one to return
        Nodo<V> temp = head;
 
        // do reference manipulation
        head = head.getNext();
        temp.setNext(null);
        size--;
 
        return temp;
    }

    // Add very first node in the single list
    private void addVeryFirst(Nodo<V> newNode) {
        head = newNode; // Give head a new value
        newNode.setNext(null);
        tail = newNode; // Give tail a new value
    }
    
    // The gets to return the head and/or tail Nodes and size of the list
    public Nodo<V> peek() {
        return head;
    }
 
    public long getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return ( size == 0 )?true:false;
    }

}
