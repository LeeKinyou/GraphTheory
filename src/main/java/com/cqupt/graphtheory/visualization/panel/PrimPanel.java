package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Prim;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;

public class PrimPanel extends AlgorithmViewPanel {

    protected Prim prim;

    public PrimPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "tree";

    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();

        super.selectEdges.clear();
        prim = new Prim(nodes, edges);
        super.selectEdges = prim.executePrim();
        super.outputTextArea.setText(ansToString());
    }

    private String ansToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("最小生成树的边:\n");
        for (Edge edge : prim.getMstEdges()) {
            stringBuilder.append(String.format("  %d -> %d  (权重: %d)\n",
                    edge.getFrom().getId(), edge.getTo().getId(), edge.getWeight()));
        }
        stringBuilder.append("\n最小生成树总权重: ").append(prim.getMinValue());
        return stringBuilder.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        return prim.getMinValue();
    }
}
