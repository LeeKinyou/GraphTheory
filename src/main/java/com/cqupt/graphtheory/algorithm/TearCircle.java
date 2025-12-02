package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import java.util.ArrayList;
import java.util.Comparator;

public class TearCircle {
    private final ArrayList<Edge> removedEdges;
    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;
    private final int nodeCount;

    private final ArrayList<Integer> parent;
    private final ArrayList<Integer> rank;

    public TearCircle(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = new ArrayList<>(edges);
        this.removedEdges = new ArrayList<>();
        this.nodeCount = nodes.size();

        this.parent = new ArrayList<>(nodeCount);
        this.rank = new ArrayList<>(nodeCount);
        for (int i = 0; i < nodeCount; i++) {
            this.parent.add(i);
            this.rank.add(0);
        }
    }

    public ArrayList<Edge> executeTearCircle() {
        // 边权从大到小排序
        this.edges.sort(Comparator.comparingInt(Edge::getWeight).reversed());
        ArrayList<Edge> edgesToKeep = new ArrayList<>(this.edges);


        for (Edge edgeToRemove : this.edges) {
            if (edgesToKeep.size() <= nodeCount - 1) {
                break;
            }

            edgesToKeep.remove(edgeToRemove);
            resetDSU();
            for (Edge edge : edgesToKeep) {
                union(edge.getFrom().getId(), edge.getTo().getId());
            }

            int components = 0;
            for(int i = 0; i < nodeCount; i++){
                if(parent.get(i) == i){
                    components++;
                }
            }

            // 如果移除该边导致联通分量大于1，说明删边导致不连通需要加回来
            if (components > 1) {
                edgesToKeep.add(edgeToRemove);
            } else {
                // 如果图仍然连通，说明这条边是多余的将其移除
                removedEdges.add(edgeToRemove);
            }
        }
        return removedEdges;
    }

    private void resetDSU() {
        for (int i = 0; i < nodeCount; i++) {
            parent.set(i, i);
            rank.set(i, 0);
        }
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
