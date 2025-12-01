package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;

public class KruskalPanel extends AlgorithmViewPanel {
    protected Kruskal kruskal;

    public KruskalPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        super.selectEdges.clear();
        kruskal = new Kruskal(nodes, edges);
        super.selectEdges = kruskal.executeKruskal();
    }

    @Override
    protected int getAlgorithmValue() {
        return kruskal.getMinValue();
    }
}
