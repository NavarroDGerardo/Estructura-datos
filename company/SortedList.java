package com.company;
public class SortedList<V extends Comparar<V>> {
    // Instance Variables. Add the tail reference.
    protected NodoD<V> head;
    protected NodoD<V> tail;
    protected long size;
 
    // Empty list constructor first
    public SortedList() {
        head = null;
        tail = null;
        size = 0;
    } 
 
    /**
     * Method to add Nodes to the list.
     * Storage space for the Node is already allocated in the calling method
     */
    public void add(NodoD<V> newNode) {
        //El primer nodo en la cola
        if (tail == null && head == null) {
            addVeryFirst(newNode);
            size++;
        }
        //El nodo menor de la lista
        else if ( newNode.getElement().esMenor(head.getElement())
               || newNode.getElement().esIgual(head.getElement()) ) {
            addFirst(newNode);
        }
        //El nodo mayor de la lista
        else if ( newNode.getElement().esMayor(tail.getElement()) ) {
            addLast(newNode);
        }
        //El nodo que va en medio de la lista
        else {
            NodoD<V> tNode = head.getNext();
            while ( newNode.getElement().esMayor(tNode.getElement()) ) {
                tNode = tNode.getNext();
            }
            tNode = tNode.getBefore();
            addAfter(tNode, newNode);
        }
    }
    
    private void addFirst(NodoD<V> newNode) {
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
 
    // Add new Node after current Node, checking to see if we are at the tail
    private void addAfter(NodoD<V> currentNode, NodoD<V> newNode) {
        if (currentNode == tail) addLast(newNode);
        else {
            newNode.setNext(currentNode.getNext());
            currentNode.setNext(newNode);
            newNode.setBefore(newNode.getNext().getBefore());
            newNode.getNext().setBefore(newNode);
            // change the size
            size++;
        }
    } 
 
    // Add new Node after the tail Node.
    private void addLast(NodoD<V> newNode) {
        if (tail == null && head == null) addVeryFirst(newNode);
        else {
            newNode.setNext(null);
            newNode.setBefore(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        // change the size
        size++;
    }
    
    // Add very first node in the double list
    private void addVeryFirst(NodoD<V> newNode) {
        head = newNode; // Give head a new value
        newNode.setNext(null);
        newNode.setBefore(null);
        tail = newNode; // Give tail a new value
    }
 
    /**
     * Methods to remove Nodes from the list.
     * (With a double linked list.
     * is easy to remove last.
     */
    public NodoD<V> removeFirst() {
        if (head == null)
            System.err.println("Error:  Attempt to remove from an empty list");
 
        // save the one to return
        NodoD<V> temp = head;
        
        if ( head == tail ) { /* Is the last Node in the list */
            head = null;
            tail = null;
        }
        else {
            // do reference manipulation
            head = head.getNext();
            head.setBefore(null);
        }
        temp.setNext(null);
        temp.setBefore(null);
        size--;
 
        return temp;
    }
 
    /**
     * Remove the Node at the end of the list.
     * Since the list is double linked,
     * there is no need to traverse the list.
     */
    public NodoD<V> removeLast() {
        // Declare local variables/objects
        NodoD<V> Before;
        NodoD<V> ToRemove;
 
        // Make sure we have something to remove
        if (size == 0)
            System.err.println("Error:  Attempt to remove fron an empty list");

        // Save the node before the last Node
        Before = tail.getBefore();
        // Save the last Node
        ToRemove = tail;
        
        if ( head == tail ) { /* Is the last Node in the list */
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
 
    /**
     * Remove a known Node from the list. No need to search or return a value.
     * This method makes use of a 'before' reference in order to allow list manipulation.
     */
    public void remove(NodoD<V> ToRemove) {
        // Declare local variables/references
        NodoD<V> Before;
        NodoD<V> Current;
 
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
            // We've already check head and tail Nodes, is one from middle
            else {
                Before = ToRemove.getBefore();
                Before.setNext(ToRemove.getNext());
                Current = ToRemove.getNext();
                Current.setBefore(ToRemove.getBefore());
                
                ToRemove.setBefore(null);
                ToRemove.setNext(null);
                size--;
            }
        }
    }
 
    // The gets to return the head and/or tail Nodes and size of the list
    public NodoD<V> getFirst() {
        return head;
    }
 
    public NodoD<V> getLast() {
        return tail;
    }
 
    public long getSize() {
        return size;
    }
}
