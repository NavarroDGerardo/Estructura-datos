package com.company;
import java.util.Calendar;

public class Ordenamiento {
    private String sName;
    private int iSize;
    private int iCompares;
    private int iFlips;
    private int iStartTime;
    private int iEndTime;
    private int iDiffTime;

    public Ordenamiento( ) {
        sName = "ALGORITMO";
	iSize = 0;
	iCompares = 0;
	iFlips = 0;
	iStartTime = 0;
	iEndTime = 0;
	iDiffTime = 0;	
    }
    
    public void calculate( ) {
        iDiffTime = iEndTime - iStartTime;
    }

    public void reset( ) {
        sName = "ALGORITMO";
	iSize = 0;
	iCompares = 0;
	iFlips = 0;
	iStartTime = 0;
	iEndTime = 0;
	iDiffTime = 0;	
    }

    public String toString() {
        return sName + "\t" +
               iSize + "\t" +
               iCompares + "\t" +
               iFlips + "\t" + 
               iDiffTime;
    }

    // Algoritmo BubbleSort
    public <T extends Comparar<T>> boolean bubbleSort( T A[] ) {
        sName = new String("BubbleSort");
        iSize = A.length;
        iStartTime = (int)Calendar.getInstance().getTimeInMillis();

        boolean swapped = false;

        for (int i=0; i<A.length-1; i++) {
            for (int j=0; j<A.length-1; j++) {
                iCompares++;
                if ( A[j].esMayor(A[j+1]) ) {
                    T aux = A[j+1];
                    A[j+1] = A[j];
                    A[j] = aux;
                    iFlips++;
                    swapped = true;
                }
            }
            if (!swapped) { 
                iEndTime = (int)Calendar.getInstance().getTimeInMillis();
                return true;
            }
            else swapped = false;
        }
        iEndTime = (int)Calendar.getInstance().getTimeInMillis();
        return true;
    }

    // Algoritmo SelectionSort
    public <T extends Comparar<T>> boolean selectionSort( T A[] ) {
        sName = new String("SelectionSort");
        iSize = A.length;
        iStartTime = (int)Calendar.getInstance().getTimeInMillis();

        boolean swapped = false;
        
        for (int i=0; i<A.length-1; i++) {
            int iMin = i;
            for (int j=i+1; j<A.length; j++) {
                iCompares++;
                if (A[j].esMenor(A[iMin])) {
                    iMin = j;
                    swapped = true;
                }
            }
            if (swapped) {
                T aux = A[i];
                A[i] = A[iMin];
                A[iMin] = aux;
                iFlips++;
            }
            swapped = false;
        }
        iEndTime = (int)Calendar.getInstance().getTimeInMillis();
        return true;
    }

    // Algoritmo InsertionSort
    public <T extends Comparar<T>> boolean insertionSort( T A[] ) {
        sName = new String("InsertionSort");
        iSize = A.length;
        iStartTime = (int)Calendar.getInstance().getTimeInMillis();

        for (int i=1; i<A.length; i++) {
            T value = A[i];
            int j = i-1;
            while ( j >= 0 && A[j].esMayor(value) ) {
                iCompares++;
                iFlips++;
                A[j+1] = A[j];
                j--;
            }
            A[j+1] = value;
            iCompares++;
        }
        iEndTime = (int)Calendar.getInstance().getTimeInMillis();
        return true;
    }

}
