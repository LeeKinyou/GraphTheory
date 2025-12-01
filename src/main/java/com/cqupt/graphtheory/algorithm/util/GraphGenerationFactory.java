package com.cqupt.graphtheory.algorithm.util;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public class GraphGenerationFactory {

    public static ArrayList<Node> generateNodes(String type, int count, JPanel graphPanel) {
        return switch (type.toLowerCase()) {
            case "circle" -> new CircleGeneration().generateNodes(count, graphPanel);
            case "tree" -> new TreeGeneration().generateNodes(count, graphPanel);
            default -> throw new IllegalArgumentException("Unknown generation type: " + type);
        };
    }

    public static ArrayList<Edge> generateEdges(String type, int count, ArrayList<Node> nodes) {
        return switch (type.toLowerCase()) {
            case "circle" -> new CircleGeneration().generateEdges(count, nodes);
            case "tree" -> new TreeGeneration().generateEdges(count, nodes);
            default -> throw new IllegalArgumentException("Unknown generation type: " + type);
        };
    }

    public static Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> generateAdjacencyList(String type, ArrayList<Node> nodes, ArrayList<Edge> edges) {
        return switch (type.toLowerCase()) {
            case "tree" -> new TreeGeneration().generateAdjacencyList(nodes, edges);
            default -> throw new IllegalArgumentException("Unknown generation type: " + nodes.size());
        };
    }
}