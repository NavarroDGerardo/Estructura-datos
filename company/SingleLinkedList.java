package com.company;
public class SingleLinkedList<V> {
    // Instance Variables. Add the tail reference.
    protected Nodo<V> head;
    protected Nodo<V> tail;
    protected long size;
 
    // Empty list constructor first
    public SingleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    } 
 
    /**
     * Method to add Nodes to the list.
     * Storage space for the Node is already allocated in the calling method
     */
    public void addFirst(Nodo<V> newNode) {
        // Set the tail only if this is the very first Node
        if (tail == null && head == null) addVeryFirst(newNode);
        else {
            newNode.setNext(head); // Make next of the new Node refer to the head
            head = newNode; // Give head a new value
        }
        // change the size
        size++;
    } 
 
    // Add new Node after current Node, checking to see if we are at the tail
    public void addAfter(Nodo<V> currentNode, Nodo<V> newNode) {
        if (currentNode == tail) tail = newNode;
        
        newNode.setNext(currentNode.getNext());
        currentNode.setNext(newNode);
 
        // change the size
        size++;
    } 
 
    // Add new Node after the tail Node.
    public void addLast(Nodo<V> newNode) {
        if (tail == null && head == null) addVeryFirst(newNode);
        else {
            newNode.setNext(null);
            tail.setNext(newNode);
            tail = newNode;
        }
        // change the size
        size++;
    }

    // Add very first node in the single list
    private void addVeryFirst(Nodo<V> newNode) {
        head = newNode; // Give head a new value
        newNode.setNext(null);
        tail = newNode; // Give tail a new value
    }

    /**
     * Methods to remove Nodes from the list.
     * (Unfortunately, with a single linked list.
     * there is no way to remove last.
     * Need a previous reference to do that.
     */
    public Nodo<V> removeFirst() {
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
 
    /**
     * Remove the Node at the end of the list.
     * tail refers to this Node, but since the list is single linked,
     * there is no way to refer to the Node before the tail Node.
     * Need to traverse the list.
     */
    public Nodo<V> removeLast() {
        // Declare local variables/objects
        Nodo<V> Before;
        Nodo<V> ToRemove;
 
        // Make sure we have something to remove
        if (size == 0)
            System.err.println("Error:  Attempt to remove fron an empty list");
 
        /*
         * Traverse through the list, getting a reference to the Node
         * before the trailer. Since there is no previous reference.
         */
        Before = getFirst();
 
        for (int count = 0; count < size - 2; count++)
            Before = Before.getNext();
 
        // Save the last Node
        ToRemove = tail;
 
        // Let's do the pointer manipulation
        Before.setNext(null);
        tail = Before;
        size--;
 
        return ToRemove;
    } 
 
    /**
     * Remove a known Node from the list. No need to search or return a value.
     * This method makes use of a 'before' reference in order to allow list manipulation.
     */
    public void remove(Nodo<V> ToRemove) {
        // Declare local variables/references
        Nodo<V> Before;
        Nodo<V> Current;
 
        // Make sure we have something to remove
        if (size == 0)
            System.err.println("Error:  Attempt to remove from an empty list");
 
        // Starting at the beginning check for removal
        Current = getFirst();
        if (Current == ToRemove)
            removeFirst();
        else {
        // Looking at the end check for removal
        Current = getLast();
        if (Current == ToRemove)
            removeLast();
        }
        // We've already check two Nodes, check the rest
        if (size - 2 > 0) {
            Before = getFirst();
            Current = getFirst().getNext();
            for (int count = 0; count < size - 2; count++) {
                if (Current == ToRemove) {
                    // remove current Node
                    Before.setNext(Current.getNext());
                    size--;
                    break;
                }
 
                // Change references
                Before = Current;
                Current = Current.getNext();
            } 
        } 
 
    }
 
    // The gets to return the head and/or tail Nodes and size of the list
    public Nodo<V> getFirst() {
        return head;
    }
 
    public Nodo<V> getLast() {
        return tail;
    }
 
    public long getSize() {
        return size;
    }
}
