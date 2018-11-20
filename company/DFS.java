package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class DFS
{
    private Stack<Integer> stack;

    public DFS()
    {
        stack = new Stack<Integer>();
    }

    public void dfs(int adjacency_matrix[][], int source)
    {
        int number_of_nodes = adjacency_matrix[source].length - 1;

        int visited[] = new int[number_of_nodes + 1];
        int element = source;
        int i = source;
        System.out.print(element + "\t");
        visited[source] = 1;
        stack.push(source);

        while (!stack.isEmpty())
        {
            element = stack.peek();
            i = element;
            while (i <= number_of_nodes)
            {
                if (adjacency_matrix[element][i] == 1 && visited[i] == 0)
                {
                    stack.push(i);
                    visited[i] = 1;
                    element = i;
                    i = 1;
                    System.out.print(element + "\t");
                    continue;
                }
                i++;
            }
            stack.pop();
        }
    }
}
