package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Dijkstra;
import com.cqupt.graphtheory.algorithm.Floyd;
import com.cqupt.graphtheory.algorithm.FloydWarshall;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FloydPanel extends AlgorithmViewPanel {
    private Floyd floyd;

    private ArrayList<ArrayList<Integer>> distance;

    public FloydPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        graphType = "graph";
    }

    @Override
    protected void runAlgorithm() {
        int s, t;
        try {
            s = Integer.parseInt(startNodeField.getText());
            t = Integer.parseInt(endNodeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的节点编号.", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (s < 0 || s >= nodes.size() || t < 0 || t >= nodes.size()) {
            JOptionPane.showMessageDialog(this, "起始或结束节点编号超出范围 (0-" + (nodes.size() - 1) + ").", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        super.runAlgorithm();

        floyd= new Floyd(nodes, edges, s, t);
        distance = floyd.executeFloyd();

        outputTextArea.setText(formatMatrix(distance));

        Dijkstra dijkstra = new Dijkstra(nodes, edges, s, t);
        super.selectEdges = dijkstra.executeDijkstra();
    }

    private String formatMatrix(ArrayList<ArrayList<Integer>> matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("距离矩阵:\n");
        int numNodes = matrix.size();

        sb.append(String.format("%-4s |", ""));
        for (int i = 0; i < numNodes; i++) {
            sb.append(String.format(" %-4s", i));
        }
        sb.append("\n");

        sb.append("----");
        for (int i = 0; i < numNodes; i++) {
            sb.append("-----");
        }
        sb.append("\n");

        for (int i = 0; i < numNodes; i++) {
            sb.append(String.format("%-4s |", i));
            for (int j = 0; j < numNodes; j++) {
                Integer value = matrix.get(i).get(j);
                String cell = (value == Integer.MAX_VALUE) ? "INF" : value.toString();
                sb.append(String.format(" %-4s", cell));
            }
            sb.append("\n");
        }
        sb.append("\n最短路径长度为:\n");
        sb.append(String.format("%-4s", distance.get(floyd.getS()).get(floyd.getT())));
        return sb.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        if (distance == null || floyd == null) return 0;
        return distance.get(floyd.getS()).get(floyd.getT());
    }
}
