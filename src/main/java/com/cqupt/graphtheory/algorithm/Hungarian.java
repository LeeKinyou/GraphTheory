package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.algorithm.util.GraphGenerationFactory;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@Getter
public class Hungarian {
    private final Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> graph;
    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;

    private int[] match;

    private boolean[] visited;

    private ArrayList<Edge> matchedEdges;

    private final int uSize;

    private final int totalSize;

    public Hungarian(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.totalSize = nodes.size();
        // 邻接表应表示从U到V的边
        this.graph = GraphGenerationFactory.generateAdjacencyList("bipartite", nodes, edges);
        System.out.println( graph);
        this.uSize = graph.size();
    }

    public ArrayList<Edge> executeHungarian() {
        this.match = new int[totalSize];
        Arrays.fill(match, -1);
        this.visited = new boolean[totalSize];

        for (int u = 0; u < uSize; u++) {
            Arrays.fill(visited, false);
            dfs(u);
        }

        this.matchedEdges = new ArrayList<>();
        for (int v = uSize; v < totalSize; v++) {
            if (match[v] != -1) {
                int u = match[v];
                for (Edge edge : this.edges) {
                    if ((edge.getFrom().getId() == u && edge.getTo().getId() == v) ||
                        (edge.getFrom().getId() == v && edge.getTo().getId() == u)) {
                        this.matchedEdges.add(edge);
                        break;
                    }
                }
            }
        }
        return this.matchedEdges;
    }

    private boolean dfs(int u) {
        for (Map.Entry<Integer, Integer> entry : graph.getOrDefault(u, new ArrayList<>())) {
            int v = entry.getKey();
            if (!visited[v]) {
                visited[v] = true;
                if (match[v] < 0 || dfs(match[v])) {
                    match[v] = u;
                    return true;
                }
            }
        }
        return false;
    }
}
