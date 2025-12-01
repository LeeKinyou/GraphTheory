package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Prim;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;

public class PrimPanel extends AlgorithmViewPanel {

    protected Prim prim;

    public PrimPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        super.selectEdges.clear();
        prim = new Prim(nodes, edges);
        super.selectEdges = prim.executePrim();
    }

    @Override
    protected int getAlgorithmValue() {
        return prim.getMinValue();
    }
}
