package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Hungarian;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

public class HungarianPanel extends AlgorithmViewPanel {
    private Hungarian hungarian;
    public HungarianPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        graphType = "bipartite";
        isDrawNumber = false;
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();
        hungarian = new Hungarian(nodes, edges);
        super.selectEdges = hungarian.executeHungarian();
        super.outputTextArea.setText(ansToString());
    }

    private String ansToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("匹配情况:\n");
        for (Edge edge : hungarian.getMatchedEdges()) {
            stringBuilder.append(String.format("%-4s", edge.getFrom().getId()));
            stringBuilder.append(String.format("%-4s", edge.getTo().getId()));
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n最大匹配点数为: ").append(hungarian.getMatchedEdges().size() * 2);
        return stringBuilder.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        return hungarian.getMatchedEdges().size() * 2;
    }
}
