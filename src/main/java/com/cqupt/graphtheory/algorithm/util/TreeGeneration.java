package com.cqupt.graphtheory.algorithm.util;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.*;

public class TreeGeneration extends Generation {
    @Override
    public ArrayList<Node> generateNodes(int count, JPanel graphPanel) {
        ArrayList<Node> nodes = new ArrayList<>();
        int width = graphPanel.getWidth();
        int height = graphPanel.getHeight();

        int levels = (int) (Math.log(count) / Math.log(2)) + 1;
        int levelHeight = height / (levels + 1);

        for (int i = 0; i < count; i++) {
            int level = (int) (Math.log(i + 1) / Math.log(2));
            int nodesInLevel = (int) Math.pow(2, level);
            int positionInLevel = i - (nodesInLevel - 1);

            int x = width / (nodesInLevel + 1) * (positionInLevel + 1);
            int y = levelHeight * (level + 1);

            nodes.add(new Node(i, x, y));
        }
        return nodes;
    }

    @Override
    public ArrayList<Edge> generateEdges(int count, ArrayList<Node> nodes) {
        ArrayList<Edge> edges = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < nodes.size() && edges.size() < count; i++) {
            int leftChildIndex = 2 * i + 1;
            if (leftChildIndex < nodes.size()) {
                int weight = random.nextInt(20) + 1;
                edges.add(new Edge(nodes.get(i), nodes.get(leftChildIndex), weight));
            }

            int rightChildIndex = 2 * i + 2;
            if (rightChildIndex < nodes.size()) {
                int weight = random.nextInt(20) + 1;
                edges.add(new Edge(nodes.get(i), nodes.get(rightChildIndex), weight));
            }
        }

        while (edges.size() < count && edges.size() < nodes.size() * (nodes.size() - 1) / 2) {
            int fromIndex = random.nextInt(nodes.size());
            int toIndex = random.nextInt(nodes.size());

            if (fromIndex != toIndex && !edgeExists(edges, fromIndex, toIndex)) {
                int weight = random.nextInt(20) + 1;
                edges.add(new Edge(nodes.get(fromIndex), nodes.get(toIndex), weight));
            }
        }

        return edges;
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

    public ArrayList<ArrayList<Integer>> generateAdjacencyMatrix(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        int n = nodes.size();
        ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    row.add(0);
                } else {
                    row.add(Integer.MAX_VALUE);
                }
            }
            adjacencyMatrix.add(row);
        }

        for (Edge edge : edges) {
            int fromId = edge.getFrom().getId();
            int toId = edge.getTo().getId();
            int weight = edge.getWeight();
            if (fromId < n && toId < n) {
                adjacencyMatrix.get(fromId).set(toId, weight);
                adjacencyMatrix.get(toId).set(fromId, weight);
            }
        }
        return adjacencyMatrix;
    }

    private boolean edgeExists(ArrayList<Edge> edges, int fromIndex, int toIndex) {
        for (Edge edge : edges) {
            if ((edge.getFrom().getId() == fromIndex && edge.getTo().getId() == toIndex) ||
                    (edge.getFrom().getId() == toIndex && edge.getTo().getId() == fromIndex)) {
                return true;
            }
        }
        return false;
    }
}