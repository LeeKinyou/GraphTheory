
package com.cqupt.graphtheory.algorithm.util;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.*;

public class GraphGeneration extends Generation {

    @Override
    public ArrayList<Node> generateNodes(int count, JPanel graphPanel) {
        ArrayList<Node> nodes = new ArrayList<>();
        Random random = new Random();

        int width = graphPanel.getWidth();
        int height = graphPanel.getHeight();

        // 设置边界，避免节点太靠近边缘
        int margin = 50;
        int effectiveWidth = width - 2 * margin;
        int effectiveHeight = height - 2 * margin;

        for (int i = 0; i < count; i++) {
            // 随机生成坐标
            int x = random.nextInt(effectiveWidth) + margin;
            int y = random.nextInt(effectiveHeight) + margin;
            nodes.add(new Node(i, x, y));
        }

        return nodes;
    }

    @Override
    public ArrayList<Edge> generateEdges(int count, ArrayList<Node> nodes) {
        ArrayList<Edge> edges = new ArrayList<>();
        Random random = new Random();

        // 如果指定的边数超过完全图的边数，则调整为最大值
        int maxEdges = nodes.size() * (nodes.size() - 1) / 2;
        int targetEdgeCount = Math.min(count, maxEdges);

        // 创建一个已存在边的集合，避免重复
        Set<String> existingEdges = new HashSet<>();

        // 如果节点数大于1，至少创建一个连接确保图连通
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

        // 继续添加随机边直到达到目标数量
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

        // 初始化每个节点的邻接表
        for (Node node : nodes) {
            adjacencyList.put(node.getId(), new ArrayList<>());
        }

        // 填充邻接表
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
        // 确保较小的索引在前面，使无向图的边表示一致
        return fromIndex < toIndex ? fromIndex + "-" + toIndex : toIndex + "-" + fromIndex;
    }
}