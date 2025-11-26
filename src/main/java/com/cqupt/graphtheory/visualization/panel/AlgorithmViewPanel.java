package com.cqupt.graphtheory.visualization.panel;


import com.cqupt.graphtheory.algorithm.util.MSTGeneration;
import com.cqupt.graphtheory.entity.Edge;
import com.cqupt.graphtheory.entity.Node;
import com.cqupt.graphtheory.visualization.frame.MainAppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AlgorithmViewPanel extends JPanel {
    protected MainAppFrame parentFrame;
    protected String algorithmName;
    
    // 控制面板组件
    protected JTextField nodeCountField;
    protected JTextField edgeCountField;
    protected JButton generateButton;
    protected JButton runAlgorithmButton;
    protected JButton backButton;
    
    // 图形显示区域
    protected JPanel graphPanel;

    protected ArrayList<Node> nodes;
    protected ArrayList<Edge> edges;
    
    public AlgorithmViewPanel(MainAppFrame parent, String algorithmName) {
        this.parentFrame = parent;
        this.algorithmName = algorithmName;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        initializeComponents();
        setupLayout();
        addEventListeners();
    }
    
    private void initializeComponents() {
        // 初始化输入组件
        nodeCountField = new JTextField(5);
        edgeCountField = new JTextField(5);
        generateButton = new JButton("生成图");
        runAlgorithmButton = new JButton("运行算法");
        backButton = new JButton("返回主页面");
        
        // 初始化图形显示区域
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 这里绘制图结构
                drawGraph(g);
            }
        };
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setPreferredSize(new Dimension(600, 400));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel("节点数:"));
        controlPanel.add(nodeCountField);
        controlPanel.add(new JLabel("边数:"));
        controlPanel.add(edgeCountField);
        controlPanel.add(generateButton);
        controlPanel.add(runAlgorithmButton);
        controlPanel.add(backButton);
        
        add(controlPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        // 生成图按钮事件
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateGraph();
            }
        });
        
        // 运行算法按钮事件
        runAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAlgorithm();
            }
        });
        
        // 返回按钮事件
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showView(MainAppFrame.VIEW_SELECTION);
            }
        });
    }

    protected void generateGraph() {
        try {
            int nodeCount = Integer.parseInt(nodeCountField.getText());
            int edgeCount = Integer.parseInt(edgeCountField.getText());

            // 验证输入合法性
            if (nodeCount <= 0 || edgeCount < 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的节点数和边数！");
                return;
            }

            // 边数不能超过完全图的边数
            int maxEdges = nodeCount * (nodeCount - 1) / 2;
            if (edgeCount > maxEdges) {
                JOptionPane.showMessageDialog(this, "边数过多！最多只能有" + maxEdges + "条边。");
                return;
            }

            // 清空之前的数据
            nodes.clear();
            edges.clear();

            // 生成节点
            nodes = MSTGeneration.generateNodes(nodeCount, graphPanel);
            // 生成边
            edges = MSTGeneration.generateEdges(edgeCount, nodes);

            // 重新绘制图形
            graphPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！");
        }
    }

    protected void runAlgorithm() {
    }

    protected void drawGraph(Graphics g) {
    }

    // 绘制边
    protected void drawEdges(Graphics g, ArrayList<Edge> selectEdges) {
        g.setColor(Color.BLACK);
        for (Edge edge : edges) {
            boolean isMstEdge = selectEdges.contains(edge);
            if (!isMstEdge) {
                drawEdge(g, edge, selectEdges);
            }
        }

        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2.0f));
        for (Edge edge : selectEdges) {
            drawEdge(g2d, edge, selectEdges);
        }
    }

    // 绘制单条边
    protected void drawEdge(Graphics g, Edge edge, ArrayList<Edge> selectEdges) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke();

        if (selectEdges.contains(edge)) {
            g2d.setStroke(new BasicStroke(2.0f));
        }

        g.drawLine(edge.getFrom().getX(), edge.getFrom().getY(), edge.getTo().getX(), edge.getTo().getY());

        // 绘制权重
        int midX = (edge.getFrom().getX() + edge.getTo().getX()) / 2;
        int midY = (edge.getFrom().getY() + edge.getTo().getY()) / 2;
        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(edge.getWeight()), midX, midY);

        // 恢复颜色和线宽
        g.setColor(selectEdges.contains(edge) ? Color.RED : Color.BLACK);
        g2d.setStroke(originalStroke);
    }

    // 绘制节点
    protected void drawNodes(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Node node : nodes) {
            g.fillOval(node.getX() - 15, node.getY() - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.drawOval(node.getX() - 15, node.getY() - 15, 30, 30);
            g.drawString(String.valueOf(node.getId()), node.getX() - 5, node.getY() + 5);
            g.setColor(Color.YELLOW);
        }
    }
}
