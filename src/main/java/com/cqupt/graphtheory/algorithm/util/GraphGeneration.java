
package com.cqupt.graphtheory.algorithm.util;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.*;

public class GraphGeneration extends Generation {

    @Override
    public ArrayList<Node> generateNodes(int count, JPanel graphPanel) {
        if (count <= 0) {
            return new ArrayList<>();
        }

        ArrayList<Node> nodes = new ArrayList<>();
        Random random = new Random();

        int width = graphPanel.getWidth();
        int height = graphPanel.getHeight();

        int margin = 50;
        int effectiveWidth = width - 2 * margin;
        int effectiveHeight = height - 2 * margin;

        int gridCols = (int) Math.ceil(Math.sqrt(count));
        int gridRows = (int) Math.ceil((double) count / gridCols);

        int cellWidth = effectiveWidth / gridCols;
        int cellHeight = effectiveHeight / gridRows;

        for (int i = 0; i < count; i++) {
            int row = i / gridCols;
            int col = i % gridCols;

            int padding = cellWidth / 4;
            int cellX = margin + col * cellWidth;
            int cellY = margin + row * cellHeight;

            int x = cellX + random.nextInt(Math.max(1, cellWidth - 2 * padding)) + padding;
            int y = cellY + random.nextInt(Math.max(1, cellHeight - 2 * padding)) + padding;

            nodes.add(new Node(i, x, y));
        }

        return nodes;
    }

    @Override
    public ArrayList<Edge> generateEdges(int count, ArrayList<Node> nodes) {
        ArrayList<Edge> edges = new ArrayList<>();
        Random random = new Random();

        int maxEdges = nodes.size() * (nodes.size() - 1) / 2;
        int targetEdgeCount = Math.min(count, maxEdges);

        Set<String> existingEdges = new HashSet<>();

        if (nodes.size() > 1) {
            int fromIndex = random.nextInt(nodes.size());
            int toIndex;
            do {
                toIndex = random.nextInt(nodes.size());
            } while (toIndex == fromIndex);

            int weight = random.nextInt(20) + 1;
            edges.add(new Edge(nodes.get(fromIndex), nodes.get(toIndex), weight));
            existingEdges.add(getEdgeKey(fromIndex, toIndex));
        }

        while (edges.size() < targetEdgeCount) {
            int fromIndex = random.nextInt(nodes.size());
            int toIndex = random.nextInt(nodes.size());

            if (fromIndex != toIndex && !existingEdges.contains(getEdgeKey(fromIndex, toIndex))) {
                int weight = random.nextInt(20) + 1;
                edges.add(new Edge(nodes.get(fromIndex), nodes.get(toIndex), weight));
                existingEdges.add(getEdgeKey(fromIndex, toIndex));
            }
        }

        return edges;
    }

    /**
     * 生成邻接表表示
     */
    public Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> generateAdjacencyList(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        Map<Integer, ArrayList<Map.Entry<Integer, Integer>>> adjacencyList = new HashMap<>();

        for (Node node : nodes) {
            adjacencyList.put(node.getId(), new ArrayList<>());
        }

        for (Edge edge : edges) {
            int fromId = edge.getFrom().getId();
            int toId = edge.getTo().getId();
            int weight = edge.getWeight();

            adjacencyList.get(fromId).add(new AbstractMap.SimpleEntry<>(toId, weight));
            adjacencyList.get(toId).add(new AbstractMap.SimpleEntry<>(fromId, weight));
        }

        return adjacencyList;
    }

    /**
     * 获取边的唯一标识键
     */
    private String getEdgeKey(int fromIndex, int toIndex) {
        return fromIndex < toIndex ? fromIndex + "-" + toIndex : toIndex + "-" + fromIndex;
    }
}
