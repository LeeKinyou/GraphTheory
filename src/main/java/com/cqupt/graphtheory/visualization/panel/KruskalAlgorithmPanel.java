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

    protected Kruskal kruskal;

    public KruskalAlgorithmPanel(MainAppFrame parent) {
        super(parent, "Kruskal算法");
        mstEdges = new ArrayList<>();
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        mstEdges.clear();
        kruskal = new Kruskal(nodes, edges);
        mstEdges = kruskal.executeKruskal();
    }

    @Override
    protected void runPreStep() {
        if (stepCounter <= 0) {
            JOptionPane.showMessageDialog(this, "已经到头了");
            return;
        }
        super.runPreStep();
        graphPanel.repaint();
    }

    @Override
    protected void runNextStep() {
        if (stepCounter >= mstEdges.size()) {
            JOptionPane.showMessageDialog(this, "算法运行结束！\n" +
                    " 最小边权和为：" + kruskal.getMinValue());
            return;
        }
        super.runNextStep();
        graphPanel.repaint();
    }

    @Override
    protected void drawGraph(Graphics g) {
        super.drawEdges(g, mstEdges, super.stepCounter);
        super.drawNodes(g);
    }

}
