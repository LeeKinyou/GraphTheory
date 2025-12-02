package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import lombok.Data;

import java.util.*;

@Data
public class Hungarian {
    private final Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> graph;
    private final ArrayList<Node> nodes;
    private final ArrayList<Edge> edges;

    private ArrayList<Integer> match;

    private ArrayList<Boolean> visited;

    private ArrayList<Edge> matchedEdges;

    private final int uSize;

    private final int totalSize;

    public Hungarian(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.totalSize = nodes.size();
        this.uSize = this.totalSize / 2;

        this.graph = new HashMap<>();
        for (int i = 0; i < uSize; i++) {
            this.graph.put(i, new ArrayList<>());
        }

        for (Edge edge : edges) {
            int u = edge.getFrom().getId();
            int v = edge.getTo().getId();
            if (u < uSize && v >= uSize) {
                graph.get(u).add(new AbstractMap.SimpleEntry<>(v, edge.getWeight()));
            } else if (v < uSize && u >= uSize) {
                graph.get(v).add(new AbstractMap.SimpleEntry<>(u, edge.getWeight()));
            }
        }
    }

    public ArrayList<Edge> executeHungarian() {
        for (int i = 0; i < totalSize; i++) {
            match.add(-1);
        }
        for (int i = 0; i < totalSize; i++) {
            visited.add(false);
        }

        for (int u = 0; u < uSize; u++) {
            visited.clear();
            for (int i = 0; i < totalSize; i++) {
                visited.add(false);
            }
            dfs(u);
        }

        matchedEdges = new ArrayList<>();
        for (int v = uSize; v < totalSize; v++) {
            if (match.get(v) != -1) {
                int u = match.get(v);
                for (Edge edge : this.edges) {
                    if ((edge.getFrom().getId() == u && edge.getTo().getId() == v) ||
                        (edge.getFrom().getId() == v && edge.getTo().getId() == u)) {
                        matchedEdges.add(edge);
                        break;
                    }
                }
            }
        }
        return matchedEdges;
    }

    private boolean dfs(int u) {
        for (Map.Entry<Integer, Integer> entry : graph.getOrDefault(u, new ArrayList<>())) {
            int v = entry.getKey();
            if (!visited.get(v)) {
                visited.set(v, true);
                // 如果没有匹配的点 或者 当前已经被匹配的点的匹配点可以重新匹配到别的点
                if (match.get(v) < 0 || dfs(match.get(v))) {
                    match.set(v, u);
                    return true;
                }
            }
        }
        return false;
    }
}
