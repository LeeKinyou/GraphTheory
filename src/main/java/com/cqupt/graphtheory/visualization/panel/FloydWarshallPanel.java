package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.algorithm.Dijkstra;
import com.cqupt.graphtheory.algorithm.FloydWarshall;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FloydWarshallPanel extends AlgorithmViewPanel {
    private FloydWarshall floydWarshall;
    private ArrayList<ArrayList<Integer>> distance;

    public FloydWarshallPanel(MainAppFrame parent, String algorithmName) {
        super(parent, algorithmName);
        super.graphType = "tree";
    }

    @Override
    protected void runAlgorithm() {
        super.selectEdges.clear();
        int s, t;
        try {
            s = Integer.parseInt(startNodeField.getText());
            t = Integer.parseInt(endNodeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的节点编号.", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (s < 0 || s >= nodes.size() || t < 0 || t >= nodes.size()) {
            JOptionPane.showMessageDialog(this, "起始或结束节点编号超出范围 (0-" + (nodes.size() - 1) + ").", "输入错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        super.runAlgorithm();

        floydWarshall = new FloydWarshall(nodes, edges, s, t);
        distance = floydWarshall.executeFloydWarshall();

        // Create a JTextArea with a monospaced font to display the matrix
        JTextArea textArea = new JTextArea(formatMatrix(distance));
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);

        // Put the JTextArea in a JScrollPane to handle larger matrices
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300)); // Adjust size as needed

        // Show the custom component in JOptionPane
        JOptionPane.showMessageDialog(this, scrollPane, "距离矩阵 (Floyd-Warshall)", JOptionPane.INFORMATION_MESSAGE);

        // Use Dijkstra to find and highlight the specific path from s to t
        Dijkstra dijkstra = new Dijkstra(nodes, edges, s, t);
        super.selectEdges = dijkstra.executeDijkstra();
    }

    private String formatMatrix(ArrayList<ArrayList<Integer>> matrix) {
        StringBuilder sb = new StringBuilder();
        int numNodes = matrix.size();

        // Header row for column indices
        sb.append(String.format("%-4s |", ""));
        for (int i = 0; i < numNodes; i++) {
            sb.append(String.format(" %-4s", i));
        }
        sb.append("\n");

        // Separator line
        sb.append("----");
        for (int i = 0; i < numNodes; i++) {
            sb.append("-----");
        }
        sb.append("\n");

        // Matrix content with row indices
        for (int i = 0; i < numNodes; i++) {
            sb.append(String.format("%-4s |", i)); // Row index
            for (int j = 0; j < numNodes; j++) {
                Integer value = matrix.get(i).get(j);
                String cell = (value == Integer.MAX_VALUE) ? "INF" : value.toString();
                sb.append(String.format(" %-4s", cell));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    protected int getAlgorithmValue() {
        if (distance == null || floydWarshall == null) return 0;
        return distance.get(floydWarshall.getS()).get(floydWarshall.getT());
    }
}
