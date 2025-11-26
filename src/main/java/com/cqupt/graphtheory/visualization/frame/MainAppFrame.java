package com.cqupt.graphtheory.visualization.frame;

import com.cqupt.graphtheory.visualization.panel.AlgorithmSelectionPanel;
import com.cqupt.graphtheory.visualization.panel.KruskalAlgorithmPanel;
import com.cqupt.graphtheory.visualization.panel.TopPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 主框架
 */
public class MainAppFrame extends JFrame {

    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public static final String VIEW_SELECTION = "SelectionView";
    public static final String VIEW_KRUSKAL = "KruskalView";
    public static final String VIEW_PRIM = "PrimView";
    public static final String VIEW_TEAR_CYCLE = "TearCycleView";
    public static final String VIEW_DIJKSTRA = "DijkstraView";
    public static final String VIEW_FLOYD = "FloydView";
    public static final String VIEW_FLOYD_WARSHALL = "FloydWarshallView";
    public static final String VIEW_HUNGARIAN = "HungarianView";
    public static final String VIEW_KUHN_MUNKRES = "KuhnMunkresView";
    public static final String VIEW_EXIT = "ExitView";


    public MainAppFrame() {
        setTitle("图论大作业");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        TopPanel topPanel = new TopPanel();
        AlgorithmSelectionPanel selectionPanel = new AlgorithmSelectionPanel(this);

        cardPanel.add(selectionPanel, VIEW_SELECTION);

        cardPanel.add(new KruskalAlgorithmPanel(this), VIEW_KRUSKAL);
        cardPanel.add(new JLabel("Prim算法内容", SwingConstants.CENTER), VIEW_PRIM);
        cardPanel.add(new JLabel("破圈算法内容", SwingConstants.CENTER), VIEW_TEAR_CYCLE);
        cardPanel.add(new JLabel("Dijkstra算法内容", SwingConstants.CENTER), VIEW_DIJKSTRA);
        cardPanel.add(new JLabel("Floyd算法内容", SwingConstants.CENTER), VIEW_FLOYD);
        cardPanel.add(new JLabel("Floyd-Warshall算法内容", SwingConstants.CENTER), VIEW_FLOYD_WARSHALL);
        cardPanel.add(new JLabel("匈牙利算法内容", SwingConstants.CENTER), VIEW_HUNGARIAN);
        cardPanel.add(new JLabel("Kuhn-Munkres算法内容", SwingConstants.CENTER), VIEW_KUHN_MUNKRES);
        cardPanel.add(new JLabel("退出", SwingConstants.CENTER), VIEW_EXIT);

        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, VIEW_SELECTION);

        pack();
        setLocationRelativeTo(null);
    }

    public void showView(String viewName) {
        cardLayout.show(cardPanel, viewName);
    }
}