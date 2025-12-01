package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Dijkstra;
import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;

public class DijkstraPanel extends AlgorithmViewPanel {
    private Dijkstra dijkstra;

    public DijkstraPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
    }

    @Override
    protected void runAlgorithm() {
        super.selectEdges.clear();
        int s = Integer.parseInt(startNodeField.getText());
        int t = Integer.parseInt(endNodeField.getText());
        if (s < 0 || s >= nodes.size() || t < 0 || t >= nodes.size()) {
            JOptionPane.showMessageDialog(this, "起始节点和结束节点的编号必须在0到" + (nodes.size() - 1) + "之间");
        }

        super.runAlgorithm();

        dijkstra = new Dijkstra(nodes, edges, s, t);
        super.selectEdges = dijkstra.executeDijkstra();
    }

    @Override
    protected int getAlgorithmValue() {
        return dijkstra.getDistance().get(dijkstra.getT());
    }
}
