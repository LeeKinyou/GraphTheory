package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.algorithm.util.MSTGeneration;
import com.cqupt.graphtheory.algorithm.util.UnionFind;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class KruskalAlgorithmPanel extends AlgorithmViewPanel {
    protected ArrayList<Edge> mstEdges;

    public KruskalAlgorithmPanel(MainAppFrame parent) {
        super(parent, "Kruskal算法");
        mstEdges = new ArrayList<>();
    }

    @Override
    protected void runAlgorithm() {
        if (nodes == null || edges == null || nodes.isEmpty() || edges.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成图！");
            return;
        }
        mstEdges.clear();

        Kruskal kruskal = new Kruskal(nodes, edges);
        mstEdges = kruskal.executeKruskal();

        graphPanel.repaint();
    }

    @Override
    protected void drawGraph(Graphics g) {
        super.drawEdges(g, mstEdges);
        super.drawNodes(g);
        mstEdges.clear();
    }

}
