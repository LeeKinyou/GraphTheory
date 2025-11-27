package com.cqupt.graphtheory.algorithm.util;

import java.util.ArrayList;

public class UnionFind {
    private final ArrayList<Integer> parent;
    private final ArrayList<Integer> rank;

    public UnionFind(int n) {
        parent = new ArrayList<>(n);
        rank = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            parent.add(i);
            rank.add(0);
        }
    }

    public int find(int x) {
        if (parent.get(x) != x) {
            parent.set(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank.get(rootX) < rank.get(rootY)) {
                parent.set(rootX, rootY);
            } else if (rank.get(rootX) > rank.get(rootY)) {
                parent.set(rootY, rootX);
            } else {
                parent.set(rootY, rootX);
                rank.set(rootX, rank.get(rootX) + 1);
            }
        }
    }
}