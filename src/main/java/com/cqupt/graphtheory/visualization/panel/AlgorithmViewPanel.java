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
    protected JButton randomGenerateButton;
    protected JButton inputGenerateButton;
    protected JButton runAlgorithmButton;
    protected JButton preButton;
    protected JButton nextButton;
    protected JButton backButton;
    protected JLabel stepCounterLabel;
    
    // 图形显示区域
    protected JPanel graphPanel;

    protected JTextArea inputTextArea;
    protected JTextArea outputTextArea;
    protected JScrollPane inputScrollPane;
    protected JScrollPane outputScrollPane;

    protected ArrayList<Node> nodes;
    protected ArrayList<Edge> edges;
    protected ArrayList<Edge> selectEdges;
    protected Integer stepCounter = -1;
    protected Boolean isDrawNumber = true;
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
        randomGenerateButton = new JButton("随机生成图");
        inputGenerateButton = new JButton("输入生成图");
        runAlgorithmButton = new JButton("运行算法");
        preButton = new JButton("上一步");
        nextButton = new JButton("下一步");
        backButton = new JButton("返回主页面");
        stepCounterLabel = new JLabel("步骤: 0");

        // 初始化输入输出组件
        inputTextArea = new JTextArea(10, 50);

        // 添加焦点监听器到初始化部分
        inputTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (inputTextArea.getForeground() == Color.GRAY) {
                    inputTextArea.setText("");
                    inputTextArea.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (inputTextArea.getText().isEmpty()) {
                    inputTextArea.setText("e.g.请输入邻接矩阵（不要加这两行）:\nadjacencyMatrix[i][j]代表从节点i到节点j有一条权为w的边\n4\n0 2 3 0\n2 0 0 0\n3 0 0 1\n0 0 1 0");
                    inputTextArea.setForeground(Color.GRAY);
                }
            }
        });
        inputTextArea.setText("e.g.请输入邻接矩阵（不要加这两行）:\nadjacencyMatrix[i][j]代表从节点i到节点j有一条权为w的边\n4\n0 2 3 0\n2 0 0 0\n3 0 0 1\n0 0 1 0");
        inputTextArea.setForeground(Color.GRAY);

        outputTextArea = new JTextArea(10, 50);
        outputTextArea.setEditable(false);

        inputScrollPane = new JScrollPane(inputTextArea);
        outputScrollPane = new JScrollPane(outputTextArea);

        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setPreferredSize(new Dimension(600, 400));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());

        // 顶部控制面板保持不变
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(new JLabel(algorithmName + "|"));
        controlPanel.add(new JLabel("节点数:"));
        controlPanel.add(nodeCountField);
        controlPanel.add(new JLabel("边数:"));
        controlPanel.add(edgeCountField);
        if (algorithmName.equals("DijkstraView") || algorithmName.equals("FloydView")
                || algorithmName.equals("FloydWarshallView")) {
            controlPanel.add(new JLabel("起始节点:"));
            controlPanel.add(startNodeField);
            controlPanel.add(new JLabel("结束节点:"));
            controlPanel.add(endNodeField);
        }
        controlPanel.add(randomGenerateButton);
        controlPanel.add(inputGenerateButton);
        controlPanel.add(runAlgorithmButton);
        controlPanel.add(preButton);
        controlPanel.add(nextButton);
        controlPanel.add(backButton);
        controlPanel.add(stepCounterLabel);
        add(controlPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(graphPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("输入区域"));
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("输出区域"));
        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        rightPanel.add(inputPanel);
        rightPanel.add(outputPanel);

        // 使用JSplitPane来分割左右面板
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(1.0);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void addEventListeners() {
        randomGenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateGraph();
            }
        });

        inputGenerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputGenerateGraph();
            }
        });

        runAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAlgorithm();
            }
        });

        preButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runPreStep();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runNextStep();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showView(MainAppFrame.VIEW_SELECTION);
            }
        });
    }

    protected void inputGenerateGraph() {
        try {
            String[] lines = inputTextArea.getText().split("\n");
            if (lines.length < 1) {
                JOptionPane.showMessageDialog(this, "输入格式错误！");
                return;
            }

            if (inputTextArea.getForeground() == Color.GRAY) {
                JOptionPane.showMessageDialog(this, "请输入有效数据！");
                return;
            }

            int nodeCount = Integer.parseInt(lines[0]);
            if (nodeCount <= 0) {
                JOptionPane.showMessageDialog(this, "节点数必须大于0！");
                return;
            }

            if (lines.length < nodeCount + 1) {
                JOptionPane.showMessageDialog(this, "输入矩阵行数不足！");
                return;
            }

            nodes.clear();
            edges.clear();
            selectEdges.clear();
            stepCounter = -1;

            nodes = GraphGenerationFactory.generateNodes(graphType, nodeCount, graphPanel);

            for (int i = 1; i <= nodeCount; i++) {
                String[] weights = lines[i].trim().split("\\s+");
                if (weights.length != nodeCount) {
                    JOptionPane.showMessageDialog(this, "第" + i + "行权重数量错误！");
                    return;
                }

                for (int j = 0; j < nodeCount; j++) {
                    int weight = Integer.parseInt(weights[j]);
                    if (i-1 < j && weight >= 0) {
                        Edge edge = new Edge(nodes.get(i-1), nodes.get(j), weight);
                        edges.add(edge);
                    }
                }
            }

            graphPanel.repaint();
            JOptionPane.showMessageDialog(this, "图生成成功！");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字！");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "输入格式错误：" + ex.getMessage());
        }

    }

    protected void generateGraph() {
        try {
            int nodeCount = Integer.parseInt(nodeCountField.getText());
            int edgeCount = Integer.parseInt(edgeCountField.getText());

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
            if (edgeCount < nodeCount - 1 && graphType.equals("tree")) {
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
                    " 最终结果为：" + getAlgorithmValue());
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
        if (isDrawNumber)
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
