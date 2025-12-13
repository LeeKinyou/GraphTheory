package com.cqupt.graphtheory.visualization.frame;

import com.cqupt.graphtheory.visualization.panel.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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


    public MainAppFrame() {
        setTitle("图论大作业");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        URL iconURL = getClass().getResource("/icon/icon.png");
        if (iconURL != null) {
            setIconImage(new ImageIcon(iconURL).getImage());
        }

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        TopPanel topPanel = new TopPanel();
        AlgorithmSelectionPanel selectionPanel = new AlgorithmSelectionPanel(this);

        cardPanel.add(selectionPanel, VIEW_SELECTION);

        cardPanel.add(new KruskalPanel(this, VIEW_KRUSKAL), VIEW_KRUSKAL);
        cardPanel.add(new PrimPanel(this, VIEW_PRIM), VIEW_PRIM);
        cardPanel.add(new TearCirclePanel(this, VIEW_TEAR_CYCLE), VIEW_TEAR_CYCLE);
        cardPanel.add(new DijkstraPanel(this, VIEW_DIJKSTRA), VIEW_DIJKSTRA);
        cardPanel.add(new FloydPanel(this, VIEW_FLOYD), VIEW_FLOYD);
        cardPanel.add(new FloydWarshallPanel(this, VIEW_FLOYD_WARSHALL), VIEW_FLOYD_WARSHALL);
        cardPanel.add(new HungarianPanel(this, VIEW_HUNGARIAN), VIEW_HUNGARIAN);
        cardPanel.add(new KuhnMunkresPanel(this, VIEW_KUHN_MUNKRES), VIEW_KUHN_MUNKRES);

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
