package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Hungarian;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

public class HungarianPanel extends AlgorithmViewPanel {
    private Hungarian hungarian;
    public HungarianPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        graphType = "bipartite";
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();
        hungarian = new Hungarian(nodes, edges);
        super.selectEdges = hungarian.executeHungarian();
        System.out.println(hungarian.getMatchedEdges());
        System.out.println(hungarian.getUSize());
    }

    @Override
    protected int getAlgorithmValue() {
        return hungarian.getMatchedEdges().size();
    }
}
