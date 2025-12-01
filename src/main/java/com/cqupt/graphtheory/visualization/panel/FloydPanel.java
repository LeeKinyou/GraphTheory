package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Dijkstra;
import com.cqupt.graphtheory.algorithm.Floyd;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FloydPanel extends AlgorithmViewPanel {
    private Floyd floyd;

    private ArrayList<ArrayList<Integer>> distance;

    public FloydPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "tree";
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

        floyd = new Floyd(nodes, edges, s, t);
        Dijkstra dijkstra = new Dijkstra(nodes, edges, s, t);
        distance = floyd.executeFloyd();
        JOptionPane.showMessageDialog(this, formatMatrix(distance));
        super.selectEdges = dijkstra.executeDijkstra();
    }

    private String formatMatrix(ArrayList<ArrayList<Integer>> matrix) {
        StringBuilder sb = new StringBuilder("距离矩阵:\n");
        for (ArrayList<Integer> row : matrix) {
            for (Integer value : row) {
                if (value == Integer.MAX_VALUE) {
                    sb.append("INF ");
                } else {
                    sb.append(value).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        return distance.get(floyd.getS()).get(floyd.getT());
    }
}
