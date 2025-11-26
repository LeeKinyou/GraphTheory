// KruskalAlgorithmPanel.java
package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.MSTGeneration;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class KruskalAlgorithmPanel extends AlgorithmViewPanel {
    // 图的数据结构
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private ArrayList<Edge> mstEdges; // 最小生成树的边
    private int stepIndex; // 算法执行步骤索引

    public KruskalAlgorithmPanel(MainAppFrame parent) {
        super(parent, "Kruskal算法");
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        mstEdges = new ArrayList<>();
        stepIndex = -1;
    }

    @Override
    protected void generateGraph() {
        try {
            int nodeCount = Integer.parseInt(nodeCountField.getText());
            int edgeCount = Integer.parseInt(edgeCountField.getText());

            // 验证输入合法性
            if (nodeCount <= 0 || edgeCount < 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的节点数和边数！");
                return;
            }

            // 边数不能超过完全图的边数
            int maxEdges = nodeCount * (nodeCount - 1) / 2;
            if (edgeCount > maxEdges) {
                JOptionPane.showMessageDialog(this, "边数过多！最多只能有" + maxEdges + "条边。");
                return;
            }

            // 清空之前的数据
            nodes.clear();
            edges.clear();
            mstEdges.clear();
            stepIndex = -1;

            // 生成节点
            nodes = MSTGeneration.generateNodes(nodeCount, graphPanel);

            // 生成边
            edges = MSTGeneration.generateEdges(edgeCount, nodes);

            // 重新绘制图形
            graphPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！");
        }
    }

    @Override
    protected void runAlgorithm() {
        if (nodes.isEmpty() || edges.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成图！");
            return;
        }

        // 重置算法状态
        mstEdges.clear();
        stepIndex = 0;

        // 执行Kruskal算法
        executeKruskal();

        // 重新绘制图形
        graphPanel.repaint();
    }

    // 执行Kruskal算法
    private void executeKruskal() {
        // 将边按权重排序
        List<Edge> sortedEdges = new ArrayList<>(edges);
        Collections.sort(sortedEdges);

        // 初始化并查集
        UnionFind uf = new UnionFind(nodes.size());

        // Kruskal算法核心
        for (Edge edge : sortedEdges) {
            int root1 = uf.find(edge.from.id);
            int root2 = uf.find(edge.to.id);

            // 如果两个顶点不在同一连通分量中
            if (root1 != root2) {
                // 将边加入最小生成树
                mstEdges.add(edge);
                // 合并两个连通分量
                uf.union(root1, root2);
            }
        }
    }

    @Override
    protected void drawGraph(Graphics g) {
        // 绘制边
        drawEdges(g);

        // 绘制节点
        drawNodes(g);
    }

    // 绘制边
    private void drawEdges(Graphics g) {
        // 先绘制普通边（黑色）
        g.setColor(Color.BLACK);
        for (Edge edge : edges) {
            // 检查是否是最小生成树中的边
            boolean isMstEdge = mstEdges.contains(edge);
            if (!isMstEdge) {
                drawEdge(g, edge);
            }
        }

        // 绘制最小生成树的边（红色）
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2.0f));
        for (Edge edge : mstEdges) {
            drawEdge(g2d, edge);
        }
    }

    // 绘制单条边
    private void drawEdge(Graphics g, Edge edge) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke();

        // 如果是MST边，加粗显示
        if (mstEdges.contains(edge)) {
            g2d.setStroke(new BasicStroke(2.0f));
        }

        g.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);

        // 绘制权重
        int midX = (edge.from.x + edge.to.x) / 2;
        int midY = (edge.from.y + edge.to.y) / 2;
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(edge.weight), midX, midY);

        // 恢复颜色和线宽
        g.setColor(mstEdges.contains(edge) ? Color.RED : Color.BLACK);
        g2d.setStroke(originalStroke);
    }

    // 绘制节点
    private void drawNodes(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Node node : nodes) {
            g.fillOval(node.x - 15, node.y - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.drawOval(node.x - 15, node.y - 15, 30, 30);
            g.drawString(String.valueOf(node.id), node.x - 5, node.y + 5);
            g.setColor(Color.YELLOW);
        }
    }

    // 并查集类
    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 路径压缩
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                // 按秩合并
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}
