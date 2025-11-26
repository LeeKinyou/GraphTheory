package com.cqupt.graphtheory.algorithm;

import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MSTGeneration {
    ArrayList<Edge> mstEdges = new ArrayList<>();
    Integer stepIndex = -1;

    // 生成节点
    public static ArrayList<Node> generateNodes(int count, JPanel graphPanel) {
        ArrayList<Node> nodes = new ArrayList<>();
        // 在圆形上均匀分布节点
        int centerX = graphPanel.getWidth() / 2;
        int centerY = graphPanel.getHeight() / 2;
        int radius = Math.min(centerX, centerY) - 50;

        for (int i = 0; i < count; i++) {
            double angle = 2 * Math.PI * i / count;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            nodes.add(new Node(i, x, y));
        }
        return nodes;
    }

    // 生成边
    public static ArrayList<Edge> generateEdges(int count, ArrayList<Node> nodes) {
        ArrayList<Edge> edges = new ArrayList<>();
        Random random = new Random();

        // 先生成一个连通图
        for (int i = 1; i < nodes.size(); i++) {
            Node from = nodes.get(i - 1);
            Node to = nodes.get(i);
            int weight = random.nextInt(20) + 1; // 权重1-20
            edges.add(new Edge(from, to, weight));
        }

        // 如果还需要更多边
        while (edges.size() < count) {
            int fromIndex = random.nextInt(nodes.size());
            int toIndex = random.nextInt(nodes.size());

            // 确保不是同一个节点且边不存在
            if (fromIndex != toIndex && !edgeExists(edges, fromIndex, toIndex)) {
                Node from = nodes.get(fromIndex);
                Node to = nodes.get(toIndex);
                int weight = random.nextInt(20) + 1; // 权重1-20
                edges.add(new Edge(from, to, weight));
            }
        }
        // 打乱边的顺序
        Collections.shuffle(edges);
        return edges;
    }

    // 检查边是否存在
    private static boolean edgeExists(ArrayList<Edge> edges, int fromIndex, int toIndex) {
        for (Edge edge : edges) {
            if ((edge.getFrom().getId() == fromIndex && edge.getTo().getId() == toIndex) ||
                    (edge.getFrom().getId() == toIndex && edge.getTo().getId() == fromIndex)) {
                return true;
            }
        }
        return false;
    }
}
