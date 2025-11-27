package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.algorithm.util.UnionFind;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Kruskal {
    private final ArrayList<Edge> mstEdges;

    private final ArrayList<Node> nodes;

    private final ArrayList<Edge> edges;

    private int minValue = 0;

    public Kruskal(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        mstEdges = new ArrayList<>();
    }

    public ArrayList<Edge> executeKruskal() {
        ArrayList<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Edge::compareTo);

        UnionFind uf = new UnionFind(nodes.size());

        for (Edge edge : sortedEdges) {
            int root1 = uf.find(edge.getFrom().getId());
            int root2 = uf.find(edge.getTo().getId());

            if (root1 != root2) {
                mstEdges.add(edge);
                minValue += edge.getWeight();
                uf.union(root1, root2);
            }
        }
        return mstEdges;
    }
}
