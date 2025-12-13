package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Dijkstra;
import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;

public class DijkstraPanel extends AlgorithmViewPanel {
    private Dijkstra dijkstra;

    public DijkstraPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "graph";
    }

    @Override
    protected void runAlgorithm() {
        super.selectEdges.clear();
        int s, t;
        try {
            s = Integer.parseInt(startNodeField.getText());
            t = Integer.parseInt(endNodeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的节点编号.", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (s < 0 || s >= nodes.size() || t < 0 || t >= nodes.size()) {
            JOptionPane.showMessageDialog(this, "起始节点和结束节点的编号必须在0到" + (nodes.size() - 1) + "之间");
        }

        super.runAlgorithm();

        dijkstra = new Dijkstra(nodes, edges, s, t);
        super.selectEdges = dijkstra.executeDijkstra();
        super.outputTextArea.setText(ansToString());
    }

    private String ansToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("最短路径数组:\n");
        stringBuilder.append(String.format("%-4s |", ""));
        for (int i = 0; i < dijkstra.getDistance().size(); i++) {
            stringBuilder.append(String.format("%-4s", i));
        }
        stringBuilder.append("\n----");
        for (int i = 0; i < dijkstra.getDistance().size(); i++) {
            stringBuilder.append("----");
        }
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%-4s |", dijkstra.getS()));
        for (int i = 0; i < dijkstra.getDistance().size(); i++) {
            if (dijkstra.getDistance().get(i) == Integer.MAX_VALUE) {
                stringBuilder.append(String.format("%-4s", "INF"));
            } else {
                stringBuilder.append(String.format("%-4s", dijkstra.getDistance().get(i)));
            }
        }
        stringBuilder.append("\n最短路径长度为:\n");
        if (dijkstra.getDistance().get(dijkstra.getT()) == Integer.MAX_VALUE) {
            stringBuilder.append("INF");
        } else {
            stringBuilder.append(String.format("%-4s", dijkstra.getDistance().get(dijkstra.getT())));
        }
        return stringBuilder.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        return dijkstra.getDistance().get(dijkstra.getT());
    }
}
