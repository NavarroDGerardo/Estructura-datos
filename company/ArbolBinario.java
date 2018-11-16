package com.company;

public class ArbolBinario <V extends Comparar<V>>{
    private NodoArbol<V> rootNode = null;

    public NodoArbol<V> getRootNode() {
        return this.rootNode;
    }

    public ArbolBinario() {
    }

    public void insert(NodoArbol<V> var1) {
        if (this.rootNode == null) {
            this.rootNode = var1;
        } else if (((Comparar)var1.getElement()).esMenor(this.rootNode.getElement())) {
            if (this.rootNode.getLeftLeaf() == null) {
                this.rootNode.setLeftLeaf(var1);
            } else {
                this.insertLeaf(var1, this.rootNode.getLeftLeaf());
            }
        } else if (this.rootNode.getRightLeaf() == null) {
            this.rootNode.setRightLeaf(var1);
        } else {
            this.insertLeaf(var1, this.rootNode.getRightLeaf());
        }

    }

    private void insertLeaf(NodoArbol<V> var1, NodoArbol<V> var2) {
        if (((Comparar)var1.getElement()).esMenor(var2.getElement())) {
            if (var2.getLeftLeaf() == null) {
                var2.setLeftLeaf(var1);
            } else {
                this.insertLeaf(var1, var2.getLeftLeaf());
            }
        } else if (var2.getRightLeaf() == null) {
            var2.setRightLeaf(var1);
        } else {
            this.insertLeaf(var1, var2.getRightLeaf());
        }

    }

    public void showInOrder(NodoArbol<V> var1) {
    }

    public void showPreOrder(NodoArbol<V> var1) {
    }

    public void showPostOrder(NodoArbol<V> var1) {
    }

    public NodoArbol<V> search(V var1, NodoArbol<V> var2) {
        Object var3 = null;
        return (NodoArbol)var3;
    }
}
