package com.cqupt.graphtheory.visualization.panel;

import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;

/**
 * 算法选择面板
 */
public class AlgorithmSelectionPanel extends JPanel {

    private final MainAppFrame parentFrame;

    public AlgorithmSelectionPanel(MainAppFrame parent) {
        this.parentFrame = parent;
        setLayout(new GridLayout(3, 3, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttonLabels = {
                "Kruskal algorithm", "Prim algorithm", "TearCycle algorithm",
                "Dijkstra algorithm", "Floyd algorithm", "Floyd-Warshall algorithm",
                "Hungarian algorithm", "Kuhn-Munkres algorithm", "exit"
        };
        String[] viewNames = {
                MainAppFrame.VIEW_KRUSKAL, MainAppFrame.VIEW_PRIM, MainAppFrame.VIEW_TEAR_CYCLE,
                MainAppFrame.VIEW_DIJKSTRA, MainAppFrame.VIEW_FLOYD, MainAppFrame.VIEW_FLOYD_WARSHALL,
                MainAppFrame.VIEW_HUNGARIAN, MainAppFrame.VIEW_KUHN_MUNKRES
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            if (i < viewNames.length) {
                final String targetView = viewNames[i];
                button.addActionListener(e -> parentFrame.showView(targetView));
            } else {
                // Special case for the exit button
                button.addActionListener(e -> System.exit(0));
            }
            add(button);
        }
    }
}
