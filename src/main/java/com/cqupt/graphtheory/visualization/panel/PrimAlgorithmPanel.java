package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.algorithm.Prim;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PrimAlgorithmPanel extends AlgorithmViewPanel {

    protected ArrayList<Edge> mstEdges;

    protected Prim prim;

    public PrimAlgorithmPanel(MainAppFrame parent) {
        super(parent, "Prim算法");
        mstEdges = new ArrayList<>();
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        mstEdges.clear();
        prim = new Prim(nodes, edges);
        mstEdges = prim.executePrim();
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
                    " 最小边权和为：" + prim.getMinValue());
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
