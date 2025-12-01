package com.cqupt.graphtheory.algorithm.util;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.*;

public class BipartiteGraphGeneration extends Generation {
    @Override
    public ArrayList<Node> generateNodes(int count, JPanel graphPanel) {
        ArrayList<Node> nodes = new ArrayList<>();
        int width = graphPanel.getWidth();
        int height = graphPanel.getHeight();

        int uSize = count / 2;
        int vSize = count - uSize;

        int uX = width / 4;
        for (int i = 0; i < uSize; i++) {
            int y = height / (uSize + 1) * (i + 1);
            nodes.add(new Node(i, uX, y));
        }

        int vX = width * 3 / 4;
        for (int i = 0; i < vSize; i++) {
            int y = height / (vSize + 1) * (i + 1);
            nodes.add(new Node(uSize + i, vX, y));
        }
        return nodes;
    }

    @Override
    public ArrayList<Edge> generateEdges(int edgeCount, ArrayList<Node> nodes) {
        ArrayList<Edge> edges = new ArrayList<>();
        if (nodes.isEmpty()) {
            return edges;
        }

        int totalCount = nodes.size();
        int uSize = totalCount / 2;

        if (uSize == 0 || uSize == totalCount) {
            return edges;
        }

        Random random = new Random();

        int maxPossibleEdges = uSize * (totalCount - uSize);
        int targetEdgeCount = Math.min(edgeCount, maxPossibleEdges);

        while (edges.size() < targetEdgeCount) {
            int uIndex = random.nextInt(uSize);
            int vIndex = uSize + random.nextInt(totalCount - uSize);

            if (!edgeExists(edges, uIndex, vIndex)) {
                int weight = random.nextInt(20) + 1;
                edges.add(new Edge(nodes.get(uIndex), nodes.get(vIndex), weight));
            }
        }
        return edges;
    }

    @Override
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
        }
        return adjacencyList;
    }
}
