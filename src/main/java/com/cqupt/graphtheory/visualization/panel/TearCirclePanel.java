package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Prim;
import com.cqupt.graphtheory.algorithm.TearCircle;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

public class TearCirclePanel extends AlgorithmViewPanel {
    public TearCirclePanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "tree";
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        super.selectEdges.clear();
        TearCircle tearCircle = new TearCircle(nodes, edges);
        super.selectEdges = tearCircle.executeTearCircle();
    }

    @Override
    protected int getAlgorithmValue() {
        int value = 0, totalWeight = 0;
        for (Edge edge : edges) {
            totalWeight += edge.getWeight();
        }
        for (Edge edge : selectEdges) {
            value += edge.getWeight();
        }
        return totalWeight - value;
    }
}
