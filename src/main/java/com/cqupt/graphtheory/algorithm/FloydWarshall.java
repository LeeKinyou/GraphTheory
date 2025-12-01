package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.algorithm.util.GraphGenerationFactory;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class FloydWarshall {
    private final ArrayList<ArrayList<Integer>> graph;
    private final ArrayList<ArrayList<Integer>> distance;
    private final int s;
    private final int t;

    public FloydWarshall(ArrayList<Node> nodes, ArrayList<Edge> edges, int s, int t) {
        graph = GraphGenerationFactory.generateAdjacencyMatrix("tree", nodes, edges);
        distance = new ArrayList<>();
        this.s = s;
        this.t = t;
    }

    public ArrayList<ArrayList<Integer>> executeFloydWarshall() {
        int n = graph.size();

        for (int i = 0; i < n; i++) {
            distance.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                distance.get(i).add(graph.get(i).get(j));
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance.get(i).get(k) != Integer.MAX_VALUE &&
                        distance.get(k).get(j) != Integer.MAX_VALUE &&
                        distance.get(i).get(k) + distance.get(k).get(j) < distance.get(i).get(j)) {
                        distance.get(i).set(j, distance.get(i).get(k) + distance.get(k).get(j));
                    }
                }
            }
        }
        return distance;
    }
}
