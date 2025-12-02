package com.cqupt.graphtheory.visualization.panel;

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
        super.outputTextArea.setText(ansToString());
    }

    private String ansToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("删除的边:\n");
        for (Edge edge : selectEdges) {
            stringBuilder.append(String.format("%-4s -> %-4s (weight %-4s)\n",
                    edge.getFrom().getId(), edge.getTo().getId(), edge.getWeight()));
        }
        stringBuilder.append("最小生成树的边权为: ").append(getAlgorithmValue()).append("\n");
        return stringBuilder.toString();
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
