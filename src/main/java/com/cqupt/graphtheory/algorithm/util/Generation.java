package com.cqupt.graphtheory.algorithm.util;


import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Generation {

    public Generation() {
    }

    public abstract ArrayList<Node> generateNodes(int count, JPanel graphPanel);
    public abstract ArrayList<Edge> generateEdges(int count, ArrayList<Node> nodes);

    protected boolean edgeExists(ArrayList<Edge> edges, int fromIndex, int toIndex) {
        for (Edge edge : edges) {
            if ((edge.getFrom().getId() == fromIndex && edge.getTo().getId() == toIndex) ||
                    (edge.getFrom().getId() == toIndex && edge.getTo().getId() == fromIndex)) {
                return true;
            }
        }
        return false;
    }

    public Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> generateAdjacencyList(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> adjacencyList = new HashMap<>();
        for (Edge edge : edges) {
            if (!adjacencyList.containsKey(edge.getFrom().getId())) {
                adjacencyList.put(edge.getFrom().getId(), new ArrayList<>());
                adjacencyList.get(edge.getFrom().getId()).add(
                        new AbstractMap.SimpleEntry<>(edge.getTo().getId(), edge.getWeight())
                );
            } else {
                adjacencyList.get(edge.getFrom().getId()).add(
                        new AbstractMap.SimpleEntry<>(edge.getTo().getId(), edge.getWeight())
                );
            }
            if (!adjacencyList.containsKey(edge.getTo().getId())) {
                adjacencyList.put(edge.getTo().getId(), new ArrayList<>());
                adjacencyList.get(edge.getTo().getId()).add(
                        new AbstractMap.SimpleEntry<>(edge.getFrom().getId(), edge.getWeight())
                );
            } else {
                adjacencyList.get(edge.getTo().getId()).add(
                        new AbstractMap.SimpleEntry<>(edge.getFrom().getId(), edge.getWeight())
                );
            }
        }
        return adjacencyList;
    }
    public static Generation getInstance(String type) {
        switch (type.toLowerCase()) {
            case "circle":
                return new CircleGeneration();
            case "tree":
                return new TreeGeneration();
            default:
                throw new IllegalArgumentException("Unknown generation type: " + type);
        }
    }
}