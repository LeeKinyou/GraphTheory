package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Kruskal {
    private final ArrayList<Edge> mstEdges;

    private final ArrayList<Node> nodes;

    private final ArrayList<Edge> edges;

    private final ArrayList<Integer> parent;

    private final ArrayList<Integer> rank;

    private int minValue = 0;

    public Kruskal(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.mstEdges = new ArrayList<>();
        this.parent = new ArrayList<>(nodes.size());
        this.rank = new ArrayList<>(nodes.size());
        for (int i = 0; i < nodes.size(); i++) {
            this.parent.add(i);
            this.rank.add(0);
        }
    }

    public ArrayList<Edge> executeKruskal() {
        ArrayList<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Edge::compareTo);

        for (Edge edge : sortedEdges) {
            int root1 = find(edge.getFrom().getId());
            int root2 = find(edge.getTo().getId());

            if (root1 != root2) {
                mstEdges.add(edge);
                minValue += edge.getWeight();
                union(root1, root2);
            }
        }
        return mstEdges;
    }

    private int find(int x) {
        if (parent.get(x) != x) {
            parent.set(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    private void union(int x, int y) {
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
