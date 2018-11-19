package com.company;

import java.util.HashMap;

public class Grafo {
    HashMap<String, HashMap<String, Integer>> Grafo;

    public Grafo(){
        Grafo = new HashMap<String, HashMap<String, Integer>>();
    }

    public void crearVertice(String vertice, HashMap<String, Integer>relacion){
        Grafo.put(vertice, relacion);
    }

    public HashMap<String, Integer> getVertice(String vertice){
        return Grafo.get(vertice);
    }

}
