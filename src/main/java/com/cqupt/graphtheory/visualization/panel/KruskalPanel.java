package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;

public class KruskalPanel extends AlgorithmViewPanel {
    protected Kruskal kruskal;

    public KruskalPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "tree";
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        super.selectEdges.clear();
        kruskal = new Kruskal(nodes, edges);
        super.selectEdges = kruskal.executeKruskal();
        super.outputTextArea.setText(ansToString());
    }

    private String ansToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("最小生成树的边:\n");
        for (Edge edge : kruskal.getMstEdges()) {
            stringBuilder.append(String.format("  %d -> %d  (权重: %d)\n",
                    edge.getFrom().getId(),
                    edge.getTo().getId(),
                    edge.getWeight()));
        }
        stringBuilder.append("\n最小生成树的总权重: ").append(kruskal.getMinValue());
        return stringBuilder.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        return kruskal.getMinValue();
    }
}
