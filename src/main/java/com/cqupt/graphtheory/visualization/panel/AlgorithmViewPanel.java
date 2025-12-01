package com.cqupt.graphtheory.visualization.panel;


import com.cqupt.graphtheory.algorithm.Kruskal;
import com.cqupt.graphtheory.algorithm.util.CircleGeneration;
import com.cqupt.graphtheory.algorithm.util.GraphGenerationFactory;
import com.cqupt.graphtheory.algorithm.util.TreeGeneration;
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
    protected JTextField startNodeField;
    protected JTextField endNodeField;
    protected JButton generateButton;
    protected JButton runAlgorithmButton;
    protected JButton preButton;
    protected JButton nextButton;
    protected JButton backButton;
    protected JLabel stepCounterLabel;
    
    // 图形显示区域
    protected JPanel graphPanel;

    protected ArrayList<Node> nodes;
    protected ArrayList<Edge> edges;
    protected ArrayList<Edge> selectEdges;
    protected Integer stepCounter = -1;
    protected String graphType;
    
    public AlgorithmViewPanel(MainAppFrame parent, String algorithmName) {
        this.parentFrame = parent;
        this.algorithmName = algorithmName;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        selectEdges = new ArrayList<>();
        initializeComponents();
        setupLayout();
        addEventListeners();
    }
    
    private void initializeComponents() {
        // 初始化输入组件
        nodeCountField = new JTextField(5);
        edgeCountField = new JTextField(5);
        startNodeField = new JTextField(5);
        endNodeField = new JTextField(5);
        generateButton = new JButton("生成图");
        runAlgorithmButton = new JButton("运行算法");
        preButton = new JButton("上一步");
        nextButton = new JButton("下一步");
        backButton = new JButton("返回主页面");
        stepCounterLabel = new JLabel("步骤: 0");
        
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
        controlPanel.add(new JLabel(algorithmName + "|"));
        controlPanel.add(new JLabel("节点数:"));
        controlPanel.add(nodeCountField);
        controlPanel.add(new JLabel("边数:"));
        controlPanel.add(edgeCountField);
        if (algorithmName.equals("DijkstraView") || algorithmName.equals("FloydView")) {
            controlPanel.add(new JLabel("起始节点:"));
            controlPanel.add(startNodeField);
            controlPanel.add(new JLabel("结束节点:"));
            controlPanel.add(endNodeField);
        }
        controlPanel.add(generateButton);
        controlPanel.add(runAlgorithmButton);
        controlPanel.add(preButton);
        controlPanel.add(nextButton);
        controlPanel.add(backButton);
        controlPanel.add(stepCounterLabel);

        
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

        // 上一步按钮事件
        preButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runPreStep();
            }
        });

        // 下一步按钮事件
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runNextStep();
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
            if (edgeCount < nodeCount - 1) {
                JOptionPane.showMessageDialog(this, "边数过少！至少需要" + (nodeCount - 1) + "条边。");
                return;
            }

            nodes.clear();
            edges.clear();
            selectEdges.clear();
            stepCounter = -1;
            nodes = GraphGenerationFactory.generateNodes(graphType, nodeCount, graphPanel);
            edges = GraphGenerationFactory.generateEdges(graphType, edgeCount, nodes);

            graphPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！");
        }
    }

    protected void runAlgorithm() {
        if (nodes == null || edges == null || nodes.isEmpty() || edges.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先生成图！");
            return;
        }
        JOptionPane.showMessageDialog(this, "算法运行成功！");
        stepCounter = 0;
    }

    protected void runPreStep() {
        if (stepCounter <= 0) {
            JOptionPane.showMessageDialog(this, "已经到头了");
            return;
        }
        stepCounter--;
        stepCounterLabel.setText("步骤: " + stepCounter);
        graphPanel.repaint();
        graphPanel.revalidate();
    }

    protected void runNextStep() {
        if (stepCounter >= selectEdges.size()) {
            JOptionPane.showMessageDialog(this, "算法运行结束！\n" +
                    " 最小边权和为：" + getAlgorithmValue());
            return;
        }
        if (stepCounter == -1) {
            JOptionPane.showMessageDialog(this, "请先运行算法！");
            return;
        }
        stepCounter++;
        stepCounterLabel.setText("步骤: " + stepCounter);
        graphPanel.repaint();
        graphPanel.revalidate();
    }

    protected int getAlgorithmValue() {
        return -1;
    }

    protected void drawGraph(Graphics g) {
        drawEdges(g);
        drawNodes(g);
    }

    // 绘制边
    protected void drawEdges(Graphics g) {
        g.setColor(Color.BLACK);
        for (Edge edge : edges) {
            drawEdge(g, edge);
        }

        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < stepCounter && i < selectEdges.size(); i++) {
            drawEdge(g2d, selectEdges.get(i));
        }
    }

    // 绘制单条边
    protected void drawEdge(Graphics g, Edge edge) {
        g.drawLine(edge.getFrom().getX(), edge.getFrom().getY(), edge.getTo().getX(), edge.getTo().getY());

        // 绘制权重
        int midX = (edge.getFrom().getX() + edge.getTo().getX()) / 2;
        int midY = (edge.getFrom().getY() + edge.getTo().getY()) / 2;
        g.drawString(String.valueOf(edge.getWeight()), midX, midY);
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
