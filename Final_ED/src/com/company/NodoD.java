package com.company;
public class NodoD<V> {
    private V element;
    private NodoD<V> next;
    private NodoD<V> before;
 
    public NodoD() {
        this(null, null, null); 
    }
 
    public NodoD(V element, NodoD<V> next, NodoD<V> before) {
        this.element = element;
        this.next = next;
        this.before = before;
    }
 
    public V getElement() {
        return element;
    }
 
    public NodoD<V> getNext() {
        return next;
    }
 
    public NodoD<V> getBefore() {
        return before;
    }
    
    public void setElement(V element) {
        this.element = element;
    }
 
    public void setNext(NodoD<V> next) {
        this.next = next;
    }
    
    public void setBefore(NodoD<V> before) {
        this.before = before;
    }
}
