package com.company;

public class Dijkstra {
    public int distance[] = new int[25];
    public int cost[][] = new int[25][25];

    public void calc(int n, int s) {
        int flag[] = new int[n + 1];
        int i, minpos = 1, k, c, minimum;

        for (i = 1; i <= n; i++) {
            flag[i] = 0;
            this.distance[i] = this.cost[s][i];
        }
        c = 2;
        while (c <= n) {
            minimum = 99;
            for (k = 1; k <= n; k++) {
                if (this.distance[k] < minimum && flag[k] != 1) {
                    minimum = this.distance[i];
                    minpos = k;
                }
            }
            flag[minpos] = 1;
            c++;
            for (k = 1; k <= n; k++) {
                if (this.distance[minpos] + this.cost[minpos][k] < this.distance[k] && flag[k] != 1)
                    this.distance[k] = this.distance[minpos] + this.cost[minpos][k];
            }
        }
    }
}