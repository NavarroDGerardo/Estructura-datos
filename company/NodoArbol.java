package com.company;

public class NodoArbol<V> {
    private V element;
    private NodoArbol<V> left;
    private NodoArbol<V> right;

    public NodoArbol() {
        this((V)null, (NodoArbol)null, (NodoArbol)null);
    }

    public NodoArbol(V var1, NodoArbol<V> var2, NodoArbol<V> var3) {
        this.element = var1;
        this.left = var2;
        this.right = var3;
    }

    public V getElement() {
        return this.element;
    }

    public NodoArbol<V> getLeftLeaf() {
        return this.left;
    }

    public NodoArbol<V> getRightLeaf() {
        return this.right;
    }

    public void setElement(V var1) {
        this.element = var1;
    }

    public void setLeftLeaf(NodoArbol<V> var1) {
        this.left = var1;
    }

    public void setRightLeaf(NodoArbol<V> var1) {
        this.right = var1;
    }
}
