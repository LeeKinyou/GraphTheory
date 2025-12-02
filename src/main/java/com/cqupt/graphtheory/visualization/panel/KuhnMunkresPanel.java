package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Hungarian;
import com.cqupt.graphtheory.algorithm.KuhnMunkres;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

public class KuhnMunkresPanel extends AlgorithmViewPanel {
    private KuhnMunkres kuhnMunkres;
    public KuhnMunkresPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "bipartite";
    }

    @Override
    protected void runAlgorithm() {
        super.runAlgorithm();
        kuhnMunkres = new KuhnMunkres(nodes, edges);
        super.selectEdges = kuhnMunkres.executeKuhnMunkres();
        super.outputTextArea.setText(ansToString());
    }

    private String ansToString() {
        StringBuilder stringBuilder = new StringBuilder();
        int sum = 0;
        stringBuilder.append("匹配情况:\n");
        for (Edge edge : kuhnMunkres.getMatchedEdges()) {
            stringBuilder.append(String.format("%-4s", edge.getFrom().getId()));
            stringBuilder.append(String.format("%-4s", edge.getTo().getId()));
            stringBuilder.append("\n");
            sum += edge.getWeight();
        }
        stringBuilder.append("\n最大匹配点数为: ").append(kuhnMunkres.getMatchedEdges().size() * 2);
        stringBuilder.append("\n最大匹配权数为: ").append(sum);
        return stringBuilder.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        return kuhnMunkres.getMatchedEdges().size() * 2;
    }
}
